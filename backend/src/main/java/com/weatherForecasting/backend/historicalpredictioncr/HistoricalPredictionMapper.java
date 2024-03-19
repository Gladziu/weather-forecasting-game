package com.weatherForecasting.backend.historicalpredictioncr;

import com.weatherForecasting.backend.historicalpredictioncr.dto.PredictionHistoryDto;
import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class HistoricalPredictionMapper {
    public static List<PredictionHistoryDto> mapToHistoryResultsDto(List<PredictionHistory> predictionHistories) {
        return predictionHistories.stream()
                .map(predictionHistory -> new PredictionHistoryDto(
                        predictionHistory.getUsername(),
                        predictionHistory.getLocation(),
                        predictionHistory.getTemperature(),
                        predictionHistory.getRealTemperature(),
                        predictionHistory.getForecastDate(),
                        predictionHistory.getForecastHour(),
                        predictionHistory.getTimeStamp(),
                        predictionHistory.getScore()
                ))
                .collect(Collectors.toList());
    }

    public static PredictionHistory crateHistoryResult(PredictionScoreDto predictionScore) {
        UUID id = UUID.randomUUID();
        int points = predictionScore.points();
        WeatherPredictionDto prediction = predictionScore.checkedPredictionDto().weatherPrediction();
        WeatherReportDto weatherReport = predictionScore.checkedPredictionDto().weatherReport();
        return PredictionHistory.builder()
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
