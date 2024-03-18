package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionCrFacade;
import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.scoremanagementcru.ScoreManagementCruFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {

    @Bean
    ResultCheckerFacade resultCheckerFacade(WeatherPredictionCrdFacade weatherPrediction, ScoreManagementCruFacade scoreManagement, HistoricalPredictionCrFacade historicalWeatherPrediction, RealWeatherProviderFacade realWeather) {
        WeatherComparator weatherComparator = new WeatherComparator(realWeather);
        return new ResultCheckerFacade(weatherPrediction, weatherComparator, scoreManagement, historicalWeatherPrediction);
    }

    ResultCheckerFacade resultCheckerFacadeForTest(WeatherPredictionCrdFacade weatherPrediction, ScoreManagementCruFacade scoreManagement, HistoricalPredictionCrFacade historicalWeatherPrediction, RealWeatherProviderFacade realWeather) {
        return resultCheckerFacade(weatherPrediction, scoreManagement, historicalWeatherPrediction, realWeather);
    }
}
