package poi.ivyphlox.com.poivender.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.EditLocationActivity;
import poi.ivyphlox.com.poivender.adapter.EditContactAdapter;
import poi.ivyphlox.com.poivender.adapter.SocialAdapter;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.model.Mobiles;
import poi.ivyphlox.com.poivender.model.ProfileMainProject;
import poi.ivyphlox.com.poivender.model.Social;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements OnMapReadyCallback,
        LocationListener,
        View.OnClickListener,
        WLAPIcalls.OnAPICallCompleteListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_CAMERA = 66;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private String latitude;
    private  String longitude;
    protected boolean gps_enabled, network_enabled;
    private FragmentActivity myContext;

    private MapView mapView;
    private MapView mapView1;

    private TextView tvNumber;
    private TextInputLayout text_input_layout;

    private ImageView ivprofileImage;
    private TextView tvEdit;
    private ImageView ivCamera;

    private Context mContext;

    private TextView tvChangeLocation;

    private ProfileMainProject logInResponce;

    private GoogleMap mMap;
    private GoogleMap mMap1;
    public int REQ_CHANGE_LOC = 10;
    private boolean isImageChange = false;
    private View view;

    private Bundle savedInstanceState;

    private RecyclerView rvContact;
    private RecyclerView rvSocial;
    private Mobiles mContactList = new Mobiles();
    private Social mSocialList = new Social();
    private ImageButton ibAdd;

    private EditText tvName;
    private EditText edtDOB;
    private EditText edtWechat;
    private EditText edtWhatsaap;

    private EditText edtHome;
    private EditText edtOffice;

    private EditText edtFacebook;
    private EditText edtTwitter;
    private EditText edtLinkedIn;
    private EditText edtInstagram;
    private EditText edtMobile;
    private EditText edtEmail;
    private EditText edtDetailAddress;


    private TextView tvDob;
    private TextView tvWechat;
    private TextView tvWhatsapp;

    private TextView tvHome;
    private TextView tvOffice;

    private TextView tvFacebook;
    private TextView tvLinkedIn;
    private TextView tvTwitter;
    private TextView tvInstagram;
    private TextView tvMobile;
    private TextView tvEmailid;
    //  private TextView edtDetailAddress;

    private TextView tvAddPhoto;


    private Button btnAdd;
    private ImageView ivSave;
    private int addsocial = 0;

    private boolean isSave = false;
    private boolean isSaveFromActivity = false;


    private LinearLayout lnrVIewProfile;
    private LinearLayout lnrEdt;


    public ProfileFragment() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            this.savedInstanceState = savedInstanceState;
            mContext = getActivity();
            view = inflater.inflate(R.layout.profile_screen, container, false);


//        setHasOptionsMenu(true);
            view = initController(savedInstanceState);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                Log.e("Permission","False");
                return view;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    this);
            Log.e("Permission","False and init");
            initLocationListner();
            initMap();
            initMap1();

            if (mParam1.equals("profile")) {
                isSaveFromActivity = true;
                isSave = true;
                lnrEdt.setVisibility(View.VISIBLE);
                lnrVIewProfile.setVisibility(View.GONE);
                tvName.setEnabled(true);
                backroundTintSelected(tvName);
                tvEdit.setText("Save");




                if(latitude==null||latitude.equals("")) {
                    Intent intent = new Intent(mContext, EditLocationActivity.class);
                    startActivityForResult(intent, REQ_CHANGE_LOC);
                }
            }
            displayLocationSettingsRequest(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return view;


        }
//        setRecyclerView();
//        setRecyclerViewSocial();
    }

    void initLocationListner()
    {



    }


    EditContactAdapter notificationAdapter;
    SocialAdapter socialAdapter;

//    void setRecyclerView() {
//        if (mContactList.size() == 0 && mContactList.size() < 4) {
//            mContactList.add(new ContactModelProfilePOJO());
//        }
//        rvContact = view.findViewById(R.id.rvContact);
//        notificationAdapter = new EditContactAdapter(mContext, mContactList);
//
//        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        rvContact.setLayoutManager(layoutManagerReview);
//        rvContact.setAdapter(notificationAdapter);
//    }

    //    void setRecyclerViewSocial() {
