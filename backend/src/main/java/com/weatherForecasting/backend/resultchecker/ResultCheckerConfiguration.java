package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherFacade;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionCrudFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {

    @Bean
    ResultCheckerFacade resultCheckerFacade(ResultRepository resultRepository, HistoryResultRepository historyRepository, WeatherPredictionCrudFacade weatherPredictionCrudFacade,
                                            RealWeatherFacade realWeatherFacade) {
        WeatherComparator weatherComparator = new WeatherComparator(realWeatherFacade);
        ResultAssigner resultAssigner = new ResultAssigner(resultRepository);
        HistoryResultManager historyResultManager = new HistoryResultManager(weatherPredictionCrudFacade, historyRepository);
        return new ResultCheckerFacade(resultRepository, historyRepository, weatherPredictionCrudFacade, weatherComparator, resultAssigner, historyResultManager);
    }
}
