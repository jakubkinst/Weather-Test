package cz.kinst.jakub.weather.android.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cz.kinst.jakub.weather.android.R;

/**
 * Created by jakubkinst on 05/04/15.
 */
public class Preferences {
	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1l;
	public static final double NULL_DOUBLE = -1.0;
	public static final String NULL_STRING = null;
	private final Context mContext;
	SharedPreferences mSharedPreferences;


	public Preferences(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mContext = context;
	}


	private SharedPreferences getPreferences() {
		return mSharedPreferences;
	}


	private String getString(int resourceId) {
		return mContext.getString(resourceId);
	}

	// Preferences


	public boolean isImperialTemperature() {
		return getPreferences()
				.getString(getString(R.string.pref_key_unit_temperature), NULL_STRING)
				.equals(getString(R.string.pref_value_unit_imperial));
	}


	public boolean isImperialLength() {
		return getPreferences()
				.getString(getString(R.string.pref_key_unit_length), NULL_STRING)
				.equals(getString(R.string.pref_value_unit_imperial));
	}

}
