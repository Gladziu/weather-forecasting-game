package com.weatherForecasting.backend.user.service;

import com.weatherForecasting.backend.user.dto.ExpectedWeatherDTO;
import com.weatherForecasting.backend.user.model.ExpectedWeather;
import com.weatherForecasting.backend.user.repository.ExpectedWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ExpectedWeatherRepository expectedWeatherRepository;

    @Override
    public void setUserForecast(ExpectedWeatherDTO expectedWeatherDTO) {
        //Moze dodaj log info zeby wyswietlac w konsolii co sie dzieje
        // TODO: sprawdzenie z api czy lokalizacja jest poprawna
        ExpectedWeather userWeather = new ExpectedWeather();
        userWeather.setUsername(expectedWeatherDTO.getUsername());
        userWeather.setLocation(expectedWeatherDTO.getLocation());
        userWeather.setTemperature(expectedWeatherDTO.getTemperature());
        userWeather.setForecastDate(expectedWeatherDTO.getForecastDate());
        userWeather.setTimeStamp(LocalDate.now());
        expectedWeatherRepository.save(userWeather);
    }

    @Override
    public boolean deleteUserForecast(Long id) {
        if(expectedWeatherRepository.existsById(id)) {
            expectedWeatherRepository.deleteById(id);
            return true;
        } else {
            //Zmien moze na throw new zamiast zwracania boolean tak jak w Amigoscode: film o spring boot
            return false;
        }
    }

}
