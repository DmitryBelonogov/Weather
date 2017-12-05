package com.nougust3.weather;

import android.app.Application;
import android.content.Context;

import com.nougust3.weather.DI.ApplicationComponent;
import com.nougust3.weather.DI.DaggerApplicationComponent;
import com.nougust3.weather.DI.Modules.ApplicationModule;
import com.nougust3.weather.DI.Modules.DataModule;

public class WeatherApplication extends Application {

    private static ApplicationComponent applicationComponent;
    private static WeatherApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        applicationComponent = buildComponent();
    }

    public ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public static Context getInstance() {
        return instance;
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

}
