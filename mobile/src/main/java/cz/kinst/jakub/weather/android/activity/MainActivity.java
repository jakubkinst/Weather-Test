package cz.kinst.jakub.weather.android.activity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;

import cz.kinst.jakub.weather.android.R;
import cz.kinst.jakub.weather.android.fragment.ForecastFragment;
import cz.kinst.jakub.weather.android.fragment.NavigationDrawerFragment;
import cz.kinst.jakub.weather.android.fragment.TodayFragment;


public class MainActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}


	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = null;
		fragment = fragmentManager.findFragmentByTag(String.valueOf(position));
		if (fragment == null) {
			switch (position) {
				case NavigationDrawerFragment.NAV_POSITION_TODAY:
					fragment = TodayFragment.newInstance();
					break;
				case NavigationDrawerFragment.NAV_POSITION_FORECAST:
					fragment = ForecastFragment.newInstance();
					break;
			}
		}
		fragmentManager.beginTransaction()
				.replace(R.id.container, fragment, String.valueOf(position))
				.commit();
		onSectionAttached(position);
	}


	public void onSectionAttached(int number) {
		switch (number) {
			case NavigationDrawerFragment.NAV_POSITION_TODAY:
				mTitle = getString(R.string.title_today);
				break;
			case NavigationDrawerFragment.NAV_POSITION_FORECAST:
				mTitle = getString(R.string.title_forecast);
				break;
		}
		restoreActionBar();
	}


	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(SettingsActivity.getIntent(this));
				return true;
			case R.id.action_about:
				SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
						.setTitle(R.string.action_about)
						.setMessage(R.string.about_message)
						.setPositiveButtonText(R.string.ok)
						.show();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
