package cz.kinst.jakub.weather.android;

import android.app.Application;

import cz.kinst.jakub.weather.android.utility.Preferences;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class WeatherApplication extends Application {
	private static Preferences sPreferences;


	public static Preferences getPreferences() {
		return sPreferences;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		sPreferences = new Preferences(this);
	}
}
