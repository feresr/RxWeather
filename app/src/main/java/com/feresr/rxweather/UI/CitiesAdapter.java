package com.feresr.rxweather.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.utils.IconManager;

import java.util.ArrayList;

/**
 * Created by Fernando on 4/11/2015.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private ArrayList<City> cities;
    private LayoutInflater inflater;
    private Context context;

    public CitiesAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cities = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }


    public void addCity(City city) {
        cities.add(city);
        notifyItemInserted(cities.size() - 1);
    }

    public void updateCity(City city) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId().equals(city.getId())) {
                cities.get(i).setCityWeather(city.getCityWeather());
                notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.city_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        City city = cities.get(position);
        holder.cityName.setText(city.getName());
        if (cities.get(position).getCityWeather() != null) {
            holder.view.setBackgroundColor(city.getCityWeather().getCurrently().getColor(context));
            holder.temp.setText(city.getCityWeather().getCurrently().getTemperature().toString() + "°");
            holder.summary.setText(city.getCityWeather().getCurrently().getSummary());
        } else {
            holder.view.setBackgroundColor(Color.GRAY);
            holder.temp.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void onItemDismiss(int adapterPosition) {
        cities.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView temp;
        LinearLayout view;
        TextView summary;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = (LinearLayout) itemView.findViewById(R.id.container);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temp = (TextView) itemView.findViewById(R.id.temp);
            summary = (TextView) itemView.findViewById(R.id.summary);
        }
    }
}
