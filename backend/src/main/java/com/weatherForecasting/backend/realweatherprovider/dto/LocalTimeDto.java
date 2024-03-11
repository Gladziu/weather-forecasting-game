package com.weatherForecasting.backend.realweatherprovider.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LocalTimeDto(LocalDate date, boolean failure) {
    public boolean hasExists() {
        return !failure;
    }
}
