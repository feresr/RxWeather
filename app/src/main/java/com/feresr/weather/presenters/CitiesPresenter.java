package com.feresr.weather.presenters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.feresr.weather.NetworkListener;
import com.feresr.weather.NetworkReceiver;
import com.feresr.weather.UI.CitiesAdapter;
import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.SettingsActivity;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.CitiesView;
import com.feresr.weather.storage.SimpleCache;
import com.feresr.weather.usecase.GetCitiesUseCase;
import com.feresr.weather.usecase.GetCityForecastUseCase;
import com.feresr.weather.usecase.RemoveCityUseCase;
import com.feresr.weather.usecase.SaveCityUseCase;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

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
public class CitiesPresenter extends BasePresenter<CitiesView> implements NetworkListener, android.view.View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private CitiesAdapter citiesAdapter;
    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private CompositeSubscription subscriptions;
    private GetCitiesUseCase getCitiesUseCase;
    private FragmentInteractionsListener fragmentInteractionListener;
    private NetworkReceiver networkReceiver;
    private Context context;
    private UpdatesReceiver updatesReceiver;


    @Inject
    public CitiesPresenter(Context context,
                           GetCitiesUseCase getCitiesUseCase,
                           GetCityForecastUseCase getCityForecastUseCase,
                           RemoveCityUseCase removeCityUseCase,
                           CitiesAdapter citiesAdapter) {
        super();
        this.getCityWeatherUseCase = getCityForecastUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.removeCityUseCase = removeCityUseCase;
        this.subscriptions = new CompositeSubscription();
        this.citiesAdapter = citiesAdapter;
        networkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver.setListener(this);
        context.registerReceiver(networkReceiver, intentFilter);
        this.context = context;

        updatesReceiver = new UpdatesReceiver();
        IntentFilter updatesIntentFilter = new IntentFilter("com.feresr.weather.UPDATE_WEATHER_DATA");
        context.registerReceiver(updatesReceiver, updatesIntentFilter);
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent("com.feresr.weather.UPDATE_WEATHER_DATA"),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
            Intent intent = new Intent("com.feresr.weather.UPDATE_WEATHER_DATA");
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SimpleCache.REFRESH_TIME,
                    SimpleCache.REFRESH_TIME, pi);
        }
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onCreate() {
        reloadCities();
    }

    private void reloadCities() {
        Subscription subscription = getCitiesUseCase.execute().doOnNext(new Action1<List<City>>() {
            @Override
            public void call(List<City> cities) {
                //Add all cities to view
                view.addCities(cities);
            }
        }).flatMapIterable(new Func1<List<City>, Iterable<City>>() {
            @Override
            public Iterable<City> call(List<City> cities) {
                return cities;
            }
        }).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(new Subscriber<City>() {
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
        });

        subscriptions.add(subscription);
    }

    @Override
    public void onDestroy() {
        networkReceiver.setListener(null);
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this);
        context.unregisterReceiver(networkReceiver);
        context.unregisterReceiver(updatesReceiver);
        subscriptions.unsubscribe();
    }

    public void addNewCity(final City city) {
    }

    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);

        //Does not touch UI, let it run on the background and un-subscribe by itself
        removeCityUseCase.execute().subscribe(new Subscriber<City>() {
            @Override
            public void onCompleted() {
                this.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                if (e != null) {
                    Log.e("error", e.toString());
                }
            }

            @Override
            public void onNext(City city) {
                Log.e("onRemoveCity", "city removed");
            }
        });
    }

    @Override
    public void onClick(android.view.View v) {
        fragmentInteractionListener.onAddCityButtonSelected();
    }

    public void setFragmentInteractionListener(FragmentInteractionsListener fragmentInteractionListener) {
        this.fragmentInteractionListener = fragmentInteractionListener;
    }

   /* @Override
    public void onItemClick(android.view.View view, int position) {
        fragmentInteractionListener.onCitySelected(citiesAdapter.getCities().get(position));
    }*/

    @Override
    public void onNetworkStateChanged(boolean online) {
        if (false && online) {
            subscriptions.add(Observable.from(citiesAdapter.getCities()).flatMap(new Func1<City, Observable<City>>() {
                @Override
                public Observable<City> call(City city) {
                    if (city.getCityWeather() == null) {
                        getCityWeatherUseCase.setCity(city);
                        return getCityWeatherUseCase.execute();
                    }
                    return null;
                }
            }).subscribe(new Subscriber<City>() {
                @Override
                public void onCompleted() {
                    subscriptions.remove(this);
                    this.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    if (e != null) {
                        Log.e(this.getClass().getSimpleName(), e.toString());
                    }
                }

                @Override
                public void onNext(City city) {
                    city.setState(City.STATE_DONE);
                    view.updateCity(city);
                }
            }));
        }
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
        subscriptions.add(Observable.from(citiesAdapter.getCities()).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                getCityWeatherUseCase.setFetchIfExpired(true);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(new Subscriber<City>() {
            @Override
            public void onCompleted() {
                subscriptions.remove(this);
                this.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                if (e != null) {
                    Log.e(this.getClass().getSimpleName(), e.toString());
                }
            }

            @Override
            public void onNext(City city) {
                city.setState(City.STATE_DONE);
                view.updateCity(city);
            }
        }));
    }

    public class UpdatesReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            reloadCities();
        }
    }
}