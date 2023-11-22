package com.weatherForecasting.backend.weatherAPI.controller;

import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import com.weatherForecasting.backend.weatherAPI.service.WeatherApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherApiController {
    private final WeatherApiService weatherApiService;

    public WeatherApiController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/current")
    public WeatherDTO getCurrentWeather(@RequestParam String location) {
        return weatherApiService.getCurrentWeather(location);
    }

    @GetMapping("/historical")
    public WeatherDTO getHistoricalWeather(@RequestParam String location,
                                           @RequestParam String date,
                                           @RequestParam String hour) {
        return weatherApiService.getHistoricalWeather(location, date, hour);
    }

}
