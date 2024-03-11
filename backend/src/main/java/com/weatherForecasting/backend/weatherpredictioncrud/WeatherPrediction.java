package com.weatherForecasting.backend.weatherpredictioncrud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPrediction {
    @Id
    private UUID id;
    private String username;
    private String location;
    private double temperature;
    private LocalDate forecastDate;
    private int forecastHour;
    private LocalDate timeStamp;
}
