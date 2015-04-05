package cz.kinst.jakub.weather.android.fragment;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.WeatherApplication;
import cz.kinst.jakub.weather.android.api.OpenWeatherMapApi;
import cz.kinst.jakub.weather.android.entity.WeatherResponse;
import cz.kinst.jakub.weather.android.location.CurrentLocationProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodayFragment extends Fragment {

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
	@InjectView(R.id.error)
	View mError;
	@InjectView(R.id.loading)
	View mLoading;
	@InjectView(R.id.swipe_refresh)
	SwipeRefreshLayout mSwipeRefresh;

	private WeatherResponse mWeatherResponse;


	public static TodayFragment newInstance() {
		TodayFragment fragment = new TodayFragment();
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onResume() {
		super.onResume();
		if (mWeatherResponse != null)
			updateWeatherInfo();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_today, container, false);
		ButterKnife.inject(this, view);
		return view;
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setRetainInstance(true);

		mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh();
			}
		});

		mLoading.setVisibility(View.VISIBLE);
		refresh();
	}


	private void refresh() {
		CurrentLocationProvider mCurrentLocationProvider = new CurrentLocationProvider(getActivity());
		mCurrentLocationProvider.getLastLocation(new CurrentLocationProvider.OnLastLocationReceivedListener() {
			@Override
			public void onLastLocationReceived(Location location) {
				loadWeather(location);
			}


			@Override
			public void onError() {
				Toast.makeText(getActivity(), getActivity().getString(R.string.error_location), Toast.LENGTH_SHORT).show();
				mLoading.setVisibility(View.GONE);
				mError.setVisibility(View.VISIBLE);
				mSwipeRefresh.setRefreshing(false);
			}
		});
	}


	private void loadWeather(Location location) {
		OpenWeatherMapApi.getApi().getWeather(location.getLatitude(), location.getLongitude(), new Callback<WeatherResponse>() {
			@Override
			public void success(WeatherResponse weatherResponse, Response response) {
				mError.setVisibility(View.GONE);
				mLoading.setVisibility(View.GONE);
				mSwipeRefresh.setRefreshing(false);
				mWeatherResponse = weatherResponse;
				updateWeatherInfo();
			}


			@Override
			public void failure(RetrofitError error) {
				error.printStackTrace();
				Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
				mError.setVisibility(View.VISIBLE);
				mLoading.setVisibility(View.GONE);
				mSwipeRefresh.setRefreshing(false);
			}
		});
	}


	private void updateWeatherInfo() {
		boolean tempImperial = WeatherApplication.getPreferences().isImperialTemperature();
		boolean lengthImperial = WeatherApplication.getPreferences().isImperialLength();


		mLocation.setText(mWeatherResponse.getLocationName());
		Picasso.with(getActivity()).load(mWeatherResponse.getWeatherInfo().getIconUrl()).into(mCurrentImage);
		mCurrentCondition.setText(mWeatherResponse.getMain().getTemp(tempImperial) + "°" + (tempImperial ? "F" : "C") + " | " + mWeatherResponse.getWeatherInfo().getMain());

		mHumidity.setText(mWeatherResponse.getMain().getHumidity() + " %");
		mPrecipitation.setText("N/A");
		mPressure.setText(mWeatherResponse.getMain().getPressure() + " hPa");
		mWindSpeed.setText(mWeatherResponse.getWind().getSpeed(lengthImperial) + (lengthImperial ? " mph" : " km/h"));
		mWindDirection.setText(mWeatherResponse.getWind().getDirection());
	}

}
