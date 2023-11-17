package com.weatherForecasting.backend.results.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weatherForecasting.backend.forecast.model.WeatherPrediction;
import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String location;
    private double temperature;
    private double realTemperature;
    private LocalDate forecastDate;
    private String forecastHour;
    private LocalDate timeStamp;
    private int points;

    public History() {
    }

    public History(WeatherPrediction forecast, WeatherDTO actual, int scored) {
        this.username = forecast.getUsername();
        this.location = forecast.getLocation();
        this.temperature = forecast.getTemperature();
        this.realTemperature = actual.getTemperature();
        this.forecastDate = forecast.getForecastDate();
        this.forecastHour = forecast.getForecastHour();
        this.timeStamp = forecast.getTimeStamp();
        this.points = scored;
    }
}
