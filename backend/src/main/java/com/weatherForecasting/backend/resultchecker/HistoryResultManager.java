package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.resultchecker.dto.PredictionScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionCrudFacade;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

import java.util.List;
import java.util.UUID;

class HistoryResultManager {
    private final WeatherPredictionCrudFacade weatherPredictionCrudFacade;
    private final HistoryResultRepository historyResultRepository;

    HistoryResultManager(WeatherPredictionCrudFacade weatherPredictionCrudFacade, HistoryResultRepository historyResultRepository) {
        this.weatherPredictionCrudFacade = weatherPredictionCrudFacade;
        this.historyResultRepository = historyResultRepository;
    }

    public void movePredictionToHistory(List<PredictionScoreDto> predictionsWithScore) {
        for (PredictionScoreDto predictionScore : predictionsWithScore) {
            HistoryResult historyResult = crateHistoryResult(predictionScore);
            historyResultRepository.save(historyResult);
            UUID predictionId = predictionScore.checkedPredictionDto().weatherPrediction().id();
            weatherPredictionCrudFacade.deletePrediction(predictionId);
        }
    }

    private static HistoryResult crateHistoryResult(PredictionScoreDto predictionScore) {
        UUID id = UUID.randomUUID();
        int points = predictionScore.points();
        WeatherPredictionDto prediction = predictionScore.checkedPredictionDto().weatherPrediction();
        WeatherReportDto weatherReport = predictionScore.checkedPredictionDto().weatherReport();
        return HistoryResult.builder()
                .id(id)
                .username(prediction.username())
                .location(prediction.location())
                .temperature(prediction.temperature())
                .realTemperature(weatherReport.temperature())
                .forecastDate(prediction.forecastDate())
                .forecastHour(prediction.forecastHour())
                .timeStamp(prediction.timeStamp())
                .score(points)
                .build();
    }
}
