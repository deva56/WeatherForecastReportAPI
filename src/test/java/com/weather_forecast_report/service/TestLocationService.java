package com.weather_forecast_report.service;

import com.weather_forecast_report.models.dto.entity_models.LocationDto;
import com.weather_forecast_report.models.dto.response.ForecastMainDto;
import com.weather_forecast_report.models.dto.weather_api.ForecastDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestLocationService {

    @Mock
    LocationService locationService;

    @Test
    public void ifLocationIsAddedInDatabase_returnOk() {

        when(locationService.addLocation(any())).thenReturn(new ResponseEntity<>("Ok", HttpStatus.OK));
        ResponseEntity<String> testEntity = locationService.addLocation(new LocationDto("Italy", "Rome"));
        assertThat(testEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(locationService, times(1)).addLocation(any());
    }

    @Test
    public void ifLocationCityExistsAndLocationBelongsToUserDeleteLocation_returnOk() {

        when(locationService.deleteLocation(any())).thenReturn(new ResponseEntity<>("Ok", HttpStatus.OK));
        ResponseEntity<String> testEntity = locationService.deleteLocation(any());
        assertThat(testEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(locationService, times(1)).deleteLocation(any());
    }

    @Test
    public void ifLocationDoesNotExist_returnNotFound() {

        when(locationService.deleteLocation(any())).thenReturn(new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND));
        ResponseEntity<String> testEntity = locationService.deleteLocation(any());
        assertThat(testEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(locationService, times(1)).deleteLocation(any());
    }

    @Test
    public void ifLocationExistsAndLocationDoesNotBelongToUser_returnNotFound() {

        when(locationService.deleteLocation(any())).thenReturn(new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND));
        ResponseEntity<String> testEntity = locationService.deleteLocation(any());
        assertThat(testEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(locationService, times(1)).deleteLocation(any());
    }

    @Test
    public void ifGetLocationsForecastValid_returnForecast() {

        List<ForecastDto> testList = new ArrayList<>();
        when(locationService.getLocationsForecasts()).thenReturn(new ResponseEntity<>(new ForecastMainDto(testList), HttpStatus.OK));
        ResponseEntity<ForecastMainDto> testEntity = locationService.getLocationsForecasts();
        assertThat(Objects.requireNonNull(testEntity.getBody()).getForecasts().size()).isNotEqualTo(null);
        assertThat(testEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(locationService, times(1)).getLocationsForecasts();
    }

}
