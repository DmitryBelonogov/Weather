package com.nougust3.weather.Repository;

import com.nougust3.weather.Constants;
import com.nougust3.weather.Data.WeatherApiService;
import com.nougust3.weather.Models.Forecast;
import com.nougust3.weather.Models.ForecastWeek;

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

    @Override
    public Single<ForecastWeek> getWeekForecast(String cityName) {
        return weatherApiService.getWeekWeather(cityName, Constants.APP_ID, "metric", "ru");
    }

}
