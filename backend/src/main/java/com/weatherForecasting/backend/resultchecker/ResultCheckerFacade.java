package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionCrFacade;
import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.scoremanagementcru.ScoreManagementCruFacade;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ResultCheckerFacade {
    private final static String LATEST_POSSIBLE_DATE = "GMT-12";
    private final WeatherPredictionCrdFacade weatherPredictionCrdFacade;
    private final WeatherComparator weatherComparator;
    private final ScoreManagementCruFacade scoreManagementCruFacade;
    private final HistoricalPredictionCrFacade historicalPredictionCrFacade;

    public ResultCheckerFacade(WeatherPredictionCrdFacade weatherPredictionCrdFacade, WeatherComparator weatherComparator, ScoreManagementCruFacade scoreManagementCruFacade, HistoricalPredictionCrFacade historicalPredictionCrFacade) {
        this.weatherPredictionCrdFacade = weatherPredictionCrdFacade;
        this.weatherComparator = weatherComparator;
        this.scoreManagementCruFacade = scoreManagementCruFacade;
        this.historicalPredictionCrFacade = historicalPredictionCrFacade;
    }


    public void processWeatherPredictionResults() {
        LocalDate cutoffDate = LocalDate.now(ZoneId.of(LATEST_POSSIBLE_DATE));
        List<WeatherPredictionDto> predictions = weatherPredictionCrdFacade.getPredictionsInTheDateScope(cutoffDate);
        if (!predictions.isEmpty()) {
            List<CheckedPredictionDto> checkedPredictions = weatherComparator.comparePredictionWithRealWeather(predictions);
            List<PredictionScoreDto> predictionsWithScore = scoreManagementCruFacade.saveScoredPoints(checkedPredictions);
            historicalPredictionCrFacade.movePredictionToHistory(predictionsWithScore);
        }
    }
}
