package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.resultchecker.dto.PredictionScoreDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ResultAssigner {
    private final ResultRepository resultRepository;

    ResultAssigner(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }


    public void assignScoredPoints(List<PredictionScoreDto> predictionsWithScore) {
        for(PredictionScoreDto predictionScore : predictionsWithScore) {
            String username = predictionScore.checkedPredictionDto().weatherPrediction().username();
            Optional<Result> resultOptional = resultRepository.findByUsername(username);
            if(resultOptional.isPresent()){
                Result result = resultOptional.get();
                updateResult(predictionScore, result);
            } else {
                createResult(predictionScore);
            }
        }
    }

    private void createResult(PredictionScoreDto predictionScore) {
        UUID id = UUID.randomUUID();
        String username = predictionScore.checkedPredictionDto().weatherPrediction().username();
        int score = predictionScore.points();
        Result result = Result.builder()
                .id(id)
                .username(username)
                .score(score)
                .build();
        resultRepository.save(result);
    }

    private void updateResult(PredictionScoreDto predictionScore, Result result) {
        int newScore = result.getScore() + predictionScore.points();
        Result updatedResult = Result.builder()
                .id(result.getId())
                .username(result.getUsername())
                .score(newScore)
                .build();
        resultRepository.save(updatedResult);
    }
}
