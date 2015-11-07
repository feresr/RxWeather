package com.feresr.rxweather.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.City;

import java.util.ArrayList;

/**
 * Created by Fernando on 4/11/2015.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private ArrayList<City> cities;
    private LayoutInflater inflater;

    public CitiesAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cities = new ArrayList<>();
    }

    public ArrayList<City> getCities() {
        return this.cities;
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
        holder.cityName.setText(cities.get(position).getName());
        if (cities.get(position).getCityWeather() != null) {
            holder.temp.setText(cities.get(position).getCityWeather().getCurrently().getTemperature().toString() + "°");
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView temp;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temp = (TextView) itemView.findViewById(R.id.temp);
        }
    }
}
