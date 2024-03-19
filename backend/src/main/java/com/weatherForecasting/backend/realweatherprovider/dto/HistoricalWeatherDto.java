package com.weatherForecasting.backend.realweatherprovider.dto;

import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Forecast;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Location;
import lombok.Builder;

@Builder
public record HistoricalWeatherDto(Location location, Forecast forecast, ErrorMessage errorMessage, boolean failure) {
    public boolean isFailure() {
        return failure;
    }
}