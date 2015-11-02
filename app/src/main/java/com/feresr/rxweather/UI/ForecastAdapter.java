package com.feresr.rxweather.UI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.UI.views.InfoDisplay;
import com.feresr.rxweather.models.Day;
import com.feresr.rxweather.models.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Day> weathers;
    private Today today;
    private Context context;
    private DayForecastAdapter dayForecastAdapter;

    public ForecastAdapter(Context context, List<Day> weathers) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dayForecastAdapter = new DayForecastAdapter(context);
        this.context = context;
        if (weathers != null) {
            this.weathers = weathers;
        } else {
            this.weathers = new ArrayList<>();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = this.inflater.inflate(R.layout.today_view, parent, false);
                return new TodayViewHolder(view);
            case 1:
                view = new RecyclerView(context);
                view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                return new NextHoursViewHolder(view);
            default:
                view = this.inflater.inflate(R.layout.weather_view, parent, false);
                return new ViewHolder(view);
        }

    }


    public void addForecast(Day day) {
        weathers.add(day);
        notifyItemInserted(weathers.size() + 1);
    }

    public DayForecastAdapter getDayForecastAdapter() {
        return dayForecastAdapter;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                if (today != null) {
                    TodayViewHolder todayholder = (TodayViewHolder) viewHolder;
                    todayholder.city.setText(today.getName() + " " + today.getSys().getCountry());
                    todayholder.description.setText(today.getWeather().get(0).getDescription());
                    todayholder.temp.setText(today.getMain().getTemp() + " °");
                    todayholder.humidity.setValue(today.getMain().getHumidity().toString() + "%");
                    todayholder.wind.setValue(today.getWind().getSpeed().toString() + " m/s");
                    todayholder.pressure.setValue(today.getMain().getPressure() / 100 + " kPa");
                    todayholder.clouds.setValue(today.getClouds().getAll() + "%");
                    todayholder.sunrise.setValue(DateFormat.getTimeFormat(context).format(today.getSys().getSunrise()));
                    todayholder.sunset.setValue(DateFormat.getTimeFormat(context).format(today.getSys().getSunset()));
                    todayholder.icon.setText(today.getWeather().get(0).getIcon(context));

                }
                break;
            case 1:

                break;
            default:
                Day lista = weathers.get(position - 2);
                ViewHolder holder = (ViewHolder) viewHolder;
                holder.mainTextView.setText(lista.getWeather().get(0).getMain());
                holder.descriptionTextView.setText(lista.getWeather().get(0).getDescription());
                holder.tempMax.setText(lista.getTemp().getMax().toString() + "°");
                holder.tempMin.setText(lista.getTemp().getMin().toString() + "°");
                holder.temp.setText(lista.getTemp().getDay().toString() + "°");
                holder.icon.setText(lista.getWeather().get(0).getIcon(context));

                break;
        }
    }

    @Override
    public int getItemCount() {
        return weathers != null ? weathers.size() + 2 : 2;
    }

    public void addToday(Today today) {
        this.today = today;
        this.notifyItemChanged(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainTextView;
        TextView descriptionTextView;
        TextView temp;
        TextView tempMax;
        TextView icon;
        TextView tempMin;

        public ViewHolder(View itemView) {
            super(itemView);
            mainTextView = (TextView) itemView.findViewById(R.id.main);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            temp = (TextView) itemView.findViewById(R.id.temp);
            tempMax = (TextView) itemView.findViewById(R.id.tempMax);
            tempMin = (TextView) itemView.findViewById(R.id.tempMin);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
            icon = (TextView) itemView.findViewById(R.id.icon);
            icon.setTypeface(font);

            icon.setText(R.string.today_weather_main_icon);
        }
    }

    public class NextHoursViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        public NextHoursViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(dayForecastAdapter);

        }
    }


    public class TodayViewHolder extends RecyclerView.ViewHolder {
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

        public TodayViewHolder(View itemView) {
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
