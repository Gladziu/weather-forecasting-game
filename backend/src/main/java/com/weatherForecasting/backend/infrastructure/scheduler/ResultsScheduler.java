package com.weatherForecasting.backend.infrastructure.scheduler;

import com.weatherForecasting.backend.resultchecker.ResultCheckerFacade;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@EnableScheduling
public class ResultsScheduler {
    private final ResultCheckerFacade resultCheckerFacade;

    public ResultsScheduler(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }


    //@Scheduled(cron = "*/15 * * * * *") // every 15s
    @Scheduled(cron = "0 5 * * * *")
    public void weatherPredictionCheck() {
        LocalDate cutoffDate = LocalDate.now(ZoneId.of("GMT-12")); // the latest possible date on earth
        resultCheckerFacade.processWeatherPredictionResults(cutoffDate);
    }

}
