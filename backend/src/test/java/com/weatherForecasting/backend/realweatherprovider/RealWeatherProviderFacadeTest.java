package com.weatherForecasting.backend.realweatherprovider;

import com.weatherForecasting.backend.realweatherprovider.dto.CurrentWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.HistoricalWeatherDto;
import com.weatherForecasting.backend.realweatherprovider.dto.LocalTimeDto;
import com.weatherForecasting.backend.realweatherprovider.dto.RealWeatherDto;
import com.weatherForecasting.backend.weatherpredictioncrd.dto.WeatherPredictionDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RealWeatherProviderFacadeTest {
    RealWeatherApiDataReceiver realWeatherApiDataReceiver = Mockito.mock(RealWeatherApiDataReceiver.class);
    RealWeatherProviderFacade realWeatherProviderFacade = new RealWeatherProviderConfiguration().realWeatherProviderFacadeForTest(realWeatherApiDataReceiver);

    @Test
    public void get_current_weather_should_return_location_not_found_when_given_incorrect_location() {
        //given
        CurrentWeatherDto currentRealWeather = ApiResponseCreatorForTest.currentWeatherLocationError();
        when(realWeatherApiDataReceiver.currentWeatherApiResponse(any())).thenReturn(currentRealWeather);
        //when
        RealWeatherDto result = realWeatherProviderFacade.getCurrentWeather("Invalid loc");
        //then
        assertThat(result.error().message()).isEqualTo("location not found");
    }

    @Test
    public void get_current_weather_should_return_real_weather_current_information_when_given_location_exists() {
        //given
        CurrentWeatherDto currentRealWeather = ApiResponseCreatorForTest.currentWeather();
        when(realWeatherApiDataReceiver.currentWeatherApiResponse(any())).thenReturn(currentRealWeather);
        //when
        RealWeatherDto result = realWeatherProviderFacade.getCurrentWeather("Warszawa");
        //then
        assertThat(result.city()).isEqualTo("Warszawa");
    }

    @Test
    public void get_historical_weather_should_return_date_error_when_given_future_date() {
        //given
        LocalDate localDateInFuture = LocalDate.now().plusDays(5);
        String date = localDateInFuture.toString();
        //when
        RealWeatherDto result = realWeatherProviderFacade.getHistoricalWeather("Warszawa", date, 2);
        //then
        assertThat(result.error().message()).isEqualTo("date out of range");
    }

    @Test
    public void get_historical_weather_should_return_date_error_when_given_historical_date_out_of_range() {
        //given
        LocalDate localDateInPast = LocalDate.now().minusDays(15);
        String date = localDateInPast.toString();
        //when
        RealWeatherDto result = realWeatherProviderFacade.getHistoricalWeather("Warszawa", date, 2);
        //then
        assertThat(result.error().message()).isEqualTo("date out of range");
    }

    @Test
    public void get_historical_weather_should_return_hour_error_when_given_impossibly_high_hour() {
        //given
        LocalDate historicalDate = LocalDate.now().minusDays(3);
        String date = historicalDate.toString();
        //when
        RealWeatherDto result = realWeatherProviderFacade.getHistoricalWeather("Warszawa", date, 28);
        //then
        assertThat(result.error().message()).isEqualTo("hour out of range");
    }

    @Test
    public void get_historical_weather_should_return_location_not_found_when_given_incorrect_location() {
        //given
        LocalDate historicalDate = LocalDate.now().minusDays(3);
        String date = historicalDate.toString();
        HistoricalWeatherDto historicalRealWeather = ApiResponseCreatorForTest.historicalWeatherLocationError();
        when(realWeatherApiDataReceiver.historicalWeatherResponse(any())).thenReturn(historicalRealWeather);
        //when
        RealWeatherDto result = realWeatherProviderFacade.getHistoricalWeather("Warszawa", date, 2);
        //then
        assertThat(result.error().message()).isEqualTo("location not found");
    }

    @Test
    public void get_historical_weather_should_return_real_weather_historical_information_when_given_location_exists() {
        //given
        LocalDate historicalDate = LocalDate.now().minusDays(3);
        String date = historicalDate.toString();
        HistoricalWeatherDto historicalRealWeather = ApiResponseCreatorForTest.historicalWeather();
        when(realWeatherApiDataReceiver.historicalWeatherResponse(any())).thenReturn(historicalRealWeather);
        //when
        RealWeatherDto result = realWeatherProviderFacade.getHistoricalWeather("Warszawa", date, 2);
        //then
        assertThat(result.temperature()).isEqualTo(5);
    }

    @Test
    public void should_return_local_time_failure_when_given_invalid_location() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Invalid loc", 10, LocalDate.now().plusDays(10), 12, LocalDate.now());
        when(realWeatherApiDataReceiver.currentWeatherApiResponse(any())).thenReturn(CurrentWeatherDto.builder()
                .failure(true)
                .build());
        //when
        LocalTimeDto result = realWeatherProviderFacade.locationLocalTime(weatherPrediction);
        //then
        assertThat(result.failure()).isTrue();
    }

    @Test
    public void should_return_local_time_when_given_location_exists() {
        //given
        WeatherPredictionDto weatherPrediction = new WeatherPredictionDto(UUID.randomUUID(), "joe", "Warszawa", 10, LocalDate.now().plusDays(10), 12, LocalDate.now());
        CurrentWeatherDto currentWeather = ApiResponseCreatorForTest.currentWeather();
        when(realWeatherApiDataReceiver.currentWeatherApiResponse(any())).thenReturn(currentWeather);
        //when
        LocalTimeDto result = realWeatherProviderFacade.locationLocalTime(weatherPrediction);
        //then
        assertThat(result.failure()).isFalse();
    }
}