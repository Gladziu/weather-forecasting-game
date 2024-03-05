package com.weatherForecasting.backend.weatherpredictioncrud;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class FutureDateGeneratorForTest {
    public static String dateInFiveDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(5);
        return futureDate.format(formatter);
    }
}
