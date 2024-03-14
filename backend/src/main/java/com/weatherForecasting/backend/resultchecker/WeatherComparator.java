package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.WeatherReportDto;
import com.weatherForecasting.backend.resultchecker.dto.CheckedPredictionDto;
import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class WeatherComparator {
    private final RealWeatherFacade realWeatherFacade;

    WeatherComparator(RealWeatherFacade realWeatherFacade) {
        this.realWeatherFacade = realWeatherFacade;
    }


    public List<CheckedPredictionDto> comparePredictionWithRealWeather(List<WeatherPredictionDto> predictions) {
        List<CheckedPredictionDto> checkedPredictions = new ArrayList<>();
        for (WeatherPredictionDto prediction : predictions) {
            WeatherReportDto weatherReport = realWeatherFacade.getWeatherReport(prediction.location());
            if (!weatherReport.isFailure() && areDatesEqual(prediction, weatherReport)) {
                checkedPredictions.add(new CheckedPredictionDto(prediction, weatherReport));
            }
        }
        return checkedPredictions;
    }

    private boolean areDatesEqual(WeatherPredictionDto prediction, WeatherReportDto weatherReport) {
        LocalDate predictionDate = prediction.forecastDate();
        LocalDate realDate = weatherReport.date();
        if (!predictionDate.isEqual(realDate)) {
            return false;
        }
        int predictionHour = prediction.forecastHour();
        int realHour = weatherReport.hour();
        return predictionHour == realHour;
    }
}
