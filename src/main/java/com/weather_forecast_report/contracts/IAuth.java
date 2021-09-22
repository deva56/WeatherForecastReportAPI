package com.weather_forecast_report.contracts;


import com.weather_forecast_report.models.dto.auth.LoginRequestDto;
import com.weather_forecast_report.models.dto.auth.RegisterRequestDto;
import org.springframework.http.ResponseEntity;

public interface IAuth {

    ResponseEntity<String> registration(RegisterRequestDto registerRequestDto);

    ResponseEntity<String> login(LoginRequestDto loginRequestDto);
}
