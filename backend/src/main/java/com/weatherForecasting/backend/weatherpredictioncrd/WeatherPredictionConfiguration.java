package com.weatherForecasting.backend.weatherpredictioncrd;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherPredictionConfiguration {

    @Bean
    public WeatherPredictionCrdFacade weatherPredictionCrdFacade(WeatherPredictionRepository repository, RealWeatherProviderFacade realWeather) {
        WeatherPredictionValidator validator = new WeatherPredictionValidator();
        return new WeatherPredictionCrdFacade(repository, validator, realWeather);
    }

    public WeatherPredictionCrdFacade weatherPredictionCrdFacadeForTest(WeatherPredictionRepository repository, RealWeatherProviderFacade realWeather) {
        return weatherPredictionCrdFacade(repository, realWeather);
    }
}
