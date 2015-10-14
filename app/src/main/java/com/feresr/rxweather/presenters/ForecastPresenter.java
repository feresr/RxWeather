package com.feresr.rxweather.presenters;

import android.content.Context;
import android.content.Intent;

import com.feresr.rxweather.Models.List;
import com.feresr.rxweather.domain.GetForecastUseCase;
import com.feresr.rxweather.presenters.views.ForecastView;
import com.feresr.rxweather.presenters.views.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter {

    private Context context;
    private GetForecastUseCase useCase;
    private Subscription forecastSubscription;
    private ArrayList<List> lists;
    private ForecastView forecastView;

    @Inject
    public ForecastPresenter(Context context, GetForecastUseCase forecastUseCase) {
        this.context = context;
        this.useCase = forecastUseCase;
        lists = new ArrayList<>();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (forecastSubscription.isUnsubscribed()) {
            forecastSubscription.unsubscribe();
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
        forecastSubscription = useCase.execute().subscribe(new Action1<List>() {
            @Override
            public void call(List list) {
                lists.add(list);
                forecastView.addForecast(list.getWeather().get(0).getMain());
            }
        });
    }
}
