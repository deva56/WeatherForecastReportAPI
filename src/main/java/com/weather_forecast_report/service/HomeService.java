package com.weather_forecast_report.service;

import com.weather_forecast_report.models.dto.response.ForecastMainDto;
import com.weather_forecast_report.models.dto.weather_api.ForecastDto;
import com.weather_forecast_report.models.dto.weather_api.WeatherDto;
import com.weather_forecast_report.models.entity.Location;
import com.weather_forecast_report.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.weather_forecast_report.other.constants.WeatherApiConstants.api_key;
import static com.weather_forecast_report.other.constants.WeatherApiConstants.api_units;

@Service
public class HomeService {

    private final LocationRepository locationRepository;
    private final WebClient apiClient;

    @Autowired
    public HomeService(LocationRepository locationRepository, WebClient apiClient) {
        this.locationRepository = locationRepository;
        this.apiClient = apiClient;
    }

    public ResponseEntity<ForecastMainDto> getLocationsForecast() {
        List<Location> locations = (List<Location>) locationRepository.findAll();
        List<ForecastDto> forecastDtoList = new ArrayList<>();

        try {
            for (Location location : locations) {
                WeatherDto weatherDto = apiClient.get().uri(uriBuilder -> uriBuilder.queryParam("q", location.getCity())
                                .queryParam("units", api_units).queryParam("APPID", api_key).build())
                        .retrieve()
                        .bodyToMono(WeatherDto.class)
                        .block();
                assert weatherDto != null;
                ForecastDto forecastDto = new ForecastDto(location.getCountry(), location.getCity(),
                        weatherDto.getWeather().get(0).getMain(), weatherDto.getWeather().get(0).getDescription(),
                        weatherDto.getMain().getTemp(), weatherDto.getMain().getFeels_like(), weatherDto.getMain().getTemp_min(),
                        weatherDto.getMain().getTemp_max(), weatherDto.getMain().getPressure(), weatherDto.getMain().getHumidity());
                forecastDtoList.add(forecastDto);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ForecastMainDto("Error while getting location temperatures."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ForecastMainDto(forecastDtoList), HttpStatus.OK);
    }

    public ResponseEntity<ForecastMainDto> getLocationsForecastByCountry(String country) {
        List<Location> locations = locationRepository.findByCountry(country);
        List<ForecastDto> forecastDtoList = new ArrayList<>();

        try {
            for (Location location : locations) {
                WeatherDto weatherDto = apiClient.get().uri(uriBuilder -> uriBuilder.queryParam("q", location.getCity())
                                .queryParam("units", api_units).queryParam("APPID", api_key).build())
                        .retrieve()
                        .bodyToMono(WeatherDto.class)
                        .block();
                assert weatherDto != null;
                ForecastDto forecastDto = new ForecastDto(location.getCountry(), location.getCity(),
                        weatherDto.getWeather().get(0).getMain(), weatherDto.getWeather().get(0).getDescription(),
                        weatherDto.getMain().getTemp(), weatherDto.getMain().getFeels_like(), weatherDto.getMain().getTemp_min(),
                        weatherDto.getMain().getTemp_max(), weatherDto.getMain().getPressure(), weatherDto.getMain().getHumidity());
                forecastDtoList.add(forecastDto);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ForecastMainDto("Error while getting location temperatures."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ForecastMainDto(forecastDtoList), HttpStatus.OK);
    }
}
