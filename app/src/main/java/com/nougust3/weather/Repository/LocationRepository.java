package com.nougust3.weather.Repository;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface LocationRepository {

    Single<String> getLocation();

    Single<ArrayList<String>> getHistory();

    Completable saveLocation(String cityName);

    void updateHistory(String cityName);
}
