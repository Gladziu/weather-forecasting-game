package com.weatherForecasting.backend.resultchecker.controller;

import com.weatherForecasting.backend.resultchecker.dto.HistoryDTO;
import com.weatherForecasting.backend.resultchecker.model.Score;
import com.weatherForecasting.backend.resultchecker.service.ResultsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class ResultsController {
    private final ResultsService resultsService;

    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @GetMapping("/score-board")
    public List<Score> getScoreBoard() {
        return resultsService.getScoreBoard();
    }

    @GetMapping("/user/results")
    public int getUserScore(@RequestParam String username) {
        return resultsService.getUserScore(username);
    }

    @GetMapping("/user/prediction/history")
    public List<HistoryDTO> getHistoricalPrediction(@RequestParam String username) {
        return resultsService.getHistoricalPrediction(username);
    }
}
