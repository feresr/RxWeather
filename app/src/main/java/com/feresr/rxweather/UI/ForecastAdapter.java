package com.feresr.rxweather.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.Lista;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Lista> weathers;

    public ForecastAdapter(Context context, List<Lista> weathers) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (weathers != null) {
            this.weathers = weathers;
        } else {
            this.weathers = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.weather_view, parent, false);
        return new ViewHolder(view);
    }

    public void addForecast(Lista lista) {
        weathers.add(lista);
        notifyItemInserted(weathers.size());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lista lista = weathers.get(position);
        holder.weatherTextView.setText(lista.getWeather().get(0).getMain());
    }

    @Override
    public int getItemCount() {
        return weathers != null ? weathers.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView weatherTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            weatherTextView = (TextView) itemView.findViewById(R.id.weatherTextView);
        }
    }
}
