package com.weather_forecast_report.models.dto.weather_api;

import lombok.Data;

@Data
public class MainInformationDto {

    private String temp;
    private String feels_like;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
}
