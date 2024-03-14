package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.resultchecker.dto.HistoryResultDto;
import com.weatherForecasting.backend.resultchecker.dto.PredictionScoreDto;
import com.weatherForecasting.backend.resultchecker.dto.ResultDto;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionCrudFacade;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ResultCheckerFacade {

    private final ResultRepository resultRepository;
    private final HistoryResultRepository historyResultRepository;
    private final WeatherPredictionCrudFacade weatherPredictionCrudFacade;
    private final WeatherComparator weatherComparator;
    private final ResultAssigner resultAssigner;
    private final HistoryResultManager historyResultManager;

    public ResultCheckerFacade(ResultRepository resultRepository, HistoryResultRepository historyResultRepository, WeatherPredictionCrudFacade weatherPredictionCrudFacade, WeatherComparator weatherComparator, ResultAssigner resultAssigner, HistoryResultManager historyResultManager) {
        this.resultRepository = resultRepository;
        this.historyResultRepository = historyResultRepository;
        this.weatherPredictionCrudFacade = weatherPredictionCrudFacade;
        this.weatherComparator = weatherComparator;
        this.resultAssigner = resultAssigner;
        this.historyResultManager = historyResultManager;
    }

    public List<ResultDto> getScoreboard() {
        List<Result> results = resultRepository.findAll();
        return ResultCheckerMapper.mapToResultsDto(results);
    }

    public int getUserScore(String username) {
        Optional<Result> userResult = resultRepository.findByUsername(username);
        return ResultCheckerMapper.createUserScore(userResult);
    }

    public List<HistoryResultDto> getHistoricalPrediction(String username) {
        List<HistoryResult> historyResults = historyResultRepository.findAllByUsername(username);
        return ResultCheckerMapper.mapToHistoryResultsDto(historyResults);
    }

    public void processWeatherPredictionResults(LocalDate cutoffDate) {
        List<WeatherPredictionDto> predictions = weatherPredictionCrudFacade.getPredictionsInTheDateScope(cutoffDate);
        if (!predictions.isEmpty()) {
            List<CheckedPredictionDto> checkedPredictions = weatherComparator.comparePredictionWithRealWeather(predictions);
            // List<PredictionScoreDto> predictionsWithScore = scoreSystemCruFacade.assignScoredPoints(checkedPredictions);
            // historicalWeatherPredictionCrFacade.movePredictionToHistory(predictionsWithScore);
            List<PredictionScoreDto> predictionsWithScore = ResultCalculator.calculateScoredPoints(checkedPredictions);
            resultAssigner.assignScoredPoints(predictionsWithScore);
            historyResultManager.movePredictionToHistory(predictionsWithScore);
        }
    }
}
