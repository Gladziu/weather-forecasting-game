package com.weatherForecasting.backend.scoremanagementcru;

import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.ScoreDto;

import java.util.List;
import java.util.Optional;

import static com.weatherForecasting.backend.scoremanagementcru.ScoreManagementMapper.createUserScore;
import static com.weatherForecasting.backend.scoremanagementcru.ScoreManagementMapper.mapToResultsDto;

public class ScoreManagementCruFacade {
    private final ScoreManagementRepository scoreManagementRepository;
    private final ScoreCalculator scoreCalculator;
    private final ScoreAssigner scoreAssigner;

    public ScoreManagementCruFacade(ScoreManagementRepository scoreManagementRepository, ScoreCalculator scoreCalculator, ScoreAssigner scoreAssigner) {
        this.scoreManagementRepository = scoreManagementRepository;
        this.scoreCalculator = scoreCalculator;
        this.scoreAssigner = scoreAssigner;
    }

    public List<ScoreDto> retrieveScoreboard() {
        List<Score> scores = scoreManagementRepository.findAll();
        return mapToResultsDto(scores);
    }

    public int retrieveUserScore(String username) {
        Optional<Score> userResult = scoreManagementRepository.findByUsername(username);
        return createUserScore(userResult);
    }

    public List<PredictionScoreDto> saveScoredPoints(List<CheckedPredictionDto> checkedPredictions) {
        List<PredictionScoreDto> predictionsWithScore = scoreCalculator.calculateScoredPoints(checkedPredictions);
        scoreAssigner.assignScoredPoints(predictionsWithScore);
        return predictionsWithScore;
    }

}
