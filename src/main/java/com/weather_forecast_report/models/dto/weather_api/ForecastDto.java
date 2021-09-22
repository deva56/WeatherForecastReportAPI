package com.weather_forecast_report.models.dto.weather_api;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ForecastDto {

    @NonNull
    private String country;
    @NonNull
    private String city;

    @NonNull
    private String main_weather_status;
    @NonNull
    private String description;

    @NonNull
    private String temp;
    @NonNull
    private String feels_like;
    @NonNull
    private String temp_min;
    @NonNull
    private String temp_max;
    @NonNull
    private String pressure;
    @NonNull
    private String humidity;
}
