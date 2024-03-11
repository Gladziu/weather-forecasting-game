package com.weatherForecasting.backend.weatherpredictioncrud.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WeatherPredictionDto(UUID id, String username, String location, double temperature, LocalDate forecastDate, int forecastHour, LocalDate timeStamp) {
    public WeatherPredictionDto(String username, String location, double temperature, LocalDate forecastDate, int forecastHour) {
        this(null, username, location, temperature, forecastDate, forecastHour, null);
    }
}
