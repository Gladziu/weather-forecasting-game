package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionCrFacade;
import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.scoremanagementcru.ScoreManagementCruFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResultCheckerFacadeTest {

    WeatherPredictionCrdFacade weatherPredictionCrdFacade = Mockito.mock(WeatherPredictionCrdFacade.class);
    ScoreManagementCruFacade scoreManagementCruFacade = Mockito.mock(ScoreManagementCruFacade.class);
    HistoricalPredictionCrFacade historicalPredictionCrFacade = Mockito.mock(HistoricalPredictionCrFacade.class);
    RealWeatherProviderFacade realWeatherProviderFacade = Mockito.mock(RealWeatherProviderFacade.class);

    ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacadeForTest(weatherPredictionCrdFacade, scoreManagementCruFacade, historicalPredictionCrFacade, realWeatherProviderFacade);

    @Test
    public void should_not_process_scoring_system_when_none_prediction_is_in_date_scope() {
        //given
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 16, 10, false);
        when(weatherPredictionCrdFacade.retrievePredictionsInTheDateScope(any())).thenReturn(Collections.emptyList());
        when(realWeatherProviderFacade.weatherReport(any())).thenReturn(weatherReport);
        //when
        resultCheckerFacade.processWeatherPredictionResults();
        //then
        verify(historicalPredictionCrFacade, times(0)).movePredictionToHistory(any());
        verify(scoreManagementCruFacade, times(0)).saveScoredPoints(any());
    }

    @Test
    public void should_not_process_scoring_system_when_prediction_time_is_different_then_real_time() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 16, 10, false);
        when(weatherPredictionCrdFacade.retrievePredictionsInTheDateScope(any())).thenReturn(List.of(weatherPrediction));
        when(realWeatherProviderFacade.weatherReport(any())).thenReturn(weatherReport);
        //when
        resultCheckerFacade.processWeatherPredictionResults();
        //then
        verify(weatherPredictionCrdFacade, times(0)).deletePrediction(any());
    }

    @Test
    public void should_process_scoring_system_when_prediction_time_is_same_as_real_time() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warsaw", 12, LocalDate.now(), 10, LocalDate.now().minusDays(10));
        WeatherReportDto weatherReport = new WeatherReportDto(LocalDate.now(), 12, 10, false);
        when(weatherPredictionCrdFacade.retrievePredictionsInTheDateScope(any())).thenReturn(List.of(weatherPrediction));
        when(realWeatherProviderFacade.weatherReport(any())).thenReturn(weatherReport);
        //when
        resultCheckerFacade.processWeatherPredictionResults();
        //then
        verify(historicalPredictionCrFacade, times(1)).movePredictionToHistory(any());
        verify(scoreManagementCruFacade, times(1)).saveScoredPoints(any());
    }
}