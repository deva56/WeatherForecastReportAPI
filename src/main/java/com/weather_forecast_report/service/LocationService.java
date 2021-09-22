package com.weather_forecast_report.service;

import com.weather_forecast_report.models.dto.entity_models.LocationDto;
import com.weather_forecast_report.models.dto.response.ForecastMainDto;
import com.weather_forecast_report.models.dto.weather_api.ForecastDto;
import com.weather_forecast_report.models.dto.weather_api.WeatherDto;
import com.weather_forecast_report.models.entity.Location;
import com.weather_forecast_report.models.entity.User;
import com.weather_forecast_report.repository.LocationRepository;
import com.weather_forecast_report.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.weather_forecast_report.other.constants.WeatherApiConstants.api_key;
import static com.weather_forecast_report.other.constants.WeatherApiConstants.api_units;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final WebClient apiClient;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserRepository userRepository, WebClient apiClient) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.apiClient = apiClient;
    }

    public ResponseEntity<ForecastMainDto> getLocationsForecasts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        List<ForecastDto> forecastDtoList = new ArrayList<>();

        try {
            User user = userRepository.findByUsername(currentUserName);
            List<Location> locations = locationRepository.findByUserOrderByCountryAscCityAsc(user);

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

    public ResponseEntity<String> addLocation(@NotNull LocationDto locationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            User user = userRepository.findByUsername(currentUserName);
            Optional<Location> savedlocation = locationRepository.findByUserAndCity(user, locationDto.getCity());
            if (savedlocation.isEmpty()) {
                Location location = new Location(locationDto.getCountry(), locationDto.getCity(), user);
                locationRepository.save(location);
            } else {
                return new ResponseEntity<>("You have already saved a city with same name.", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving location in database.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("New location successfully saved.", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteLocation(String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            User user = userRepository.findByUsername(currentUserName);
            Optional<Location> location = locationRepository.findByUserAndCity(user, city);
            if (location.isEmpty()) {
                return new ResponseEntity<>("You don't have any locations with given name.", HttpStatus.NOT_FOUND);
            } else {
                locationRepository.deleteById(location.get().getId());
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting location from database. " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Successfully deleted location from database.", HttpStatus.OK);
    }
}
