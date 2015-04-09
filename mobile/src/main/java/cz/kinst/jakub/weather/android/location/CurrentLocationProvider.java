package cz.kinst.jakub.weather.android.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Helper class for obtaining last user's location via Google Play services
 * <p>
 * Created by jakubkinst on 04/04/15.
 */
public class CurrentLocationProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	private final Context mContext;
	private GoogleApiClient mGoogleApiClient;
	private OnLastLocationReceivedListener mListener;


	public CurrentLocationProvider(Context context) {
		mContext = context;
	}


	public synchronized void getLastLocation(OnLastLocationReceivedListener listener) {
		mListener = listener;
		mGoogleApiClient = new GoogleApiClient.Builder(mContext)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
		mGoogleApiClient.connect();
	}


	@Override
	public void onConnected(Bundle bundle) {
		Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
				mGoogleApiClient);
		mListener.onLastLocationReceived(lastLocation);
	}


	@Override
	public void onConnectionSuspended(int i) {
		mListener.onError();
	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		mListener.onError();
	}


	public interface OnLastLocationReceivedListener {
		void onLastLocationReceived(Location location);

		void onError();
	}

}
