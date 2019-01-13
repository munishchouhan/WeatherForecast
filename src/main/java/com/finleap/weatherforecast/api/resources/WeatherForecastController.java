package com.finleap.weatherforecast.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finleap.weatherforecast.api.FinLeapWeatherMatrix;
import com.finleap.weatherforecast.api.exceptions.NoDataFoundException;


@RestController
public class WeatherForecastController {
	
	@Autowired
	WeatherForecastService weatherForcastService;

	@RequestMapping(method = RequestMethod.GET,value= "/data/{city}")
	public List<FinLeapWeatherMatrix> getData(@PathVariable String city){
		weatherForcastService.validateCIty(city);
		List<FinLeapWeatherMatrix> finleapWeatherResponses = weatherForcastService.getFinleapWeatherForecastResponse(city);
		
		if(finleapWeatherResponses==null)
			throw new NoDataFoundException();
		
		return finleapWeatherResponses;
	}
	
}
