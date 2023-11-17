package com.weatherForecasting.backend.results.service;

import com.weatherForecasting.backend.results.dto.HistoryDTO;
import com.weatherForecasting.backend.results.model.History;
import com.weatherForecasting.backend.results.model.Score;
import com.weatherForecasting.backend.forecast.model.WeatherPrediction;
import com.weatherForecasting.backend.results.repository.HistoryRepository;
import com.weatherForecasting.backend.results.repository.ScoreRepository;
import com.weatherForecasting.backend.forecast.repository.WeatherPredictionRepository;
import com.weatherForecasting.backend.weatherAPI.dto.WeatherDTO;
import com.weatherForecasting.backend.weatherAPI.service.WeatherApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@EnableScheduling
@Slf4j
public class ResultsServiceImpl implements ResultsService {
    @Autowired
    private WeatherApiService weatherApiService;
    @Autowired
    private WeatherPredictionRepository weatherPredictionRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<Score> getScoreBoard() {
        return scoreRepository.findAll();
    }

    @Override
    public int getUserScore(String username) {
        Optional<Score> score = scoreRepository.findByUsername(username);
        return score.map(Score::getScore).orElse(0);
    }

    @Override
    public List<HistoryDTO> getHistoricalPrediction(String username) {
        List<History> histories = historyRepository.findAllByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return histories.stream()
                .map(history -> new HistoryDTO(
                        history.getId(),
                        history.getUsername(),
                        history.getLocation(),
                        history.getTemperature(),
                        history.getRealTemperature(),
                        history.getForecastDate().format(formatter),
                        history.getForecastHour(),
                        history.getTimeStamp().format(formatter),
                        history.getPoints()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 5 * * * *")
    //@Scheduled(cron = "*/15 * * * * *")
    public void weatherPredictionCheck() {
        log.info("Scheduler has just run the task");
        LocalDate currentTime = LocalDate.now(ZoneId.of("GMT+14"));
        // all possible dates of weather forecasts that have already taken place
        List<WeatherPrediction> forecasts = weatherPredictionRepository.findByForecastDateLessThanEqual(currentTime);
        for (WeatherPrediction forecast : forecasts) {
            WeatherDTO currentWeather = weatherApiService.getCurrentWeather(forecast.getLocation());
            System.out.println("Forecast city: " + forecast.getLocation());
            System.out.println("Current city: " + currentWeather.getCity());
            if(!areDatesEqual(forecast, currentWeather)) {
                continue;
            }
            int scored = scoreAssign(forecast, currentWeather);
            moveToHistory(forecast, currentWeather, scored);
        }
    log.info("Scheduler task has been completed successfully");
    }

    private void moveToHistory(WeatherPrediction forecast, WeatherDTO actual , int scored) {
        History history = new History(forecast, actual, scored);
        historyRepository.save(history);
        weatherPredictionRepository.delete(forecast);
        log.info("Forecast moved to history and added points.");
    }

    @Transactional
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
        if(daysBetween == 0 || tempDiff > 10) {
            return 0;
        }
        if(tempDiff == 0) {
            return daysBetween * 3;
        }
        if(tempDiff < 0.5) {
            return daysBetween * 2;
        }
        if(tempDiff < 2) {
            return daysBetween;
        }

        return (int) (daysBetween / tempDiff);

    }

    private boolean areDatesEqual(WeatherPrediction userForecast, WeatherDTO actual) {
        String date = userForecast.getForecastDate() + " " + userForecast.getForecastHour();
        String currentDate = actual.getTime();
        int withoutMinutes = currentDate.length()-3;

        return date.substring(0, withoutMinutes).equals(currentDate.substring(0, withoutMinutes));
    }
}
