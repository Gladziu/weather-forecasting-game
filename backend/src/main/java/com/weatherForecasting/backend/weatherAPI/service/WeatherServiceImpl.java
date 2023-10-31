package com.weatherForecasting.backend.weatherAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.weatherAPI.dto.Condition;
import com.weatherForecasting.backend.weatherAPI.dto.ErrorMessage;
import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    //TODO: ogarnij co to WebClient i Mono w jsonResponse -> https://tanzu.vmware.com/developer/guides/spring-webclient-gs/
    //TODO: dodac najwazniejsze komentarze
    @Autowired
    public WeatherServiceImpl(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public  WeatherDTO getCurrentWeather(String location) {
        String fullUrl = apiUrl + "/current.json?key=" + apiKey + "&q=" + location + "&aqi=no";
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

            fillResultWithApiWeatherData(weatherDTO, forecastLocation, forecastDetails);

        } catch (WebClientResponseException e) {
            handleHttpClientError(weatherDTO, e);

        } catch (IOException e) {
            log.error("An error occurred while processing the request");
        }

        return weatherDTO;
    }

    @Override
    public WeatherDTO getHistoricalWeather(String location, String date, String hour) {
        String fullUrl = apiUrl + "/history.json?key=" + apiKey + "&q=" + location + "&dt=" + date;
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

            JsonNode hourData = getrDataOfInputHour(forecastDay, hour);
            if(hourData != null) {
                fillResultWithApiWeatherData(weatherDTO, forecastLocation, hourData);
            } else {
                hourNotFound(weatherDTO);
            }

        } catch (WebClientResponseException e) {
            handleHttpClientError(weatherDTO, e);

        } catch (IOException e) {
            log.error("An error occurred while processing the request");
        }

        return weatherDTO;
    }

    private void hourNotFound(WeatherDTO weatherDTO) {
        weatherDTO.setError(new ErrorMessage());
        weatherDTO.getError().setErrorCode(HttpStatus.NOT_FOUND.value());
        weatherDTO.getError().setMessage("Hour not found");

        log.error("Code: 404 Message: No matching hour found.");
    }

    private JsonNode getrDataOfInputHour(JsonNode forecastDay, String hour) {
        for (JsonNode hourData : forecastDay.path("hour")) {
            String time = hourData.path("time").asText();
            if (time.startsWith(hour, 11)) {
                return hourData;
            }
        }
        return null;
    }

    private void handleHttpClientError(WeatherDTO weatherDTO, WebClientResponseException e) {
        try {
            JsonNode jsonError = objectMapper.readTree(e.getResponseBodyAsString());

            int errorCode = e.getStatusCode().value();
            String message = jsonError.path("error").path("message").asText();

            weatherDTO.setError(new ErrorMessage());
            weatherDTO.getError().setErrorCode(errorCode);
            weatherDTO.getError().setMessage(message);

            log.error("Code: " + errorCode + " Message: " + message);
        } catch (IOException ex) {
            log.error("Unable to parse error response from the API.");
        }
    }

    private void fillResultWithApiWeatherData(WeatherDTO weatherDTO, JsonNode location, JsonNode details) {
        weatherDTO.setCondition(new Condition());
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
        weatherDTO.getCondition().setText(details.path("condition").path("text").asText());
        weatherDTO.getCondition().setIcon(details.path("condition").path("icon").asText());
        log.info("Fetching API weather data: " + location.path("name").asText());
    }
}