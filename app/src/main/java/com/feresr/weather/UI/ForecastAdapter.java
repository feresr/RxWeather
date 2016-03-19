package com.feresr.weather.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feresr.weather.R;
import com.feresr.weather.UI.viewholders.CurrentlyViewHolder;
import com.feresr.weather.UI.viewholders.DailyViewHolder;
import com.feresr.weather.UI.viewholders.DayViewHolder;
import com.feresr.weather.UI.viewholders.HourlyViewHolder;
import com.feresr.weather.UI.viewholders.UpdatedAtViewHolder;
import com.feresr.weather.UI.viewholders.WarningViewHolder;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.models.Currently;
import com.feresr.weather.models.Daily;
import com.feresr.weather.models.Day;
import com.feresr.weather.models.DisplayWeatherInfo;
import com.feresr.weather.models.Hour;
import com.feresr.weather.models.Hourly;
import com.feresr.weather.models.Warning;
import com.feresr.weather.utils.IconManager;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CURRENTLY = 0;
    private static final int HOURLY = 1;
    private static final int DAILY = 2;
    private static final int DAY = 3;
    private static final int WARNING = 4;
    private static final int UPDATED_AT = 5;

    private LayoutInflater inflater;
    private List<DisplayWeatherInfo> weatherInfo;
    private WeakReference<Context> context;
    private boolean celsius = true;
    private Typeface font;
    private long fetchTime;
    private ArrayList<Hour> hours;

    public ForecastAdapter(Context context) {
        super();
        this.context = new WeakReference<Context>(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
        this.weatherInfo = new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, "celsius");
        if (syncConnPref.equals("celsius")) {
            celsius = true;
        } else {
            celsius = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == weatherInfo.size()) {
            return UPDATED_AT;
        }
        DisplayWeatherInfo weatherInfoObject = weatherInfo.get(position);

        if (weatherInfoObject instanceof Currently) {
            return CURRENTLY;
        } else if (weatherInfoObject instanceof Hourly) {
            return HOURLY;
        } else if (weatherInfoObject instanceof Daily) {
            return DAILY;
        } else if (weatherInfoObject instanceof Day) {
            return DAY;
        } else if (weatherInfoObject instanceof Warning) {
            return WARNING;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CURRENTLY:
                view = this.inflater.inflate(R.layout.currently_view, parent, false);
                return new CurrentlyViewHolder(view, font);
            case HOURLY:
                view = this.inflater.inflate(R.layout.hourly_view, parent, false);
                return new HourlyViewHolder(view, new LinearLayoutManager(context.get()), context.get());
            case DAILY:
                view = this.inflater.inflate(R.layout.daily_view, parent, false);
                return new DailyViewHolder(view);
            case DAY:
                view = this.inflater.inflate(R.layout.day_view, parent, false);
                return new DayViewHolder(view, font);
            case WARNING:
                view = this.inflater.inflate(R.layout.warning_view, parent, false);
                return new WarningViewHolder(view);
            case UPDATED_AT:
                view = this.inflater.inflate(R.layout.updated_at, parent, false);
                return new UpdatedAtViewHolder(view);
            default:
                return null;
        }
    }


    public void addForecast(CityWeather cityWeather) {
        weatherInfo.add(cityWeather.getCurrently());
        weatherInfo.add(cityWeather.getHourly());

        this.hours = (ArrayList<Hour>) cityWeather.getHourly().getData();
        //dayForecastAdapter.addHourForecast(cityWeather.getHourly().getData());

        fetchTime = cityWeather.getFetchTime();

        weatherInfo.add(cityWeather.getDaily());
        for (int i = 0; i < cityWeather.getDaily().getDays().size(); i++) {
            if (i == 0) {
                cityWeather.getDaily().getDays().get(i).setIsToday(true);
            }

            if (i == 1) {
                cityWeather.getDaily().getDays().get(i).setIsTomorrow(true);
            }

            if (i == cityWeather.getDaily().getDays().size() - 1) {
                cityWeather.getDaily().getDays().get(i).setIsLastDayOfWeek(true);
            }

            weatherInfo.add(cityWeather.getDaily().getDays().get(i));
        }


        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case HOURLY:
                HourlyViewHolder labelViewHolder = (HourlyViewHolder) viewHolder;
                Hourly hourly = (Hourly) weatherInfo.get(position);
                labelViewHolder.description.setText(hourly.getSummary());
                labelViewHolder.view.setBackgroundColor(IconManager.getColorResource(hourly.getIcon(), context.get()));
                ((DayForecastAdapter) labelViewHolder.recyclerView.getAdapter()).setData(hours);

                break;
            case WARNING:
                WarningViewHolder warningViewHolder = (WarningViewHolder) viewHolder;
                Warning warning = (Warning) weatherInfo.get(position);
                warningViewHolder.title.setText(warning.getText());
                warningViewHolder.explanation.setText(warning.getExplanation());
                break;

            case CURRENTLY:
                CurrentlyViewHolder currentlyViewHolder = (CurrentlyViewHolder) viewHolder;
                Currently currently = (Currently) weatherInfo.get(position);

                currentlyViewHolder.main.setBackgroundColor(currently.getColor(context.get()));
                currentlyViewHolder.description.setText(currently.getSummary().toUpperCase());
                if (celsius) {
                    currentlyViewHolder.feelsLike.setValue(context.get().getString(R.string.degree, Math.round(currently.getApparentTemperature())));
                    currentlyViewHolder.temp.setText(context.get().getString(R.string.degree, Math.round(currently.getTemperature())));
                } else {
                    currentlyViewHolder.feelsLike.setValue(context.get().getString(R.string.degree, Math.round(currently.getApparentTemperature() * 1.8 + 32)));
                    currentlyViewHolder.temp.setText(context.get().getString(R.string.degree, Math.round(currently.getTemperature() * 1.8 + 32)));
                }
                currentlyViewHolder.humidity.setValue(Math.round(currently.getHumidity() * 100) + "%");
                currentlyViewHolder.wind.setValue(context.get().getString(R.string.km_h, Math.round(currently.getWindSpeed())));
                currentlyViewHolder.wind.setSubValue(currently.getWindBearingString());

                currentlyViewHolder.pressure.setValue(context.get().getString(R.string.hPa, Math.round(currently.getPressure())));
                currentlyViewHolder.clouds.setValue(Math.round(currently.getCloudCover() * 100) + "%");
                currentlyViewHolder.precipitation.setValue(Math.round(currently.getPrecipProbability() * 100) + "%");
                currentlyViewHolder.precipitation.setSubValue(new DecimalFormat("#.##").format(currently.getPrecipIntensity() * 100) + "cm");

                currentlyViewHolder.icon.setText(IconManager.getIconResource(currently.getIcon(), context.get()));

                break;
            case DAILY:
                DailyViewHolder dailyViewHolder = (DailyViewHolder) viewHolder;
                Daily daily = (Daily) weatherInfo.get(position);
                dailyViewHolder.description.setText(daily.getSummary());
                break;
            case UPDATED_AT:
                UpdatedAtViewHolder updatedAtViewHolder = (UpdatedAtViewHolder) viewHolder;

                updatedAtViewHolder.updatedAt.setText(context.get().getString(R.string.updated_at) + " " + new SimpleDateFormat("kk:mm a", Locale.getDefault()).format(new Date(fetchTime)).toUpperCase());
                break;
            case DAY:
                Day day = (Day) weatherInfo.get(position);
                DayViewHolder holder = (DayViewHolder) viewHolder;
                holder.main.setText(day.getSummary());
                if (celsius) {
                    holder.tempMax.setText(day.getTemperatureMax() + "°");
                    holder.tempMin.setText(day.getTemperatureMin() + "°");
                } else {
                    holder.tempMax.setText((Math.round(day.getTemperatureMax() * 1.8 + 32) * 100.0) / 100.0 + "°");
                    holder.tempMin.setText((Math.round(day.getTemperatureMin() * 1.8 + 32) * 100.0) / 100.0 + "°");
                }
                holder.icon.setText(IconManager.getIconResource(day.getIcon(), context.get()));


                if (position % 2 == 0) {
                    float[] hsv = new float[3];
                    Color.colorToHSV(day.getColor(context.get()), hsv);
                    hsv[2] *= 0.94f; // value component
                    holder.view.setBackgroundColor(Color.HSVToColor(hsv));
                } else {
                    holder.view.setBackgroundColor(day.getColor(context.get()));
                }


                //First
                if (day.isToday()) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    holder.dayName.setText(context.get().getString(R.string.today));

                    holder.view.setLayoutParams(params);
                } else if (day.isLastDayOfWeek()) {
                    holder.dayName.setText(new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(day.getTime() * 1000L)).toUpperCase());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    holder.view.setLayoutParams(params);
                } else {
                    if (day.isTomorrow()) {
                        holder.dayName.setText(context.get().getString(R.string.tomorrow));
                    } else {
                        holder.dayName.setText(new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(day.getTime() * 1000L)).toUpperCase());
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    holder.view.setLayoutParams(params);
                }
        }
    }

    @Override
    public int getItemCount() {
        return weatherInfo.size() + 1;
    }

    public void showNoInternetWarning() {
        Warning warning = new Warning(context.get().getString(R.string.no_internet_title), context.get().getString(R.string.no_internet_description));
        weatherInfo.add(0, warning);
        notifyItemInserted(0);
    }

    public void hideNoInternetWarning() {
        if (weatherInfo.get(0) instanceof Warning) {
            weatherInfo.remove(0);
            notifyItemRemoved(0);
        }
    }
}
