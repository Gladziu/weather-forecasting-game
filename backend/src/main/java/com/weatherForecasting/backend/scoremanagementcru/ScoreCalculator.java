package com.weatherForecasting.backend.scoremanagementcru;

import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

class ScoreCalculator {
    private static final double ZERO_TEMPERATURE_DIFFERENCE = 0;
    private static final double AVERAGE_TEMPERATURE_DIFFERENCE = 2;
    private static final double MAXIMAL_TEMPERATURE_DIFFERENCE = 6;

    private static final int HIGHEST_MULTIPLIER = 10;
    private static final int AVERAGE_MULTIPLIER = 5;
    private static final int LOWEST_MULTIPLIER = 2;

    private static final int ZERO_POINTS = 0;

    public List<PredictionScoreDto> calculateScoredPoints(List<CheckedPredictionDto> checkedPredictions) {
        List<PredictionScoreDto> predictionsWithScore = new ArrayList<>();
        for (CheckedPredictionDto prediction : checkedPredictions) {
            double temperatureDifference = calculateTemperatureDifference(prediction);
            int daysDifference = calculateDaysDifference(prediction);
            int score = calculatePoints(daysDifference, temperatureDifference);
            predictionsWithScore.add(new PredictionScoreDto(prediction, score));
        }
        return predictionsWithScore;
    }

    private int calculateDaysDifference(CheckedPredictionDto prediction) {
        LocalDate predictionDate = prediction.weatherPrediction().forecastDate();
        LocalDate predictionTimeStamp = prediction.weatherPrediction().timeStamp();
        return (int) DAYS.between(predictionTimeStamp, predictionDate);
    }

    private double calculateTemperatureDifference(CheckedPredictionDto prediction) {
        double predictionTemp = prediction.weatherPrediction().temperature();
        double realTemp = prediction.weatherReport().temperature();
        return Math.abs(realTemp - predictionTemp);
    }

    private int calculatePoints(int daysBetween, double tempDiff) {
        if (tempDiff == ZERO_TEMPERATURE_DIFFERENCE) {
            return daysBetween * HIGHEST_MULTIPLIER;
        }
        if (tempDiff <= AVERAGE_TEMPERATURE_DIFFERENCE) {
            return daysBetween * AVERAGE_MULTIPLIER;
        }
        if (tempDiff <= MAXIMAL_TEMPERATURE_DIFFERENCE) {
            return daysBetween * LOWEST_MULTIPLIER;
        }
        return ZERO_POINTS;
    }

}
