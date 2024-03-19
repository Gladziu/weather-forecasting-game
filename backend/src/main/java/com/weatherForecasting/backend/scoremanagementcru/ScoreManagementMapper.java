package com.weatherForecasting.backend.scoremanagementcru;

import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.ScoreDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

class ScoreManagementMapper {
    public static List<ScoreDto> mapToResultsDto(List<Score> results) {
        return results.stream()
                .map(result -> new ScoreDto(
                        result.getUsername(),
                        result.getPoints()))
                .collect(Collectors.toList());
    }

    public static int createUserScore(Optional<Score> userScore) {
        return userScore.map(Score::getPoints).orElse(0);
    }

    public static Score createScore(PredictionScoreDto predictionScore) {
        UUID id = UUID.randomUUID();
        String username = predictionScore.checkedPredictionDto().weatherPrediction().username();
        int score = predictionScore.points();
        return Score.builder()
                .id(id)
                .username(username)
                .points(score)
                .build();
    }

    public static Score updateScore(PredictionScoreDto predictionScore, Score score) {
        int newScore = score.getPoints() + predictionScore.points();
        return Score.builder()
                .id(score.getId())
                .username(score.getUsername())
                .points(newScore)
                .build();
    }
}
