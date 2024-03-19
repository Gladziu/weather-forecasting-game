package com.weatherForecasting.backend.weatherpredictioncrd;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.weatherForecasting.backend.weatherpredictioncrd.CrdOperationResult.DELETED_WEATHER_PREDICTION;
import static com.weatherForecasting.backend.weatherpredictioncrd.CrdOperationResult.DELETE_ERROR;
import static com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionMapper.createWeatherPredictionFromDTO;
import static com.weatherForecasting.backend.weatherpredictioncrd.WeatherPredictionMapper.mapToDTOs;


public class WeatherPredictionCrdFacade {

    private final WeatherPredictionRepository weatherPredictionRepository;
    private final WeatherPredictionValidator validator;
    private final RealWeatherProviderFacade realWeather;

    public WeatherPredictionCrdFacade(WeatherPredictionRepository weatherPredictionRepository, WeatherPredictionValidator validator, RealWeatherProviderFacade realWeather) {
        this.weatherPredictionRepository = weatherPredictionRepository;
        this.validator = validator;
        this.realWeather = realWeather;
    }

    public CrdOperationResult addPrediction(WeatherPredictionDto weatherPredictionDTO) {
        LocalTimeDto localTime = realWeather.locationLocalTime(weatherPredictionDTO);
        ValidationResult validate = validator.validate(weatherPredictionDTO, localTime);
        String message = validate.message();
        if (validate.isFailure()) {
            return new CrdOperationResult(message);
        }
        WeatherPrediction userWeather = createWeatherPredictionFromDTO(weatherPredictionDTO, localTime);
        weatherPredictionRepository.save(userWeather);
        return new CrdOperationResult(message);
    }

    public List<WeatherPredictionDto> showPredictions(String userName) {
        List<WeatherPrediction> predictions = weatherPredictionRepository.findAllByUsername(userName);
        return mapToDTOs(predictions);
    }

    public CrdOperationResult deletePrediction(UUID id) {
        if (weatherPredictionRepository.existsById(id)) {
            weatherPredictionRepository.deleteById(id);
            return new CrdOperationResult(DELETED_WEATHER_PREDICTION);
        }
        return new CrdOperationResult(DELETE_ERROR);
    }

    public List<WeatherPredictionDto> getPredictionsInTheDateScope(LocalDate date) {
        List<WeatherPrediction> predictions = weatherPredictionRepository.findByForecastDateGreaterThanEqual(date);
        return mapToDTOs(predictions);
    }

}