//        if (mSocialList.size() == 0 && mSocialList.size() < 4) {
//            mSocialList.add(new Social());
//        }
//        rvSocial = view.findViewById(R.id.rvSocial);
//        socialAdapter = new SocialAdapter(mContext, mSocialList);
//
//        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        rvSocial.setLayoutManager(layoutManagerReview);
//        rvSocial.setAdapter(socialAdapter);
//    }
    private static final String TAG = "ProfileFragment";
    private static final int REQUEST_CHECK_SETTINGS = 100;

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
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
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

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * init Controller here
     *
     * @param savedInstanceState
     * @return
     */
    View initController(Bundle savedInstanceState) {

        logInResponce = new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER, myContext), ProfileMainProject.class);


        btnAdd = view.findViewById(R.id.btnAddMore);

        lnrEdt = view.findViewById(R.id.lnrEdt);
        lnrVIewProfile = view.findViewById(R.id.lnrVIewProfile);

        tvWhatsapp = view.findViewById(R.id.tvWhatsapp);
        tvWechat = view.findViewById(R.id.tvWechat);
        tvHome = view.findViewById(R.id.tvHome);
        tvOffice = view.findViewById(R.id.tvOffice);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvEmailid = view.findViewById(R.id.tvEmailid);


        tvFacebook = view.findViewById(R.id.tvFacebook);
        tvInstagram = view.findViewById(R.id.tvInstagram);
        tvTwitter = view.findViewById(R.id.tvTwitter);
        tvLinkedIn = view.findViewById(R.id.tvLinkedIn);
        tvDob = view.findViewById(R.id.tvDob);
//        tvAdd = view.findViewById(R.id.edtDetailAddress);


        /**
         * editext
         */
        edtWhatsaap = view.findViewById(R.id.edtWhatsaap);
        edtWechat = view.findViewById(R.id.edtWechat);
        edtHome = view.findViewById(R.id.edtHome);
        edtOffice = view.findViewById(R.id.edtOffice);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtEmail = view.findViewById(R.id.edtEmail);


        edtFacebook = view.findViewById(R.id.edtFacebook);
        edtInstagram = view.findViewById(R.id.edtInstagram);
        edtTwitter = view.findViewById(R.id.edtTwitter);
        edtLinkedIn = view.findViewById(R.id.edtLinkedIn);
        edtDOB = view.findViewById(R.id.edtDOB);
        edtDetailAddress = view.findViewById(R.id.edtDetailAddress);

        text_input_layout = view.findViewById(R.id.text_input_layout);
        text_input_layout.setOnClickListener(this);

        edtDOB.setEnabled(true);
        ibAdd = view.findViewById(R.id.ibAdd);
        tvName = view.findViewById(R.id.tvName);
        tvNumber = view.findViewById(R.id.tvMobile);
        tvAddPhoto = view.findViewById(R.id.tvAddPhoto);


        ivprofileImage = view.findViewById(R.id.profileImage);


        edtEmail.setText(logInResponce.getEmail());
        tvEmailid.setText(logInResponce.getEmail());

        tvEdit = view.findViewById(R.id.ivEdit);
        tvEdit.setOnClickListener(this);

        ivCamera = view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        tvChangeLocation = view.findViewById(R.id.tvChangeLocation);
        tvChangeLocation.setOnClickListener(this);
        ibAdd.setOnClickListener(this);
        edtDOB.setOnClickListener(this);
        tvAddPhoto.setOnClickListener(this);


        setValue();

        if (isSave) {
            tvName.setEnabled(true);
            lnrEdt.setVisibility(View.VISIBLE);
            lnrVIewProfile.setVisibility(View.GONE);
            backroundTintSelected(tvName);

        } else {
            tvName.setEnabled(false);
//            tvName.setFocusable(false);
            lnrEdt.setVisibility(View.GONE);
            lnrVIewProfile.setVisibility(View.VISIBLE);
            backroundTintTrans(tvName);
        }
        edtHome.requestFocus();

