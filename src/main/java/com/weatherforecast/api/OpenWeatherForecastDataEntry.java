package com.weatherforecast.api;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/*
 * This is a POJO class to get entries from openweathermap.org
 */
public class OpenWeatherForecastDataEntry implements Serializable {

	private Instant timestamp;
	private double temperature;
	private double pressure;
	private LocalDate date;
	private LocalTime time;

	@JsonProperty("timestamp")
	public Instant getTimestamp() {
		return this.timestamp;
	}

	@JsonSetter("dt")
	public void setTimestamp(long unixTime) {
		this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC);
		setDate(localDateTime.toLocalDate());
		setTime(localDateTime.toLocalTime());
	}

	/**
	 * temperature in Fahrenheit(F).
	 */
	public double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * temperature in hectopascal(hPa).
	 */
	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	@JsonProperty("main")
	public void setMain(Map<String, Object> main) {
		setTemperature(Double.parseDouble(main.get("temp").toString()));
		setPressure(Double.parseDouble(main.get("pressure").toString()));
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

}
