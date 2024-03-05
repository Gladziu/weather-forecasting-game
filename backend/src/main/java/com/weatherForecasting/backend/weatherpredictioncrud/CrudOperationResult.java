package com.weatherForecasting.backend.weatherpredictioncrud;

import java.util.UUID;

public record CrudOperationResult(String message, UUID weatherPredictionId) {
    static final String DELETED_WEATHER_PREDICTION = "weather prediction deleted";
    static final String DELETE_ERROR = "can not find weather prediction, delete error";
    static final String SUCCESS_MESSAGE = "weather prediction successfully added";

    public CrudOperationResult(String message) {
        this(message, null);
    }
}
