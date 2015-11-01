package com.feresr.rxweather.presenters;

import android.content.Intent;
import android.util.Log;

import com.feresr.rxweather.domain.GetForecastUseCase;

import com.feresr.rxweather.domain.GetTodayForecastUseCase;
import com.feresr.rxweather.domain.GetTodaysWeatherUseCase;
import com.feresr.rxweather.models.Day;
import com.feresr.rxweather.models.Hour;
import com.feresr.rxweather.models.Today;
import com.feresr.rxweather.presenters.views.ForecastView;
import com.feresr.rxweather.presenters.views.View;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter {
    private GetForecastUseCase forecastUseCase;
    private GetTodaysWeatherUseCase todaysWeatherUseCase;
    private GetTodayForecastUseCase todaysForecastUseCase;
    private Subscription forecastObservable;
    private Subscription todaysForecastObservable;
    private Subscription todayObservable;
    private ForecastView forecastView;

    @Inject
    public ForecastPresenter(GetForecastUseCase forecastUseCase, GetTodaysWeatherUseCase todaysWeatherUseCase, GetTodayForecastUseCase todaysForecastUseCase) {
        this.forecastUseCase = forecastUseCase;
        this.todaysWeatherUseCase = todaysWeatherUseCase;
        this.todaysForecastUseCase = todaysForecastUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (forecastObservable.isUnsubscribed()) {
            forecastObservable.unsubscribe();
        }

        if (todayObservable.isUnsubscribed()) {
            forecastObservable.unsubscribe();
        }

        if (todaysForecastObservable.isUnsubscribed()) {
            forecastObservable.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        forecastView = (ForecastView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        forecastObservable = forecastUseCase.execute().subscribe(new Subscriber<Day>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(Day day) {
                forecastView.addForecast(day);
            }
        });

        todayObservable = todaysWeatherUseCase.execute().subscribe(new Subscriber<Today>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(Today today) {
                forecastView.addToday(today);
            }
        });

        todaysForecastObservable = todaysForecastUseCase.execute().subscribe(new Subscriber<Hour>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(Hour hour) {
                forecastView.addTodayForecast(hour);
            }
        });
    }
}
