package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;

import java.util.List;
import java.util.UUID;

import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.DELETED_WEATHER_PREDICTION;
import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.DELETE_ERROR;


public class WeatherPredictionCrudFacade {

    private final WeatherPredictionRepository weatherPredictionRepository;
    private final WeatherPredictionReceiverValidator receiverValidator;

    public WeatherPredictionCrudFacade(WeatherPredictionRepository weatherPredictionRepository, WeatherPredictionReceiverValidator receiverValidator) {
        this.weatherPredictionRepository = weatherPredictionRepository;
        this.receiverValidator = receiverValidator;
    }

    public CrudOperationResult addPrediction(WeatherPredictionDTO weatherPredictionDTO) {
        ValidationResult validate = receiverValidator.validate(weatherPredictionDTO);
        String message = validate.message();
        if (validate.isFailure()) {
            return new CrudOperationResult(message);
        }
        WeatherPrediction userWeather = WeatherPredictionMapper.createWeatherPredictionFromDTO(weatherPredictionDTO);
        weatherPredictionRepository.save(userWeather);
        return new CrudOperationResult(message);
    }

    public List<WeatherPredictionDTO> showPrediction(String userName) {
        List<WeatherPrediction> predictions = weatherPredictionRepository.findAllByUsername(userName);
        return WeatherPredictionMapper.mapToDTOs(predictions);
    }

    public CrudOperationResult deletePrediction(UUID id) {
        if (weatherPredictionRepository.existsById(id)) {
            weatherPredictionRepository.deleteById(id);
            return new CrudOperationResult(DELETED_WEATHER_PREDICTION, id);
        }
        return new CrudOperationResult(DELETE_ERROR, id);
    }

}
