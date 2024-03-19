package com.weatherForecasting.backend.realweatherprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherForecasting.backend.realweatherprovider.dto.CurrentWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.ErrorMessage;
import com.weatherForecasting.backend.realweatherprovider.dto.HistoricalWeatherDto;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static com.weatherForecasting.backend.realweatherprovider.dto.ErrorMessage.LOCATION_NOT_FOUND;

class RealWeatherApiDataReceiver {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrentWeatherDto currentWeatherApiResponse(URI uri) {
        try {
            URL url = (uri).toURL();
            return objectMapper.readValue(url, CurrentWeatherDto.class);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(LOCATION_NOT_FOUND);
            return CurrentWeatherDto.builder()
                    .errorMessage(errorMessage)
                    .failure(true)
                    .build();
        }
    }

    public HistoricalWeatherDto historicalWeatherResponse(URI uri) {
        try {
            URL url = (uri).toURL();
            return objectMapper.readValue(url, HistoricalWeatherDto.class);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(LOCATION_NOT_FOUND);
            return HistoricalWeatherDto.builder()
                    .errorMessage(errorMessage)
                    .failure(true)
                    .build();
        }
    }
}
