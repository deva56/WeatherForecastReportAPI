package com.weather_forecast_report.models.dto.weather_api;

import lombok.Data;

@Data
public class WeatherInformationDto {
    private String main;
    private String description;
}
