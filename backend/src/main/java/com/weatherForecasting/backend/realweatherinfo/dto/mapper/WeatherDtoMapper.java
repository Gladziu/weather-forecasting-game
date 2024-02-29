package com.weatherForecasting.backend.realweatherinfo.dto.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatherForecasting.backend.realweatherinfo.dto.Condition;
import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;

public class WeatherDtoMapper {
    public static WeatherDTO mapApiWeather(JsonNode location, JsonNode details) {
        WeatherDTO weatherDTO = new WeatherDTO();
        mapLocation(weatherDTO, location);
        mapDetails(weatherDTO, details);
        addTime(weatherDTO, location, details);
        return weatherDTO;
    }

    private static void addTime(WeatherDTO weatherDTO, JsonNode location, JsonNode details) {
        weatherDTO.setTime(details.path("time").asText().equals("") ?
                location.path("localtime").asText() : details.path("time").asText());
    }

    private static void mapDetails(WeatherDTO weatherDTO, JsonNode details) {
        weatherDTO.setTemperature(details.path("temp_c").asDouble());
        weatherDTO.setWindSpeed(details.path("wind_kph").asDouble());
        weatherDTO.setWindDirectory(details.path("wind_dir").asText());
        weatherDTO.setPressure(details.path("pressure_mb").asDouble());
        weatherDTO.setHumidity(details.path("humidity").asInt());

        mapConditionDetails(weatherDTO, details.path("condition"));
    }

    private static void mapLocation(WeatherDTO weatherDTO, JsonNode location) {
        weatherDTO.setCity(location.path("name").asText());
        weatherDTO.setCountry(location.path("country").asText());
    }

    private static void mapConditionDetails(WeatherDTO weatherDTO, JsonNode conditionNode) {
        Condition condition = new Condition();
        condition.setText(conditionNode.path("text").asText());
        condition.setIcon(conditionNode.path("icon").asText());

        weatherDTO.setCondition(condition);
    }
}
