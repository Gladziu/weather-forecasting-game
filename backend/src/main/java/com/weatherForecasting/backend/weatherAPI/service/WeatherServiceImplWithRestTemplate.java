/*

DZIALAJACY SERVICE Z REST TEMPLATE

package com.weatherForecasting.backend.weatherAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherServiceImplWithRestTemplate implements WeatherService{
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    //TODO: dodac SLF4J i w ten sposob zapisywac do logow wyjątki/błędy
    //TODO: zamiast RestTemplate podobno lepiej WebClient
    //TODO: zobacz chatgpt
    @Override
    public ObjectNode getCurrentWeather(String location) {
        ObjectNode result = objectMapper.createObjectNode();
        String fullUrl = apiUrl + "/current.json?key=" + apiKey + "&q=" + location + "&aqi=no";
        try {
            String jsonResponse = restTemplate.getForObject(fullUrl, String.class);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode forecastLocation = rootNode.path("location");
            JsonNode forecastDetails = rootNode.path("current");

            fillResultWithApiWeatherData(result, forecastLocation, forecastDetails);

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            handleHttpClientError(e, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObjectNode getHistoricalWeather(String location, String date, String hour) {

        //Dodaj warunek czy data nie jest czasem późniejsza niż 7 dni wstecz

        String fullUrl = apiUrl + "/history.json?key=" + apiKey + "&q=" + location + "&dt=" + date;
        ObjectNode result = objectMapper.createObjectNode();
        try {
            String jsonResponse = restTemplate.getForObject(fullUrl, String.class);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode forecastLocation = rootNode.path("location");
            JsonNode forecastDay = rootNode.path("forecast").path("forecastday").get(0);

            //Iterate through all historical hours and save details with input hour
            for (JsonNode hourData : forecastDay.path("hour")) {
                String time = hourData.path("time").asText();
                if (time.startsWith(hour, 11)) {
                    fillResultWithApiWeatherData(result, forecastLocation, hourData);
                    break;
                }
            }
        } catch (HttpClientErrorException e) {
            handleHttpClientError(e, result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void fillResultWithApiWeatherData(ObjectNode result, JsonNode forecastLocation, JsonNode forecastDetails) {
        // The local time and time are located in different JsonNode
        String time = forecastDetails.path("time").asText().equals("") ?
                forecastLocation.path("localtime").asText() : forecastDetails.path("time").asText();
        result.put("time", time);

        result.put("name", forecastLocation.path("name").asText());
        result.put("country", forecastLocation.path("country").asText());
        result.put("temp_c", forecastDetails.path("temp_c").asDouble());
        result.put("wind_kph", forecastDetails.path("wind_kph").asDouble());
        result.put("wind_dir", forecastDetails.path("wind_dir").asText());
        result.put("pressure_mb", forecastDetails.path("pressure_mb").asDouble());
        result.put("humidity", forecastDetails.path("humidity").asInt());
    }

    private void handleHttpClientError(HttpClientErrorException e, ObjectNode result) {
        e.printStackTrace();
        try {
            JsonNode jsonError = objectMapper.readTree(e.getResponseBodyAsString());
            result.put("code", e.getStatusCode().value());
            result.put("code_api", jsonError.path("error").path("code").asInt());
            result.put("message", jsonError.path("error").path("message").asText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}*/
