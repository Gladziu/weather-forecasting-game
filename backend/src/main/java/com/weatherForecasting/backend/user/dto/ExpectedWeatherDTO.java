package com.weatherForecasting.backend.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ExpectedWeatherDTO {
    private String username;
    private String location;
    private double temperature;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate forecastDate;
}
