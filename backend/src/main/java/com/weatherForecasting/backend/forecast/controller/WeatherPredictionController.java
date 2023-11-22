package com.weatherForecasting.backend.forecast.controller;

import com.weatherForecasting.backend.forecast.dto.WeatherPredictionDTO;
import com.weatherForecasting.backend.forecast.model.WeatherPrediction;
import com.weatherForecasting.backend.forecast.service.WeatherPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather/prediction")
public class WeatherPredictionController {

    private final WeatherPredictionService weatherPredictionService;
    public WeatherPredictionController(WeatherPredictionService weatherPredictionService){
        this.weatherPredictionService = weatherPredictionService;
    }
    //TODO: ogarnać cały ten cotnroller, czyli ustal czy korzystac z ResponseEntity
    @PostMapping("/add")
    public ResponseEntity<String> addPrediction(@RequestBody WeatherPredictionDTO weatherPredictionDTO) {
        weatherPredictionService.addPrediction(weatherPredictionDTO);
        return ResponseEntity.ok("User prediction successfully added.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePrediction(@RequestParam Long id) {
        boolean isDeleted = weatherPredictionService.deletePrediction(id);
        if(isDeleted) {
            return ResponseEntity.ok("Deleted weather prediction with id=" + id);
        } else {
            return new ResponseEntity<>("Delete error. Can not find weather prediction with id=" + id,
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/show")
    public List<WeatherPredictionDTO> showPrediction(@RequestParam String username) {
        return weatherPredictionService.showPrediction(username);
    }
}
