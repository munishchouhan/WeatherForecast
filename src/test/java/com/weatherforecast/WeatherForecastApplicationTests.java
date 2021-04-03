package com.weatherforecast;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import com.manu.weatherforecast.models.WeatherMatrix;
import com.manu.weatherforecast.controllers.WeatherForecastController;
import com.manu.weatherforecast.services.WeatherForecastService;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *This class contains the test the API from WeatherForecastController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WeatherForecastController.class)
public class WeatherForecastApplicationTests {

	@MockBean
	WeatherForecastService weatherForecastService;
	@Autowired
	private MockMvc mockmvc;
	@Test
	public void dataTest() throws Exception {
		WeatherMatrix WeatherMatrix = new WeatherMatrix();
		WeatherMatrix.setDate(LocalDate.now().plusDays(1));
		WeatherMatrix.setAvg_day_temp(82.8775);
		WeatherMatrix.setAvg_night_temp(78.54249999999999);
		WeatherMatrix.setAvg_pressure(1020.005);
		List<WeatherMatrix> WeatherMatrices = new ArrayList<>();
		WeatherMatrices.add(WeatherMatrix);
		String cityname="cyberjaya";
		given(this.weatherForecastService.getWeatherForecastResponse(cityname)).willReturn(WeatherMatrices);
		this.mockmvc.perform(get("/data/"+cityname)).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].date",is("2019-01-14"))).andExpect(jsonPath("$[0].avg_day_temp",is(82.8775)))
		.andExpect(jsonPath("$[0].avg_night_temp",is(78.54249999999999))).andExpect(jsonPath("$[0].avg_pressure",is(1020.005)));
		verify(this.weatherForecastService).getWeatherForecastResponse(cityname);
	}

}
