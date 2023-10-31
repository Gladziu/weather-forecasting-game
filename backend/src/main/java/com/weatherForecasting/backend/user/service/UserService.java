package com.weatherForecasting.backend.user.service;

import com.weatherForecasting.backend.user.dto.ExpectedWeatherDTO;
import com.weatherForecasting.backend.user.model.ExpectedWeather;

import java.util.List;

public interface UserService {
    void setUserForecast(ExpectedWeatherDTO expectedWeatherDTO);
    boolean deleteUserForecast(Long id);
}
