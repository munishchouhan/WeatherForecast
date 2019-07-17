package com.weatherforecast.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * exception class when there is not data found for a given city
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="NO Data Found for this city")
public class NoDataFoundException extends RuntimeException{

}
