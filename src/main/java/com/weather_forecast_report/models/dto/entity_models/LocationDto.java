package com.weather_forecast_report.models.dto.entity_models;

import lombok.Data;
import lombok.NonNull;

@Data
public class LocationDto {

    @NonNull
    private String country;

    @NonNull
    private String city;
}
