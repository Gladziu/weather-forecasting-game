package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.*;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;

public class RealWeatherFacade {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String apiUrl;
    private final RealWeatherApiReceiver realWeatherApiReceiver;
    private final RealWeatherValidator realWeatherValidator;

    public RealWeatherFacade(RealWeatherApiReceiver realWeatherApiReceiver, RealWeatherValidator realWeatherValidator) {
        this.realWeatherApiReceiver = realWeatherApiReceiver;
        this.realWeatherValidator = realWeatherValidator;
    }

    public RealWeatherDto getCurrentWeather(String location) {
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            ErrorMessage message = currentWeather.errorMessage();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        return RealWeatherMapper.mapCurrentWeatherToRealWeatherDto(currentWeather);
    }

    public RealWeatherDto getHistoricalWeather(String location, String date, int hour) {
        ValidationResult validate = realWeatherValidator.validateParameters(date, hour);
        if (validate.isFailure()) {
            ErrorMessage message = validate.message();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        URI uri = ApiUriGenerator.historicalWeatherUri(location, date, apiKey, apiUrl);
        HistoricalWeatherDto historicalWeather = realWeatherApiReceiver.historicalWeatherResponse(uri);
        if (historicalWeather.isFailure()) {
            ErrorMessage message = historicalWeather.errorMessage();
            return RealWeatherDto.builder()
                    .error(message)
                    .build();
        }
        return RealWeatherMapper.mapHistoricalWeatherToRealWeatherDto(historicalWeather, hour);
    }

    public LocalTimeDto locationLocalTime(WeatherPredictionDto weatherPrediction) {
        String location = weatherPrediction.location();
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            return LocalTimeDto.builder()
                    .failure(true)
                    .build();
        }
        return RealWeatherMapper.mapCurrentWeatherToLocalTimeDto(currentWeather);
    }

    public WeatherReportDto getWeatherReport(String location) {
        URI uri = ApiUriGenerator.currentWeatherUri(location, apiKey, apiUrl);
        CurrentWeatherDto currentWeather = realWeatherApiReceiver.currentWeatherApiResponse(uri);
        if (currentWeather.isFailure()) {
            return WeatherReportDto.builder()
                    .failure(true)
                    .build();
        }
        return RealWeatherMapper.mapCurrentWeatherToWeatherReportDto(currentWeather);
    }

}