package com.nougust3.weather.DI.Modules;

import com.nougust3.weather.DI.Scopes.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePresenterModule {

    private final String cityName;

    public HomePresenterModule(String cityName) {
        this.cityName = cityName;
    }

    @Provides
    @Presenter
    String provideCityName() {
        return cityName;
    }

}
