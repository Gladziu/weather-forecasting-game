package com.weatherForecasting.backend.scheduler;

import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPrediction;
import com.weatherForecasting.backend.weatherpredictioncrud.WeatherPredictionRepository;
import com.weatherForecasting.backend.resultchecker.model.History;
import com.weatherForecasting.backend.resultchecker.model.Score;
import com.weatherForecasting.backend.resultchecker.repository.HistoryRepository;
import com.weatherForecasting.backend.resultchecker.repository.ScoreRepository;
import com.weatherForecasting.backend.realweatherinfo.dto.WeatherDTO;
import com.weatherForecasting.backend.realweatherinfo.service.WeatherApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@EnableScheduling
@Slf4j
public class PredictionChecker {
    private final WeatherPredictionRepository weatherPredictionRepository;
    private final ScoreRepository scoreRepository;
    private final HistoryRepository historyRepository;
    private final WeatherApiService weatherApiService;


    public PredictionChecker(WeatherPredictionRepository weatherPredictionRepository, ScoreRepository scoreRepository, HistoryRepository historyRepository, WeatherApiService weatherApiService) {
        this.weatherPredictionRepository = weatherPredictionRepository;
        this.scoreRepository = scoreRepository;
        this.historyRepository = historyRepository;
        this.weatherApiService = weatherApiService;
    }

    private static final int MAX_ALLOWED_TEMP_DIFF = 10;
    private static final double MIN_TEMP_DIFF = 0.5;
    private static final double MODERATE_TEMP_DIFF = 2;

    private static final int BASE_MULTIPLIER = 3;
    private static final int MULTIPLIER_FOR_MODERATE_TEMP_DIFF = 2;


    //@Scheduled(cron = "*/15 * * * * *")
    @Scheduled(cron = "0 5 * * * *")
    public void weatherPredictionCheck() {
        log.info("Scheduler has just run the task");
        LocalDate currentTime = LocalDate.now(ZoneId.of("GMT+14")); // the latest possible date time in the world
        processWeatherPredictions(currentTime);
        log.info("Scheduler task has been completed successfully");
    }


    private void processWeatherPredictions(LocalDate currentTime) {
        List<WeatherPrediction> forecasts = weatherPredictionRepository.findByForecastDateLessThanEqual(currentTime);
        for (WeatherPrediction forecast : forecasts) {
            WeatherDTO currentWeather = weatherApiService.getCurrentWeather(forecast.getLocation());
            if (!areDatesEqualWithoutMinutes(forecast, currentWeather)) {
                continue;
            }
            int pointsScored = scoreAssign(forecast, currentWeather);
            moveToHistory(forecast, currentWeather, pointsScored);
        }
    }

    private void moveToHistory(WeatherPrediction forecast, WeatherDTO actual, int scored) {
        History history = new History(forecast, actual, scored);
        historyRepository.save(history);
        weatherPredictionRepository.delete(forecast);
        log.info("Forecast moved to history and added points.");
    }

    private int scoreAssign(WeatherPrediction forecast, WeatherDTO actual) {
        double tempDiff = Math.abs(forecast.getTemperature() - actual.getTemperature());
        int daysBetween = (int) DAYS.between(forecast.getTimeStamp(), forecast.getForecastDate());
        int score = pointsScored(daysBetween, tempDiff);

        Optional<Score> currentScoreOptional = scoreRepository.findByUsername(forecast.getUsername());
        if (currentScoreOptional.isPresent()) {
            Score currentScore = currentScoreOptional.get();
            int newScore = currentScore.getScore() + score;
            currentScore.setScore(newScore);
            scoreRepository.save(currentScore);
        } else {
            Score newUser = new Score();
            newUser.setUsername(forecast.getUsername());
            newUser.setScore(score);
            scoreRepository.save(newUser);
        }
        log.info(forecast.getUsername() + " scored points.");
        return score;
    }

    private int pointsScored(int daysBetween, double tempDiff) {
        if (daysBetween == 0 || tempDiff > MAX_ALLOWED_TEMP_DIFF) {
            return 0;
        }
        if (tempDiff == 0) {
            return daysBetween * BASE_MULTIPLIER;
        }
        if (tempDiff < MIN_TEMP_DIFF) {
            return daysBetween * MULTIPLIER_FOR_MODERATE_TEMP_DIFF;
        }
        if (tempDiff < MODERATE_TEMP_DIFF) {
            return daysBetween;
        }

        return (int) (daysBetween / tempDiff);
    }

    private boolean areDatesEqualWithoutMinutes(WeatherPrediction userForecast, WeatherDTO actual) {
        String date = userForecast.getForecastDate() + " " + userForecast.getForecastHour();
        String currentDate = actual.getTime();
        int withoutMinutes = currentDate.length() - 3;

        return date.substring(0, withoutMinutes).equals(currentDate.substring(0, withoutMinutes));
    }
}
