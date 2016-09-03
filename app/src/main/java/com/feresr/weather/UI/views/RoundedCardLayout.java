package com.feresr.weather.UI.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Fernando on 3/11/2015.
 */
public class RoundedCardLayout extends LinearLayout {

    private int upperRadius = 0;
    private int lowerRadius = 0;
    private Paint paint;
    private RectF upperRect;
    private RectF lowerRect;
    private RectF wholeRect;


    public RoundedCardLayout(Context context) {
        super(context);
        init();
        setWillNotDraw(false);
    }

    public RoundedCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setWillNotDraw(false);
    }

    public RoundedCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setWillNotDraw(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundedCardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        setWillNotDraw(false);
    }

    @Override
    public void setBackgroundColor(int color) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(0);
        //super.setBackgroundColor(color);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wholeRect = new RectF(0, 0, w, h);
        upperRect = new RectF(0, 0, w, h / 2 + 10);
        lowerRect = new RectF(0, h / 2 - 10, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //If there's no radius, just draw one rectangle
        if (upperRadius == 0 && lowerRadius == 0) {
            canvas.drawRoundRect(wholeRect, upperRadius, upperRadius, paint);
            return;
        }
        canvas.drawRoundRect(upperRect, upperRadius, upperRadius, paint);
        canvas.drawRoundRect(lowerRect, lowerRadius, lowerRadius, paint);
    }

    public void setUpperRadius(int r) {
        this.upperRadius = r;
    }

    public void setLowerRadius(int r) {
        this.lowerRadius = r;
    }
}
