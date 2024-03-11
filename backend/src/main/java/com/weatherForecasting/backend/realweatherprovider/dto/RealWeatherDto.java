package com.weatherForecasting.backend.realweatherprovider.dto;

import lombok.Builder;

@Builder
public record RealWeatherDto(String time, String city, String country, double temperature, double windSpeed, String windDirectory, double pressure, int humidity, ErrorMessage error) {
}
