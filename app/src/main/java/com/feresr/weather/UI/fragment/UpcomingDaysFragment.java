package com.feresr.weather.UI.fragment;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feresr.weather.R;
import com.feresr.weather.UI.views.DayView;
import com.feresr.weather.UI.views.UpcomingDayView;
import com.feresr.weather.models.Daily;
import com.feresr.weather.models.Day;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feresr on 27/08/16.
 */
public class UpcomingDaysFragment extends Fragment {

    private final static String ARG_DAYS = "ARG_DAYS";
    @BindView(R.id.container)
    LinearLayout container;
    private Daily daily;

    public static UpcomingDaysFragment newInstance(Daily daily) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DAYS, daily);
        UpcomingDaysFragment fragment = new UpcomingDaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daily = (Daily) getArguments().getSerializable(ARG_DAYS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_days, container, false);
        ButterKnife.bind(this, view);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        p.weight = 1;
        float iconSize = 28f;
        int end = 6;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            end = 4;
            iconSize = 20f;
        }
        for (Day day : daily.getDays().subList(2, end)) {
            UpcomingDayView dayView = new UpcomingDayView(getContext());
            dayView.setIcon(day.getIcon());
            dayView.setMainText(getDayString(day));
            dayView.setSummary(day.getSummary());
            dayView.setLayoutParams(p);
            dayView.setIconSize(iconSize);

            this.container.addView(dayView);
        }

        return view;
    }

    private String getDayString(Day day) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(day.getTime() * 1000L)).toUpperCase();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
