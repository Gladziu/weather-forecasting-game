package com.weatherForecasting.backend.results.repository;

import com.weatherForecasting.backend.results.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByUsername(String username);
}
