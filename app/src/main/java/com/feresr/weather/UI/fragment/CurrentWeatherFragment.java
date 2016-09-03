package com.feresr.weather.UI.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.views.InfoDisplay;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.Currently;
import com.feresr.weather.presenters.CurrentWeatherPresenter;
import com.feresr.weather.presenters.views.CurrentWeatherView;
import com.feresr.weather.utils.IconManager;

import butterknife.BindView;

/**
 * Created by feresr on 24/08/16.
 */
public class CurrentWeatherFragment extends BaseFragment<CurrentWeatherPresenter> implements CurrentWeatherView {

    public static final String ARG_CURRENT_WEATHER = "CURRENT_WEATHER";
    @BindView(R.id.main_icon)
    TextView mainIcon;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.humidity)
    InfoDisplay humidity;
    @BindView(R.id.pressure)
    InfoDisplay pressure;
    @BindView(R.id.clouds)
    InfoDisplay clouds;
    @BindView(R.id.precipitation)
    InfoDisplay precipitation;
    @BindView(R.id.feels_like)
    InfoDisplay feelsLike;
    @BindView(R.id.wind)
    InfoDisplay wind;

    public static CurrentWeatherFragment newInstance(Currently currentWeather) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_WEATHER, currentWeather);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void bindPresenterToView() {
        presenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.current_weather_view;
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void displayWeather(Currently currently) {
        mainIcon.setText(IconManager.getIconResource(currently.getIcon(), getActivity()));
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "weathericons-regular-webfont.ttf");
        mainIcon.setTypeface(font);
        temp.setText(String.valueOf(currently.getTemperature()));
        description.setText(currently.getSummary());
        humidity.setValue(String.valueOf(currently.getHumidity()));
        wind.setValue(String.valueOf(currently.getWindSpeed()));
        pressure.setValue(String.valueOf(currently.getPressure()));
        clouds.setValue(String.valueOf(currently.getCloudCover()));
        precipitation.setValue(String.valueOf(currently.getPrecipProbability()));
        feelsLike.setValue(String.valueOf(currently.getApparentTemperature()));
    }
}
