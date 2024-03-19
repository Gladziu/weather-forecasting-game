package com.weatherForecasting.backend.realweatherprovider.dto.apistructure;


public record Location(String name, String region, String country, double lat, double lon, String tz_id, long localtime_epoch, String localtime) {
}

