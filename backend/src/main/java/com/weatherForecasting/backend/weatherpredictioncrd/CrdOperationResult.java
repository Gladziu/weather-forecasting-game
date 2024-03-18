package com.weatherForecasting.backend.weatherpredictioncrd;

public record CrdOperationResult(String message) {
    static final String DELETED_WEATHER_PREDICTION = "weather prediction deleted";
    static final String DELETE_ERROR = "can not find weather prediction, delete error";
    static final String SUCCESS_MESSAGE = "weather prediction successfully added";

}
