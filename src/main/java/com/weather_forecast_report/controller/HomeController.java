package com.weather_forecast_report.controller;

import com.weather_forecast_report.models.dto.response.ForecastMainDto;
import com.weather_forecast_report.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(value = "/home")
    public ResponseEntity<ForecastMainDto> home(@RequestParam(value = "countryName", required = false) String countryName) {
        if (countryName == null) {
            return homeService.getLocationsForecast();
        } else {
            return homeService.getLocationsForecastByCountry(countryName);
        }
    }
}
