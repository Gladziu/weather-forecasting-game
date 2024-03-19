package com.weatherForecasting.backend.weatherpredictioncrd;

import java.time.LocalDate;

class FutureDateGeneratorForTest {
    public static LocalDate dateInFiveDays() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(5);
    }
}
