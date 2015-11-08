package com.feresr.rxweather.presenters;

import android.os.Bundle;
import android.util.Log;

import com.feresr.rxweather.domain.GetCitiesUseCase;
import com.feresr.rxweather.domain.GetCityForecastUseCase;
import com.feresr.rxweather.domain.RemoveCityUseCase;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.presenters.views.View;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fernando on 6/11/2015.
 */
public class CitiesPresenter implements Presenter {

    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private CitiesView citiesView;
    private CompositeSubscription subscriptions;
    private GetCitiesUseCase getCitiesUseCase;

    @Inject
    public CitiesPresenter(GetCitiesUseCase getCitiesUseCase, GetCityForecastUseCase getCityForecastUseCase, RemoveCityUseCase removeCityUseCase) {
        super();
        this.getCityWeatherUseCase = getCityForecastUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.removeCityUseCase = removeCityUseCase;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        citiesView = (CitiesView) v;
    }

    @Override
    public void attachIncomingArg(Bundle intent) {

    }


    @Override
    public void onCreate() {

        Subscription subscription = getCitiesUseCase.execute().subscribe(new Subscriber<List<City>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(final List<City> cities) {
                citiesView.addCities(cities);
                for (City city : cities) {
                    getCityWeatherUseCase.setLatLon(city.getLat().toString(), city.getLon().toString(), city.getId());
                    getCityWeather(city);
                }
            }
        });

        subscriptions.add(subscription);
    }

    public void addNewCity(final City city) {
        citiesView.addCity(city);
        getCityWeatherUseCase.setLatLon(city.getLat().toString(), city.getLon().toString(), city.getId());

        getCityWeather(city);
    }

    private void getCityWeather(final City city) {
        subscriptions.add(getCityWeatherUseCase.execute().subscribe(new Subscriber<CityWeather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(CityWeather cityWeather) {
                city.setCityWeather(cityWeather);
                citiesView.updateCity(city);
            }
        }));
    }

    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);
        subscriptions.add(removeCityUseCase.execute().subscribe());
    }
}
