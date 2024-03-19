package com.weatherForecasting.backend.realweatherprovider.dto.apistructure;

public record Hour(String time, double temp_c, Condition condition, double wind_kph, String wind_dir, double pressure_mb, int humidity) {
}