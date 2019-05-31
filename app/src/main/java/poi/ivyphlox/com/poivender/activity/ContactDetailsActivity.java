package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class ContactDetailsActivity extends AppCompatActivity implements WLAPIcalls.OnAPICallCompleteListener, OnMapReadyCallback, View.OnClickListener {

    private LinearLayout llEmailid;
    private TextView tvDirection;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvAdd;
    private TextView tvEmail;
    private TextView tvDOB;
    private ImageView ivShare;
    private Context mContext = ContactDetailsActivity.this;
    private GoogleMap mMap = null;
    private ProfileMainProject logInResponce = null;
    private ProfileMainProject logInResponceGlobal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_screen);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("My Contact");

        initSharedPref();

        initCOntroller();

        initMap();
    }

    void initSharedPref() {
        logInResponceGlobal = new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER,
                ContactDetailsActivity.this)
                , ProfileMainProject.class);

    }

    void initCOntroller() {
        llEmailid = findViewById(R.id.llEmailid);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvContact);
        tvAdd = findViewById(R.id.tvAddDetails);
        tvEmail = findViewById(R.id.tvEmailid);
        tvDOB = findViewById(R.id.tvDOB);
        ivShare = findViewById(R.id.ivshare);
        ivShare.setOnClickListener(this);
        if (getIntent().getStringExtra("mobile") != null) {
            loadDetails(getIntent().getStringExtra("mobile"));
        }
        if (getIntent().getStringExtra("name") != null) {
            tvName.setText(getIntent().getStringExtra("name"));
        }
    }

    void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadDetails(String id) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.getProfile(id);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Family) {
            return true;
        }
        if (id == R.id.action_Friends) {
            return true;
        }
        if (id == R.id.action_Office) {
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        ProfileMainProject logInResponce = new Gson().fromJson(result, ProfileMainProject.class);
        if (logInResponce != null) {

            this.logInResponce = logInResponce;
            //tvName.setText(logInResponce.getName());
            tvNumber = findViewById(R.id.tvContact);
            tvNumber.setText(logInResponce.getMobile_number());

            if (this.logInResponce.getEmail() != null) {
                tvEmail.setText(this.logInResponce.getEmail());
            }
            if (this.logInResponce.getDOB() != null) {
                tvDOB.setText(this.logInResponce.getDOB());
            }
            if (mMap != null) {
                if (logInResponce != null && logInResponce.getLatitude() != null
                        && !logInResponce.getLatitude().equals("")) {

//        // Add a marker in Sydney and move the camera
//        //latlong object
                    LatLng sydney = new LatLng(Double.parseDouble(logInResponce.getLatitude()),
                            Double.parseDouble(logInResponce.getLongitude()));
//
//        //add lat long object to add marker
                    mMap.addMarker(new MarkerOptions().position(sydney).title(getIntent().getStringExtra("name") + "'s Location"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.5f));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
                }
                tvDirection = findViewById(R.id.tvDirection);
                tvDirection.setOnClickListener(this);
            }
            if (logInResponce.getAddressTitle() != null) {
                tvAdd.setText(logInResponce.getAddressTitle() + logInResponce.getAddressDetails());
            } else {
                tvAdd.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (logInResponce != null && logInResponce.getLatitude() != null
                && !logInResponce.getLatitude().equals("")) {

//        // Add a marker in Sydney and move the camera
//        //latlong object
            LatLng sydney = new LatLng(19.8991299, 75.3115947);
//
//        //add lat long object to add marker
            mMap.addMarker(new MarkerOptions().position(sydney).title(logInResponce.getName() + "'s Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.5f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
        }
        mMap.getUiSettings().setMapToolbarEnabled(true);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivshare) {
            startActivity(new Intent(mContext, MemberListActivity.class).putExtra("mobile", logInResponce.getMobile_number()));
        }
        if (view.getId() == R.id.tvDirection) {
            if (logInResponce != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + logInResponceGlobal.getLatitude() + "" +
                                "," + logInResponceGlobal.getLongitude() + "&daddr=" + logInResponce.getLatitude() + "" +
                                "," + logInResponce.getLongitude() + ""));
                startActivity(intent);
            }
        }
    }

}
