package com.weatherForecasting.backend.historicalpredictioncr;

import com.weatherForecasting.backend.historicalpredictioncr.dto.PredictionHistoryDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;

import java.util.List;
import java.util.UUID;

import static com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionMapper.crateHistoryResult;
import static com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionMapper.mapToHistoryResultsDto;

public class HistoricalPredictionCrFacade {
    private final HistoricalPredictionRepository historicalPredictionRepository;
    private final WeatherPredictionCrdFacade weatherPredictionCrdFacade;

    public HistoricalPredictionCrFacade(HistoricalPredictionRepository historicalPredictionRepository, WeatherPredictionCrdFacade weatherPredictionCrdFacade) {
        this.historicalPredictionRepository = historicalPredictionRepository;
        this.weatherPredictionCrdFacade = weatherPredictionCrdFacade;
    }

    public List<PredictionHistoryDto> getHistoricalPrediction(String username) {
        List<PredictionHistory> predictionHistories = historicalPredictionRepository.findAllByUsername(username);
        return mapToHistoryResultsDto(predictionHistories);
    }

    public void movePredictionToHistory(List<PredictionScoreDto> predictionsWithScore) {
        for (PredictionScoreDto predictionScore : predictionsWithScore) {
            PredictionHistory predictionHistory = crateHistoryResult(predictionScore);
            historicalPredictionRepository.save(predictionHistory);
            UUID predictionId = predictionScore.checkedPredictionDto().weatherPrediction().id();
            weatherPredictionCrdFacade.deletePrediction(predictionId);
        }
    }
}
