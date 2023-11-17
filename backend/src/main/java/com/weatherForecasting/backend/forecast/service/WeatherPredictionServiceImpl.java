package com.weatherForecasting.backend.forecast.service;

import com.weatherForecasting.backend.forecast.dto.WeatherPredictionDTO;
import com.weatherForecasting.backend.forecast.model.WeatherPrediction;
import com.weatherForecasting.backend.forecast.repository.WeatherPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherPredictionServiceImpl implements WeatherPredictionService {
    @Autowired
    private WeatherPredictionRepository weatherPredictionRepository;

    @Override
    public void addPrediction(WeatherPredictionDTO weatherPredictionDTO) {
        // TODO: sprawdzenie z api czy lokalizacja jest poprawna
        isPredictionValid(weatherPredictionDTO);
        WeatherPrediction userWeather = new WeatherPrediction();
        userWeather.setUsername(weatherPredictionDTO.getUsername());
        userWeather.setLocation(weatherPredictionDTO.getLocation());
        userWeather.setTemperature(weatherPredictionDTO.getTemperature());
        userWeather.setForecastDate(convertDate(weatherPredictionDTO.getForecastDate()));
        userWeather.setForecastHour(formatHour(weatherPredictionDTO.getForecastHour()));
        userWeather.setTimeStamp(LocalDate.now());
        weatherPredictionRepository.save(userWeather);
    }

    private void isPredictionValid(WeatherPredictionDTO weatherPredictionDTO) {
        //TODO: dokoncz sprawdzanie czy lokalizajaca jest poprawna
    }

    @Override
    public boolean deletePrediction(Long id) {
        if(weatherPredictionRepository.existsById(id)) {
            weatherPredictionRepository.deleteById(id);
            return true;
        } else {
            //Zmien moze na throw new zamiast zwracania boolean tak jak w Amigoscode: film o spring boot
            return false;
        }
    }

    @Override
    public List<WeatherPredictionDTO> showPrediction(String userName) {
        List<WeatherPrediction> predictions = weatherPredictionRepository.findAllByUsername(userName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return predictions.stream()
                .map(prediction-> new WeatherPredictionDTO(
                        prediction.getId(),
                        prediction.getUsername(),
                        prediction.getLocation(),
                        prediction.getTemperature(),
                        prediction.getForecastDate().format(formatter),
                        prediction.getForecastHour()
                ))
                .collect(Collectors.toList());

    }

    private String formatHour(String forecastHour) {
        if(forecastHour.length() == 1) {
            return "0" + forecastHour + ":00";
        }
        return forecastHour + ":00";
    }

    private LocalDate convertDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
