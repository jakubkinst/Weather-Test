package cz.kinst.jakub.weather.android.event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jakubkinst on 09/04/15.
 */
public class ErrorEvent implements Parcelable {
	private String name;


	public ErrorEvent(String name) {
		this.name = name;
	}


	public ErrorEvent(Parcel in) {
		this.name = in.readString();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
	}


	@Override
	public String toString() {
		return "ErrorEvent{" +
				"name='" + name + '\'' +
				'}';
	}
}
