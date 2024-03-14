package com.weatherForecasting.backend.realweatherprovider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealWeatherConfiguration {
    @Bean
    RealWeatherFacade realWeatherFacade() {
        RealWeatherApiReceiver realWeatherApiReceiver = new RealWeatherApiReceiver();
        RealWeatherValidator validator = new RealWeatherValidator();
        return new RealWeatherFacade(realWeatherApiReceiver, validator);
    }

    RealWeatherFacade realWeatherFacadeForTest(RealWeatherApiReceiver realWeatherApiReceiver) {
        RealWeatherValidator validator = new RealWeatherValidator();
        return new RealWeatherFacade(realWeatherApiReceiver, validator);
    }
}
