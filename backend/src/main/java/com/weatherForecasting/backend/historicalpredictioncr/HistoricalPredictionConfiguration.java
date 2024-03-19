package com.weatherForecasting.backend.historicalpredictioncr;

import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricalPredictionConfiguration {
    @Bean
    HistoricalPredictionCrFacade historicalPredictionCrFacade(HistoricalPredictionRepository historicalPredictionRepository, WeatherPredictionCrdFacade weatherPredictionCrdFacade) {
        return new HistoricalPredictionCrFacade(historicalPredictionRepository, weatherPredictionCrdFacade);
    }

    HistoricalPredictionCrFacade historicalPredictionCrFacadeForTest(HistoricalPredictionRepository historicalPredictionRepository, WeatherPredictionCrdFacade weatherPredictionCrdFacade){
        return new HistoricalPredictionCrFacade(historicalPredictionRepository, weatherPredictionCrdFacade);
    }
}
