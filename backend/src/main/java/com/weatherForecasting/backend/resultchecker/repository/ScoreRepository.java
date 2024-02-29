package com.weatherForecasting.backend.resultchecker.repository;

import com.weatherForecasting.backend.resultchecker.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByUsername(String username);
}
