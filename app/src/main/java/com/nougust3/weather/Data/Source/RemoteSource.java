package com.nougust3.weather.Data.Source;

import com.nougust3.weather.Models.Forecast;

import io.reactivex.Single;

public interface RemoteSource {

        Single<Forecast> getCurrentForecast(String cityId);

}
