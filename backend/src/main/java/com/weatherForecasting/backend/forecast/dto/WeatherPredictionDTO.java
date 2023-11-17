package com.weatherForecasting.backend.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherPredictionDTO {
    private long id;
    private String username;
    private String location;
    private double temperature;
    private String forecastDate;
    private String forecastHour;
}
