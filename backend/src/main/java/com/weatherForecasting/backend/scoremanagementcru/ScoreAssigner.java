package com.weatherForecasting.backend.scoremanagementcru;

import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;

import java.util.List;
import java.util.Optional;

import static com.weatherForecasting.backend.scoremanagementcru.ScoreManagementMapper.createScore;
import static com.weatherForecasting.backend.scoremanagementcru.ScoreManagementMapper.updateScore;

class ScoreAssigner {
    private final ScoreManagementRepository scoreManagementRepository;

    ScoreAssigner(ScoreManagementRepository scoreManagementRepository) {
        this.scoreManagementRepository = scoreManagementRepository;
    }


    public void assignScoredPoints(List<PredictionScoreDto> predictionsWithScore) {
        for (PredictionScoreDto predictionScore : predictionsWithScore) {
            String username = predictionScore.checkedPredictionDto().weatherPrediction().username();
            Optional<Score> resultOptional = scoreManagementRepository.findByUsername(username);
            if (resultOptional.isPresent()) {
                Score score = resultOptional.get();
                Score updatedScore = updateScore(predictionScore, score);
                scoreManagementRepository.save(updatedScore);
            } else {
                Score score = createScore(predictionScore);
                scoreManagementRepository.save(score);
            }
        }
    }
}
