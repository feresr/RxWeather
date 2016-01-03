package com.feresr.rxweather.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.City;
import java.util.ArrayList;

/**
 * Created by Fernando on 4/11/2015.
 */
    public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    boolean compact = false;
    private ArrayList<City> cities;
    private LayoutInflater inflater;
    private Context context;
    //Weather it should show temperature in celsius or not
    private boolean celsius = true;

    public CitiesAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cities = new ArrayList<>();
        this.context = context;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, "celsius");
        if (syncConnPref.equals("celsius")) {
            showTemperaturesInCelsius();
        } else {
            showTemperaturesInFahrenheit();
        }

        compact = sharedPref.getBoolean(SettingsActivity.GRIDVIEW, false);
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void showTemperaturesInCelsius() {
        celsius = true;
        notifyDataSetChanged();
    }

    public void showTemperaturesInFahrenheit() {
        celsius = false;
        notifyDataSetChanged();
    }

    public void addCity(City city) {
        cities.add(city);
        notifyItemInserted(cities.size() - 1);
    }

    public void updateCity(City city) {
        notifyItemChanged(cities.indexOf(city));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (compact) {
            view = inflater.inflate(R.layout.city_view_compact, parent, false);
        } else {
            view = inflater.inflate(R.layout.city_view, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        City city = cities.get(position);

        holder.cityName.setText(city.getName());
        if (cities.get(position).getCityWeather() != null) {
            holder.view.setBackgroundColor(city.getCityWeather().getCurrently().getColor(context));
            double temperature = city.getCityWeather().getCurrently().getTemperature();
            if (!celsius) {
                temperature = temperature * 1.8 + 32;
            }
            temperature = Math.round(temperature * 100.0) / 100.0;

            holder.temp.setText(temperature + "°");
            holder.temp.setVisibility(View.VISIBLE);
            holder.summary.setVisibility(View.VISIBLE);
            holder.summary.setText(city.getCityWeather().getCurrently().getSummary());
        } else {
            holder.view.setBackgroundColor(Color.GRAY);
            holder.temp.setVisibility(View.GONE);
            holder.summary.setVisibility(View.INVISIBLE);
        }

        if (city.getState() == City.STATE_FETCHING) {
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return compact? 1 : 0;
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
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = (LinearLayout) itemView.findViewById(R.id.container);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
            temp = (TextView) itemView.findViewById(R.id.temp);
            summary = (TextView) itemView.findViewById(R.id.summary);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public void setCompactView(boolean compact) {
        this.compact = compact;
    }
}
