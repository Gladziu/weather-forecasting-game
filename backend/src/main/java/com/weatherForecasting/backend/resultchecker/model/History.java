package com.weatherForecasting.backend.resultchecker.model;

import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPrediction;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
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

    public History(WeatherPrediction forecast, RealWeatherDto actual, int scored) {
        this.username = forecast.getUsername();
        this.location = forecast.getLocation();
        this.temperature = forecast.getTemperature();
        this.realTemperature = actual.temperature();
        this.forecastDate = forecast.getForecastDate();
        this.forecastHour = String.valueOf(forecast.getForecastHour()); // TODO: REMEMBER THATH IT SHOULD BE INT
        this.timeStamp = forecast.getTimeStamp();
        this.points = scored;
    }
}
