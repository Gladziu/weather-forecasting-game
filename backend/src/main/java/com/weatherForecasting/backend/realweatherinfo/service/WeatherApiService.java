package com.weatherForecasting.backend.realweatherinfo.service;

import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;

public interface WeatherApiService {
    WeatherDTO getCurrentWeather(String location);
    WeatherDTO getHistoricalWeather(String location, String date, String hour);
}
