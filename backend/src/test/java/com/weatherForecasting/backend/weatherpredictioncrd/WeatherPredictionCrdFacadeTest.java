package com.weatherForecasting.backend.weatherpredictioncrd;

import com.weatherForecasting.backend.realweatherprovider.RealWeatherProviderFacade;
import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WeatherPredictionCrdFacadeTest {
    WeatherPredictionRepository repository = new WeatherPredictionRepositoryTestImpl();
    RealWeatherProviderFacade realWeather = Mockito.mock(RealWeatherProviderFacade.class);
    WeatherPredictionCrdFacade weatherPredictionCrdFacade = new WeatherPredictionConfiguration().weatherPredictionCrdFacadeForTest(repository, realWeather);

    LocalDate date = FutureDateGeneratorForTest.dateInFiveDays();

    @Test
    public void add_prediction_should_return_failure_when_given_hour_is_in_wrong_range() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 123);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("hour out of range");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_date_is_in_past() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 10.4, LocalDate.of(2023, 1, 1), 12);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("date must be in the future");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_location_does_not_exist() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 123);
        when(realWeather.locationLocalTime(any())).thenReturn(LocalTimeDto.builder().failure(true).build());
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("location not found");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_temperature_is_impossibly_high() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 999, date, 12);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("temperature out of range");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_temperature_is_impossibly_low() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", -999, date, 12);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("temperature out of range");
    }

    @Test
    public void add_prediction_should_return_success_when_given_correct_weather_prediction() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 15);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("weather prediction successfully added");
    }

    @Test
    public void should_return_all_weather_predictions_when_given_user_name() {
        //given
        String userName = "joe";

        WeatherPredictionDto weatherPredictionDto_joe1 = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 15);
        WeatherPredictionDto weatherPredictionDto_joe2 = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 15);
        WeatherPredictionDto weatherPredictionDto_tom = new WeatherPredictionDto("tom", "Paris", 9.9, date, 15);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        weatherPredictionCrdFacade.addPrediction(weatherPredictionDto_joe1);
        weatherPredictionCrdFacade.addPrediction(weatherPredictionDto_joe2);
        weatherPredictionCrdFacade.addPrediction(weatherPredictionDto_tom);

        //when
        List<WeatherPredictionDto> results = weatherPredictionCrdFacade.showPredictions(userName);

        //then
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void should_return_no_weather_predictions_when_user_has_not_played() {
        //given
        //when
        List<WeatherPredictionDto> results = weatherPredictionCrdFacade.showPredictions("joe");

        //then
        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    void delete_weather_prediction_should_return_success_when_given_id() {
        //given
        WeatherPredictionDto weatherPredictionDTO = new WeatherPredictionDto("joe", "Warsaw", 10.4, date, 15);
        when(realWeather.locationLocalTime(any())).thenReturn(new LocalTimeDto(LocalDate.now(), false));
        weatherPredictionCrdFacade.addPrediction(weatherPredictionDTO);
        List<WeatherPredictionDto> predictionDTOS = weatherPredictionCrdFacade.showPredictions("joe");
        UUID weatherPredictionId = predictionDTOS.get(0).id();
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.deletePrediction(weatherPredictionId);

        //then
        assertThat(result.message()).isEqualTo("weather prediction deleted");
    }

    @Test
    void delete_weather_prediction_should_return_error_when_given_no_existing_id() {
        //given
        //when
        CrdOperationResult result = weatherPredictionCrdFacade.deletePrediction(UUID.randomUUID());

        //then
        assertThat(result.message()).isEqualTo("can not find weather prediction, delete error");
    }
}