package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

import java.util.List;
import java.util.UUID;

import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.DELETED_WEATHER_PREDICTION;
import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.DELETE_ERROR;


public class WeatherPredictionCrudFacade {

    private final WeatherPredictionCrudRepository weatherPredictionCrudRepository;
    private final WeatherPredictionValidator validator;
    private final RealWeatherFacade realWeather;

    public WeatherPredictionCrudFacade(WeatherPredictionCrudRepository weatherPredictionCrudRepository, WeatherPredictionValidator validator, RealWeatherFacade realWeather) {
        this.weatherPredictionCrudRepository = weatherPredictionCrudRepository;
        this.validator = validator;
        this.realWeather = realWeather;
    }

    public CrudOperationResult addPrediction(WeatherPredictionDto weatherPredictionDTO) {
        LocalTimeDto localTime = realWeather.locationLocalTime(weatherPredictionDTO);
        ValidationResult validate = validator.validate(weatherPredictionDTO, localTime);
        String message = validate.message();
        if (validate.isFailure()) {
            return new CrudOperationResult(message);
        }
        WeatherPrediction userWeather = WeatherPredictionMapper.createWeatherPredictionFromDTO(weatherPredictionDTO, localTime);
        weatherPredictionCrudRepository.save(userWeather);
        return new CrudOperationResult(message);
    }

    public List<WeatherPredictionDto> showPrediction(String userName) {
        List<WeatherPrediction> predictions = weatherPredictionCrudRepository.findAllByUsername(userName);
        return WeatherPredictionMapper.mapToDTOs(predictions);
    }

    public CrudOperationResult deletePrediction(UUID id) {
        if (weatherPredictionCrudRepository.existsById(id)) {
            weatherPredictionCrudRepository.deleteById(id);
            return new CrudOperationResult(DELETED_WEATHER_PREDICTION);
        }
        return new CrudOperationResult(DELETE_ERROR);
    }

}
