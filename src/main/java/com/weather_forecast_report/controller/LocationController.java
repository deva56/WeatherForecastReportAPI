package com.weather_forecast_report.controller;

import com.weather_forecast_report.models.dto.entity_models.LocationDto;
import com.weather_forecast_report.models.dto.response.ForecastMainDto;
import com.weather_forecast_report.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/location/forecast")
    public ResponseEntity<ForecastMainDto> getLocationsForecasts() {
        return locationService.getLocationsForecasts();
    }

    @PostMapping(value = "/location")
    public ResponseEntity<String> addLocation(@RequestBody LocationDto locationDto) {
        return locationService.addLocation(locationDto);
    }

    @DeleteMapping(value = "/location/{city}")
    public ResponseEntity<String> deleteLocation(@PathVariable String city) {
        return locationService.deleteLocation(city);
    }
}
