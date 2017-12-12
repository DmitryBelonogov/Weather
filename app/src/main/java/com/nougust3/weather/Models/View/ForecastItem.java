package com.nougust3.weather.Models.View;

import com.nougust3.weather.Models.ForecastList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForecastItem {

    public String date;
    public String weather;
    public String temp;

    public ForecastItem(ForecastList forecastList) {
        date = getDate(forecastList.getDtTxt());
        weather = forecastList.getWeather().get(0).getMain();
        temp = forecastList.getMain().getTemp() + "°";
    }

    private String getDate(String dtTxt) {
        Date d;
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm E", Locale.getDefault());

        try {
            d = oldFormat.parse(dtTxt);
        }
        catch (ParseException e) {
            return dtTxt;
        }

        return newFormat.format(d);
    }

}
