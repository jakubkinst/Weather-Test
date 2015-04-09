package cz.kinst.jakub.weather.android;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cz.kinst.jakub.weather.android.utility.Preferences;
import cz.kinst.jakub.weather.android.utility.WearWeatherManager;
import pl.tajchert.buswear.EventBus;

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
		Logger.init(WeatherConfig.LOG_TAG)
				.setMethodCount(3)
				.setLogLevel(BuildConfig.LOGS ? LogLevel.FULL : LogLevel.NONE);

		EventBus.getDefault().register(this);
	}


	public void onEvent(String message) {
		Logger.i("WEAR Message: " + message);

		if (message.equals(getString(R.string.event_get_weather))) {
			WearWeatherManager.sendCurrentWeatherToWear();
		}
	}


	@Override
	public void onTerminate() {
		EventBus.getDefault().unregister(this);
		super.onTerminate();
	}
}
