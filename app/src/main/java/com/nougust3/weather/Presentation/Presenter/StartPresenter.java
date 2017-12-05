package com.nougust3.weather.Presentation.Presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.nougust3.weather.Data.Source.Remote.Exceptions.NoNetworkException;
import com.nougust3.weather.Domain.Location.LocationInteractor;
import com.nougust3.weather.Presentation.Common.Rx.RxSchedulersProvider;
import com.nougust3.weather.Presentation.View.Start.StartView;

import java.util.ArrayList;

import javax.inject.Inject;

@InjectViewState
public class StartPresenter extends MvpPresenter<StartView> {

    private LocationInteractor locationInteractor;
    private RxSchedulersProvider rxSchedulersProvider;

    @Inject
    public StartPresenter(LocationInteractor locationInteractor, RxSchedulersProvider rxSchedulersProvider) {
        this.rxSchedulersProvider = rxSchedulersProvider;
        this.locationInteractor = locationInteractor;
    }

    public void onCheckLocation() {
        locationInteractor.getLocation()
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onLocationLoadSuccess, this::onLocationLoadError);
    }

    private void onHistoryLoadError(Throwable throwable) {
        if(throwable instanceof NoNetworkException) {
            throwable.printStackTrace();
        }
    }

    private void onHistoryLoadSuccess(ArrayList<String> cities) {
        getViewState().showHistory(cities);
    }

    private void onLocationLoadError(Throwable throwable) {
        if(throwable instanceof NoNetworkException) {
            throwable.printStackTrace();
        }
    }

    private void onLocationLoadSuccess(String cityName) {
        if(cityName.length() > 0) {
            getViewState().loadForecast(cityName);
        }
    }

    public void onSetCityName(String cityName) {
        locationInteractor.setLocation(cityName)
                .compose(rxSchedulersProvider.getIoToMainTransformerCompletableCompletable())
                .subscribe(() -> onLocationLoadSuccess(cityName));
    }

    public void onShowHistory() {
        locationInteractor.getHistory()
                .compose(rxSchedulersProvider.getIoToMainTransformerSingle())
                .subscribe(this::onHistoryLoadSuccess, this::onHistoryLoadError);
    }
}
