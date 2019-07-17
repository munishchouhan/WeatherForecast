package com.weatherforecast.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/*
 * This is a POJO class to get and parse JSON response from openweathermap.org
 */
public class OpenWeatherForecastData implements Serializable {

	private String name;

	private List<OpenWeatherForecastDataEntry> entries = new ArrayList<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("entries")
	public List<OpenWeatherForecastDataEntry> getEntries() {
		return this.entries;
	}

	@JsonSetter("list")
	public void setEntries(List<OpenWeatherForecastDataEntry> entries) {
		this.entries = entries;
	}

	@JsonProperty("city")
	public void setCity(Map<String, Object> city) {
		setName(city.get("name").toString());
	}

}
