package com.weatherForecasting.backend.weatherpredictioncrd;

import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class WeatherPredictionMapper {

    public static WeatherPrediction createWeatherPredictionFromDTO(WeatherPredictionDto weatherPredictionDTO, LocalTimeDto localTime) {
        return WeatherPrediction.builder()
                .id(UUID.randomUUID())
                .username(weatherPredictionDTO.username())
                .location(weatherPredictionDTO.location())
                .temperature(weatherPredictionDTO.temperature())
                .forecastDate(weatherPredictionDTO.forecastDate())
                .forecastHour(weatherPredictionDTO.forecastHour())
                .timeStamp(localTime.date())
                .build();
    }

    public static List<WeatherPredictionDto> mapToDTOs(List<WeatherPrediction> predictions) {
        return predictions.stream()
                .map(prediction -> new WeatherPredictionDto(prediction.getId(),
                        prediction.getUsername(),
                        prediction.getLocation(),
                        prediction.getTemperature(),
                        prediction.getForecastDate(),
                        prediction.getForecastHour(),
                        prediction.getTimeStamp()))
                .collect(Collectors.toList());
    }
}
