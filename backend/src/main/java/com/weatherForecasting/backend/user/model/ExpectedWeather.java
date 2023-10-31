package com.weatherForecasting.backend.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ExpectedWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String location;
    private double temperature;
    @Temporal(TemporalType.DATE)
    private LocalDate forecastDate;
    private LocalDate timeStamp;
}
