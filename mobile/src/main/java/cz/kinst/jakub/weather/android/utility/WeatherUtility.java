package cz.kinst.jakub.weather.android.utility;

/**
 * Weather utilities for converting metric units to imperial
 * <p>
 * Created by jakubkinst on 04/04/15.
 */
public class WeatherUtility {
	public static double celsiusToFahrenheit(double celsius) {
		return (celsius * 9 / 5.0) + 32;
	}


	public static double kilometersToMiles(double km) {
		return km * .621371192;
	}
}
