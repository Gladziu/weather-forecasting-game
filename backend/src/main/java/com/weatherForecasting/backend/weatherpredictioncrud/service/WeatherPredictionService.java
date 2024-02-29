package com.weatherForecasting.backend.weatherpredictioncrud.service;

import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;

import java.util.List;

public interface WeatherPredictionService {
    void addPrediction(WeatherPredictionDTO weatherPredictionDTO);
    boolean deletePrediction(Long id);
    List<WeatherPredictionDTO> showPrediction(String userName);
}
