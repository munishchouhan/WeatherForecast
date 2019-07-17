package com.finleap.weatherforecast.api.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import com.finleap.weatherforecast.api.FinLeapWeatherMatrix;
import com.finleap.weatherforecast.api.OpenWeatherForecastDataEntry;
import com.finleap.weatherforecast.api.exceptions.BadCityNameException;
import com.finleap.weatherforecast.api.exceptions.OpenWeatherMapException;
import com.finleap.weatherforecast.api.OpenWeatherForecastData;

/*
 * This is a service, which gets the data from openweathermap.org 
 * and contains the logic to support WeatherForeCastController
 */
@Service
public class WeatherForecastService {

	private final RestTemplate restTemplate;
	@Value("${openweathermap.api.key}")
	private String apiKey;
	@Value("${openweathermap.forecast.url}")
	private String url_openweathermap;
	@Value("${openweathermap.max.days}")
	private Integer maxdays;
	@Value("${finleap.day.starttime}")
	private Integer daystarttime;
	@Value("${finleap.day.endtime}")
	private Integer dayendtime;
	@Value("${finleap.night.starttime}")
	private Integer nightstarttime;
	@Value("${finleap.night.endtime}")
	private Integer nightendtime;

	public WeatherForecastService(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	// To get the data from openweathermap.org
	@Cacheable("openwetaherforecastdata")
	public OpenWeatherForecastData getOpenWeatherMapForecastData(String city) {
		URI url_openweathermap_expanded = new UriTemplate(url_openweathermap).expand(city, this.apiKey);
		return invoke(url_openweathermap_expanded, OpenWeatherForecastData.class);
	}

	private <T> T invoke(URI url, Class<T> responseType) {
		RequestEntity<?> requestEntity = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<T> exchange = null;
		try {
			exchange = this.restTemplate.exchange(requestEntity, responseType);
		} catch (Exception ex) {
			throw new OpenWeatherMapException(ex.getMessage());
		}
		return exchange.getBody();
	}

	// get the matrices for maxdays
	public List<FinLeapWeatherMatrix> getFinleapWeatherForecastResponse(String city) {
		OpenWeatherForecastData openWeatherForecastData = getOpenWeatherMapForecastData(city);
		List<OpenWeatherForecastDataEntry> openWeatherForecastDataEntries = openWeatherForecastData.getEntries();
		List<FinLeapWeatherMatrix> FinLeapWeatherMatrixs = new ArrayList<>();
		for (int i = 1; i <= maxdays; i++) {

			LocalDate localDate = LocalDate.now().plusDays(i);

			FinLeapWeatherMatrixs.add(generateFinLeapWeatherMatrics(
					openWeatherForecastDataEntries.stream().filter(t -> t.getDate().equals(localDate)).iterator(),
					localDate));

		}
		return FinLeapWeatherMatrixs;
	}

	// to calculate matrices for response
	private FinLeapWeatherMatrix generateFinLeapWeatherMatrics(
			Iterator<OpenWeatherForecastDataEntry> openWeatherForecastDataEntriesIterator, LocalDate localDate) {

		List<Double> daytemps = new ArrayList<>();
		List<Double> nighttemps = new ArrayList<>();
		List<Double> pressures = new ArrayList<>();

		while (openWeatherForecastDataEntriesIterator.hasNext()) {

			OpenWeatherForecastDataEntry openWeatherForecastDataEntry = openWeatherForecastDataEntriesIterator.next();

			int hour = openWeatherForecastDataEntry.getTime().getHour();

			if (hour >= daystarttime && hour < dayendtime) {
				daytemps.add(openWeatherForecastDataEntry.getTemperature());
			} else if (hour >= nightstarttime || hour < nightendtime) {
				nighttemps.add(openWeatherForecastDataEntry.getTemperature());
			}

			pressures.add(openWeatherForecastDataEntry.getPressure());
		}

		return new FinLeapWeatherMatrix(localDate, getAverage(daytemps), getAverage(nighttemps), getAverage(pressures));
	}

	// to calculate the mean or average of the matrices
	public double getAverage(List<Double> values) {
		double sum = 0;
		if (!values.isEmpty()) {
			for (double value : values) {
				sum += value;
			}
			return sum / values.size();
		}
		return 0;
	}

	// to validate the city name
	public void validateCIty(String city) {
		if (!city.chars().allMatch(Character::isAlphabetic))
			throw new BadCityNameException();
	}
}
