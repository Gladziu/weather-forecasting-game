package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.weatherpredictioncrd.CrdOperationResult;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weather-prediction")
public class WeatherPredictionCrdController {

    private final WeatherPredictionCrdFacade weatherPredictionCrdFacade;

    public WeatherPredictionCrdController(WeatherPredictionCrdFacade weatherPredictionCrdFacade) {
        this.weatherPredictionCrdFacade = weatherPredictionCrdFacade;
    }

    @PostMapping("/add")
    public CrdOperationResult inputPrediction(@RequestBody WeatherPredictionDto weatherPredictionDTO) {
        return weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);
    }

    @DeleteMapping("/delete")
    public CrdOperationResult deletePrediction(@RequestParam UUID id) {
        return weatherPredictionCrdFacade.deletePrediction(id);
    }

    @GetMapping("/show")
    public List<WeatherPredictionDto> showPrediction(@RequestParam String username) {
        return weatherPredictionCrdFacade.showPredictions(username);
    }
}
