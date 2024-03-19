package com.weatherForecasting.backend.scoremanagementcru;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface ScoreManagementRepository extends JpaRepository<Score, UUID> {
    Optional<Score> findByUsername(String username);
}
