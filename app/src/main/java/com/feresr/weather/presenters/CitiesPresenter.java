package com.feresr.weather.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.feresr.weather.UI.CitiesAdapter;
import com.feresr.weather.UI.SettingsActivity;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.CitiesView;
import com.feresr.weather.usecase.GetCitiesUseCase;
import com.feresr.weather.usecase.GetCityForecastUseCase;
import com.feresr.weather.usecase.RemoveCityUseCase;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fernando on 6/11/2015.
 */
public class CitiesPresenter extends BasePresenter<CitiesView> implements SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CitiesAdapter.OnCitySelectedListener {

    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private GetCitiesUseCase getCitiesUseCase;
    private CitiesCallbackListener activity;
    private CompositeSubscription subscriptions;
    private Context context;
    private HashMap<String, City> cities = new HashMap<>();

    @Inject
    public CitiesPresenter(Context context,
                           GetCitiesUseCase getCitiesUseCase,
                           GetCityForecastUseCase getCityForecastUseCase,
                           RemoveCityUseCase removeCityUseCase) {
        super();
        this.context = context;
        this.getCityWeatherUseCase = getCityForecastUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.removeCityUseCase = removeCityUseCase;
        this.subscriptions = new CompositeSubscription();

        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (view.getActivity() instanceof CitiesCallbackListener) {
            activity = (CitiesCallbackListener) view.getActivity();
        } else {
            throw new RuntimeException(view.getActivity().toString()
                    + " must implement CitiesCallbackListener");
        }

        loadCities();
    }

    private void loadCities() {
        Subscriber<City> subscriber = new Subscriber<City>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                //Could not fetch weather
                if (e != null) {
                    Log.e(this.getClass().getSimpleName(), e.toString());
                }
            }

            @Override
            public void onNext(City city) {
                city.setState(City.STATE_DONE);
                view.updateCity(city);
            }
        };

        Subscription subscription = getCitiesUseCase.execute().filter(new Func1<City, Boolean>() {
            @Override
            public Boolean call(City city) {
                return cities.get(city.getId()) == null;
            }
        }).doOnNext(new Action1<City>() {
            @Override
            public void call(City city) {
                city.setState(City.STATE_FETCHING);
                cities.put(city.getId(), city);
                view.addCity(city);
            }
        }).filter(new Func1<City, Boolean>() {
            @Override
            public Boolean call(City city) {
                return city.getCityWeather() == null;
            }
        }).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                getCityWeatherUseCase.setFetchIfExpired(true);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(subscriber);

        subscriptions.add(subscription);
    }


    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);

        subscriptions.add(removeCityUseCase.execute().subscribe(new Subscriber<City>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e != null) {
                    Log.e("error", e.toString());
                }
            }

            @Override
            public void onNext(City city) {
                cities.remove(city.getId());
                Log.e("onRemoveCity", "city removed");
            }
        }));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case SettingsActivity.PREF_UNIT:
                if (sharedPreferences.getString(key, "celsius").equals("celsius")) {
                    view.showTemperatureInCelsius();
                } else {
                    view.showTemperatureInFahrenheit();
                }
                break;
            case SettingsActivity.GRIDVIEW:
                if (sharedPreferences.getBoolean(key, false)) {
                    view.setSetColumns(2);
                } else {
                    view.setSetColumns(1);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadCities();
    }

    @Override
    public void onCitySelected(City city) {
        activity.onCitySelected(city);
    }

    @Override
    public void onClick(View view) {
        activity.onAddCityButtonSelected();
    }

    @Override
    public void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this);
        subscriptions.unsubscribe();
    }

    public interface CitiesCallbackListener {
        void onCitySelected(City city);

        void onAddCityButtonSelected();
    }
}