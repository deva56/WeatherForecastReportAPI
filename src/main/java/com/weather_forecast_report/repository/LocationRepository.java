package com.weather_forecast_report.repository;

import com.weather_forecast_report.models.entity.Location;
import com.weather_forecast_report.models.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    Optional<Location> findByUserAndCity(User user, String city);

    List<Location> findByUserOrderByCountryAscCityAsc(User user);

    List<Location> findByCountry(String country);
}
