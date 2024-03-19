package com.weatherForecasting.backend.weatherpredictioncrd;

enum ValidationError {
    LOCATION_NOT_FOUND("location not found"),
    DATE_IN_WRONG_TIME("date must be in the future"),
    HOUR_OUT_OF_RANGE("hour out of range"),
    TEMPERATURE_OUT_OF_RANGE("temperature out of range");

    final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
