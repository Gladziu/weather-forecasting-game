package com.weatherForecasting.backend.realweatherprovider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealWeatherConfiguration {
    @Bean
    RealWeatherFacade realWeatherInfoFacade() {
        RealWeatherApiReceiver realWeatherApiReceiver = new RealWeatherApiReceiver();
        RealWeatherValidator validator = new RealWeatherValidator();
        return new RealWeatherFacade(realWeatherApiReceiver, validator);
    }

    RealWeatherFacade realWeatherInfoFacadeForTest(RealWeatherApiReceiver realWeatherApiReceiver) {
        RealWeatherValidator validator = new RealWeatherValidator();
        return new RealWeatherFacade(realWeatherApiReceiver, validator);
    }
}
