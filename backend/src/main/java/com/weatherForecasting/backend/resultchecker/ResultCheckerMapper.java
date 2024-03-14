package com.weatherForecasting.backend.resultchecker;

import com.weatherForecasting.backend.resultchecker.dto.HistoryResultDto;
import com.weatherForecasting.backend.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ResultCheckerMapper {

    public static List<ResultDto> mapToResultsDto(List<Result> results) {
        return results.stream()
                .map(result -> new ResultDto(
                        result.getUsername(),
                        result.getScore()))
                .collect(Collectors.toList());
    }

    public static int createUserScore(Optional<Result> userResult) {
        return userResult.map(Result::getScore).orElse(0);
    }

    public static List<HistoryResultDto> mapToHistoryResultsDto(List<HistoryResult> historyResults) {
        return historyResults.stream()
                .map(historyResult -> new HistoryResultDto(
                        historyResult.getUsername(),
                        historyResult.getLocation(),
                        historyResult.getTemperature(),
                        historyResult.getRealTemperature(),
                        historyResult.getForecastDate(),
                        historyResult.getForecastHour(),
                        historyResult.getTimeStamp(),
                        historyResult.getScore()
                ))
                .collect(Collectors.toList());
    }
}
