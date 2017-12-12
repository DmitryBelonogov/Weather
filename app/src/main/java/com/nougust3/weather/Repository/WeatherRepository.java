package com.nougust3.weather.Repository;

import com.nougust3.weather.Models.Forecast;
import com.nougust3.weather.Models.ForecastWeek;

import io.reactivex.Single;

public interface WeatherRepository {

    Single<Forecast> getForecast(String cityName);

    Single<ForecastWeek> getWeekForecast(String cityName);

}
