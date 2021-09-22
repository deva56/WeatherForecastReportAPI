//Main model for receiving and parsing response from http://api.openweathermap.org

package com.weather_forecast_report.models.dto.weather_api;

import lombok.Data;

import java.util.List;

@Data
public class WeatherDto {

    private List<WeatherInformationDto> weather;

    private MainInformationDto main;
}
