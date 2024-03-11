package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

import java.time.LocalDate;

import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.SUCCESS_MESSAGE;
import static com.weatherForecasting.backend.weatherpredictioncrud.ValidationError.*;

class WeatherPredictionValidator {
    private static final int EXTRA_DAYS_TO_START_PREDICTING = 2;
    private static final int MIN_TEMPERATURE = -100;
    private static final int MAX_TEMPERATURE = 100;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    public ValidationResult validate(WeatherPredictionDto weatherPredictionDTO, LocalTimeDto localTimeDto) {
        if (!doesLocationExist(localTimeDto)) {
            return new ValidationResult(LOCATION_NOT_FOUND);
        }
        if (!hasUserGaveCorrectFutureDate(weatherPredictionDTO, localTimeDto)) {
            return new ValidationResult(DATE_IN_WRONG_TIME);
        }
        if (!hasUserGaveHourInRange(weatherPredictionDTO)) {
            return new ValidationResult(HOUR_OUT_OF_RANGE);
        }
        if (!hasUserGaveTemperatureInRange(weatherPredictionDTO)) {
            return new ValidationResult(TEMPERATURE_OUT_OF_RANGE);
        }

        return new ValidationResult(SUCCESS_MESSAGE, true);
    }

    private boolean doesLocationExist(LocalTimeDto localTimeDto) {
        return localTimeDto.hasExists();
    }

    private boolean hasUserGaveCorrectFutureDate(WeatherPredictionDto weatherPredictionDTO, LocalTimeDto localTimeDto) {
        LocalDate date = weatherPredictionDTO.forecastDate();
        LocalDate initialDate = localTimeDto.date().plusDays(EXTRA_DAYS_TO_START_PREDICTING);
        return date.isAfter(initialDate);
    }

    private boolean hasUserGaveHourInRange(WeatherPredictionDto weatherPredictionDTO) {
        int hour = weatherPredictionDTO.forecastHour();
        return hour >= MIN_HOUR && hour <= MAX_HOUR;
    }

    private boolean hasUserGaveTemperatureInRange(WeatherPredictionDto weatherPredictionDTO) {
        double temperature = weatherPredictionDTO.temperature();
        return temperature > MIN_TEMPERATURE && temperature < MAX_TEMPERATURE;
    }
}
