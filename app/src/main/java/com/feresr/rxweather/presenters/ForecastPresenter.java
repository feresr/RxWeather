package com.feresr.rxweather.presenters;

import android.content.Intent;
import android.util.Log;

import com.feresr.rxweather.domain.GetForecastUseCase;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.presenters.views.ForecastView;
import com.feresr.rxweather.presenters.views.View;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter {
    private GetForecastUseCase useCase;
    private Subscription forecastObservable;
    private ForecastView forecastView;

    @Inject
    public ForecastPresenter(GetForecastUseCase forecastUseCase) {
        this.useCase = forecastUseCase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (forecastObservable.isUnsubscribed()) {
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
        forecastObservable = useCase.execute().subscribe(new Subscriber<Lista>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(Lista lista) {
                forecastView.addForecast(lista.getWeather().get(0).getMain());
            }
        });
    }
}
