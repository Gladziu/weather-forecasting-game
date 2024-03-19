package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.historicalpredictioncr.HistoricalPredictionCrFacade;
import com.weatherForecasting.backend.historicalpredictioncr.dto.PredictionHistoryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoricalPredictionCrController {
    private final HistoricalPredictionCrFacade historicalPredictionCrFacade;

    public HistoricalPredictionCrController(HistoricalPredictionCrFacade historicalPredictionCrFacade) {
        this.historicalPredictionCrFacade = historicalPredictionCrFacade;
    }


    @GetMapping("/history")
    public List<PredictionHistoryDto> getHistoricalPrediction(@RequestParam String username) {
        return historicalPredictionCrFacade.getHistoricalPrediction(username);
    }
}
