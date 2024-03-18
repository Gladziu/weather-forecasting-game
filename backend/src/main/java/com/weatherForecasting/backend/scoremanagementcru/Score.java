package com.weatherForecasting.backend.scoremanagementcru;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Score {
    @Id
    private UUID id;
    private String username;
    private int points;
}
