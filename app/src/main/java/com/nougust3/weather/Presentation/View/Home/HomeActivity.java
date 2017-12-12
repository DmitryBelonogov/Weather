package com.nougust3.weather.Presentation.View.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.nougust3.weather.DI.DaggerHomePresenterComponent;
import com.nougust3.weather.DI.HomePresenterComponent;
import com.nougust3.weather.DI.Modules.HomePresenterModule;
import com.nougust3.weather.Models.ForecastList;
import com.nougust3.weather.Models.View.ForecastItem;
import com.nougust3.weather.Presentation.Presenter.HomePresenter;
import com.nougust3.weather.Presentation.View.Start.StartActivity;
import com.nougust3.weather.R;
import com.nougust3.weather.WeatherApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends MvpAppCompatActivity implements HomeView {

    private ProgressBar progressBar;
    private RelativeLayout summaryView;
    private LinearLayout detailsView;

    private ImageView weatherIcon;

    private TextView cityView;
    private TextView countryView;
    private TextView temperatureView;
    private TextView descriptionView;
    private TextView heatIndexView;
    private TextView humidityView;
    private TextView windView;
    private TextView pressureView;
    private TextView visibilityView;
    private TextView sunsetView;

    private LinearLayout weekForecast;

    @InjectPresenter
    HomePresenter homePresenter;

    @ProvidePresenter
    HomePresenter providePresenter() {
        String cityName = getIntent().getStringExtra("city_name");
        HomePresenterModule presenterModule = new HomePresenterModule(cityName);

        HomePresenterComponent presenterComponent = DaggerHomePresenterComponent.builder()
                .applicationComponent(WeatherApplication.getComponent())
                .homePresenterModule(presenterModule)
                .build();

        return presenterComponent.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        populate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_location) {
            homePresenter.onSetLocation();
        }

        return false;
    }

    private void init() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        progressBar = findViewById(R.id.progress_view);
        summaryView = findViewById(R.id.summary_view);
        detailsView = findViewById(R.id.details_view);

        weatherIcon = findViewById(R.id.icon_view);

        cityView = findViewById(R.id.city_view);
        countryView = findViewById(R.id.country_view);
        temperatureView = findViewById(R.id.temperature_view);
        descriptionView = findViewById(R.id.description_view);
        heatIndexView = findViewById(R.id.heat_index_view);
        humidityView = findViewById(R.id.humidity_view);
        windView = findViewById(R.id.wind_view);
        pressureView = findViewById(R.id.pressure_view);
        visibilityView = findViewById(R.id.visibility_view);
        sunsetView = findViewById(R.id.sunset_view);

        weekForecast = findViewById(R.id.week_forecast);
    }

    @Override
    public void showProgress() {
        summaryView.setVisibility(View.GONE);
        detailsView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        summaryView.setVisibility(View.VISIBLE);
        detailsView.setVisibility(View.VISIBLE);
    }

    private void populate() {
        homePresenter.onGetForecast();
    }

    @Override
    public void showForecast(Double temp) {
        temperatureView.setText(String.format(Locale.ENGLISH, "%s°", temp));
    }

    @Override
    public void setCityName(String cityName) {
        cityView.setText(cityName);
    }

    @Override
    public void setCountryName(String displayCountry) {
        countryView.setText(displayCountry);
    }

    @Override
    public void setDescription(String description) {
        description = description.substring(0, 1).toUpperCase() + description.substring(1, description.length());
        descriptionView.setText(description);
    }

    @Override
    public void setApparentTemp(Double temp) {
        heatIndexView.setText(String.format(Locale.ENGLISH, "%s°", temp));
    }

    @Override
    public void setHumidity(Integer humidity) {
        humidityView.setText(String.format(Locale.ENGLISH, "%s%%", humidity));
    }

    @Override
    public void setWind(Double speed) {
        windView.setText(String.format(Locale.ENGLISH, getString(R.string.wind_value), speed.intValue()));
    }

    @Override
    public void setPressure(Integer pressure) {
        pressureView.setText(String.format(Locale.ENGLISH, getString(R.string.pressure_value), pressure));
    }

    @Override
    public void setVisibility(int i) {
        visibilityView.setText(String.format(Locale.ENGLISH, getString(R.string.visibility_value), i));
    }

    @Override
    public void setSunset(String sunset) {
        sunsetView.setText(sunset);
    }

    @Override
    public void showLocationActivity() {
        Intent intent = new Intent(HomeActivity.this, StartActivity.class);

        intent.putExtra("reset", true);

        startActivity(intent);
    }

    @Override
    public void setWeekForecast(ArrayList<ForecastItem> forecastItems) {
        for(ForecastItem forecast: forecastItems) {
            View forecastView = View.inflate(this, R.layout.forecast_item, null);

            ((TextView) forecastView.findViewById(R.id.date)).setText(forecast.date);
            ((TextView) forecastView.findViewById(R.id.description)).setText(forecast.weather);
            ((TextView) forecastView.findViewById(R.id.temp)).setText(forecast.temp);

            weekForecast.addView(forecastView);
        }
    }

    @Override
    public void showNoNetworkLayout() {
        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
    }
}
