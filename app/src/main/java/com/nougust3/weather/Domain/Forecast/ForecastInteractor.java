package com.nougust3.weather.Domain.Forecast;

import com.nougust3.weather.Models.Forecast;
import com.nougust3.weather.Repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class ForecastInteractor {

    private WeatherRepository weatherRepository;

    @Inject
    public ForecastInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Single<Forecast> getForecast(String cityName) {
        return weatherRepository.getForecast(cityName);
    }

}
