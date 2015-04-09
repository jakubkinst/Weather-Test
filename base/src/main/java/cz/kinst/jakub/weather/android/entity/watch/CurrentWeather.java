package cz.kinst.jakub.weather.android.entity.watch;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import cz.kinst.jakub.weather.android.utility.BitmapUtility;

/**
 * A payload class used to transfer current weather to wearable device
 * <p>
 * Created by jakubkinst on 09/04/15.
 */
public class CurrentWeather implements Parcelable {
	private String condition;
	private byte[] icon;
	private String temperature;
	private String location;


	public CurrentWeather(String condition, Bitmap icon, String temperature, String location) {
		this.condition = condition;
		this.icon = BitmapUtility.bitmapToByteArray(icon);
		this.temperature = temperature;
		this.location = location;
	}


	public CurrentWeather(Parcel in) {
		condition = in.readString();
		icon = new byte[in.readInt()];
		in.readByteArray(icon);
		temperature = in.readString();
		location = in.readString();
	}


	public String getCondition() {
		return condition;
	}


	public Bitmap getIcon() {
		return BitmapUtility.byteArrayToButmap(icon);
	}


	public String getTemperature() {
		return temperature;
	}


	public String getLocation() {
		return location;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(condition);
		dest.writeInt(icon.length);
		dest.writeByteArray(icon);
		dest.writeString(temperature);
		dest.writeString(location);
	}
}
