package com.weatherForecasting.backend.realweatherprovider.dto.apistructure;

public record Current(double temp_c, Condition condition, double wind_kph, String wind_dir, double pressure_mb, int humidity) {
}
