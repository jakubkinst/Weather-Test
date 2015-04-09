package cz.kinst.jakub.weather.android.entity.api;

import cz.kinst.jakub.weather.android.utility.WeatherUtility;

/**
 * Created by jakubkinst on 05/04/15.
 */
public class WeatherTemperature {
	double day;


	public long getDayTemp(boolean fahrenheit) {
		return Math.round(fahrenheit ? WeatherUtility.celsiusToFahrenheit(day) : day);
	}
}
