package com.weatherForecasting.backend.scoremanagementcru;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScoreManagementConfiguration {
    @Bean
    ScoreManagementCruFacade scoreManagementCruFacade(ScoreManagementRepository scoreManagementRepository) {
        ScoreAssigner scoreAssigner = new ScoreAssigner(scoreManagementRepository);
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        return new ScoreManagementCruFacade(scoreManagementRepository, scoreCalculator, scoreAssigner);
    }

    ScoreManagementCruFacade scoreManagementCruFacadeForTest(ScoreManagementRepository scoreManagementRepository) {
        return scoreManagementCruFacade(scoreManagementRepository);
    }
}
