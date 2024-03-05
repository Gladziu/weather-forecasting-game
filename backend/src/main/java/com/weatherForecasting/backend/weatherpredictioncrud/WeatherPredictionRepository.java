package com.weatherForecasting.backend.weatherpredictioncrud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface WeatherPredictionRepository extends JpaRepository<WeatherPrediction, UUID> {
    List<WeatherPrediction> findByForecastDateLessThanEqual(LocalDate forecastDate);

    List<WeatherPrediction> findAllByUsername(String username);
}
