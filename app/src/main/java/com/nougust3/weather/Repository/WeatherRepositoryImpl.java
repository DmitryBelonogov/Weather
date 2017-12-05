package com.nougust3.weather.Repository;

import com.nougust3.weather.Constants;
import com.nougust3.weather.Data.WeatherApiService;
import com.nougust3.weather.Models.Forecast;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private WeatherApiService weatherApiService;

    @Inject
    WeatherRepositoryImpl(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Override
    public Single<Forecast> getForecast(String cityName) {
        return weatherApiService.getCurrentWeather(cityName, Constants.APP_ID, "metric", "ru");
    }

}
