package com.weatherForecasting.backend.weatherpredictioncrud.repository;

import com.weatherForecasting.backend.weatherpredictioncrud.model.WeatherPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherPredictionRepository extends JpaRepository<WeatherPrediction, Long> {
    List<WeatherPrediction> findByForecastDateLessThanEqual(LocalDate forecastDate);
    List<WeatherPrediction> findAllByUsername(String username);
}
