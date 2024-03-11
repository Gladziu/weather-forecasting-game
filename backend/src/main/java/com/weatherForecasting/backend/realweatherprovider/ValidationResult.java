package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.ErrorMessage;

record ValidationResult(ErrorMessage message, boolean valid) {
    public ValidationResult(boolean isValid) {
        this(null, isValid);
    }

    public boolean isFailure() {
        return !valid;
    }
}