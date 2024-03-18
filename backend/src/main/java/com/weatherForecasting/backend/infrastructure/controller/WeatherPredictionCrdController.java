package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.weatherpredictioncrd.CrdOperationResult;
import com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionCrdFacade;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CrdOperationResult> addPrediction(@RequestBody WeatherPredictionDto weatherPredictionDTO) {
        CrdOperationResult crdOperationResult = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);
        return ResponseEntity.ok(crdOperationResult);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CrdOperationResult> deletePrediction(@RequestParam UUID id) {
        CrdOperationResult crdOperationResult = weatherPredictionCrdFacade.deletePrediction(id);
        return ResponseEntity.ok(crdOperationResult);
    }

    @GetMapping("/show")
    public ResponseEntity<List<WeatherPredictionDto>> showPrediction(@RequestParam String username) {
        List<WeatherPredictionDto> weatherPredictionDtos = weatherPredictionCrdFacade.showPredictions(username);
        return ResponseEntity.ok(weatherPredictionDtos);
    }
}
