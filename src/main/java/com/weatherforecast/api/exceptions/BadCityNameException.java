package com.weatherforecast.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * exception class for the input, which is  not a valid a valid city name
 */
@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE, reason="This format of city name is not accpetable")
public class BadCityNameException extends RuntimeException  {

}
