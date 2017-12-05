package com.nougust3.weather.Data.Source.Remote;

import com.nougust3.weather.Data.Source.RemoteSource;
import com.nougust3.weather.Data.WeatherApiService;
import com.nougust3.weather.Models.Forecast;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRemoteSource implements RemoteSource {

    private WeatherApiService weatherApiService;

    @Inject
    public WeatherRemoteSource(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Override
    public Single<Forecast> getCurrentForecast(String cityId) {
        return null;
    }
}
