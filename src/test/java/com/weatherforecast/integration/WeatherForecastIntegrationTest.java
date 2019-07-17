package com.weatherforecast.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.Manu.weatherforecast.api.ManuWeatherMatrix;
import com.Manu.weatherforecast.api.resources.WeatherForecastService;

/*
 *This class contains the test to check the integration between this application and openweathermap.org
 */
@RunWith(SpringRunner.class)
@RestClientTest(WeatherForecastService.class)
@TestPropertySource(properties = { "openweathermap.api.key=manukey", "openweathermap.max.days=3",
		"openweathermap.forecast.url=http://api.openweathermap.org/data/2.5/forecast?q={city}&APPID={apikey}&units=imperial",
		"Manu.day.starttime=6", "Manu.day.endtime=18", "Manu.night.starttime=18", "Manu.night.endtime=6" })

public class WeatherForecastIntegrationTest {
	private static final String url_openweathermap = "http://api.openweathermap.org/data/2.5/";

	@Autowired
	private MockRestServiceServer mockRestServiceServer;
	@Autowired
	private WeatherForecastService weatherForecastService;

	@Test
	public void getManuWeatherForecastResponse() {
		this.mockRestServiceServer
				.expect(requestTo(url_openweathermap + "forecast?q=cyberjaya&APPID=manukey&units=imperial"))
				.andRespond(withSuccess(new ClassPathResource("openweathermap_cyberjaya.json", getClass()),
						MediaType.APPLICATION_JSON));
		List<ManuWeatherMatrix> ManuWeatherMatrices = this.weatherForecastService
				.getManuWeatherForecastResponse("cyberjaya");
		ManuWeatherMatrix ManuWeatherMatrix = ManuWeatherMatrices.get(0);
		assertThat(ManuWeatherMatrix.getAvg_day_temp()).isEqualTo(82.8775);
		assertThat(ManuWeatherMatrix.getAvg_night_temp()).isEqualTo(78.54249999999999);
		assertThat(ManuWeatherMatrix.getAvg_pressure()).isEqualTo(1020.005);
		this.mockRestServiceServer.verify();
	}
}
