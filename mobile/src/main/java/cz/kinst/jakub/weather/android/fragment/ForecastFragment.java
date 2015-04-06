package cz.kinst.jakub.weather.android.fragment;


import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.WeatherApplication;
import cz.kinst.jakub.weather.android.adapter.ForecastAdapter;
import cz.kinst.jakub.weather.android.api.OpenWeatherMapApi;
import cz.kinst.jakub.weather.android.entity.api.ForecastWeatherResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForecastFragment extends BaseWeatherFragment {


	@InjectView(R.id.list_forecast)
	ListView mListForecast;


	public static ForecastFragment newInstance() {
		ForecastFragment fragment = new ForecastFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_forecast, container, false);
		ButterKnife.inject(this, view);
		return view;
	}


	@Override
	protected void loadWeather(Location location) {
		OpenWeatherMapApi.getApi().getForecast(location.getLatitude(), location.getLongitude(), new Callback<ForecastWeatherResponse>() {
			@Override
			public void success(ForecastWeatherResponse forecastResponse, Response response) {
				onLoadSuccess(forecastResponse);
			}


			@Override
			public void failure(RetrofitError error) {
				error.printStackTrace();
				onLoadFailed();
			}
		});
	}


	@Override
	protected void updateWeatherInfo() {
		boolean tempImperial = WeatherApplication.getPreferences().isImperialTemperature();
		boolean lengthImperial = WeatherApplication.getPreferences().isImperialLength();

		ForecastWeatherResponse forecast = (ForecastWeatherResponse) getWeatherResponse();

		mListForecast.setAdapter(new ForecastAdapter(getActivity(), forecast.getList(), tempImperial));

	}

}
