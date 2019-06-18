package poi.ivyphlox.com.poivender.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import poi.ivyphlox.com.poivender.DBHelper;
import poi.ivyphlox.com.poivender.LoadContactsActivity;
import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.fragments.DashboardFragment;
import poi.ivyphlox.com.poivender.fragments.HomeFragment;
import poi.ivyphlox.com.poivender.fragments.NotificationFragment;
import poi.ivyphlox.com.poivender.fragments.ProfileFragment;
import poi.ivyphlox.com.poivender.fragments.ViewProfileFragment;
import poi.ivyphlox.com.poivender.model.BussinessProfileModel;
import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.model.ContactResponce;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;
import poi.ivyphlox.com.poivender.utils.BottomNavigationViewHelper;
import poi.ivyphlox.com.poivender.utils.RuntimePermissionActivity;

public class NavigationActivity extends RuntimePermissionActivity
        implements NavigationView.OnNavigationItemSelectedListener, WLAPIcalls.OnAPICallCompleteListener {
    private static final int REQUEST_CHECK_SETTINGS =99 ;
    private Context mContext=NavigationActivity.this;

    private static final String TAG = "NavigationActivity";
    private static final int REQUEST_PERMISSIONS = 100;
    private TextView mTextMessage;
    boolean isPermission = false;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_btm_home:
                    if (isPermission) {
                        pushFragment(DashboardFragment.newInstance("",""), false);
                    } else {
                        permission();
                    }
                    return true;
                case R.id.navigation_btm_location:
                    comingSoon();
                    return true;
//                case R.id.navigation_notifications:
//                    pushFragment(NotificationFragment.newInstance("",""), false);
//                    return true;
                case R.id.navigation_btm_profile:
                    pushFragment( ViewProfileFragment.newInstance("",""), false);
                    return true;
            }
            return false;
        }
    };

    void comingSoon()
    {
        Toast.makeText(mContext,"Coming Soon",Toast.LENGTH_SHORT).show();
    }

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText(R.string.app_name);
        mTextMessage = findViewById(R.id.tvName);

//        dbHelper=new DBHelper(mContext);
//        if(dbHelper.getAllContacts().size()==0){
//            Intent intent=new Intent(mContext, LoadContactsActivity.class);
//            startActivity(intent);
//        }

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        permission();

//        if(isPermission) {
        pushFragment(DashboardFragment.newInstance("",""), false);
//        }
        //getContactList();
        LogInResponce logInResponce=new LogInResponce();
        logInResponce=new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER,mContext),LogInResponce.class);
        //loadRecent(logInResponce.getMobile_number());

        /**call for turn on location
         *
         */
        displayLocationSettingsRequest(mContext);
    }



    /**
     * This function is used to get recently viewed data from server
     */
    private void loadMembers(List<ContactResponce> username) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            //fromValue++;
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loadMember), this);
            mAPIcall.getMembers(username);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //getContactList();
        BussinessProfileModel logInResponce=new BussinessProfileModel();
        logInResponce=new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER,mContext),BussinessProfileModel.class);
        loadRecent(logInResponce.getMobile_number());
    }

    void permission() {
        NavigationActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS},
                R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        isPermission=true;
       // pushFragment(new HomeFragment(), true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void pushFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fram_container, fragment);
        ft.commit();
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecent(String username) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.getProfile(username);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
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
                            status.startResolutionForResult(NavigationActivity.this, REQUEST_CHECK_SETTINGS);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void logout()
    {
        AppPrefs.putBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,false,mContext);
        startActivity(new Intent(mContext,LoginActivity.class));
        finish();
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        try {
            if(flag.equalsIgnoreCase(mContext.getString(R.string.loginDate))){

                Log.e("ProfileResponce",result);

                JSONObject jsonObject =new JSONObject(result);

                BussinessProfileModel logInResponce=new BussinessProfileModel();
                logInResponce=new Gson().fromJson(result,BussinessProfileModel.class);
                AppPrefs.putStringPref(AppConstants.PREFS_USER,result,mContext);
                AppPrefs.putBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,true,mContext);

                if(logInResponce.getBussinessName().equals(""))
                {
                     startActivity(new Intent(mContext,ProfileActivity.class));
                }

            }
            else if(flag.equalsIgnoreCase(getString(R.string.loadMember)))
            {
                List<ContactResponce> contactList = getConfig(result);
                for (ContactResponce contact : contactList) {
                    if (Boolean.valueOf(contact.getIsPOI())) {
                        dbHelper.updateContact(contact);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(mContext,"Mobile no and Password is not Matched",Toast.LENGTH_LONG).show();
        }

    }

    /**
     * get Contact data from shared Prefference using Gson
     */
    public ArrayList<ContactResponce> getConfig(String str) {
        try {
            Gson gson = new Gson();
            ArrayList<ContactResponce> yourSerializableObject = new ArrayList<ContactResponce>();
            ContactResponce[] yourSerializableObject1 = gson.fromJson(str, ContactResponce[].class);
            yourSerializableObject = new ArrayList<>(Arrays.asList(yourSerializableObject1));
            return yourSerializableObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
