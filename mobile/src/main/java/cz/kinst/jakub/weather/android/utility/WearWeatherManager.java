package cz.kinst.jakub.weather.android.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import cz.kinst.jakub.weather.android.WeatherApplication;
import cz.kinst.jakub.weather.android.api.OpenWeatherMapApi;
import cz.kinst.jakub.weather.android.entity.api.CurrentWeatherResponse;
import cz.kinst.jakub.weather.android.entity.watch.CurrentWeather;
import cz.kinst.jakub.weather.android.event.ErrorEvent;
import cz.kinst.jakub.weather.android.location.CurrentLocationProvider;
import pl.tajchert.buswear.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jakubkinst on 09/04/15.
 */
public class WearWeatherManager {
	public static void sendCurrentWeatherToWear(Context context) {
		boolean tempImperial = WeatherApplication.getPreferences().isImperialTemperature();

		// get current location
		CurrentLocationProvider mCurrentLocationProvider = new CurrentLocationProvider(context);
		mCurrentLocationProvider.getLastLocation(new CurrentLocationProvider.OnLastLocationReceivedListener() {
			@Override
			public void onLastLocationReceived(Location location) {

				// get current weather
				OpenWeatherMapApi.getApi().getCurrentWeather(location.getLatitude(), location.getLongitude(), new Callback<CurrentWeatherResponse>() {
					@Override
					public void success(CurrentWeatherResponse weatherResponse, Response response) {

						// get weather icon
						Picasso.with(context).load(weatherResponse.getWeatherInfo().getIconUrl()).into(new Target() {
							@Override
							public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
								String condition = weatherResponse.getWeatherInfo().getMain();
								String location = weatherResponse.getLocationName();
								String temperature = weatherResponse.getMain().getTemp(tempImperial) + "°" + (tempImperial ? "F" : "C");
								EventBus.getDefault().postRemote(new CurrentWeather(condition, bitmap, temperature, location), context);
							}


							@Override
							public void onBitmapFailed(Drawable errorDrawable) {
								EventBus.getDefault().postRemote(new ErrorEvent("Icon error"), context);
							}


							@Override
							public void onPrepareLoad(Drawable placeHolderDrawable) {

							}
						});

					}


					@Override
					public void failure(RetrofitError error) {
						Logger.e(error);
						EventBus.getDefault().postRemote(new ErrorEvent(error.getMessage()), context);
					}
				});
			}


			@Override
			public void onError() {
				EventBus.getDefault().postRemote(new ErrorEvent("Location error"), context);
			}
		});

	}
}
