package com.weatherForecasting.backend.resultchecker.service;

import com.weatherForecasting.backend.resultchecker.dto.HistoryDTO;
import com.weatherForecasting.backend.resultchecker.model.Score;

import java.util.List;

public interface ResultsService {
    List<Score> getScoreBoard();

    int getUserScore(String userName);

    List<HistoryDTO> getHistoricalPrediction(String userName);

}
