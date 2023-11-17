package com.weatherForecasting.backend.weatherAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.weatherAPI.dto.Condition;
import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import com.weatherForecasting.backend.weatherAPI.exception.ExceptionHandling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Slf4j
public class WeatherApiServiceImpl implements WeatherApiService {
    private static final String CURRENT_WEATHER_URL = "/current.json?key=";
    private static final String HISTORY_WEATHER_URL = "/history.json?key=";
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @Autowired
    public WeatherApiServiceImpl(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public WeatherDTO getCurrentWeather(String location) {
        String fullUrl = apiUrl + CURRENT_WEATHER_URL + apiKey + "&q=" + location + "&aqi=no";
        Mono<String> jsonResponseMono = webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);

        WeatherDTO weatherDTO = new WeatherDTO();
        try {
            // Blokujemy Mono na potrzeby tego przykładu
            String jsonResponse = jsonResponseMono.block();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode forecastLocation = rootNode.path("location");
            JsonNode forecastDetails = rootNode.path("current");

            fillResultWithDataFromApi(weatherDTO, forecastLocation, forecastDetails);

        } catch (WebClientResponseException e) {
            ExceptionHandling.handleHttpClientException(weatherDTO, e, objectMapper);

        } catch (IOException e) {
            log.error("An error occurred while processing the request");
        }

        return weatherDTO;
    }

    @Override
    public WeatherDTO getHistoricalWeather(String location, String date, String hour) {
        WeatherDTO weatherDTO = areCorrectDateAndHour(location, date, hour);
        if(weatherDTO != null) {
            return weatherDTO;
        }

        return historicalWeather(location, date, hour);
    }

    private WeatherDTO historicalWeather(String location, String date, String hour) {
        String fullUrl = apiUrl + HISTORY_WEATHER_URL + apiKey + "&q=" + location + "&dt=" + date;
        Mono<String> jsonResponseMono = webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);

        WeatherDTO weatherDTO = new WeatherDTO();
        try {
            String jsonResponse = jsonResponseMono.block(); // Blokujemy Mono na potrzeby tego przykładu
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode forecastLocation = rootNode.path("location");
            JsonNode forecastDay = rootNode.path("forecast").path("forecastday").get(0);

            JsonNode hourData = weatherWithEntryHour(forecastDay, hour);
            if(hourData != null) {
                fillResultWithDataFromApi(weatherDTO, forecastLocation, hourData);
            } else {
                return ExceptionHandling.errorMessage("Hour data not found.");
            }

        } catch (WebClientResponseException e) {
            ExceptionHandling.handleHttpClientException(weatherDTO, e, objectMapper);

        } catch (IOException e) {
            log.error("An error occurred while processing the request");
        }

        return weatherDTO;
    }

    private WeatherDTO areCorrectDateAndHour(String location, String date, String hour) {
        try{
            if (Integer.parseInt(hour) < 0 || Integer.parseInt(hour) > 24) {
                return ExceptionHandling.errorMessage("Incorrect hour.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);

            int endOfDateIndex = 10;
            String actualDate = getCurrentWeather(location).getTime().substring(0, endOfDateIndex);
            LocalDate parsedActualDate = LocalDate.parse(actualDate, formatter);

            int week = 7;
            if(!parsedDate.isBefore(parsedActualDate) || !parsedDate.isAfter(parsedActualDate.minusDays(week))) {
                return ExceptionHandling.errorMessage("Possibility to check only the date 7 days ago.");
            }
            return null;

        } catch (NumberFormatException e) {
            return ExceptionHandling.errorMessage("Incorrect hour format.");
        } catch (DateTimeParseException e) {
            return ExceptionHandling.errorMessage("Incorrect date format.");
        } catch (NullPointerException e) {
            return ExceptionHandling.errorMessage("Incorrect location.");
        }
    }

    private JsonNode weatherWithEntryHour(JsonNode forecastDay, String hour) {
        String correctHour = formatHour(hour);
        for (JsonNode hourData : forecastDay.path("hour")) {
            String time = hourData.path("time").asText();
            int hourIndex = 11;
            if (time.startsWith(correctHour, hourIndex)) {
                return hourData;
            }
        }
        return null;
    }

    private String formatHour(String hour) {
        if(hour.length() == 1) {
            return "0" + hour;
        }
        if(hour.equals("24")) {
            return "00";
        }
        return hour;
    }

    private void fillResultWithDataFromApi(WeatherDTO weatherDTO, JsonNode location, JsonNode details) {

        // "local time" is for current weather
        // "time" is for historical weather
        weatherDTO.setTime(details.path("time").asText().equals("") ?
                location.path("localtime").asText() : details.path("time").asText());

        weatherDTO.setCity(location.path("name").asText());
        weatherDTO.setCountry(location.path("country").asText());
        weatherDTO.setTemperature(details.path("temp_c").asDouble());
        weatherDTO.setWindSpeed(details.path("wind_kph").asDouble());
        weatherDTO.setWindDirectory(details.path("wind_dir").asText());
        weatherDTO.setPressure(details.path("pressure_mb").asDouble());
        weatherDTO.setHumidity(details.path("humidity").asInt());

        weatherDTO.setCondition(new Condition());
        weatherDTO.getCondition().setText(details.path("condition").path("text").asText());
        weatherDTO.getCondition().setIcon(details.path("condition").path("icon").asText());

        log.info("Fetching API weather data: " + location.path("name").asText());
    }
}