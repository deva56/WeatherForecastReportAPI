package com.weather_forecast_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static com.weather_forecast_report.other.constants.WeatherApiConstants.api_base_url;

@SpringBootApplication
public class WeatherForecastReportApplication {

	@Bean
	public WebClient apiClient() {
		return WebClient.create(api_base_url);
	}

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastReportApplication.class, args);
	}

}
