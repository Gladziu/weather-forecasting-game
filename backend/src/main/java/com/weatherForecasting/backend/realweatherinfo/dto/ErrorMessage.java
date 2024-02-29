package com.weatherForecasting.backend.realweatherinfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private int errorCode;
    private String message;
}
