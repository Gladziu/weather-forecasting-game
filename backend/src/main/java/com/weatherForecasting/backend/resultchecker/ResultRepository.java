package com.weatherForecasting.backend.resultchecker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface ResultRepository extends JpaRepository<Result, UUID> {
    Optional<Result> findByUsername(String username);
}
