package com.weatherForecasting.backend.weatherpredictioncrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class WeatherPrediction {
    //TODO: dodać walidacje data musi byc przyszła
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String location;
    private double temperature;
    //@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date input failed. Format: YYYY-MM-DD.")
    @Future(message = "Data musi być w przyszłości")
    private LocalDate forecastDate;
    @Pattern(regexp = "^(?:(?:0[1-9]|1[0-9]):00|2[1-3]:00)$", message ="Hour input failed. Format: HH or H.")
    private String forecastHour;
    private LocalDate timeStamp;
}
