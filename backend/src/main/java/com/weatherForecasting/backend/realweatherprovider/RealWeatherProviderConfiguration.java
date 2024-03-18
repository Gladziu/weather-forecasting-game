package com.weatherForecasting.backend.realweatherprovider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealWeatherProviderConfiguration {
    @Bean
    RealWeatherProviderFacade realWeatherProviderFacade() {
        RealWeatherApiDataReceiver realWeatherApiDataReceiver = new RealWeatherApiDataReceiver();
        RealWeatherProviderValidator validator = new RealWeatherProviderValidator();
        return new RealWeatherProviderFacade(realWeatherApiDataReceiver, validator);
    }

    RealWeatherProviderFacade realWeatherProviderFacadeForTest(RealWeatherApiDataReceiver realWeatherApiDataReceiver) {
        RealWeatherProviderValidator validator = new RealWeatherProviderValidator();
        return new RealWeatherProviderFacade(realWeatherApiDataReceiver, validator);
    }
}
