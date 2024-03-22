package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.scoremanagementcru.ScoreManagementCruFacade;
import com.weatherForecasting.backend.scoremanagementcru.dto.ScoreDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScoreManagementCruController {
    private final ScoreManagementCruFacade scoreManagementCruFacade;

    public ScoreManagementCruController(ScoreManagementCruFacade scoreManagementCruFacade) {
        this.scoreManagementCruFacade = scoreManagementCruFacade;
    }

    @GetMapping("/scoreboard")
    public List<ScoreDto> showScoreboard() {
        return scoreManagementCruFacade.retrieveScoreboard();
    }

    @GetMapping("/user-score")
    public int showUserScore(@RequestParam String username) {
        return scoreManagementCruFacade.retrieveUserScore(username);
    }
}
