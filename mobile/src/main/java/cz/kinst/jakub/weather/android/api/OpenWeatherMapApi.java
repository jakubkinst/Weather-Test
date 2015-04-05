package cz.kinst.jakub.weather.android.api;

import cz.kinst.jakub.weather.android.entity.WeatherResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jakubkinst on 04/04/15.
 */
public class OpenWeatherMapApi {
	private static ApiInterface sInstance;


	public static ApiInterface getApi() {
		if (sInstance == null)
			sInstance = new RestAdapter.Builder()
					.setEndpoint("http://api.openweathermap.org/data/2.5")
					.build().create(ApiInterface.class);
		return sInstance;
	}


	public interface ApiInterface {
		@GET("/weather?units=metric")
		void getWeather(@Query("lat") double lat, @Query("lon") double lon, Callback<WeatherResponse> callback);
	}
}
