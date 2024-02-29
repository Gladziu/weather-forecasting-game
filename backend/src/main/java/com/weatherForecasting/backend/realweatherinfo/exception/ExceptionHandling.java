package com.weatherForecasting.backend.realweatherinfo.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.realweatherinfo.dto.ErrorMessage;
import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

@Slf4j
public class ExceptionHandling {

    public static WeatherDTO handleHttpClientException(WebClientResponseException e, ObjectMapper objectMapper) {
        try {
            JsonNode jsonError = objectMapper.readTree(e.getResponseBodyAsString());

            int errorCode = e.getStatusCode().value();
            String message = jsonError.path("error").path("message").asText();

            return errorMessage(message, HttpStatus.valueOf(errorCode));
        } catch (IOException ex) {
            log.error("Unable to parse error response from the API.");
        }
        return new WeatherDTO();
    }

    public static WeatherDTO errorMessage(String message, HttpStatus httpStatus) {
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setError(new ErrorMessage());
        weatherDTO.getError().setErrorCode(httpStatus.value());
        weatherDTO.getError().setMessage(message);
        log.error(message + " Code: " + httpStatus.value());
        return weatherDTO;
    }


}
