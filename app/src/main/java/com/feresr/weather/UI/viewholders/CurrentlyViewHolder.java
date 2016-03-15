package com.feresr.weather.UI.viewholders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.UI.views.InfoDisplay;

/**
 * Created by Fernando on 22/2/2016.
 */
public class CurrentlyViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout main;
    public TextView temp;
    public TextView description;
    public InfoDisplay humidity;
    public InfoDisplay wind;
    public InfoDisplay pressure;
    public InfoDisplay clouds;
    public InfoDisplay precipitation;
    public InfoDisplay feelsLike;
    public TextView icon;

    public CurrentlyViewHolder(View itemView, Typeface font) {
        super(itemView);
        main = (FrameLayout) itemView.findViewById(R.id.main_info);
        temp = (TextView) itemView.findViewById(R.id.temp);
        description = (TextView) itemView.findViewById(R.id.description);
        humidity = (InfoDisplay) itemView.findViewById(R.id.humidity);
        wind = (InfoDisplay) itemView.findViewById(R.id.tempMax);
        pressure = (InfoDisplay) itemView.findViewById(R.id.tempMin);
        clouds = (InfoDisplay) itemView.findViewById(R.id.clouds);
        precipitation = (InfoDisplay) itemView.findViewById(R.id.precipitation);
        feelsLike = (InfoDisplay) itemView.findViewById(R.id.feels_like);

        icon = (TextView) itemView.findViewById(R.id.main_icon);
        icon.setTypeface(font);

        icon.setText(R.string.today_weather_main_icon);
    }
}
