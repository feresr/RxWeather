package com.feresr.weather.UI.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feresr.weather.R;

/**
 * Created by feresr on 3/09/16.
 * Simple view pager page indicator
 */
public class ViewPagerIndicator extends FrameLayout implements ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener {

    private int indicatorPadding = 10;
    private int movingIndicatorId = R.drawable.vpindicator_selected;
    private int indicatorId = R.drawable.vpindicator;
    private Context context;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private ImageView movingIndicator;

    private DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            generateIndicators(viewPager.getAdapter().getCount());
            onPageScrolled(viewPager.getCurrentItem(), 0, 0);
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    public ViewPagerIndicator(Context context) {
        super(context);
        initView(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        linearLayout = new LinearLayout(context, attributeSet);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.context = context;
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            this.viewPager = viewPager;
            if (viewPager.getAdapter() != null) {
                generateIndicators(viewPager.getAdapter().getCount());
                viewPager.getAdapter().registerDataSetObserver(dataSetObserver);
            }
            viewPager.addOnPageChangeListener(this);
            viewPager.addOnAdapterChangeListener(this);
        } else {
            throw new IllegalArgumentException("view pager cannot be null");
        }
    }

    public void setIndicatorPadding(int padding) {
        this.indicatorPadding = padding;
    }

    public void setMovingIndicatorResourceId(int selectedIndicatorId) {
        this.movingIndicatorId = selectedIndicatorId;
    }

    public void setIndicatorResourceId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    private void generateIndicators(int count) {
        linearLayout.removeAllViews();
        removeAllViews();

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, indicatorId));
            imageView.setPadding(indicatorPadding, indicatorPadding, indicatorPadding, indicatorPadding);
            linearLayout.addView(imageView);
        }

        addView(linearLayout);

        movingIndicator = new ImageView(context);
        movingIndicator.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        movingIndicator.setImageDrawable(ContextCompat.getDrawable(context, movingIndicatorId));
        movingIndicator.setPadding(indicatorPadding, indicatorPadding, indicatorPadding, indicatorPadding);
        addView(movingIndicator);
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(dataSetObserver);
        }

        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(dataSetObserver);
            generateIndicators(newAdapter.getCount());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (linearLayout.getChildCount() > 0 && viewPager.getWidth() > 0) {
            movingIndicator.setX((getMeasuredWidth() * position / linearLayout.getChildCount() + positionOffsetPixels * getMeasuredWidth() / viewPager.getWidth() / linearLayout.getChildCount()));
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
