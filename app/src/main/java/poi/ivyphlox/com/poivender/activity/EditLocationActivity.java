package poi.ivyphlox.com.poivender.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.ProfileMainProject;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;
import poi.ivyphlox.com.poivender.utils.GPSGetLocation;
import poi.ivyphlox.com.poivender.utils.GPSTracker;

public class EditLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMapClickListener,
        View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {

    private static final int REQUEST_CHECK_SETTINGS = 100;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private GoogleMap mMap;
    public static String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private boolean isFirst = false;
    private ImageView ivBack;
    private TextView tvSave;
    public static String NAME_LAT = "lat";
    public static String NAME_LAN = "lan";
    public static String NAME_ADD_TITLE = "address title";
    public static String NAME_ADD_DETAILS = "address details";
    private ProfileMainProject logInResponce;
    private EditText edtAddressTitle;
    private EditText edtAddressDetails;
    private Context mContext = EditLocationActivity.this;
    private String TAG = "EditLocationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.logInResponce = new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER, getApplicationContext()), ProfileMainProject.class);

        edtAddressDetails = findViewById(R.id.edtAddressDetails);
        edtAddressTitle = findViewById(R.id.edtAddressTitle);
        edtAddressTitle.setText(this.logInResponce.getAddressTitle());
        edtAddressDetails.setText(this.logInResponce.getAddressDetails());
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvSave = findViewById(R.id.ivSave);
        tvSave.setOnClickListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        setPlaces();
        displayLocationSettingsRequest(mContext);
    }

    void setPlaces() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16.0f));
                AppPrefs.putStringPref(AppConstants.PREFS_LATITUDE, place.getLatLng().latitude + "", mContext);
                AppPrefs.putStringPref(AppConstants.PREFS_LONGITUDE, place.getLatLng().longitude + "", mContext);
                latitude = place.getLatLng().latitude + "";
                longitude = place.getLatLng().longitude + "";

            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    private void displayLocationSettingsRequest(Context context) {
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
                            status.startResolutionForResult(EditLocationActivity.this, REQUEST_CHECK_SETTINGS);
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        LatLng latLng = mMap.getCameraPosition().target;
        if (logInResponce != null && logInResponce.getLongitude() != null) {

            GPSTracker gpsTracker = new GPSTracker(mContext);
            gpsTracker = new GPSGetLocation(mContext).getCurrentLatLongs();
            LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
//
            //add lat long object to add marker
            // mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.5f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);


        } else {

//        // Add a marker in Sydney and move the camera
//        //latlong object

            LatLng sydney = new LatLng(
                    Double.parseDouble(logInResponce.getLatitude()),
                    Double.parseDouble(logInResponce.getLongitude()));
//
            //add lat long object to add marker
            // mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.5f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                Log.i("centerLat", String.valueOf(cameraPosition.target.latitude));

                Log.i("centerLong", String.valueOf(cameraPosition.target.longitude));
                latitude = String.valueOf(cameraPosition.target.latitude);
                longitude = String.valueOf(cameraPosition.target.longitude);

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("Location", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        if (logInResponce.getLongitude() != null
                &&logInResponce.getLongitude().isEmpty()) {
            if (mMap != null) {
                if (!isFirst) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(sydney).title("CUrrent Location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());

                    isFirst = true;
                }
            }
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.e("Location", "Latitude:" + latLng.latitude + ", Longitude:" + latLng.longitude);
        LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);

        if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(sydney).title("Current Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));
            latitude = String.valueOf(latLng.latitude);
            longitude = String.valueOf(latLng.longitude);

        }
    }

    private void save() {
        if (logInResponce != null) {
            logInResponce.setMobile_number(this.logInResponce.getMobile_number());
            logInResponce.set_id(null);
            logInResponce.setActive(this.logInResponce.getActive());
            logInResponce.setIs_POI(this.logInResponce.getIs_POI());
            logInResponce.setImage("");
            logInResponce.setLatitude(latitude);
            logInResponce.setLongitude(longitude);
            logInResponce.setName(this.logInResponce.getName());
            logInResponce.setAddressTitle(edtAddressTitle.getText().toString());
            logInResponce.setAddressDetails(edtAddressDetails.getText().toString());
            saveLogin(logInResponce);
        }
    }

    /**
     * here we saving Latlong to Profile
     */
    private void saveLogin(ProfileMainProject logInResponce) {
        if (new AppCommonMethods(getApplicationContext()).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(EditLocationActivity.this, getString(R.string.loginDate), this);
            mAPIcall.profileUpdate(logInResponce);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivSave:

                save();


                break;
        }
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        Intent intent = new Intent();

        intent.putExtra(NAME_LAT, latitude);
        intent.putExtra(NAME_LAN, longitude);
//                intent.putExtra(NAME_ADD_DETAILS, edt);
//                intent.putExtra(NAME_ADD_TITLE, longitude);
        setResult(RESULT_OK, intent);

        finish();
    }
}
