package cz.kinst.jakub.weather.android.entity.api;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jakubkinst on 05/04/15.
 */
public class WeatherForecastItem {
	WeatherTemperature temp;
	List<WeatherInfo> weather;


	public static String getForecastDayName(int dayFromToday) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, dayFromToday);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		DateFormatSymbols symbols = new DateFormatSymbols();
		return symbols.getWeekdays()[dayOfWeek];
	}


	public double getTemp(boolean fahrenheit) {
		return temp.getDayTemp(fahrenheit);
	}


	public WeatherInfo getWeatherInfo() {
		return weather.get(0);
	}
}
