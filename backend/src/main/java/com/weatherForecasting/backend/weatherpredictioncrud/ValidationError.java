package com.weatherForecasting.backend.weatherpredictioncrud;

enum ValidationError {
    FUTURE_OR_INCORRECT_FORMAT_YYYY_MM_DD("date must be in the future or incorrect format yyyy-MM-dd"),
    HOUR_FORMAT_HH_OR_H("incorrect hour format HH or H"),
    TEMPERATURE_OUT_OF_RANGE("temperature out of range");

    final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