//        setDisableFocus();

        return view;
    }

    void setValue() {
        edtMobile.setText(logInResponce.getMobile_number());
        tvMobile.setText(logInResponce.getMobile_number());

        tvEmailid.setText(logInResponce.getEmail());

        latitude = logInResponce.getLatitude();
        longitude = logInResponce.getLongitude();
        tvName.setText(logInResponce.getName());
        // tvNumber.setText(logInResponce.getMobile_number());

        mSocialList = logInResponce.getSocial();
        mContactList = logInResponce.getMobiles();
        if (logInResponce.getImage() != null && logInResponce.getImage().length() != 0) {
            tvAddPhoto.setVisibility(View.GONE);

            Glide.with(getActivity()).load(logInResponce.getImage()).into(ivprofileImage);
        } else {
            if (ivprofileImage.getDrawable() == null) {
                tvAddPhoto.setVisibility(View.VISIBLE);
            }
        }
        if (logInResponce.getDOB() != null) {
            edtDOB.setText(logInResponce.getDOB());
            tvDob.setText(logInResponce.getDOB());

        }
        if (logInResponce.getAddressDetails() != null) {
            edtDetailAddress.setText(logInResponce.getAddressDetails());
        }
        if (logInResponce.getWechat() != null) {
            edtWechat.setText(logInResponce.getWechat());
            tvWechat.setText(logInResponce.getWechat());

        }
        if (logInResponce.getWhatsapp() != null) {
            edtWhatsaap.setText(logInResponce.getWhatsapp());
            tvWhatsapp.setText(logInResponce.getWhatsapp());

        }
        if (logInResponce.getSocial() != null) {
            Social social = new Social();
            social = logInResponce.getSocial();
            if (social.getFacebook() != null) {
                edtFacebook.setText(social.getFacebook());
                tvFacebook.setText(logInResponce.getWhatsapp());

            }
            if (social.getTwitter() != null) {
                edtTwitter.setText(social.getTwitter());
                tvTwitter.setText(social.getTwitter());
            }
            if (social.getInstagram() != null) {
                edtInstagram.setText(social.getInstagram());
                tvInstagram.setText(social.getInstagram());
            }
            if (social.getInstagram() != null) {
                edtLinkedIn.setText(social.getInstagram());
                tvLinkedIn.setText(social.getInstagram());
            }
        }
        if (logInResponce.getMobiles() != null) {
            Mobiles mobiles = logInResponce.getMobiles();
            if (mobiles.getHome() != null) {
                edtHome.setText(mobiles.getHome());
                tvHome.setText(mobiles.getHome());
            }
            if (mobiles.getOffice() != null) {
                edtOffice.setText(mobiles.getOffice());
                tvOffice.setText(mobiles.getOffice());
            }
        }
        /**
         * set TextView
         */
        edtWhatsaap.requestFocus();


    }

    void initMap() {

// Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });
        // Gets to GoogleMap from the MapView and does initialization stuff
        //map = mapView.getMap();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        if (mMap != null) {
            if (latitude != null && latitude.length() != 0
                    && longitude != null && longitude.length() != 0) {
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //add lat long object to add marker
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
            } else {

            }
        }if (mMap1 != null) {
            if (latitude != null && latitude.length() != 0
                    && longitude != null && longitude.length() != 0) {
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //add lat long object to add marker
                mMap1.clear();
                mMap1.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
            } else {

            }
        }
    }

    void initMap1() {

// Gets the MapView from the XML layout and creates it
        mapView1 = (MapView) view.findViewById(R.id.map1);
        mapView1.onCreate(savedInstanceState);
      //  mapView1.getMapAsync(this);
        mapView1.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap1=googleMap;

//        // Add a marker in Sydney and move the camera
                //latlong object
                if (latitude != null && latitude.length() != 0
                        && longitude != null && longitude.length() != 0) {
                    LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    //add lat long object to add marker
                    mMap1.clear();
                    mMap1.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                    mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
                } else {

                }

            }
        });
        // Gets to GoogleMap from the MapView and does initialization stuff
        //map = mapView.getMap();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        if (mMap1 != null) {
            if (latitude != null && latitude.length() != 0
                    && longitude != null && longitude.length() != 0) {
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //add lat long object to add marker
                mMap1.clear();
                mMap1.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
            } else {

            }
        }
       // onMapReady(mMap);
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecent(ProfileMainProject logInResponce) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.profileUpdate(logInResponce);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecentImage(ProfileMainProject logInResponce) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDateImage), this);
            mAPIcall.profileUpdate(logInResponce);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_save, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Activity context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
        //latlong object
        if (latitude != null && latitude.length() != 0
                && longitude != null && longitude.length() != 0) {
            LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            //add lat long object to add marker
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
        } else {

        }

    }

    boolean isfirstTime = false;

    @Override
    public void onLocationChanged(Location location) {
        Log.e("Location", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());


        if (latitude == null || latitude.isEmpty()) {

            if (mMap != null) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                // mMap.clear();
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //add lat long object to add marker
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
//

            }
        }


