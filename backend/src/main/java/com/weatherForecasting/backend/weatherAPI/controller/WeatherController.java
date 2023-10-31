package com.weatherForecasting.backend.weatherAPI.controller;

import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import com.weatherForecasting.backend.weatherAPI.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public WeatherDTO getCurrentWeather(@RequestParam String location) {
        return weatherService.getCurrentWeather(location);
    }

    @GetMapping("/historical")
    public WeatherDTO getHistoricalWeather(@RequestParam String location,
                                                           @RequestParam String date,
                                                           @RequestParam String hour) {
        return weatherService.getHistoricalWeather(location, date, hour);
    }

}
