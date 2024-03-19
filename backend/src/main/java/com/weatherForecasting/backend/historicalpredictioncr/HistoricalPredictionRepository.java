package com.weatherForecasting.backend.historicalpredictioncr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface HistoricalPredictionRepository extends JpaRepository<PredictionHistory, UUID> {
    List<PredictionHistory> findAllByUsername(String username);
}
