package com.feresr.weather.UI.viewholders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feresr.weather.R;

/**
 * Created by Fernando on 22/2/2016.
 */
public class DayViewHolder extends RecyclerView.ViewHolder {
    public TextView dayName;
    public TextView main;
    public TextView temp;
    public TextView tempMax;
    public TextView icon;
    public TextView tempMin;
    public LinearLayout view;

    public DayViewHolder(View itemView, Typeface font) {
        super(itemView);
        view = (LinearLayout) itemView;
        dayName = (TextView) itemView.findViewById(R.id.day);
        main = (TextView) itemView.findViewById(R.id.main);
        temp = (TextView) itemView.findViewById(R.id.temp);
        tempMax = (TextView) itemView.findViewById(R.id.tempMax);
        tempMin = (TextView) itemView.findViewById(R.id.tempMin);
        icon = (TextView) itemView.findViewById(R.id.icon);
        icon.setTypeface(font);

        icon.setText(R.string.today_weather_main_icon);
    }
}
