package com.weatherForecasting.backend.realweatherprovider.dto;

import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Current;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Location;
import lombok.Builder;

@Builder
public record CurrentWeatherDto(Location location, Current current, ErrorMessage errorMessage, boolean failure) {
    public boolean isFailure() {
        return failure;
    }
}
