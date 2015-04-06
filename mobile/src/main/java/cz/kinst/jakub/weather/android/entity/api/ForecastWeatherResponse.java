package cz.kinst.jakub.weather.android.entity.api;

import java.util.List;

/**
 * Created by jakubkinst on 05/04/15.
 */
public class ForecastWeatherResponse extends WeatherResponse {
	WeatherCity city;
	List<WeatherForecastItem> list;


	public WeatherCity getCity() {
		return city;
	}


	public List<WeatherForecastItem> getList() {
		return list;
	}
}
