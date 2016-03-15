package com.feresr.weather.UI.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.UI.DayForecastAdapter;

/**
 * Created by Fernando on 22/2/2016.
 */
public class HourlyViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public RecyclerView recyclerView;
    public LinearLayout view;

    public HourlyViewHolder(View itemView, LinearLayoutManager linearLayoutManager, Context context) {
        super(itemView);
        view = (LinearLayout) itemView;
        description = (TextView) itemView.findViewById(R.id.description);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.nextHoursRecyclerview);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DayForecastAdapter dayForecastAdapter = new DayForecastAdapter(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(dayForecastAdapter);
    }
}
