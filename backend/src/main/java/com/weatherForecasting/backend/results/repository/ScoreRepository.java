package com.weatherForecasting.backend.results.repository;

import com.weatherForecasting.backend.results.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByUsername(String username);
}
