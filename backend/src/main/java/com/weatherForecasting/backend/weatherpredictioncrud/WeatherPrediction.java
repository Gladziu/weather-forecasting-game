package com.weatherForecasting.backend.weatherpredictioncrud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Builder
public class WeatherPrediction {
    @Id
    private UUID id;
    private String username;
    private String location;
    private double temperature;
    private LocalDate forecastDate;
    private String forecastHour;
    private LocalDate timeStamp;
}
