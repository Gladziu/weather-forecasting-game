package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.CurrentWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.ErrorMessage;
import com.weatherForecasting.backend.realweatherprovider.dto.HistoricalWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.*;

import java.util.ArrayList;
import java.util.List;

class ApiResponseCreatorForTest {
    public static CurrentWeatherDto currentWeather() {
        return CurrentWeatherDto.builder()
                .location(new Location("Warszawa", "", "Poland", 52.25, 21.0, "Europe/Warsaw", 1710107723, "2024-03-10 22:55"))
                .current(new Current(10, new Condition(), 5, "W", 1010, 23))
                .failure(false)
                .build();
    }

    public static CurrentWeatherDto currentWeatherLocationError() {
        return CurrentWeatherDto.builder()
                .failure(true)
                .errorMessage(new ErrorMessage("location not found"))
                .build();
    }

    public static HistoricalWeatherDto historicalWeather() {
        List<Hour> hours = new ArrayList<>();
        hours.add(new Hour("00:00", 4, new Condition(), 15, "S", 1020, 5));
        hours.add(new Hour("01:00", 5, new Condition(), 12, "S", 1021, 5));
        hours.add(new Hour("02:00", 5, new Condition(), 10, "S", 1021, 5));
        hours.add(new Hour("03:00", 6, new Condition(), 10, "S", 1021, 5));
        hours.add(new Hour("04:00", 8, new Condition(), 10, "S", 1021, 5));

        List<Forecastday> forecastDays = new ArrayList<>();
        forecastDays.add(new Forecastday("2024-05-05", new Day(new Condition()), new Astro(), hours));
        return HistoricalWeatherDto.builder()
                .location(new Location("Warszawa", "", "Poland", 52.25, 21.0, "Europe/Warsaw", 1710107723, "2024-03-10 22:55"))
                .forecast(new Forecast(forecastDays))
                .build();
    }

    public static HistoricalWeatherDto historicalWeatherLocationError() {
        return HistoricalWeatherDto.builder()
                .failure(true)
                .errorMessage(new ErrorMessage("location not found"))
                .build();
    }
}
