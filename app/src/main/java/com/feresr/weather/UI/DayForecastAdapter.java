package com.feresr.weather.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.weather.R;
import com.feresr.weather.UI.viewholders.HourlyForecastViewHolder;
import com.feresr.weather.models.Hour;
import com.feresr.weather.utils.IconManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fernando on 2/11/2015.
 */
public class DayForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Hour> hours;
    private WeakReference<Context> context;
    private boolean celsius = true;
    private int px;
    CardView.LayoutParams params;

    public DayForecastAdapter(Context context) {
        super();
        this.context = new WeakReference<Context>(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hours = new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, "celsius");

        //Adjust margins according to their positions
        Resources r = context.getResources();
        px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                r.getDisplayMetrics()
        );
        params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.WRAP_CONTENT
        );

        if (syncConnPref.equals("celsius")) {
            celsius = true;
        } else {
            celsius = false;
        }

    }

    public void setData(ArrayList<Hour> hours) {
        this.hours = hours;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hourly_forecast_view, parent, false);
        Typeface font = Typeface.createFromAsset(context.get().getAssets(), "weathericons-regular-webfont.ttf");
        return new HourlyForecastViewHolder(view, font);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HourlyForecastViewHolder hourlyForecastViewHolder = ((HourlyForecastViewHolder) holder);
        hourlyForecastViewHolder.hour.setText(DateFormat.getTimeFormat(context.get()).format(new Date(hours.get(position).getTime() * 1000L)));

        if (celsius) {
            hourlyForecastViewHolder.temp.setText(hours.get(position).getTemperature() + "°");
        } else {
            hourlyForecastViewHolder.temp.setText(Math.round((hours.get(position).getTemperature() * 1.8 + 32) * 100.0) / 100.0 + "°");
        }
        hourlyForecastViewHolder.icon.setText(IconManager.getIconResource(hours.get(position).getIcon(), context.get()));

        //First
        if (position == 0) {
            params.setMargins(px, 0, 8, 0);
        }
        //Last
        else if (position == hours.size() - 1) {
            params.setMargins(8, 0, px, 0);
        } else {
            params.setMargins(8, 0, 8, 0);
        }

        //hourlyForecastViewHolder.itemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public void addHourForecast(List<Hour> hours) {
        this.hours = (ArrayList<Hour>) hours;
        notifyDataSetChanged();
    }
}
