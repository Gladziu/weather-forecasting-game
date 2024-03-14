package com.weatherForecasting.backend.resultchecker;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class HistoryResult {
    @Id
    private UUID id;
    private String username;
    private String location;
    private double temperature;
    private double realTemperature;
    private LocalDate forecastDate;
    private int forecastHour;
    private LocalDate timeStamp;
    private int score;
}
