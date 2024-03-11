package com.weatherForecasting.backend.realweatherprovider;

import java.net.URI;

class ApiUriGenerator {
    private static final String SPACE_CHARACTER_IN_URL = "%20";
    private static final String CURRENT_WEATHER_URL = "/current.json?key=";
    private static final String HISTORY_WEATHER_URL = "/history.json?key=";

    public static URI currentWeatherUri(String location, String apiKey, String apiUrl) {
        String locationParameter = replaceSpaces(location);
        return URI.create(apiUrl + CURRENT_WEATHER_URL + apiKey + "&q=" + locationParameter + "&aqi=no");
    }

    public static URI historicalWeatherUri(String location, String date, String apiKey, String apiUrl) {
        String locationParameter = replaceSpaces(location);
        return URI.create(apiUrl + HISTORY_WEATHER_URL + apiKey + "&q=" + locationParameter + "&dt=" + date);
    }

    private static String replaceSpaces(String location) {
        return location.replaceAll(" ", SPACE_CHARACTER_IN_URL);
    }
}
