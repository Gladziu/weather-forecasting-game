package com.weatherForecasting.backend.forecast.repository;

import com.weatherForecasting.backend.forecast.dto.WeatherPredictionDTO;
import com.weatherForecasting.backend.forecast.model.WeatherPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherPredictionRepository extends JpaRepository<WeatherPrediction, Long> {
    List<WeatherPrediction> findByForecastDateLessThanEqual(LocalDate forecastDate);
    List<WeatherPrediction> findAllByUsername(String username);
}
