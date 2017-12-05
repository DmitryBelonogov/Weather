package com.nougust3.weather.DI;

import com.nougust3.weather.DI.Modules.StartPresenterModule;
import com.nougust3.weather.DI.Scopes.Presenter;
import com.nougust3.weather.Presentation.Presenter.StartPresenter;

import dagger.Component;

@Presenter
@Component(dependencies = ApplicationComponent.class, modules = StartPresenterModule.class)
public interface StartPresenterComponent {

    StartPresenter getPresenter();

}