//        if(mMap.Marker)

        if (mMap != null) {
            if (!isfirstTime) {
//                latitude = String.valueOf(location.getLatitude());
//                longitude = String.valueOf(location.getLongitude());
                // mMap.clear();
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //add lat long object to add marker
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
                isfirstTime = true;
                onResume();

            }
        }
//        onMapReady(mMap);
//       // mMap.clear();
//        LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//        //add lat long object to add marker
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
//        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//
//        if (mMap != null) {
//            mMap.clear();
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));
//        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    String[] socialArray = new String[]{"Facebook",
            "Whatsapp",
            "WeChat",
            "Instagram",
            "Twitter",
            "Skype",
            "Viber",
            "Snapchat",
            "Linkedin"};

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEdit:

//                if (!tvName.isEnabled()) {
//                    tvName.setEnabled(true);
//                    showKeyboard();
//                    tvName.requestFocus();
////                    tvName.requestFocus();
////                    tvName.requestFocus();
//
//                    tvEdit.setBackgroundResource(R.drawable.ic_check_mark_white);
//                } else {
                //isSaveFromActivity=true;
                save();
//                }

                break;

            case R.id.ivCamera:
                showCameraDialog();
                break;
            case R.id.ibAdd:

                notificationAdapter.notifyDataSetChanged();
                break;
            case R.id.btnAddMore:
                addsocial++;

                break;
            case R.id.tvChangeLocation:
                Intent intent = new Intent(mContext, EditLocationActivity.class);
                startActivityForResult(intent, REQ_CHANGE_LOC);
                break;
            case R.id.edtDOB:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


                break;
            case R.id.tvAddPhoto:
                showCameraDialog();

                break;
        }
    }

    private void save() {
        // new AppCommonMethods().hideKeyBoard(view);
        // lnrEdt.requestFocus();
        if (!isSave) {
            lnrVIewProfile.setVisibility(View.GONE);
            lnrEdt.setVisibility(View.VISIBLE);
            tvName.setEnabled(true);
            isSave = true;
            tvEdit.setText("Save");
            backroundTintSelected(tvName);
            tvName.setEnabled(true);
            tvName.requestFocus();

            new AppCommonMethods().showKeyBoard(tvName);

            return;

        }

        ProfileMainProject logInResponce = new ProfileMainProject();

        logInResponce.setMobile_number(this.logInResponce.getMobile_number());
        //logInResponce.set_id(this.logInResponce.get_id());
        logInResponce.setActive(this.logInResponce.getActive());
        logInResponce.setIs_POI(this.logInResponce.getIs_POI());
        if (isImageChange) {
            logInResponce.setImage(imageviewtoString(ivprofileImage));
        }
        logInResponce.setLatitude(latitude);
        logInResponce.setLongitude(longitude);
        logInResponce.setAddressTitle(edtDetailAddress.getText().toString());
        logInResponce.setAddressDetails(this.logInResponce.getAddressDetails());
        logInResponce.setName(tvName.getText().toString());
        logInResponce.setDOB(edtDOB.getText().toString());

        logInResponce.setWechat(edtWechat.getText().toString());
        logInResponce.setWhatsapp(edtWhatsaap.getText().toString());

        Social social = new Social();
        Mobiles mobiles = new Mobiles();
        mobiles.setHome(edtHome.getText().toString());
        mobiles.setOffice(edtOffice.getText().toString());

        logInResponce.setMobiles(mobiles);
        logInResponce.setEmail(edtEmail.getText().toString());
        social.setFacebook(edtFacebook.getText().toString());
        social.setTwitter(edtTwitter.getText().toString());
        social.setInstagram(edtInstagram.getText().toString());
        social.setLinkedin(edtLinkedIn.getText().toString());
        logInResponce.setSocial(social);


        loadRecent(logInResponce);


    }

    private void saveImage() {
        // new AppCommonMethods().hideKeyBoard(view);
        // lnrEdt.requestFocus();
        if (!isSave) {
            lnrVIewProfile.setVisibility(View.GONE);
            lnrEdt.setVisibility(View.VISIBLE);
            tvName.setEnabled(true);
            isSave = true;
            tvEdit.setText("Save");
            backroundTintSelected(tvName);
            tvName.setEnabled(true);


            return;

        }

        ProfileMainProject logInResponce = new ProfileMainProject();

        logInResponce.setMobile_number(this.logInResponce.getMobile_number());
        //logInResponce.set_id(this.logInResponce.get_id());
        logInResponce.setActive(this.logInResponce.getActive());
        logInResponce.setIs_POI(this.logInResponce.getIs_POI());
        if (isImageChange) {
            logInResponce.setImage(imageviewtoString(ivprofileImage));
        }
        logInResponce.setLatitude(latitude);
        logInResponce.setLongitude(longitude);
        logInResponce.setAddressTitle(edtDetailAddress.getText().toString());
        logInResponce.setAddressDetails(this.logInResponce.getAddressDetails());
        logInResponce.setName(tvName.getText().toString());
        logInResponce.setDOB(edtDOB.getText().toString());
        logInResponce.setEmail(edtEmail.getText().toString());

        logInResponce.setWechat(edtWechat.getText().toString());
        logInResponce.setWhatsapp(edtWhatsaap.getText().toString());

        Social social = new Social();
        Mobiles mobiles = new Mobiles();
        mobiles.setHome(edtHome.getText().toString());
        mobiles.setOffice(edtOffice.getText().toString());

        logInResponce.setMobiles(mobiles);
        social.setFacebook(edtFacebook.getText().toString());
        social.setTwitter(edtTwitter.getText().toString());
        social.setInstagram(edtInstagram.getText().toString());
        social.setLinkedin(edtLinkedIn.getText().toString());
        logInResponce.setSocial(social);


        loadRecentImage(logInResponce);


    }


    //    List<ContactModelProfilePOJO> getAllRecyclerDataCOntact() {
