package com.feresr.weather.UI.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.utils.IconManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feresr on 28/08/16.
 */
public class UpcomingDayView extends LinearLayout {

    @BindView(R.id.icon)
    TextView icon;

    @BindView(R.id.main)
    TextView main;

    @BindView(R.id.summary)
    TextView summary;

    @BindView(R.id.temp_max)
    TextView tempMax;

    @BindView(R.id.temp_min)
    TextView tempMin;

    @BindView(R.id.rain)
    TextView rain;

    public UpcomingDayView(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public UpcomingDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public UpcomingDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UpcomingDayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.upcoming_day_view, this);
        ButterKnife.bind(this, view);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
        this.setOrientation(VERTICAL);
        icon.setTypeface(font);
        tempMax.setTypeface(font);
        tempMax.setVisibility(GONE);
        tempMin.setTypeface(font);
        tempMin.setVisibility(GONE);
        rain.setTypeface(font);
        rain.setVisibility(GONE);

        if (attrs != null) {
            try {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayView, 0, 0);
                main.setText(typedArray.getString(R.styleable.DayView_main));
                icon.setTextSize(typedArray.getFloat(R.styleable.DayView_icon_size, 24));
                typedArray.recycle();
            } catch (Exception ignored) {

            }
        }
    }

    public void setIcon(String icon) {
        this.icon.setText(IconManager.getIconResource(icon, getContext()));
        this.icon.setTextColor(IconManager.getColorResource(icon, getContext()));

    }

    public void setMainText(String main) {
        this.main.setText(main);
    }

    public void setSummary(String summary) {
        this.summary.setText(summary);
    }

    public void setTempMax(String tempMax) {
        this.tempMax.setText(IconManager.getIconResource("temp_max", getContext()) + " " + tempMax);
        this.tempMax.setVisibility(VISIBLE);
    }

    public void setTempMin(String tempMin) {
        this.tempMin.setText(IconManager.getIconResource("temp_min", getContext()) + " " + tempMin);
        this.tempMin.setVisibility(VISIBLE);
    }

    public void setRain(String rain) {
        this.rain.setText(IconManager.getIconResource("umbrella", getContext()) + " " + rain);
        this.rain.setVisibility(VISIBLE);
    }

    public void setIconSize(float size) {
        icon.setTextSize(size);
    }
}
