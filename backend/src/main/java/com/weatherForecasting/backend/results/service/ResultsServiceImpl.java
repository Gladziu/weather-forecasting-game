package com.weatherForecasting.backend.results.service;

import com.weatherForecasting.backend.forecast.repository.WeatherPredictionRepository;
import com.weatherForecasting.backend.results.dto.HistoryDTO;
import com.weatherForecasting.backend.results.model.History;
import com.weatherForecasting.backend.results.model.Score;
import com.weatherForecasting.backend.results.repository.HistoryRepository;
import com.weatherForecasting.backend.results.repository.ScoreRepository;
import com.weatherForecasting.backend.weatherAPI.service.WeatherApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResultsServiceImpl implements ResultsService {

    private final ScoreRepository scoreRepository;
    private final HistoryRepository historyRepository;

    public ResultsServiceImpl(ScoreRepository scoreRepository, HistoryRepository historyRepository) {
        this.scoreRepository = scoreRepository;
        this.historyRepository = historyRepository;
    }

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
}
