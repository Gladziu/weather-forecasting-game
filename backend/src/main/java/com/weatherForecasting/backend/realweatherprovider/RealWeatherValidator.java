package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.ErrorMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.weatherForecasting.backend.realweatherprovider.ValidationError.DATE_OUT_OF_RANGE;
import static com.weatherForecasting.backend.realweatherprovider.ValidationError.HOUR_OUT_OF_RANGE;
import static java.util.stream.Collectors.joining;

class RealWeatherValidator {
    private static final String ERROR_DELIMITER = ", ";
    private static final int HISTORY_DAYS_RANGE = 7;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    public ValidationResult validateParameters(String date, int hour) {
        List<ValidationError> errors = new ArrayList<>();
        if (!hasUserGaveCorrectPastDate(date)) {
            errors.add(DATE_OUT_OF_RANGE);
        }
        if (!hasUserHaveHourInRange(hour)) {
            errors.add(HOUR_OUT_OF_RANGE);
        }
        if (errors.isEmpty()) {
            return new ValidationResult(true);
        }
        String message = concatenateValidationMessage(errors);
        return new ValidationResult(new ErrorMessage(message), false);
    }

    private String concatenateValidationMessage(List<ValidationError> errors) {
        return errors.stream()
                .map(error -> error.message)
                .collect(joining(ERROR_DELIMITER));
    }

    private boolean hasUserGaveCorrectPastDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            LocalDate maxRange = LocalDate.now();
            LocalDate minRange = LocalDate.now().minusDays(HISTORY_DAYS_RANGE);
            return parsedDate.isAfter(minRange) && parsedDate.isBefore(maxRange);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean hasUserHaveHourInRange(int hour) {
        return hour >= MIN_HOUR && hour <= MAX_HOUR;
    }

}
