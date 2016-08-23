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
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fernando on 6/11/2015.
 */
public class CitiesPresenter extends BasePresenter<CitiesView> implements SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CitiesAdapter.OnCitySelectedListener {

    private final static String TAG = CitiesPresenter.class.getSimpleName();

    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private GetCitiesUseCase getCitiesUseCase;
    private CitiesCallbackListener citiesCallbackListener;
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
            citiesCallbackListener = (CitiesCallbackListener) view.getActivity();
        } else {
            throw new RuntimeException(view.getActivity().toString()
                    + " must implement CitiesCallbackListener");
        }

        //1 fetch cities from db
        subscriptions.add(getCitiesUseCase.execute().filter(new Func1<City, Boolean>() {
            @Override
            public Boolean call(City city) {
                return cities.get(city.getId()) == null;
            }
            //2 show them
        }).doOnNext(new Action1<City>() {
            @Override
            public void call(City city) {
                city.setState(City.STATE_FETCHING);
                cities.put(city.getId(), city);
                view.addCity(city);
                //3 fetch weather data for each city
            }
        }).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                getCityWeatherUseCase.setFetchIfExpired(true);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(new CityForecastSubscriber()));
    }

    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);
        subscriptions.add(removeCityUseCase.execute().subscribe(new RemoveCitySubscriber()));
    }

    @Override
    public void onRefresh() {
        subscriptions.add(Observable.from(cities.values()).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(new CityForecastSubscriber()));
    }

    @Override
    public void onCitySelected(City city) {
        citiesCallbackListener.onCitySelected(city);
    }

    @Override
    public void onClick(View view) {
        citiesCallbackListener.onAddCityButtonSelected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this);
        subscriptions.unsubscribe();
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

    public interface CitiesCallbackListener {
        void onCitySelected(City city);

        void onAddCityButtonSelected();
    }

    public final class CityForecastSubscriber extends Subscriber<City> {
        @Override
        public void onCompleted() {
            view.hideLoadingIndicator();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.toString());
        }

        @Override
        public void onNext(City city) {
            city.setState(City.STATE_DONE);
            view.updateCity(city);
        }
    }

    public final class RemoveCitySubscriber extends Subscriber<City> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.toString());
        }

        @Override
        public void onNext(City city) {
            cities.remove(city.getId());
            Log.d(TAG, "city removed: " + city.toString());
        }
    }
}