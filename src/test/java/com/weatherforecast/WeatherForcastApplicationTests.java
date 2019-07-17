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
import com.Manu.weatherforecast.api.ManuWeatherMatrix;
import com.Manu.weatherforecast.api.resources.WeatherForecastController;
import com.Manu.weatherforecast.api.resources.WeatherForecastService;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *This class contains the test the API from WeatherForecastController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WeatherForecastController.class)
public class WeatherForcastApplicationTests {

	@MockBean
	WeatherForecastService weatherForecastService;
	@Autowired
	private MockMvc mockmvc;
	@Test
	public void dataTest() throws Exception {
		ManuWeatherMatrix ManuWeatherMatrix = new ManuWeatherMatrix();
		ManuWeatherMatrix.setDate(LocalDate.now().plusDays(1));
		ManuWeatherMatrix.setAvg_day_temp(82.8775);
		ManuWeatherMatrix.setAvg_night_temp(78.54249999999999);
		ManuWeatherMatrix.setAvg_pressure(1020.005);
		List<ManuWeatherMatrix> ManuWeatherMatrices = new ArrayList<>();
		ManuWeatherMatrices.add(ManuWeatherMatrix);
		String cityname="cyberjaya";
		given(this.weatherForecastService.getManuWeatherForecastResponse(cityname)).willReturn(ManuWeatherMatrices);
		this.mockmvc.perform(get("/data/"+cityname)).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].date",is("2019-01-14"))).andExpect(jsonPath("$[0].avg_day_temp",is(82.8775)))
		.andExpect(jsonPath("$[0].avg_night_temp",is(78.54249999999999))).andExpect(jsonPath("$[0].avg_pressure",is(1020.005)));
		verify(this.weatherForecastService).getManuWeatherForecastResponse(cityname);
	}

}
