package cz.kinst.jakub.weather.android.entity;

import java.util.List;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class WeatherResponse {
	Wind wind;
	WeatherMain main;
	List<WeatherInfo> weather;
	WeatherSys sys;
	String name;


	public Wind getWind() {
		return wind;
	}


	public WeatherMain getMain() {
		return main;
	}


	public WeatherInfo getWeatherInfo() {
		return weather.get(0);
	}


	public String getLocationName() {
		return name + ", " + sys.getCountry();
	}
}
