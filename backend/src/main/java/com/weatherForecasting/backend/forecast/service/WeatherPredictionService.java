package com.weatherForecasting.backend.forecast.service;

import com.weatherForecasting.backend.forecast.dto.WeatherPredictionDTO;

import java.util.List;

public interface WeatherPredictionService {
    void addPrediction(WeatherPredictionDTO weatherPredictionDTO);
    boolean deletePrediction(Long id);
    List<WeatherPredictionDTO> showPrediction(String userName);
}
