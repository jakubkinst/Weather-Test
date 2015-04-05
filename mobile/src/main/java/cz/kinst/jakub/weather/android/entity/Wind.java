package cz.kinst.jakub.weather.android.entity;

import cz.kinst.jakub.weather.android.utility.WeatherUtility;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class Wind {
	double speed;
	double deg;


	public double getSpeed(boolean miles) {

		return Math.round(miles ? WeatherUtility.kilometersToMiles(speed) : speed);
	}


	public double getDeg() {
		return deg;
	}


	public String getDirection() {
		long val = Math.round((deg / 22.5) + .5);
		String[] values = new String[]{"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
		return values[(int) (val % 16)];
	}
}
