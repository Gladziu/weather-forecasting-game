package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.resultchecker.dto.PredictionScoreDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

class ResultCalculator {
    private static final double ZERO_TEMPERATURE_DIFFERENCE = 0;
    private static final double MINIMAL_TEMPERATURE_DIFFERENCE = 1;
    private static final double AVERAGE_TEMPERATURE_DIFFERENCE = 3;
    private static final double MAXIMAL_TEMPERATURE_DIFFERENCE = 10;

    private static final int HIGHEST_MULTIPLIER = 4;
    private static final int AVERAGE_MULTIPLIER = 2;

    public static List<PredictionScoreDto> calculateScoredPoints(List<CheckedPredictionDto> checkedPredictions) {
        List<PredictionScoreDto> predictionsWithScore = new ArrayList<>();
        for (CheckedPredictionDto prediction : checkedPredictions) {
            double temperatureDifference = calculateTemperatureDifference(prediction);
            int daysDifference = calculateDaysDifference(prediction);
            int score = calculatePoints(daysDifference, temperatureDifference);
            predictionsWithScore.add(new PredictionScoreDto(prediction, score));
        }
        return predictionsWithScore;
    }

    private static int calculateDaysDifference(CheckedPredictionDto prediction) {
        LocalDate predictionDate = prediction.weatherPrediction().forecastDate();
        LocalDate predictionTimeStamp = prediction.weatherPrediction().timeStamp();
        return (int) DAYS.between(predictionTimeStamp, predictionDate);
    }

    private static double calculateTemperatureDifference(CheckedPredictionDto prediction) {
        double predictionTemp = prediction.weatherPrediction().temperature();
        double realTemp = prediction.weatherReport().temperature();
        return Math.abs(realTemp - predictionTemp);
    }

    private static int calculatePoints(int daysBetween, double tempDiff) {
        if (tempDiff == ZERO_TEMPERATURE_DIFFERENCE) {
            return daysBetween * HIGHEST_MULTIPLIER;
        }
        if (tempDiff < MINIMAL_TEMPERATURE_DIFFERENCE) {
            return daysBetween * AVERAGE_MULTIPLIER;
        }
        if (tempDiff < AVERAGE_TEMPERATURE_DIFFERENCE) {
            return daysBetween;
        }
        if (tempDiff > MAXIMAL_TEMPERATURE_DIFFERENCE) {
            return 0;
        }

        return (int) (daysBetween / tempDiff);
    }

}
