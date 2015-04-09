package cz.kinst.jakub.weather.android.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import cz.kinst.jakub.weather.android.R;

/**
 * Settings fragment
 */
public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
