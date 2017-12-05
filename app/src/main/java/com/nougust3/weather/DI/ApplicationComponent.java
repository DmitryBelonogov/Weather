package com.nougust3.weather.DI;

import com.nougust3.weather.DI.Modules.ApplicationModule;
import com.nougust3.weather.DI.Modules.DataModule;
import com.nougust3.weather.Repository.LocationRepository;
import com.nougust3.weather.Repository.WeatherRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    WeatherRepository provideWeatherRepository();
    LocationRepository provideLocationRepository();

}