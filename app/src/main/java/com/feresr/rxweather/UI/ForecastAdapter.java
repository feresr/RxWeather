package com.feresr.rxweather.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ForecastAdapter(Context context, List<Day> weathers) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (weathers != null) {
            this.weathers = weathers;
        } else {
            this.weathers = new ArrayList<>();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = this.inflater.inflate(R.layout.today_view, parent, false);
                return new TodayViewHolder(view);
            default:
                view = this.inflater.inflate(R.layout.weather_view, parent, false);
                return new ViewHolder(view);
        }

    }


    public void addForecast(Day day) {
        weathers.add(day);
        notifyItemInserted(weathers.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                if (today != null) {
                    TodayViewHolder todayholder = (TodayViewHolder) viewHolder;
                    todayholder.city.setText(today.getName());
                    todayholder.temp.setText(today.getMain().getTemp() + " °");
                    todayholder.humidity.setValue(today.getMain().getHumidity().toString() + "%");
                    todayholder.max.setValue(today.getMain().getTempMax().toString() + " °");
                    todayholder.min.setValue(today.getMain().getTempMin().toString() + " °");
                }
                break;
            case 1:
                Day lista = weathers.get(position);
                ViewHolder holder = (ViewHolder) viewHolder;
                holder.mainTextView.setText(lista.getWeather().get(0).getMain());
                holder.descriptionTextView.setText(lista.getWeather().get(0).getDescription());
                holder.tempMax.setText(lista.getTemp().getMax().toString() + "°");
                holder.tempMin.setText(lista.getTemp().getMin().toString() + "°");
                holder.temp.setText(lista.getTemp().getDay().toString() + "°");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return weathers != null ? weathers.size() : 1;
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
        TextView tempMin;

        public ViewHolder(View itemView) {
            super(itemView);
            mainTextView = (TextView) itemView.findViewById(R.id.main);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            temp = (TextView) itemView.findViewById(R.id.temp);
            tempMax = (TextView) itemView.findViewById(R.id.tempMax);
            tempMin = (TextView) itemView.findViewById(R.id.tempMin);
        }
    }


    public class TodayViewHolder extends RecyclerView.ViewHolder {
        TextView city;
        TextView temp;
        InfoDisplay humidity;
        InfoDisplay max;
        InfoDisplay min;

        public TodayViewHolder(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.city);
            temp = (TextView) itemView.findViewById(R.id.temp);
            humidity = (InfoDisplay) itemView.findViewById(R.id.humidity);
            max = (InfoDisplay) itemView.findViewById(R.id.tempMax);
            min = (InfoDisplay) itemView.findViewById(R.id.tempMin);
        }
    }
}
