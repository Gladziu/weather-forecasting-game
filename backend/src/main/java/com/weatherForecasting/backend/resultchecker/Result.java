package com.weatherForecasting.backend.resultchecker;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Result {
    @Id
    private UUID id;
    private String username;
    private int score;
}
