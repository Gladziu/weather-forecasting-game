package com.weatherForecasting.backend.historicalpredictioncr;

import com.weatherForecasting.backend.historicalpredictioncr.dto.PredictionHistoryDto;
import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.scoremanagementcru.dto.PredictionScoreDto;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HistoricalPredictionCrFacadeTest {
    HistoricalPredictionRepositoryForTestImpl historyRepositoryForTest = new HistoricalPredictionRepositoryForTestImpl();
    WeatherPredictionCrdFacade weatherPredictionCrdFacade = Mockito.mock(WeatherPredictionCrdFacade.class);
    HistoricalPredictionCrFacade historicalPredictionCrFacade = new HistoricalPredictionConfiguration().historicalPredictionCrFacade(historyRepositoryForTest, weatherPredictionCrdFacade);

    @Test
    public void should_return_empty_history_of_user_predictions_when_given_user_has_no_score() {
        //given
        //when
        List<PredictionHistoryDto> result = historicalPredictionCrFacade.retrieveHistoricalPrediction("joe");
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_return_history_of_user_predictions_when_given_user_has_score() {
        //given
        PredictionHistory predictionHistory = PredictionHistory.builder()
                .id(UUID.randomUUID())
                .username("joe")
                .build();
        historyRepositoryForTest.database.put(UUID.randomUUID(), predictionHistory);
        historyRepositoryForTest.database.put(UUID.randomUUID(), predictionHistory);
        //when
        List<PredictionHistoryDto> result = historicalPredictionCrFacade.retrieveHistoricalPrediction("joe");
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void should_move_prediction_to_prediction_history_with_scored_points() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, 10, false);
        CheckedPredictionDto checkedPrediction = new CheckedPredictionDto(weatherPrediction, weatherReport);
        PredictionScoreDto predictionScore = new PredictionScoreDto(checkedPrediction, 50);
        //when
        historicalPredictionCrFacade.movePredictionToHistory(List.of(predictionScore));
        //then
        assertThat(historyRepositoryForTest.database.size()).isEqualTo(1);
        verify(weatherPredictionCrdFacade, times(1)).deletePrediction(any(UUID.class));
    }

    @Test
    public void should_not_move_prediction_to_prediction_history_when_given_empty_predictions_with_score_list() {
        //given
        //when
        historicalPredictionCrFacade.movePredictionToHistory(Collections.emptyList());
        //then
        assertThat(historyRepositoryForTest.database).isEmpty();
        verify(weatherPredictionCrdFacade, times(0)).deletePrediction(any(UUID.class));
    }
}