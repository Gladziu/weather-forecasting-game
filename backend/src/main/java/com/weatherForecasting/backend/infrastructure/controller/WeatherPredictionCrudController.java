package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionCrudFacade;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weather-prediction")
public class WeatherPredictionCrudController {

    private final WeatherPredictionCrudFacade weatherPredictionCrudFacade;

    public WeatherPredictionCrudController(WeatherPredictionCrudFacade weatherPredictionCrudFacade) {
        this.weatherPredictionCrudFacade = weatherPredictionCrudFacade;
    }

    @PostMapping("/add")
    public ResponseEntity<CrudOperationResult> addPrediction(@RequestBody WeatherPredictionDto weatherPredictionDTO) {
        CrudOperationResult crudOperationResult = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);
        return ResponseEntity.ok(crudOperationResult);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CrudOperationResult> deletePrediction(@RequestParam UUID id) {
        CrudOperationResult crudOperationResult = weatherPredictionCrudFacade.deletePrediction(id);
        return ResponseEntity.ok(crudOperationResult);
    }

    @GetMapping("/show")
    public ResponseEntity<List<WeatherPredictionDto>> showPrediction(@RequestParam String username) {
        List<WeatherPredictionDto> weatherPredictionDtos = weatherPredictionCrudFacade.showPredictions(username);
        return ResponseEntity.ok(weatherPredictionDtos);
    }
}
