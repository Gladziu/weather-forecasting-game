package com.weatherForecasting.backend.resultchecker.dto;

import java.time.LocalDate;

public record HistoryResultDto(String username, String location, double temperature, double realTemperature, LocalDate forecastDate, int forecastHour, LocalDate timeStamp, int score) {
}
