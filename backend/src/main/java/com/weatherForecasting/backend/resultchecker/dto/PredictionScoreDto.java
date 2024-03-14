package com.weatherForecasting.backend.resultchecker.dto;

public record PredictionScoreDto(CheckedPredictionDto checkedPredictionDto, int points) {
}
