package com.weatherForecasting.backend.realweatherprovider.controller;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherApiController {
    private final RealWeatherFacade realWeatherFacade;

    public WeatherApiController(RealWeatherFacade realWeatherFacade) {
        this.realWeatherFacade = realWeatherFacade;
    }


    @GetMapping("/current")
    public ResponseEntity<RealWeatherDto> getCurrentWeather(@RequestParam String location) {
        return ResponseEntity.ok(realWeatherFacade.getCurrentWeather(location));
    }

    @GetMapping("/historical")
    public ResponseEntity<RealWeatherDto> getHistoricalWeather(@RequestParam String location,
                                                               @RequestParam String date,
                                                               @RequestParam int hour) {
        return ResponseEntity.ok(realWeatherFacade.getHistoricalWeather(location, date, hour));
    }

}
