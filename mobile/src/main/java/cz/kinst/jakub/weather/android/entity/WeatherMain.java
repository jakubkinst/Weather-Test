package cz.kinst.jakub.weather.android.entity;

import cz.kinst.jakub.weather.android.utility.WeatherUtility;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class WeatherMain {
	double temp;
	double pressure;
	double humidity;


	public long getTemp(boolean fahrenheit) {
		return Math.round(fahrenheit ? WeatherUtility.celsiusToFahrenheit(temp) : temp);
	}


	public double getPressure() {
		return pressure;
	}


	public double getHumidity() {
		return humidity;
	}
}
