package com.weatherforecast.api;

import java.time.LocalDate;

/*
 * This is the response POJO class
 * which user will get from /data/{city} API
 */
public class ManuWeatherMatrix {
	private LocalDate date;
	private double avg_day_temp;
	private double avg_night_temp;
	private double avg_pressure;

	public ManuWeatherMatrix() {
	}

	public ManuWeatherMatrix(LocalDate date, double avg_day_temp, double avg_night_temp, double avg_pressure) {
		super();
		this.date = date;
		this.avg_day_temp = avg_day_temp;
		this.avg_night_temp = avg_night_temp;
		this.avg_pressure = avg_pressure;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAvg_day_temp() {
		return avg_day_temp;
	}

	public void setAvg_day_temp(double avg_day_temp) {
		this.avg_day_temp = avg_day_temp;
	}

	public double getAvg_night_temp() {
		return avg_night_temp;
	}

	public void setAvg_night_temp(double avg_night_temp) {
		this.avg_night_temp = avg_night_temp;
	}

	public double getAvg_pressure() {
		return avg_pressure;
	}

	public void setAvg_pressure(double avg_pressure) {
		this.avg_pressure = avg_pressure;
	}
}
