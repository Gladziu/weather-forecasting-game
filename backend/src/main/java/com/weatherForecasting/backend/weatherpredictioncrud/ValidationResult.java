package com.weatherForecasting.backend.weatherpredictioncrud;

record ValidationResult(String message, boolean valid) {
    public ValidationResult(ValidationError validationError) {
        this(validationError.message, false);
    }

    public boolean isFailure() {
        return !valid;
    }
}
