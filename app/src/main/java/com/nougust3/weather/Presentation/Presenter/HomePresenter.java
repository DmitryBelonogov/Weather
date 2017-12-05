package com.nougust3.weather.Presentation.Presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.nougust3.weather.Data.Source.Remote.Exceptions.NoNetworkException;
import com.nougust3.weather.Domain.Forecast.ForecastInteractor;
import com.nougust3.weather.Models.Forecast;
import com.nougust3.weather.Presentation.Common.Rx.RxSchedulersProvider;
import com.nougust3.weather.Presentation.View.Home.HomeView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private ForecastInteractor forecastInteractor;
    private RxSchedulersProvider rxSchedulersProvider;
    private String cityName;

    @Inject
    public HomePresenter(ForecastInteractor forecastInteractor, RxSchedulersProvider rxSchedulersProvider, String cityName) {
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.forecastInteractor = forecastInteractor;
        this.cityName = cityName;
    }

    public void onGetForecast() {
        forecastInteractor.getForecast(cityName)
            .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
            .subscribe(this::onForecastLoadSuccess, this::onForecastLoadError);
    }

    private void onForecastLoadSuccess(Forecast forecast) {
        getViewState().showForecast(forecast.getMain().getTemp());
        getViewState().setCityName(forecast.getName());
        getViewState().setHeatIndex(forecast.getMain().getTemp());
        getViewState().setHumidity(forecast.getMain().getHumidity());
        getViewState().setWind(forecast.getWind().getSpeed());
        getViewState().setPressure(forecast.getMain().getPressure());
        getViewState().setVisibility(10);
        getViewState().setSunset(new SimpleDateFormat("HH:MM").format(forecast.getSys().getSunset()));
        getViewState().setDescription(forecast.getWeather().get(0).getDescription());
        getViewState().setCountryName((new Locale("", forecast.getSys().getCountry())).getDisplayCountry());
    }

    private void onForecastLoadError(Throwable throwable) {
        if(throwable instanceof NoNetworkException) {
            //getViewState().hideLoadingProgress();
            //getViewState().showNoNetworkLayout();
        }
    }

    public void onSetLocation() {
        getViewState().showLocationActivity();
    }
}

