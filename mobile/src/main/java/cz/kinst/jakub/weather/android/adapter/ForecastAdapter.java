package cz.kinst.jakub.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.entity.api.WeatherForecastItem;

/**
 * Adapter for displaying list of weather forecast for upcoming days
 * <p>
 * Created by jakubkinst on 04/04/15.
 */
public class ForecastAdapter extends ArrayAdapter<WeatherForecastItem> {
	private static final int LAYOUT = R.layout.item_forecast;
	private final boolean mTempImperial;


	public ForecastAdapter(Context context, List<WeatherForecastItem> objects, boolean tempImperial) {
		super(context, LAYOUT, objects);
		mTempImperial = tempImperial;
	}


	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder row;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(LAYOUT, parent, false);
			row = new ViewHolder(view);
			view.setTag(row);
		} else {
			row = (ViewHolder) view.getTag();
		}

		WeatherForecastItem forecast = getItem(position);

		Picasso.with(getContext()).load(forecast.getWeatherInfo().getIconUrl()).into(row.weatherImage);
		String day = WeatherForecastItem.getForecastDayName(position);
		row.dayName.setText(Character.toUpperCase(day.charAt(0)) + day.substring(1));
		row.temperature.setText((int) forecast.getTemp(mTempImperial) + "°" + (mTempImperial ? "F" : "C"));
		row.condition.setText(forecast.getWeatherInfo().getMain());
		return view;
	}


	public static class ViewHolder {
		@InjectView(R.id.weather_image)
		public ImageView weatherImage;
		@InjectView(R.id.day_name)
		public TextView dayName;
		@InjectView(R.id.temperature)
		public TextView temperature;
		@InjectView(R.id.condition)
		public TextView condition;


		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}

}
