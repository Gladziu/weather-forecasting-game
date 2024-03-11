package com.weatherForecasting.backend.realweatherprovider.dto.apistructure;

import java.util.List;

public record Forecastday(String date, Day day, Astro astro, List<Hour> hour) {
}
