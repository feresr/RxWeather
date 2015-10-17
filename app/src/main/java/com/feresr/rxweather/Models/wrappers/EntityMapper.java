package com.feresr.rxweather.models.wrappers;

import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.Weather;

/**
 * Created by Fernando on 17/10/2015.
 */
public interface EntityMapper {
    FiveDays convert(FiveDaysWrapper wrapper);
    FiveDaysWrapper convert(FiveDays fiveDays);
    Lista convert(ListaWrapper listaWrapper);
    ListaWrapper convert(Lista listaWrapper);
    Weather convert(WeatherWrapper weatherWrapper);
}
