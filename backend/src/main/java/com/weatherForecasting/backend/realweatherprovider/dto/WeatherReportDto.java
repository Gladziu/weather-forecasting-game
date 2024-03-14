package com.weatherForecasting.backend.realweatherprovider.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WeatherReportDto(LocalDate date, int hour, double temperature, boolean failure) {
    public boolean isFailure() {
        return failure;
    }
}
