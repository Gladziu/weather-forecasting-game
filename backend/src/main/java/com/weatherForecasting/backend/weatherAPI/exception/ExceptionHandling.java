package com.weatherForecasting.backend.weatherAPI.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.weatherAPI.dto.ErrorMessage;
import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

@Slf4j
public class ExceptionHandling {

    public static void handleHttpClientException(WeatherDTO weatherDTO, WebClientResponseException e, ObjectMapper objectMapper) {
        try {
            JsonNode jsonError = objectMapper.readTree(e.getResponseBodyAsString());

            int errorCode = e.getStatusCode().value();
            String message = jsonError.path("error").path("message").asText();
            weatherDTO.setError(new ErrorMessage());
            weatherDTO.getError().setErrorCode(errorCode);
            weatherDTO.getError().setMessage(message);

            log.error(message + " Code: " + errorCode);
        } catch (IOException ex) {
            log.error("Unable to parse error response from the API.");
        }
    }

    public static WeatherDTO errorMessage(String message) {
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setError(new ErrorMessage());
        weatherDTO.getError().setErrorCode(HttpStatus.NOT_FOUND.value());
        weatherDTO.getError().setMessage(message);

        log.error(message + " Code: 404");

        return weatherDTO;
    }


}
