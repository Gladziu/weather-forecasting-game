package com.weatherForecasting.backend.realweatherprovider.dto;

public record ErrorMessage(String message) {
    public static final String LOCATION_NOT_FOUND = "location not found";
}
