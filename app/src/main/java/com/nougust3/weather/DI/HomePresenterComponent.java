package com.nougust3.weather.DI;

import com.nougust3.weather.DI.Modules.HomePresenterModule;
import com.nougust3.weather.DI.Scopes.Presenter;
import com.nougust3.weather.Presentation.Presenter.HomePresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = HomePresenterModule.class)
public interface HomePresenterComponent {

    HomePresenter getPresenter();

}