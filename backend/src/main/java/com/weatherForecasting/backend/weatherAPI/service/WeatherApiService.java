package com.weatherForecasting.backend.weatherAPI.service;

import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;

public interface WeatherApiService {
    WeatherDTO getCurrentWeather(String location);
    WeatherDTO getHistoricalWeather(String location, String date, String hour);
}
