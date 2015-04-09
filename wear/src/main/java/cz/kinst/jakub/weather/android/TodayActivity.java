package cz.kinst.jakub.weather.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.entity.watch.CurrentWeather;
import cz.kinst.jakub.weather.android.event.ErrorEvent;
import pl.tajchert.buswear.EventBus;

public class TodayActivity extends Activity {

	@InjectView(R.id.icon)
	ImageView mIcon;
	@InjectView(R.id.temperature)
	TextView mTemperature;
	@InjectView(R.id.condition)
	TextView mCondition;
	@InjectView(R.id.location)
	TextView mLocation;
	@InjectView(R.id.loading)
	FrameLayout mLoading;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		ButterKnife.inject(this);

		EventBus.getDefault().register(this);
	}


	@Override
	protected void onStart() {
		super.onStart();
		mLoading.setVisibility(View.VISIBLE);
		EventBus.getDefault().postRemote(getString(R.string.event_get_weather), this);
	}


	public void onEvent(CurrentWeather currentWeatherResponse) {
		runOnUiThread(() -> {
			mCondition.setText(currentWeatherResponse.getCondition());
			mLocation.setText(currentWeatherResponse.getLocation());
			mTemperature.setText(currentWeatherResponse.getTemperature());
			mIcon.setImageBitmap(currentWeatherResponse.getIcon());
			mLoading.setVisibility(View.GONE);
		});
	}


	public void onEvent(ErrorEvent errorEvent) {
		runOnUiThread(() -> {
			mLoading.setVisibility(View.GONE);
			Toast.makeText(TodayActivity.this, R.string.network_error, Toast.LENGTH_LONG).show();
		});
	}


	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
