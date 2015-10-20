package com.feresr.rxweather.UI.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feresr.rxweather.R;

/**
 * Created by Fernando on 19/10/2015.
 */
public class InfoDisplay extends RelativeLayout {

    private boolean hasValue;
    private String main;
    private String sub;
    private String ic;

    public InfoDisplay(Context context) {
        super(context);
        initializeViews(context);
    }

    public InfoDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttr(context, attrs);
        initializeViews(context);
    }

    public InfoDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttr(context, attrs);
        initializeViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InfoDisplay(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttr(context, attrs);
        initializeViews(context);
    }

    public void setValue(String main) {
        ((TextView) findViewById(R.id.main)).setText(main);
    }

    private void parseAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoDisplayWidget, 0, 0);
        main = typedArray.getString(R.styleable.InfoDisplayWidget_main);
        sub = typedArray.getString(R.styleable.InfoDisplayWidget_sub);
        ic = typedArray.getString(R.styleable.InfoDisplayWidget_ic);
        typedArray.recycle();
    }

    private void initializeViews(Context context) {
        inflate(context, R.layout.info_dispay, this);
        TextView main = (TextView) findViewById(R.id.main);
        TextView sub = (TextView) findViewById(R.id.sub);
        main.setText(this.main);
        sub.setText(this.sub);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "weathericons-regular-webfont.ttf");
        TextView icon = (TextView) findViewById(R.id.icon);
        icon.setText(this.ic);
        icon.setTypeface(font);
    }
}
