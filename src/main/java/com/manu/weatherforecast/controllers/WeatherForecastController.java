package com.manu.weatherforecast.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manu.weatherforecast.services.WeatherForecastService;
import com.manu.weatherforecast.models.WeatherMatrix;
import com.manu.weatherforecast.exceptions.NoDataFoundException;


@RestController
public class WeatherForecastController {
	
	@Autowired
	WeatherForecastService weatherForecastService;

	@RequestMapping(method = RequestMethod.GET,value= "/data/{city}")
	public List<WeatherMatrix> getData(@PathVariable String city){
		weatherForecastService.validateCIty(city);
		List<WeatherMatrix> WeatherResponses = weatherForecastService.getWeatherForecastResponse(city);
		
		if(WeatherResponses==null)
			throw new NoDataFoundException();
		
		return WeatherResponses;
	}
	
}
