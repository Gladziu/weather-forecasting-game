package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.weatherForecasting.backend.weatherpredictioncrud.CrudOperationResult.SUCCESS_MESSAGE;
import static com.weatherForecasting.backend.weatherpredictioncrud.ValidationError.*;
import static java.util.stream.Collectors.joining;

class WeatherPredictionReceiverValidator {

    private static final String ERROR_DELIMITER = ", ";

    private static final int MIN_TEMPERATURE = -100;
    private static final int MAX_TEMPERATURE = 100;
    private static final int MIN_HOUR = 1;
    private static final int MAX_HOUR = 24;
    private static final int MAX_HOUR_DIGITS = 2;

    List<ValidationError> errors = new ArrayList<>();

    public ValidationResult validate(WeatherPredictionDTO weatherPredictionDTO) {
        if (!isDateInFuture(weatherPredictionDTO)) {
            errors.add(FUTURE_OR_INCORRECT_FORMAT_YYYY_MM_DD);
        }
        if (!isHourInRange(weatherPredictionDTO)) {
            errors.add(HOUR_FORMAT_HH_OR_H);
        }
        if (!isTemperatureInRange(weatherPredictionDTO)) {
            errors.add(TEMPERATURE_OUT_OF_RANGE);
        }
        if (errors.isEmpty()) {
            return new ValidationResult(SUCCESS_MESSAGE, true);
        }
        String message = concatenateValidationMessage();
        return new ValidationResult(message, false);
    }

    private String concatenateValidationMessage() {
        return errors.stream()
                .map(error -> error.message)
                .collect(joining(ERROR_DELIMITER));
    }

    private boolean isTemperatureInRange(WeatherPredictionDTO weatherPredictionDTO) {
        double temperature = weatherPredictionDTO.temperature();
        return temperature > MIN_TEMPERATURE && temperature < MAX_TEMPERATURE;
    }

    private boolean isHourInRange(WeatherPredictionDTO weatherPredictionDTO) {
        String hour = weatherPredictionDTO.forecastHour();
        if (hour.length() > MAX_HOUR_DIGITS) {
            return false;
        }
        try {
            int hourValue = Integer.parseInt(hour);
            return hourValue >= MIN_HOUR && hourValue <= MAX_HOUR;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDateInFuture(WeatherPredictionDTO weatherPredictionDTO) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = weatherPredictionDTO.forecastDate();
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            LocalDate currentDate = LocalDate.now();
            return parsedDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
