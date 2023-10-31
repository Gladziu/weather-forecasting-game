package com.weatherForecasting.backend.weatherAPI.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {
    private String time;
    private String city;
    private String country;
    private double temperature;
    private double windSpeed;
    private String windDirectory;
    private double pressure;
    private int humidity;
    private Condition condition;
    private ErrorMessage error;
}
