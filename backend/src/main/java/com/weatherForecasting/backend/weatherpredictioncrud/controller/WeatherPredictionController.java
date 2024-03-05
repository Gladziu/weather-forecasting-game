package com.weatherForecasting.backend.weatherpredictioncrud.controller;

import com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionCrudFacade;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weather-prediction")
public class WeatherPredictionController {

    private final WeatherPredictionCrudFacade weatherPredictionCrudFacade;

    public WeatherPredictionController(WeatherPredictionCrudFacade weatherPredictionCrudFacade) {
        this.weatherPredictionCrudFacade = weatherPredictionCrudFacade;
    }

    @PostMapping("/add")
    public ResponseEntity<CrudOperationResult> addPrediction(@RequestBody WeatherPredictionDTO weatherPredictionDTO) {
        CrudOperationResult crudOperationResult = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);
        return ResponseEntity.ok(crudOperationResult);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CrudOperationResult> deletePrediction(@RequestParam UUID id) {
        CrudOperationResult crudOperationResult = weatherPredictionCrudFacade.deletePrediction(id);
        return ResponseEntity.ok(crudOperationResult);
    }

    @GetMapping("/show")
    public ResponseEntity<List<WeatherPredictionDTO>> showPrediction(@RequestParam String username) {
        List<WeatherPredictionDTO> weatherPredictionDTOS = weatherPredictionCrudFacade.showPrediction(username);
        return ResponseEntity.ok(weatherPredictionDTOS);
    }
}
