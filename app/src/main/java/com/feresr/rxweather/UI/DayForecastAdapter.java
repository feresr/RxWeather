package com.feresr.rxweather.UI;

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
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.Hour;
import com.feresr.rxweather.utils.IconManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fernando on 2/11/2015.
 */
public class DayForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Hour> hours;
    private Context context;
    private boolean celsius = true;

    public DayForecastAdapter(Context context) {

        super();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hours = new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String syncConnPref = sharedPref.getString(SettingsActivity.PREF_UNIT, "celsius");
        if (syncConnPref.equals("celsius")) {
            celsius = true;
        } else {
            celsius = false;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hourly_forecast_view, parent, false);
        return new HourlyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HourlyForecastViewHolder hourlyForecastViewHolder = ((HourlyForecastViewHolder) holder);
        hourlyForecastViewHolder.hour.setText(DateFormat.getTimeFormat(context).format(new Date(hours.get(position).getTime() * 1000L)));

        if (celsius) {
            hourlyForecastViewHolder.temp.setText(hours.get(position).getTemperature() + "°");
        } else {
            hourlyForecastViewHolder.temp.setText(Math.round((hours.get(position).getTemperature() * 1.8 + 32) * 100.0) / 100.0 + "°");
        }
        hourlyForecastViewHolder.icon.setText(IconManager.getIconResource(hours.get(position).getIcon(), context));

        //Adjust margins according to their positions
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                r.getDisplayMetrics()
        );

        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.WRAP_CONTENT
        );

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

    public class HourlyForecastViewHolder extends RecyclerView.ViewHolder {

        TextView hour;
        TextView icon;
        TextView temp;
        View view;

        public HourlyForecastViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            hour = (TextView) itemView.findViewById(R.id.hour);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
            icon = (TextView) itemView.findViewById(R.id.icon);
            icon.setTypeface(font);
            temp = (TextView) itemView.findViewById(R.id.temp);
        }
    }
}
