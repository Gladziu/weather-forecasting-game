package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

class WeatherPredictionMapper {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static WeatherPrediction createWeatherPredictionFromDTO(WeatherPredictionDTO weatherPredictionDTO) {
        LocalDate convertedDate = convertDate(weatherPredictionDTO.forecastDate());
        String formattedHour = formatHour(weatherPredictionDTO.forecastHour());
        return WeatherPrediction.builder()
                .id(weatherPredictionDTO.id())
                .username(weatherPredictionDTO.username())
                .location(weatherPredictionDTO.location())
                .temperature(weatherPredictionDTO.temperature())
                .forecastDate(convertedDate)
                .forecastHour(formattedHour)
                .timeStamp(LocalDate.now())
                .build();
    }

    public static List<WeatherPredictionDTO> mapToDTOs(List<WeatherPrediction> predictions) {
        return predictions.stream()
                .map(prediction -> new WeatherPredictionDTO(prediction.getId(),
                        prediction.getUsername(),
                        prediction.getLocation(),
                        prediction.getTemperature(),
                        prediction.getForecastDate().format(dateFormatter),
                        prediction.getForecastHour(),
                        prediction.getTimeStamp()))
                .collect(Collectors.toList());
    }


    private static String formatHour(String forecastHour) {
        String hourPrefix = "0";
        String hourSuffix = ":00";
        if (forecastHour.length() == 1) {
            return hourPrefix + forecastHour + hourSuffix;
        }
        return forecastHour + hourSuffix;
    }

    private static LocalDate convertDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }
}
