package com.weatherForecasting.backend.weatherpredictioncrud;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherPredictionConfiguration {

    public WeatherPredictionCrudFacade WeatherPredictionCrudFacadeForTest(WeatherPredictionRepository repository) {
        WeatherPredictionReceiverValidator validator = new WeatherPredictionReceiverValidator();
        return new WeatherPredictionCrudFacade(repository, validator);
    }
}
