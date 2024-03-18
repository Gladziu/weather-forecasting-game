package com.weatherForecasting.backend.infrastructure.scheduler;

import com.weatherForecasting.backend.resultchecker.ResultCheckerFacade;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ResultsScheduler {
    private final ResultCheckerFacade resultCheckerFacade;

    public ResultsScheduler(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }


    @Scheduled(cron = "0 5 * * * *")
    public void weatherPredictionCheck() {
        resultCheckerFacade.processWeatherPredictionResults();
    }

}
