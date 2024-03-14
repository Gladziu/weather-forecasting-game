package com.weatherForecasting.backend.resultchecker.dto;

import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

public record CheckedPredictionDto(WeatherPredictionDto weatherPrediction, WeatherReportDto weatherReport) {
}
