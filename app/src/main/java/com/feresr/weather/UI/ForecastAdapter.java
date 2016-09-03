package com.feresr.weather.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feresr.weather.UI.fragment.NowWeatherFragment;
import com.feresr.weather.UI.fragment.TwoDaysFragment;
import com.feresr.weather.UI.fragment.NextHoursFragment;
import com.feresr.weather.UI.fragment.UpcomingDaysFragment;
import com.feresr.weather.UI.fragment.WeekFragment;
import com.feresr.weather.models.CityWeather;

import javax.inject.Inject;

public class ForecastAdapter extends FragmentStatePagerAdapter {

    private CityWeather cityWeather;

    @Inject
    public ForecastAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCityWeather(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NowWeatherFragment.newInstance(cityWeather.getCurrently());
            case 1:
                return NextHoursFragment.newInstance(cityWeather.getHourly());
            case 2:
                return TwoDaysFragment.newInstance(cityWeather.getDaily().getDays().get(0), cityWeather.getDaily().getDays().get(1));
            case 3:
                return WeekFragment.newInstance(cityWeather.getDaily());
            case 4:
                return UpcomingDaysFragment.newInstance(cityWeather.getDaily());
            case 5:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return cityWeather == null ? 0 : 5;
    }
}