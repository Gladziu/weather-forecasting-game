package com.weatherForecasting.backend.realweatherinfo.util;

import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;
import com.weatherForecasting.backend.realweatherinfo.exception.ExceptionHandling;
import com.weatherForecasting.backend.realweatherinfo.service.WeatherApiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateHourValidator {
    private final WeatherApiService weatherApiService;

    public DateHourValidator(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    private static final int DAYS_IN_WEEK = 7;

    public WeatherDTO areCorrectDateAndHour(String location, String date, String hour) {
        try {
            validateHourFormat(hour);

            LocalDate parsedDate = parseDate(date);
            LocalDate parsedActualDate = parseActualDate(location);

            validateDateRange(parsedDate, parsedActualDate);

        } catch (NullPointerException e) {
            return ExceptionHandling.errorMessage("Incorrect location.", HttpStatus.BAD_REQUEST);
        } catch (DateTimeParseException e) {
            return ExceptionHandling.errorMessage("Incorrect date format.", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return ExceptionHandling.errorMessage("Incorrect hour format.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return ExceptionHandling.errorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    private void validateHourFormat(String hour) {
        int parsedHour = Integer.parseInt(hour);
        if (parsedHour < 0 || parsedHour > 24) {
            throw new NumberFormatException();
        }
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private LocalDate parseActualDate(String location) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int lengthOfTheDate = 10;
        String actualDate = weatherApiService.getCurrentWeather(location).getTime().substring(0, lengthOfTheDate);
        return LocalDate.parse(actualDate, formatter);
    }

    private void validateDateRange(LocalDate parsedDate, LocalDate parsedActualDate) {
        if (!parsedDate.isBefore(parsedActualDate) || !parsedDate.isAfter(parsedActualDate.minusDays(DAYS_IN_WEEK))) {
            throw new IllegalArgumentException("Possibility to check only the date 7 days ago.");
        }
    }

}
