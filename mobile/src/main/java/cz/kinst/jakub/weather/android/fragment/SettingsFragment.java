package cz.kinst.jakub.weather.android.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import cz.kinst.jakub.weather.android.R;

public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setRetainInstance(true);
	}
}
