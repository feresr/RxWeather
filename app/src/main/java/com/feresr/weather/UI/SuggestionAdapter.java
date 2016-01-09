package com.feresr.weather.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.models.City;

import java.util.ArrayList;

/**
 * Created by Fernando on 4/11/2015.
 */
public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {

    private ArrayList<City> cities;
    private LayoutInflater inflater;

    public SuggestionAdapter(Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cities = new ArrayList<>();
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.city_search_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cityName.setText(cities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
        }
    }
}
