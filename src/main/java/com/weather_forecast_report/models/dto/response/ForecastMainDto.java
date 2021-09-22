//Dto model used to send response to user after getting necessary data from http://api.openweathermap.org

package com.weather_forecast_report.models.dto.response;

import com.weather_forecast_report.models.dto.weather_api.ForecastDto;
import lombok.Data;

import java.util.List;

@Data
public class ForecastMainDto {

    private List<ForecastDto> forecasts;

    private String error;

    public ForecastMainDto(List<ForecastDto> forecasts){
        this.forecasts = forecasts;
    }

    public ForecastMainDto(String error){
        this.error = error;
    }
}
