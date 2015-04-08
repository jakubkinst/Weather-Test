package cz.kinst.jakub.weather.android.fragment;


import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.WeatherApplication;
import cz.kinst.jakub.weather.android.api.OpenWeatherMapApi;
import cz.kinst.jakub.weather.android.entity.api.CurrentWeatherResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodayFragment extends BaseWeatherFragment {

	@InjectView(R.id.current_image)
	ImageView mCurrentImage;
	@InjectView(R.id.location)
	TextView mLocation;
	@InjectView(R.id.current_condition)
	TextView mCurrentCondition;
	@InjectView(R.id.humidity)
	TextView mHumidity;
	@InjectView(R.id.precipitation)
	TextView mPrecipitation;
	@InjectView(R.id.pressure)
	TextView mPressure;
	@InjectView(R.id.wind_speed)
	TextView mWindSpeed;
	@InjectView(R.id.wind_direction)
	TextView mWindDirection;


	public static TodayFragment newInstance() {
		TodayFragment fragment = new TodayFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_today, container, false);
		ButterKnife.inject(this, view);
		return view;
	}


	@Override
	protected void loadWeather(Location location) {
		OpenWeatherMapApi.getApi().getCurrentWeather(location.getLatitude(), location.getLongitude(), new Callback<CurrentWeatherResponse>() {
			@Override
			public void success(CurrentWeatherResponse weatherResponse, Response response) {
				Logger.d("Current weather response received");
				onLoadSuccess(weatherResponse);
			}


			@Override
			public void failure(RetrofitError error) {
				Logger.e(error);
				onLoadFailed();
			}
		});
	}


	@Override
	protected void updateWeatherInfo() {
		boolean tempImperial = WeatherApplication.getPreferences().isImperialTemperature();
		boolean lengthImperial = WeatherApplication.getPreferences().isImperialLength();

		CurrentWeatherResponse currentWeatherResponse = (CurrentWeatherResponse) getWeatherResponse();

		mLocation.setText(currentWeatherResponse.getLocationName());
		Picasso.with(getActivity()).load(currentWeatherResponse.getWeatherInfo().getIconUrl()).into(mCurrentImage);
		mCurrentCondition.setText(currentWeatherResponse.getMain().getTemp(tempImperial) + "°" + (tempImperial ? "F" : "C") + " | " + currentWeatherResponse.getWeatherInfo().getMain());

		mHumidity.setText(currentWeatherResponse.getMain().getHumidity() + " %");
		mPrecipitation.setText("N/A");
		mPressure.setText(currentWeatherResponse.getMain().getPressure() + " hPa");
		mWindSpeed.setText(currentWeatherResponse.getWind().getSpeed(lengthImperial) + (lengthImperial ? " mph" : " km/h"));
		mWindDirection.setText(currentWeatherResponse.getWind().getDirection());
	}


}
