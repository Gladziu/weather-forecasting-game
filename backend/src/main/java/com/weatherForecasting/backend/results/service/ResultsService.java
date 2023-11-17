package com.weatherForecasting.backend.results.service;

import com.weatherForecasting.backend.results.dto.HistoryDTO;
import com.weatherForecasting.backend.results.model.Score;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ResultsService {
    void weatherPredictionCheck();

    List<Score> getScoreBoard();

    int getUserScore(String userName);

    List<HistoryDTO> getHistoricalPrediction(String userName);

}
