package com.weatherForecasting.backend.historicalpredictioncr.dto;

import java.time.LocalDate;

public record PredictionHistoryDto(String username, String location, double temperature, double realTemperature, LocalDate forecastDate, int forecastHour, LocalDate timeStamp, int score) {
}
