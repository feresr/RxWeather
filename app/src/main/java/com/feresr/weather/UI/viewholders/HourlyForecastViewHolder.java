package com.feresr.weather.UI.viewholders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feresr.weather.R;

/**
 * Created by Fernando on 22/2/2016.
 */
public class HourlyForecastViewHolder extends RecyclerView.ViewHolder {
    public TextView hour;
    public TextView icon;
    public TextView temp;
    public View view;

    public HourlyForecastViewHolder(View itemView, Typeface font) {
        super(itemView);
        this.view = itemView;
        hour = (TextView) itemView.findViewById(R.id.hour);
        icon = (TextView) itemView.findViewById(R.id.icon);
        icon.setTypeface(font);
        temp = (TextView) itemView.findViewById(R.id.temp);
    }
}
