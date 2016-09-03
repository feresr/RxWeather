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
import com.feresr.weather.models.Daily;
import com.feresr.weather.utils.IconManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feresr on 27/08/16.
 */
public class WeekFragment extends Fragment {

    private final static String ARG_DAILY = "ARG_DAILY";

    @BindView(R.id.main_icon)
    TextView mainIcon;

    @BindView(R.id.description)
    TextView description;

    public static WeekFragment newInstance(Daily daily) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DAILY, daily);
        WeekFragment fragment = new WeekFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Daily daily = (Daily) getArguments().get(ARG_DAILY);
        description.setText(daily.getSummary());
        mainIcon.setText(IconManager.getIconResource(daily.getIcon(), getActivity()));
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-regular-webfont.ttf");
        mainIcon.setTypeface(font);
    }
}
