package com.nougust3.weather.Data;

import com.nougust3.weather.Models.Forecast;
import com.nougust3.weather.Models.ForecastWeek;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("data/2.5/weather?")
    Single<Forecast> getCurrentWeather(@Query("q") String cityName,
                                       @Query("appid") String appId,
                                       @Query("units") String units,
                                       @Query("lang") String lang);

    @GET("data/2.5/forecast?")
    Single<ForecastWeek> getWeekWeather(@Query("q") String cityName,
                                        @Query("appid") String appId,
                                        @Query("units") String units,
                                        @Query("lang") String lang);

}
