package com.feresr.rxweather.UI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.UI.views.InfoDisplay;
import com.feresr.rxweather.UI.views.RoundedCardLayout;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.models.Currently;
import com.feresr.rxweather.models.Daily;
import com.feresr.rxweather.models.Day;
import com.feresr.rxweather.models.DisplayWeatherInfo;
import com.feresr.rxweather.models.Hourly;

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

    private LayoutInflater inflater;
    private List<DisplayWeatherInfo> weatherInfo;
    private Context context;
    private DayForecastAdapter dayForecastAdapter;
    private int groupTopBottomMargin;
    private int groupLeftRightMargin;

    public ForecastAdapter(Context context) {
        super();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dayForecastAdapter = new DayForecastAdapter(context);
        this.weatherInfo = new ArrayList<>();

        Resources r = context.getResources();
        groupTopBottomMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                5,
                r.getDisplayMetrics()
        );
        groupLeftRightMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                r.getDisplayMetrics()
        );
    }

    @Override
    public int getItemViewType(int position) {
        DisplayWeatherInfo weatherInfoObject = weatherInfo.get(position);

        if (weatherInfoObject instanceof Currently) {
            return CURRENTLY;
        } else if (weatherInfoObject instanceof Hourly) {
            return HOURLY;
        } else if (weatherInfoObject instanceof Daily) {
            return DAILY;
        } else if (weatherInfoObject instanceof Day) {
            return DAY;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CURRENTLY:
                view = this.inflater.inflate(R.layout.currently_view, parent, false);
                return new CurrentlyViewHolder(view);
            case HOURLY:
                view = this.inflater.inflate(R.layout.hourly_view, parent, false);
                return new HourlyViewHolder(view);
            case DAILY:
                view = this.inflater.inflate(R.layout.daily_view, parent, false);
                return new DailyViewHolder(view);
            case DAY:
                view = this.inflater.inflate(R.layout.day_view, parent, false);
                return new DayViewHolder(view);
            default:
                return null;
        }
    }


    public void addForecast(CityWeather cityWeather) {
        weatherInfo.add(cityWeather.getCurrently());
        weatherInfo.add(cityWeather.getHourly());

        dayForecastAdapter.addHourForecast(cityWeather.getHourly().getData());

        weatherInfo.add(cityWeather.getDaily());
        for (Day day : cityWeather.getDaily().getDays()) {
            weatherInfo.add(day);
        }


        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case CURRENTLY:
                CurrentlyViewHolder currentlyViewHolder = (CurrentlyViewHolder) viewHolder;
                Currently currently = (Currently) weatherInfo.get(position);

//                    currentlyViewHolder.city.setText(String.format(context.getResources().getString(R.string.city_country_name),
//                            today.getName(), today.getSys().getCountry()));
                currentlyViewHolder.description.setText(currently.getSummary().toUpperCase());
                currentlyViewHolder.temp.setText(Math.round(currently.getTemperature()) + "°");
                currentlyViewHolder.humidity.setValue(currently.getHumidity() * 100 + "%");
                currentlyViewHolder.wind.setValue(currently.getWindSpeed() + " m/s");
                currentlyViewHolder.pressure.setValue(currently.getPressure() + " kPa");
                currentlyViewHolder.clouds.setValue(currently.getCloudCover() * 100 + "%");
                //currentlyViewHolder.sunrise.setValue(DateFormat.getTimeFormat(context).format(today.getSys().getSunrise()));
                //currentlyViewHolder.sunset.setValue(DateFormat.getTimeFormat(context).format(today.getSys().getSunset()));
                currentlyViewHolder.icon.setText(currently.getIcon(context));

                break;
            case HOURLY:
                HourlyViewHolder labelViewHolder = (HourlyViewHolder) viewHolder;
                Hourly hourly = (Hourly) weatherInfo.get(position);
                labelViewHolder.description.setText(hourly.getSummary());

                break;
            case DAILY:
                DailyViewHolder dailyViewHolder = (DailyViewHolder) viewHolder;
                Daily daily = (Daily) weatherInfo.get(position);
                dailyViewHolder.description.setText(daily.getSummary());
                break;
            case DAY:
                Day day = (Day) weatherInfo.get(position);
                DayViewHolder holder = (DayViewHolder) viewHolder;
                holder.main.setText(day.getSummary());
                holder.tempMax.setText(day.getTemperatureMax() + "°");
                holder.tempMin.setText(day.getTemperatureMin() + "°");
                holder.icon.setText(day.getIcon(context));


                //First
                if (day == ((Daily) weatherInfo.get(2)).getDays().get(0)) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(groupLeftRightMargin, groupTopBottomMargin, groupLeftRightMargin, 0);
                    holder.dayName.setText("TODAY");

                    holder.view.setLayoutParams(params);
                    holder.view.setUpperRadius(10);
                    holder.view.setLowerRadius(0);
                }
                else if (day == ((Daily) weatherInfo.get(2)).getDays().get(7)) {
                    holder.view.setLowerRadius(10);
                    holder.view.setUpperRadius(0);
                    holder.dayName.setText(new SimpleDateFormat("EEEE", new Locale("EN")).format(new Date(day.getTime() * 1000L)).toUpperCase());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(groupLeftRightMargin, 0, groupLeftRightMargin, groupTopBottomMargin);
                    holder.view.setLayoutParams(params);
                } else {
                    holder.view.setUpperRadius(0);
                    holder.view.setLowerRadius(0);
                    if (day == ((Daily) weatherInfo.get(2)).getDays().get(1)) {
                        holder.dayName.setText("TOMORROW");
                    } else {
                        holder.dayName.setText(new SimpleDateFormat("EEEE", new Locale("EN")).format(new Date(day.getTime() * 1000L)).toUpperCase());
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(groupLeftRightMargin, 0, groupLeftRightMargin, 0);
                    holder.view.setLayoutParams(params);

                    if (position % 3 == 0) {
                        //holder.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_bright));
                    }
                }
        }
    }

    @Override
    public int getItemCount() {
        return weatherInfo.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayName;
        TextView main;
        TextView temp;
        TextView tempMax;
        TextView icon;
        TextView tempMin;
        RoundedCardLayout view;

        public DayViewHolder(View itemView) {
            super(itemView);
            view = (RoundedCardLayout) itemView;
            dayName = (TextView) itemView.findViewById(R.id.day);
            main = (TextView) itemView.findViewById(R.id.main);
            temp = (TextView) itemView.findViewById(R.id.temp);
            tempMax = (TextView) itemView.findViewById(R.id.tempMax);
            tempMin = (TextView) itemView.findViewById(R.id.tempMin);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
            icon = (TextView) itemView.findViewById(R.id.icon);
            icon.setTypeface(font);

            icon.setText(R.string.today_weather_main_icon);
        }
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder {
        TextView description;

        public DailyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        RecyclerView recyclerView;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.nextHoursRecyclerview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(dayForecastAdapter);
        }
    }

    public class CurrentlyViewHolder extends RecyclerView.ViewHolder {
        TextView city;
        TextView temp;
        TextView description;
        InfoDisplay humidity;
        InfoDisplay wind;
        InfoDisplay pressure;
        InfoDisplay clouds;
        InfoDisplay sunrise;
        InfoDisplay sunset;
        TextView icon;

        public CurrentlyViewHolder(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.city);
            temp = (TextView) itemView.findViewById(R.id.temp);
            description = (TextView) itemView.findViewById(R.id.description);
            humidity = (InfoDisplay) itemView.findViewById(R.id.humidity);
            wind = (InfoDisplay) itemView.findViewById(R.id.tempMax);
            pressure = (InfoDisplay) itemView.findViewById(R.id.tempMin);
            clouds = (InfoDisplay) itemView.findViewById(R.id.clouds);
            sunrise = (InfoDisplay) itemView.findViewById(R.id.sunrise);
            sunset = (InfoDisplay) itemView.findViewById(R.id.sunset);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
            icon = (TextView) itemView.findViewById(R.id.main_icon);
            icon.setTypeface(font);

            icon.setText(R.string.today_weather_main_icon);
        }
    }
}
