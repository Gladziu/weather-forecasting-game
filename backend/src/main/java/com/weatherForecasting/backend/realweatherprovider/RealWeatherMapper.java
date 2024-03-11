package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.CurrentWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.HistoricalWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Current;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Forecastday;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Hour;
import com.weatherForecasting.backend.realweatherprovider.dto.apistructure.Location;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class RealWeatherMapper {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static RealWeatherDto mapCurrentWeatherToRealWeather(CurrentWeatherDto currentWeather) {
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

    public static RealWeatherDto mapHistoricalWeatherToRealWeather(HistoricalWeatherDto historicalWeather, int hourValue) {
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

    public static LocalTimeDto createRealLocalTime(CurrentWeatherDto currentWeather) {
        LocalDate date = parseDate(currentWeather);
        return LocalTimeDto.builder()
                .date(date)
                .failure(false)
                .build();
    }

    private static LocalDate parseDate(CurrentWeatherDto currentWeather) {
            String date = currentWeather.location().localtime().substring(0, 10);
        return LocalDate.parse(date, dateFormatter);
    }
}
