package com.weatherForecasting.backend.resultchecker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface HistoryResultRepository extends JpaRepository<HistoryResult, UUID> {
    List<HistoryResult> findAllByUsername(String username);
}
