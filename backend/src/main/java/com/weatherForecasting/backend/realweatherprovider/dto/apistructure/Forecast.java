package com.weatherForecasting.backend.realweatherprovider.dto.apistructure;

import java.util.List;

public record Forecast(List<Forecastday> forecastday) {

}
