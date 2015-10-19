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
        holder.mainTextView.setText(lista.getWeather().get(0).getMain());
        holder.descriptionTextView.setText(lista.getWeather().get(0).getDescription());
        holder.tempMax.setText(lista.getMain().getTempMax().toString() + "°");
        holder.tempMin.setText(lista.getMain().getTempMin().toString() + "°");
        holder.temp.setText(lista.getMain().getTemp().toString() + "°");
    }

    @Override
    public int getItemCount() {
        return weathers != null ? weathers.size() : 0;
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
}
