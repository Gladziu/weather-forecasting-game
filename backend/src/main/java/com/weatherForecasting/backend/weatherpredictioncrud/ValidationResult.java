package com.weatherForecasting.backend.weatherpredictioncrud;

record ValidationResult(String message, boolean isValid) {
    public boolean isFailure() {
        return !isValid;
    }
}