//        List<ContactModelProfilePOJO> mContactList1 = new ArrayList<>();
//
//        for (int i = 0; i <= mContactList.size(); i++) {
//
//            RecyclerView.ViewHolder view = rvContact.findViewHolderForAdapterPosition(i);
//            view = view.findViewById(R.id.edtMobile);
//            EditText editTextCode = view.findViewById(R.id.edtMobCode);
//            ContactModelProfilePOJO mContactModelProfilePOJO = new ContactModelProfilePOJO();
//            mContactModelProfilePOJO.setCode(editTextCode.getText().toString());
//            mContactModelProfilePOJO.setContact(editText.getText().toString());
//            mContactList1.add(mContactModelProfilePOJO);
//        }
//        return mContactList1;
//    }
//    List<Social> getAllRecyclerDataSocial() {
//        List<Social> mContactList1 = new ArrayList<>();
//
//        for (int i = 0; i <= mSocialList.size(); i++) {
//            View view = rvSocial.getChildAt(i);
//            EditText editText = view.findViewById(R.id.edtSocial);
//            Social mContactModelProfilePOJO = new Social();
//            mContactModelProfilePOJO.setLink(mContactModelProfilePOJO.getLink());
//            mContactModelProfilePOJO.setName(editText.getHint().toString());
//            mContactList1.add(mContactModelProfilePOJO);
//        }
//        return mContactList1;
//    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        if (flag.equals(mContext.getResources().getString(R.string.getProfile))) {
            JSONObject jsonObject = new JSONObject(result);

            ProfileMainProject logInResponce = new ProfileMainProject();
            logInResponce = new Gson().fromJson(result, ProfileMainProject.class);
            AppPrefs.putStringPref(AppConstants.PREFS_USER, result, mContext);
            //


            initController(savedInstanceState);
            // tvName.setFocusable(true);
            //setDisableFocus();
        }
        if (flag.equals(mContext.getResources().getString(R.string.loginDate))) {
            isSave = false;

            edtWhatsaap.requestFocus();
            backroundTintTrans(tvName);
            tvName.setEnabled(false);
            tvEdit.setText("Edit");

            if (mParam1.equals("profile")) {
                if (isSaveFromActivity) {
                    getActivity().onBackPressed();
                }
            }
            getProfile(logInResponce.getMobile_number());

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == TAKE_PICTURE && data != null && data.getData() != null) {
                try {
                    tvAddPhoto.setVisibility(View.GONE);
                    final Uri imageUri = data.getData();
                    if (imageUri == null) {
                        Toast.makeText(mContext, "Something went wrong,try again", Toast.LENGTH_LONG).show();
                        return;
                    }
                    final InputStream imageStream = mContext.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ivprofileImage.setImageBitmap(selectedImage);
                    String path = imageUri.getPath();
                    // "/mnt/sdcard/FileName.mp3"
                    Log.e("imgUri", imageUri.getEncodedPath() + "\n" + path);
                    Uri selectedImage1 = data.getData();
                    isImageChange = true;
                    saveImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == RESULT_LOAD_IMAGE) {
                try {
                    tvAddPhoto.setVisibility(View.GONE);
                    Uri selectedImage = imageUri;
                    if (selectedImage == null) {
                        Toast.makeText(mContext, "Something went wrong,try again", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ivprofileImage.setImageURI(selectedImage);
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = mContext.getContentResolver();
                    Bitmap bitmap;

                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pic.jpg");
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                    ivprofileImage.setImageBitmap(bitmap);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);


                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    isImageChange = true;
                    saveImage();
                    //                Toast.makeText(this, selectedImage.toString(),
                    //                        Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, "Failed to load", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                }
            }
            if (requestCode == REQ_CHANGE_LOC) {


                latitude = data.getExtras().getString(EditLocationActivity.NAME_LAT);
                longitude = data.getExtras().getString(EditLocationActivity.NAME_LAN);
                if (latitude != null && mMap != null) {
                    LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    mMap.clear();
                    //add lat long object to add marker
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Selected location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));


                    mMap1.clear();
                    //add lat long object to add marker
                    mMap1.addMarker(new MarkerOptions().position(sydney).title("Selected location"));
                    mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));
                }
                //Toast.makeText(mContext, "latitude " + latitude + " longitude : " + longitude, Toast.LENGTH_LONG).show();
                getProfile(logInResponce.getMobile_number());

            }
            if (requestCode == REQ_CAMERA) {
                openCamera();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Uri imageUri;
    private final int RESULT_LOAD_IMAGE = 100;
    private final int TAKE_PICTURE = 99;

    void showCameraDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.gallary_camera_dialog);
        TextView tvGallery = dialog.findViewById(R.id.tvGallery);
        TextView tvCamera = dialog.findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
                } else {

                    openCamera();
                    dialog.dismiss();
                }
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "in progress", Toast.LENGTH_SHORT).show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, TAKE_PICTURE);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        Uri photoURI = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".my.package.name.provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoURI);
        imageUri = photoURI;
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
        if (mapView1 != null) {
            mapView1.onResume();
        }
        //getContactList();
        if (!mParam1.equals("profile")) {
            LogInResponce logInResponce = new LogInResponce();
            logInResponce = new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER, mContext), LogInResponce.class);
            getProfile(logInResponce.getMobile_number());
        }
        super.onResume();
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void getProfile(String username) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.getProfile), this);
            mAPIcall.getProfile(username);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView1.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        mapView1.onLowMemory();
    }

    String imageviewtoString(ImageView imageView) {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageInByte = baos.toByteArray();
            return Base64.encodeToString(imageInByte, Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }

    }


    void setDisableFocus() {
        backroundTintTrans(tvName);
        backroundTintTrans(edtDOB);
        backroundTintTrans(edtWechat);
        backroundTintTrans(edtWhatsaap);

        backroundTintTrans(edtHome);
        backroundTintTrans(edtOffice);

        backroundTintTrans(edtFacebook);
        backroundTintTrans(edtTwitter);
        backroundTintTrans(edtLinkedIn);
        backroundTintTrans(edtInstagram);
        backroundTintTrans(edtMobile);
        backroundTintTrans(edtEmail);
        backroundTintTrans(edtDetailAddress);
    }

    void setEnableFocus() {
        backroundTintTrans(tvName);
        backroundTintTrans(edtDOB);
        backroundTintTrans(edtWechat);
        backroundTintTrans(edtWhatsaap);

        backroundTintTrans(edtHome);
        backroundTintTrans(edtOffice);

        backroundTintTrans(edtFacebook);
        backroundTintTrans(edtTwitter);
        backroundTintTrans(edtLinkedIn);
        backroundTintTrans(edtInstagram);
        backroundTintTrans(edtMobile);
        backroundTintTrans(edtEmail);
        backroundTintTrans(edtDetailAddress);
    }

    void backroundTintTrans(EditText editText) {
        DrawableCompat.setTint(editText.getBackground(), ContextCompat.getColor(mContext, R.color.tranns));
        //editText.setFocusable(false);
    }

    void backroundTintSelected(EditText editText) {
        DrawableCompat.setTint(editText.getBackground(), ContextCompat.getColor(mContext, R.color.selected_trans));
        // editText.setFocusable(true);
    }
}
