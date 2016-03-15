package com.feresr.weather.UI.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feresr.weather.R;

/**
 * Created by Fernando on 22/2/2016.
 */
public class WarningViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView explanation;

    public WarningViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        explanation = (TextView) itemView.findViewById(R.id.explanation);
    }
}
