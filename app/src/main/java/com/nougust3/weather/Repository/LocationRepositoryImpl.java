package com.nougust3.weather.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocationRepositoryImpl implements LocationRepository {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";
    private static final String SP_LOCATION = "location";
    private static final String SP_HISTORY = "history";

    private SharedPreferences sharedPreferences;

    @Inject
    LocationRepositoryImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Single<String> getLocation() {
        return Single.fromCallable(() -> sharedPreferences.getString(SP_LOCATION, ""));
    }
    @Override
    public Single<ArrayList<String>> getHistory() {
        return Single.fromCallable(this::getHistoryList);
    }

    @Override
    public Completable saveLocation(String cityName) {
        return Completable.fromAction(() -> {
            updateHistory(cityName);
            sharedPreferences.edit().putString(SP_LOCATION, cityName).apply();
        });
    }

    @Override
    public void updateHistory(String cityName) {
        ArrayList<String> historyList = getHistoryList();

        if(historyList == null) {
            historyList = new ArrayList<>();
        }

        if(historyList.contains(cityName)) {
            return;
        }

        historyList.add(String.valueOf(historyList.size() + 1) + cityName);
        if(historyList.size() > 4) {
            historyList = new ArrayList<>(historyList.subList(historyList.size() - 4, historyList.size()));
            for(int i = 0; i < historyList.size(); i++) {
                historyList.set(i, (Integer.valueOf(historyList.get(i).substring(0, 1)) - 1) + historyList.get(i).substring(1, historyList.get(i).length()));
            }
        }

        sharedPreferences.edit().putStringSet(SP_HISTORY, new HashSet<>(historyList)).apply();
    }

    private ArrayList<String> getHistoryList() {
        Set<String> historySet = sharedPreferences.getStringSet(SP_HISTORY, null);

        if(historySet == null) {
            return null;
        }
        else {
            ArrayList<String> historyList = new ArrayList<>();
            historyList.addAll(historySet);
            Collections.sort(historyList);

            return historyList;
        }
    }
}
