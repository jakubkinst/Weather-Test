package cz.kinst.jakub.weather.android.entity.api;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class WeatherInfo {
	private static final String ICON_URL_BASE = "http://openweathermap.org/img/w/";
	int id;
	String main;
	String description;
	String icon;


	public int getId() {
		return id;
	}


	public String getMain() {
		return main;
	}


	public String getDescription() {
		return description;
	}


	public String getIconUrl() {
		return ICON_URL_BASE + icon + ".png";
	}
}
