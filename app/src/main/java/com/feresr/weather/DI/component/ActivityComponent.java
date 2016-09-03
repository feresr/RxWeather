package com.feresr.weather.DI.component;

import com.feresr.weather.DI.ActivityScope;
import com.feresr.weather.DI.modules.ActivityModule;
import com.feresr.weather.UI.MainActivity;
import com.feresr.weather.UI.SearchCityActivity;
import com.feresr.weather.UI.fragment.CitiesFragment;
import com.feresr.weather.UI.fragment.CurrentWeatherFragment;
import com.feresr.weather.UI.fragment.ForecastFragment;
import com.feresr.weather.UI.fragment.ForecastPagerFragment;
import com.feresr.weather.UI.fragment.NextHoursFragment;
import com.feresr.weather.UI.fragment.SearchFragment;

import dagger.Component;

/**
 * Created by feresr on 25/07/16.
 */
@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class}
)
public interface ActivityComponent {
    void inject(CitiesFragment welcomeFragment);

    void inject(ForecastFragment forecastFragment);

    void inject(SearchFragment searchFragment);

    void inject(MainActivity mainActivity);

    void inject(SearchCityActivity searchCityActivity);

    void inject(CurrentWeatherFragment currentWeatherFragment);

    void inject(ForecastPagerFragment forecastPagerFragment);

    void inject(NextHoursFragment nextHoursFragment);
}
