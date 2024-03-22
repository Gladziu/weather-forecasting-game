package com.weatherForecasting.backend.scoremanagementcru;

import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.ScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreManagementCruFacadeTest {
    ScoreManagementRepositoryForTestImpl scoreRepositoryForTest = new ScoreManagementRepositoryForTestImpl();
    ScoreManagementCruFacade scoreManagementCruFacade = new ScoreManagementConfiguration().scoreManagementCruFacadeForTest(scoreRepositoryForTest);
    @Test
    public void should_return_worst_score_when_given_very_different_temperature() {
        //given
        int givenTemperature = 18;
        int realTemperature = 10;
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", givenTemperature, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, realTemperature, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        //when
        List<PredictionScoreDto> result = scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //then
        assertThat(result.get(0).points()).isEqualTo(0);
    }

    @Test
    public void should_return_min_score_when_given_maximal_different_temperature() {
        //given
        int givenTemperature = 16;
        int realTemperature = 10;
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", givenTemperature, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, realTemperature, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        //when
        List<PredictionScoreDto> result = scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //then
        assertThat(result.get(0).points()).isEqualTo(20);
    }

    @Test
    public void should_return_average_score_when_given_average_different_temperature() {
        //given
        int givenTemperature = 12;
        int realTemperature = 10;
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", givenTemperature, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, realTemperature, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        //when
        List<PredictionScoreDto> result = scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //then
        assertThat(result.get(0).points()).isEqualTo(50);
    }

    @Test
    public void should_return_highest_score_when_given_same_temperature() {
        //given
        int givenTemperature = 10;
        int realTemperature = 10;
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", givenTemperature, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, realTemperature, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        //when
        List<PredictionScoreDto> result = scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //then
        assertThat(result.get(0).points()).isEqualTo(100);
    }

    @Test
    public void should_return_saved_score_when_user_has_no_early_score() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, 10, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //when
        int result = scoreManagementCruFacade.retrieveUserScore("joe");
        //then
        assertThat(result).isEqualTo(50);
    }

    @Test
    public void should_return_updated_score_when_user_scored_early() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, 10, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //when
        int result = scoreManagementCruFacade.retrieveUserScore("joe");
        //then
        assertThat(result).isEqualTo(100);
    }
    @Test
    public void should_return_empty_score_board_when_there_is_no_score() {
        //given
        //when
        List<ScoreDto> result = scoreManagementCruFacade.retrieveScoreboard();
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_score_board_when_score_exists_in_db() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, 10, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        scoreManagementCruFacade.saveScoredPoints(List.of(checkedPrediction));
        //when
        List<ScoreDto> result = scoreManagementCruFacade.retrieveScoreboard();
        //then
        assertThat(result).isNotEmpty();
    }
}