package com.weatherForecasting.backend.results.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HistoryDTO {
    private long id;
    private String username;
    private String location;
    private double temperature;
    private double realTemperature;
    private String forecastDate;
    private String forecastHour;
    private String timeStamp;
    private int points;

}
