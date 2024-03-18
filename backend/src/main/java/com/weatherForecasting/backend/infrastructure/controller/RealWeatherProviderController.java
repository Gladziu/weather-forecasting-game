package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RealWeatherProviderController {
    private final RealWeatherProviderFacade realWeatherProviderFacade;

    public RealWeatherProviderController(RealWeatherProviderFacade realWeatherProviderFacade) {
        this.realWeatherProviderFacade = realWeatherProviderFacade;
    }


    @GetMapping("/current")
    public ResponseEntity<RealWeatherDto> getCurrentWeather(@RequestParam String location) {
        return ResponseEntity.ok(realWeatherProviderFacade.getCurrentWeather(location));
    }

    @GetMapping("/historical")
    public ResponseEntity<RealWeatherDto> getHistoricalWeather(@RequestParam String location,
                                                               @RequestParam String date,
                                                               @RequestParam int hour) {
        return ResponseEntity.ok(realWeatherProviderFacade.getHistoricalWeather(location, date, hour));
    }

}
