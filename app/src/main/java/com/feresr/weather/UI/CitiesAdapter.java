package com.feresr.weather.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feresr.weather.UI.fragment.CityFragment;
import com.feresr.weather.models.City;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Fernando on 4/11/2015.
 */
public class CitiesAdapter extends FragmentStatePagerAdapter {

    private static final String CELSIUS = "celsius";
    private ArrayList<City> cities;
    private Context context;
    //Weather it should show temperature in celsius or not
    private boolean celsius = true;

    @Inject
    public CitiesAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        cities = new ArrayList<>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, CELSIUS);
        if (syncConnPref.equals(CELSIUS)) {
            showTemperaturesInCelsius();
        } else {
            showTemperaturesInFahrenheit();
        }
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public int getCityColor(int position) {
        return cities.get(position).getWeather().getCurrently().getColor(context);
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
        notifyDataSetChanged();
    }

    public void updateCity(City city) {
        int cityIndex = cities.indexOf(city);
        if (cityIndex == -1) return;
    }

/*    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (compact) {
            view = inflater.inflate(R.layout.city_view_compact, parent, false);
        } else {
            view = inflater.inflate(R.layout.city_view, parent, false);
        }
        return new ViewHolder(view);
    }*/

/*    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final City city = cities.get(position);

        holder.cityName.setText(city.getName());
        if (cities.get(position).getWeather() != null) {
            holder.view.setBackgroundColor(city.getWeather().getCurrently().getColor(context));
            double temperature = city.getWeather().getCurrently().getTemperature();
            if (!celsius) {
                temperature = temperature * 1.8 + 32;
            }
            temperature = Math.round(temperature * 100.0) / 100.0;

            holder.temp.setText(temperature + "°");
            holder.temp.setVisibility(View.VISIBLE);
            holder.summary.setVisibility(View.VISIBLE);
            holder.summary.setText(city.getWeather().getCurrently().getSummary());
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCitySelected(city);
            }
        });
    }*/


    @Override
    public int getCount() {
        return cities == null ? 0 : cities.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CityFragment.newInstance(cities.get(position));
    }
}
