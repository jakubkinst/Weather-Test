package cz.kinst.jakub.weather.android.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.entity.api.WeatherResponse;
import cz.kinst.jakub.weather.android.location.CurrentLocationProvider;

/**
 * Abstract base fragment with common methods for determining
 * current position and downloading weather
 * <p>
 * Created by jakubkinst on 05/04/15.
 */
public abstract class BaseWeatherFragment extends Fragment {
	protected WeatherResponse mWeatherResponse;
	@InjectView(R.id.error)
	View mError;
	@InjectView(R.id.loading)
	View mLoading;
	@InjectView(R.id.swipe_refresh)
	SwipeRefreshLayout mSwipeRefresh;


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setRetainInstance(true);
		mSwipeRefresh.setOnRefreshListener(() -> refresh());
		mLoading.setVisibility(View.VISIBLE);
		refresh();
	}


	@Override
	public void onResume() {
		super.onResume();
		if (mWeatherResponse != null)
			updateWeatherInfo();
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


	protected void onLoadSuccess(WeatherResponse weatherResponse) {
		mError.setVisibility(View.GONE);
		mLoading.setVisibility(View.GONE);
		mSwipeRefresh.setRefreshing(false);

		mWeatherResponse = weatherResponse;
		updateWeatherInfo();
	}


	protected void onLoadFailed() {
		Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
		mError.setVisibility(View.VISIBLE);
		mLoading.setVisibility(View.GONE);
		mSwipeRefresh.setRefreshing(false);
	}


	protected abstract void loadWeather(Location location);


	protected abstract void updateWeatherInfo();


	protected WeatherResponse getWeatherResponse() {
		return mWeatherResponse;
	}
}
