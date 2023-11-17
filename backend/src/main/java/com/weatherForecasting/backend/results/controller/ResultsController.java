package com.weatherForecasting.backend.results.controller;

import com.weatherForecasting.backend.results.dto.HistoryDTO;
import com.weatherForecasting.backend.results.model.Score;
import com.weatherForecasting.backend.results.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class ResultsController {
    @Autowired
    private ResultsService resultsService;

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
