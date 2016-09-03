package com.feresr.weather.UI.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.UI.views.DayView;
import com.feresr.weather.models.Daily;
import com.feresr.weather.models.Day;
import com.feresr.weather.utils.IconManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feresr on 27/08/16.
 */
public class DaysFragment extends Fragment {

    private final static String ARG_DAY1 = "ARG_DAY1";
    private final static String ARG_DAY2 = "ARG_DAY2";

    private Day day1;
    private Day day2;

    @BindView(R.id.day1)
    DayView dayView1;
    @BindView(R.id.day2)
    DayView dayView2;


    public static DaysFragment newInstance(Day day1, Day day2) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DAY1, day1);
        args.putSerializable(ARG_DAY2, day2);
        DaysFragment fragment = new DaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day1 = (Day) getArguments().getSerializable(ARG_DAY1);
        day2 = (Day) getArguments().getSerializable(ARG_DAY2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dayView1.setIcon(day1.getIcon());
        dayView2.setIcon(day2.getIcon());

        dayView1.setTempMax(day1.getTemperatureMax().toString());
        dayView1.setTempMin(day1.getTemperatureMin().toString());

        dayView2.setTempMax(day2.getTemperatureMax().toString());
        dayView2.setTempMin(day2.getTemperatureMin().toString());

        dayView1.setSummary(day1.getSummary());
        dayView2.setSummary(day2.getSummary());

        dayView1.setRain(day1.getPrecipProbability().toString());
        dayView2.setRain(day2.getPrecipProbability().toString());
    }
}
