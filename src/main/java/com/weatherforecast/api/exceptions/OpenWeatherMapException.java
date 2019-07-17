package com.weatherforecast.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * exception class for any exception thrown by openweathermap.org
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="There is an error while accesing openweathermap.org")
public class OpenWeatherMapException extends RuntimeException{
	public OpenWeatherMapException(String msg){
		super(msg);
	}

}
