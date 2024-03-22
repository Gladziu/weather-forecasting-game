package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.*;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

import static com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderMapper.*;

public class RealWeatherProviderFacade {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;
    private final RealWeatherApiDataReceiver realWeatherApiDataReceiver;
    private final RealWeatherProviderValidator realWeatherProviderValidator;

    public RealWeatherProviderFacade(RealWeatherApiDataReceiver realWeatherApiDataReceiver, RealWeatherProviderValidator realWeatherProviderValidator) {
        this.realWeatherApiDataReceiver = realWeatherApiDataReceiver;
        this.realWeatherProviderValidator = realWeatherProviderValidator;
    }

    public RealWeatherDto retrieveCurrentWeather(String location) {
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiDataReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            ErrorMessage message = currentWeather.errorMessage();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        return mapCurrentWeatherToRealWeatherDto(currentWeather);
    }

    public RealWeatherDto retrieveHistoricalWeather(String location, String date, int hour) {
        ValidationResult validate = realWeatherProviderValidator.validateParameters(date, hour);
        if (validate.isFailure()) {
            ErrorMessage message = validate.message();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        URI uri = ApiUriGenerator.historicalWeatherUri(location, date, apiKey, apiUrl);
        HistoricalWeatherDto historicalWeather = realWeatherApiDataReceiver.historicalWeatherResponse(uri);
        if (historicalWeather.isFailure()) {
            ErrorMessage message = historicalWeather.errorMessage();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        return mapHistoricalWeatherToRealWeatherDto(historicalWeather, hour);
    }

    public LocalTimeDto locationLocalTime(WeatherPredictionDto weatherPrediction) {
        String location = weatherPrediction.location();
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiDataReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            return LocalTimeDto.builder()
                    .failure(true)
                    .build();
        }
        return mapCurrentWeatherToLocalTimeDto(currentWeather);
    }

    public WeatherReportDto weatherReport(String location) {
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiDataReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            return WeatherReportDto.builder()
                    .failure(true)
                    .build();
        }
        return mapCurrentWeatherToWeatherReportDto(currentWeather);
    }

}