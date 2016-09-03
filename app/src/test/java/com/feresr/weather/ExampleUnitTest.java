package com.feresr.weather;

import android.content.Context;
import android.content.res.Resources;

import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.repository.DiskDataSource;
import com.feresr.weather.storage.SQLiteStorage;
import com.feresr.weather.utils.IconManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mockContext;
    @Mock
    Resources mockResources;
    @Mock
    CityWeather mockCityWeather;
    @Mock
    City city;

    @Test
    public void iconManager_getIconResource() throws Exception {
        when(mockContext.getString(R.string.rain))
                .thenReturn("&#xf019;");

        assertEquals(IconManager.getIconResource("rain", mockContext), "&#xf019;");
    }

    @Test
    public void iconManager_getColorResource() {
        ;
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockResources.getColor(R.color.rain)).thenReturn(2);

        assertEquals(IconManager.getColorResource("rain", mockContext), 2);
    }

    @Test
    public void DiskDataSource_getForecast() {
        SQLiteStorage cache = new SQLiteStorage(mockContext);
        DiskDataSource dataSource = new DiskDataSource(cache);
        final City city = mock(City.class);
        TestSubscriber<City> testSubscriber = new TestSubscriber<>();
        dataSource.getForecast(city).subscribe(testSubscriber);
        testSubscriber.assertReceivedOnNext(Collections.singletonList(city));
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    @Test
    public void SimpleCache_isDataExpired() {

    }
}