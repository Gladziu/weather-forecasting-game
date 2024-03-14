package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.resultchecker.ResultCheckerFacade;
import com.weatherForecasting.backend.resultchecker.dto.HistoryResultDto;
import com.weatherForecasting.backend.resultchecker.dto.ResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultCheckerController {
    private final ResultCheckerFacade resultCheckerFacade;

    public ResultCheckerController(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }


    @GetMapping("/scoreboard")
    public List<ResultDto> getScoreboard() {
        return resultCheckerFacade.getScoreboard();
    }

    @GetMapping("/user-score")
    public int getUserScore(@RequestParam String username) {
        return resultCheckerFacade.getUserScore(username);
    }

    @GetMapping("/history")
    public List<HistoryResultDto> getHistoricalPrediction(@RequestParam String username) {
        return resultCheckerFacade.getHistoricalPrediction(username);
    }
}
