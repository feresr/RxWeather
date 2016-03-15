package com.feresr.weather.UI.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feresr.weather.R;

/**
 * Created by Fernando on 22/2/2016.
 */
public class DailyViewHolder extends RecyclerView.ViewHolder {
    public TextView description;

    public DailyViewHolder(View itemView) {
        super(itemView);
        description = (TextView) itemView.findViewById(R.id.description);
    }
}
