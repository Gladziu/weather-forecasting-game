package com.weatherForecasting.backend.user.controller;

import com.weatherForecasting.backend.user.dto.ExpectedWeatherDTO;
import com.weatherForecasting.backend.user.model.ExpectedWeather;
import com.weatherForecasting.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/forecast")
    public ResponseEntity<String> setUserForecast(@RequestBody ExpectedWeatherDTO expectedWeatherDTO) {
        userService.setUserForecast(expectedWeatherDTO);
        return ResponseEntity.ok("User forecast successfully set.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserForecast(@RequestParam Long id) {
        boolean isDeleted = userService.deleteUserForecast(id);
        if(isDeleted) {
            return ResponseEntity.ok("Deleted weather forecast with id=" + id);
        } else {
            return new ResponseEntity<>("Delete error. Can not find weather forecast with id=" + id,
                    HttpStatus.NOT_FOUND);
        }
    }
}
