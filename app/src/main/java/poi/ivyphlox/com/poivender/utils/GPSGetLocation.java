package poi.ivyphlox.com.poivender.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class GPSGetLocation {
    private GPSTracker mGps;
    private String mLattitude;
    private String mLongitude;
    private String TAG="GPSGetLocation";
    public Activity mContext;
    private int REQUEST_CHECK_SETTINGS=11;


    public GPSGetLocation(Activity mContext)
    {
        this.mContext=mContext;
    }
    public GPSTracker getCurrentLatLongs() {
                mGps = new GPSTracker(mContext);

        // check if GPS enabled
        if (mGps.canGetLocation()) {

            mLattitude = String.valueOf(mGps.getLatitude());
            mLongitude = String.valueOf(mGps.getLongitude());
            new AppCommonMethods(mContext).LOG(0, TAG, "Lattitude= " + mLattitude + " Longitude= " + mLongitude);
            AppPrefs.putStringPref(AppConstants.PREFS_LATITUDE, mLattitude, mContext);
            AppPrefs.putStringPref(AppConstants.PREFS_LONGITUDE, mLongitude, mContext);
            AppPrefs.putStringPref(AppConstants.KEY_SELECTED_LOCATION, "", mContext);

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            mGps.showSettingsAlert();
        }
        displayLocationSettingsRequest(mContext);

        return mGps;
    }
    public void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mContext, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}
