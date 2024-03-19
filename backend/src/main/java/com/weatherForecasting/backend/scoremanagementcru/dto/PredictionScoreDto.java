package com.weatherForecasting.backend.scoremanagementcru.dto;

import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;

public record PredictionScoreDto(CheckedPredictionDto checkedPredictionDto, int points) {
}
