package com.weatherForecasting.backend.user.repository;

import com.weatherForecasting.backend.user.model.ExpectedWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectedWeatherRepository extends JpaRepository<ExpectedWeather, Long> {
}
