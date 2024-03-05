package com.weatherForecasting.backend.weatherpredictioncrud.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WeatherPredictionDTO(UUID id, String username, String location, double temperature, String forecastDate, String forecastHour, LocalDate timeStamp) {
    public WeatherPredictionDTO(String username, String location, double temperature, String forecastDate, String forecastHour) {
        this(UUID.randomUUID(), username, location, temperature, forecastDate, forecastHour, null);
    }
}
