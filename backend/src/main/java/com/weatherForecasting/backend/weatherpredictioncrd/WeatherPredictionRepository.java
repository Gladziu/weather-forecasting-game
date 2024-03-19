package com.weatherForecasting.backend.weatherpredictioncrd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
interface WeatherPredictionRepository extends JpaRepository<WeatherPrediction, UUID> {
    List<WeatherPrediction> findByForecastDateGreaterThanEqual(LocalDate forecastDate);

    List<WeatherPrediction> findAllByUsername(String username);
}
