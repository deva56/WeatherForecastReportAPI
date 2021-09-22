package com.weather_forecast_report.models.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;
    private String password;
}
