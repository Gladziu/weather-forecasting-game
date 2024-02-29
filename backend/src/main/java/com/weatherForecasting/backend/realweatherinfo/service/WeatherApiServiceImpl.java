package com.weatherForecasting.backend.realweatherinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;
import com.weatherForecasting.backend.realweatherinfo.dto.mapper.WeatherDtoMapper;
import com.weatherForecasting.backend.realweatherinfo.exception.ExceptionHandling;
import com.weatherForecasting.backend.realweatherinfo.util.DateHourValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Slf4j
public class WeatherApiServiceImpl implements WeatherApiService {
    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    private final DateHourValidator dateHourValidator;


    public WeatherApiServiceImpl(WebClient webClient, ObjectMapper objectMapper, @Lazy DateHourValidator dateHourValidator) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.dateHourValidator = dateHourValidator;
    }

    private static final String CURRENT_WEATHER_URL = "/current.json?key=";
    private static final String HISTORY_WEATHER_URL = "/history.json?key=";

    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;

    @Override
    public WeatherDTO getCurrentWeather(String location) {
        String fullUrl = buildApiUrl(location, CURRENT_WEATHER_URL,"&aqi=no");
        return processWeatherRequest(fullUrl, null);
    }

    @Override
    public WeatherDTO getHistoricalWeather(String location, String date, String hour) {
        //TODO: NIE DZIALA HISTORICAL WEATHER, ZAWSZE WYRZUCA Z TYM BLAD "INCORRECT LOCATION"
        /*WeatherDTO weatherDTO = dateHourValidator.areCorrectDateAndHour(location, date, hour);
        if (weatherDTO != null) {
            return weatherDTO;
        }*/
        String fullUrl = buildApiUrl(location, HISTORY_WEATHER_URL,"&dt=" + date );
        return processWeatherRequest(fullUrl, hour);
    }

    private WeatherDTO processWeatherRequest(String fullUrl, String hour) {
        Mono<String> jsonResponseMono = webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class);

        try {
            String jsonResponse = jsonResponseMono.block(); //tutaj wszytskie bled z API sa wylapywane
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode forecastLocation = rootNode.path("location");
            JsonNode forecastDetails;

            if(hour != null) {
                //TODO: hour validation
                JsonNode everyHourDetails = rootNode.path("forecast").path("forecastday").get(0);
                forecastDetails = detailsWithSpecificHour(everyHourDetails, hour);
            } else {
                forecastDetails = rootNode.path("current");
            }
            return WeatherDtoMapper.mapApiWeather(forecastLocation,forecastDetails);
            //fillResultWithDataFromApi(weatherDTO, forecastLocation, forecastDetails);

        } catch (WebClientResponseException e) {
            return ExceptionHandling.handleHttpClientException(e, objectMapper);

        } catch (IOException e) {
            log.error("An error occurred while processing the request");
        }

        return new WeatherDTO();
    }

    private String buildApiUrl(String location, String weather, String additional) {
        return apiUrl + weather + apiKey + "&q=" + location + additional;
    }

    private JsonNode detailsWithSpecificHour(JsonNode forecastDay, String hour) {
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
        if (hour.length() == 1) {
            return "0" + hour;
        }
        if (hour.equals("24")) {
            return "00";
        }
        return hour;
    }

  /*  private void fillResultWithDataFromApi(WeatherDTO weatherDTO, JsonNode location, JsonNode details) {

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
    }*/
}