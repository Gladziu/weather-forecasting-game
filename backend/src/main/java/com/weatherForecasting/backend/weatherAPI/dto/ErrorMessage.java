package com.weatherForecasting.backend.weatherAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private int errorCode;
    private String message;
}
