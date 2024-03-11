package com.weatherForecasting.backend.weatherpredictioncrud;

public record CrudOperationResult(String message) {
    static final String DELETED_WEATHER_PREDICTION = "weather prediction deleted";
    static final String DELETE_ERROR = "can not find weather prediction, delete error";
    static final String SUCCESS_MESSAGE = "weather prediction successfully added";

}
