package com.weatherForecasting.backend.realweatherprovider;

enum ValidationError {
    DATE_OUT_OF_RANGE("date out of range"),
    HOUR_OUT_OF_RANGE("hour out of range");
    final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
