package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.*;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Current;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Forecastday;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Hour;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class RealWeatherMapper {
    static DateTimeFormatter dateAndHourFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static RealWeatherDto mapCurrentWeatherToRealWeatherDto(CurrentWeatherDto currentWeather) {
        Location location = currentWeather.location();
        Current currentDetails = currentWeather.current();
        return RealWeatherDto.builder()
                .time(location.localtime())
                .city(location.name())
                .country(location.country())
                .temperature(currentDetails.temp_c())
                .windSpeed(currentDetails.wind_kph())
                .windDirectory(currentDetails.wind_dir())
                .pressure(currentDetails.pressure_mb())
                .humidity(currentDetails.humidity())
                .build();
    }

    public static RealWeatherDto mapHistoricalWeatherToRealWeatherDto(HistoricalWeatherDto historicalWeather, int hourValue) {
        Location location = historicalWeather.location();
        Forecastday forecastDay = historicalWeather.forecast().forecastday().get(0);
        Hour hour = forecastDay.hour().get(hourValue);
        return RealWeatherDto.builder()
                .time(hour.time())
                .city(location.name())
                .country(location.country())
                .temperature(hour.temp_c())
                .windSpeed(hour.wind_kph())
                .windDirectory(hour.wind_dir())
                .pressure(hour.pressure_mb())
                .humidity(hour.humidity())
                .build();
    }

    public static LocalTimeDto mapCurrentWeatherToLocalTimeDto(CurrentWeatherDto currentWeather) {
        LocalDate date = parseDate(currentWeather);
        return LocalTimeDto.builder()
                .date(date)
                .failure(false)
                .build();
    }

    public static WeatherReportDto mapCurrentWeatherToWeatherReportDto(CurrentWeatherDto currentWeather) {
        LocalDate date = parseDate(currentWeather);
        int hour = parseHour(currentWeather);
        double temperature = currentWeather.current().temp_c();
        return WeatherReportDto.builder()
                .date(date)
                .hour(hour)
                .temperature(temperature)
                .failure(false)
                .build();
    }

    private static int parseHour(CurrentWeatherDto currentWeather) {
        String localtime = currentWeather.location().localtime();
        LocalDateTime dateTime = LocalDateTime.parse(localtime, dateAndHourFormatter);
        return dateTime.getHour();
    }

    private static LocalDate parseDate(CurrentWeatherDto currentWeather) {
        String localtime = currentWeather.location().localtime();
        LocalDateTime dateTime = LocalDateTime.parse(localtime, dateAndHourFormatter);
        return dateTime.toLocalDate();
    }
}
