package com.weatherForecasting.backend.infrastructure.controller;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
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
    public RealWeatherDto getCurrentWeather(@RequestParam String location) {
        return realWeatherProviderFacade.getCurrentWeather(location);
    }

    @GetMapping("/historical")
    public RealWeatherDto getHistoricalWeather(@RequestParam String location,
                                               @RequestParam String date,
                                               @RequestParam int hour) {
        return realWeatherProviderFacade.getHistoricalWeather(location, date, hour);
    }

}
