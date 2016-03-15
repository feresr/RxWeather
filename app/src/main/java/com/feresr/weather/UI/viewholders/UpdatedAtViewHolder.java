package com.feresr.weather.UI.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feresr.weather.R;

/**
 * Created by Fernando on 22/2/2016.
 */
public class UpdatedAtViewHolder extends RecyclerView.ViewHolder {
    public TextView updatedAt;

    public UpdatedAtViewHolder(View itemView) {
        super(itemView);
        updatedAt = (TextView) itemView.findViewById(R.id.updated_at);
    }
}
