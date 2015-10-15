package com.feresr.rxweather.presenters;

import android.content.Intent;

import com.feresr.rxweather.models.List;
import com.feresr.rxweather.domain.GetForecastUseCase;
import com.feresr.rxweather.presenters.views.ForecastView;
import com.feresr.rxweather.presenters.views.View;

import java.util.ArrayList;

import javax.inject.Inject;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter {
    private GetForecastUseCase useCase;
    private Subscription forecastObservable;
    private ArrayList<List> lists;
    private ForecastView forecastView;

    @Inject
    public ForecastPresenter(GetForecastUseCase forecastUseCase) {
        this.useCase = forecastUseCase;
        lists = new ArrayList<>();
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
        if (lists.isEmpty()) {
            forecastObservable = useCase.execute().cache().subscribe(new Action1<List>() {
                @Override
                public void call(List list) {
                    lists.add(list);
                    forecastView.addForecast(list.getWeather().get(0).getMain());
                }
            });
        } else {
            forecastView.addForecast(lists.get(0).toString());
        }
    }
}
