package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherPredictionCrudConfiguration {

    @Bean
    public WeatherPredictionCrudFacade weatherPredictionCrudFacade(WeatherPredictionCrudRepository repository, RealWeatherFacade realWeather) {
        WeatherPredictionValidator validator = new WeatherPredictionValidator();
        return new WeatherPredictionCrudFacade(repository, validator, realWeather);
    }

    public WeatherPredictionCrudFacade weatherPredictionCrudFacadeForTest(WeatherPredictionCrudRepository repository, RealWeatherFacade realWeather) {
        return weatherPredictionCrudFacade(repository, realWeather);
    }
}
