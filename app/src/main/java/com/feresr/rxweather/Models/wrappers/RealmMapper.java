package com.feresr.rxweather.models.wrappers;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.Weather;

import javax.inject.Inject;

import io.realm.RealmList;

/**
 * Created by Fernando on 17/10/2015.
 */
public class RealmMapper implements EntityMapper {

    @Inject
    public RealmMapper() {
        super();
    }

    @Override
    public FiveDays convert(FiveDaysWrapper wrapper) {
        FiveDays fiveDays = new FiveDays();
        for (int i = 0; i < wrapper.getLista().size(); i++) {
            fiveDays.getLista().add(convert(wrapper.getLista().get(i)));
        }

        City city = new City();
        city.setCountry("Aus");
        city.setName("Sydney");
        fiveDays.setCity(city);

        return fiveDays;
    }

    @Override
    public FiveDaysWrapper convert(FiveDays fiveDays) {
        return null;
    }

    @Override
    public Lista convert(ListaWrapper listaWrapper) {
        Lista lista = new Lista();
        for (int i = 0; i < listaWrapper.getWeather().size(); i++) {
            lista.getWeather().add(convert(listaWrapper.getWeather().get(i)));
        }
        return lista;
    }

    @Override
    public ListaWrapper convert(Lista listaWrapper) {
        ListaWrapper wrapper = new ListaWrapper();
        RealmList<WeatherWrapper> realmList = new RealmList<WeatherWrapper>();
        for (int i = 0; i < listaWrapper.getWeather().size(); i++) {
            WeatherWrapper weatherWrapper = new WeatherWrapper();
            weatherWrapper.setMain(listaWrapper.getWeather().get(i).getMain());
            weatherWrapper.setDescription(listaWrapper.getWeather().get(i).getDescription());
            weatherWrapper.setId(listaWrapper.getWeather().get(i).getId());
            weatherWrapper.setIcon(listaWrapper.getWeather().get(i).getIcon());
            realmList.add(weatherWrapper);
        }
        wrapper.setWeather(realmList);
        return wrapper;
    }

    @Override
    public Weather convert(WeatherWrapper weatherWrapper) {
        Weather weather = new Weather();
        weather.setMain(weatherWrapper.getMain());
        weather.setDescription(weatherWrapper.getDescription());
        weather.setId(weatherWrapper.getId());
        weather.setIcon(weatherWrapper.getIcon());
        return weather;
    }
}
