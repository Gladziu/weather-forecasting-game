package com.weatherForecasting.backend.weatherpredictioncrud;

import com.weatherForecasting.backend.weatherpredictioncrud.dto.WeatherPredictionDTO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherPredictionCrudFacadeTest {
    WeatherPredictionRepository repository = new WeatherPredictionRepositoryTestImpl();
    WeatherPredictionCrudFacade weatherPredictionCrudFacade = new WeatherPredictionConfiguration().WeatherPredictionCrudFacadeForTest(repository);
    String date = FutureDateGeneratorForTest.dateInFiveDays();

    @Test
    public void add_prediction_should_return_failure_when_given_hour_is_in_wrong_format() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "123");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("incorrect hour format HH or H");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_date_is_in_past() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, "2023-12-12", "12");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("date must be in the future or incorrect format yyyy-MM-dd");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_date_is_in_wrong_format() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, "10.01.2024", "12");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("date must be in the future or incorrect format yyyy-MM-dd");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_temperature_is_impossibly_high() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 999, date, "12");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("temperature out of range");
    }

    @Test
    public void add_prediction_should_return_failure_when_given_temperature_is_impossibly_low() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", -999, date, "12");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("temperature out of range");
    }

    @Test
    public void add_prediction_should_return_success_when_given_correct_weather_prediction() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "15");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("weather prediction successfully added");
    }

    @Test
    public void add_prediction_should_return_success_when_given_prediction_with_one_digit_hour() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "9");

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //then
        assertThat(result.message()).isEqualTo("weather prediction successfully added");
    }

    @Test
    public void should_return_all_weather_predictions_when_given_user_name() {
        //given
        String userName = "joe";

        WeatherPredictionDTO weatherPredictionDTO_joe1 = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "15");
        WeatherPredictionDTO weatherPredictionDTO_joe2 = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "15");
        WeatherPredictionDTO weatherPredictionDTO_tom = new WeatherPredictionDTO("tom", "Paris", 9.9, date, "15");
        weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO_joe1);
        weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO_joe2);
        weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO_tom);

        //when
        List<WeatherPredictionDTO> results = weatherPredictionCrudFacade.showPrediction(userName);

        //then
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void should_return_no_weather_predictions_when_user_has_not_played() {
        //given
        //when
        List<WeatherPredictionDTO> results = weatherPredictionCrudFacade.showPrediction("joe");

        //then
        assertThat(results.size()).isEqualTo(0);
    }

    @Test
    void delete_weather_prediction_should_return_success_when_given_id() {
        //given
        WeatherPredictionDTO weatherPredictionDTO = new WeatherPredictionDTO("joe", "Warsaw", 10.4, date, "15");
        weatherPredictionCrudFacade.addPrediction(weatherPredictionDTO);

        //when
        CrudOperationResult result = weatherPredictionCrudFacade.deletePrediction(weatherPredictionDTO.id());

        //then
        assertThat(result.message()).isEqualTo("weather prediction deleted");
    }

    @Test
    void delete_weather_prediction_should_return_error_when_given_no_existing_id() {
        //given
        //when
        CrudOperationResult result = weatherPredictionCrudFacade.deletePrediction(UUID.randomUUID());

        //then
        assertThat(result.message()).isEqualTo("can not find weather prediction, delete error");
    }
}