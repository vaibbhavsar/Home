package poi.ivyphlox.com.poivender.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.BussinessProfileModel;
import poi.ivyphlox.com.poivender.utils.GPSGetLocation;
import poi.ivyphlox.com.poivender.utils.GPSTracker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileContactInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileContactInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileContactInfoFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private BussinessProfileModel mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvWebsite;
    private TextView tvOpeningTime;
    private TextView tvAddress;

    public ProfileContactInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProfileContactInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileContactInfoFragment newInstance(BussinessProfileModel param1) {
        ProfileContactInfoFragment fragment = new ProfileContactInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_contact_info, container, false);
        mContext=getActivity();
        initMap(view,savedInstanceState);
        tvPhone=view.findViewById(R.id.tvPhone);
        tvAddress=view.findViewById(R.id.tvAddress);
        tvEmail=view.findViewById(R.id.tvEmail);
        tvOpeningTime=view.findViewById(R.id.tvOpeningTime);
        tvWebsite=view.findViewById(R.id.tvWebsite);

        setValues();
        return view;
    }

    private void setValues() {

        tvPhone.setText(mParam1.getBussinessMobile());
        tvAddress.setText(mParam1.getDetailsAddress());
        tvWebsite.setText(mParam1.getWebsite());
        tvOpeningTime.setText(mParam1.getOpenTime());
//        tvEmail.setText(mParam1.);
    }

    private GoogleMap mMap;
    private MapView mapView;

    void initMap(View view,Bundle savedInstanceState) {

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
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
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
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        GPSGetLocation gpsGetLocation = new GPSGetLocation(getActivity());
        GPSTracker gpsTracker = new GPSTracker(getActivity());
        gpsTracker = gpsGetLocation.getCurrentLatLongs();
        LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        if(mParam1.getLat()!=null&&!mParam1.getLat().equals(""))
        {
            sydney=new LatLng(Double.parseDouble(mParam1.getLat()),Double.parseDouble(mParam1.getLon()));
        }

        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));

//        latitude=gpsTracker.getLatitude()+"";
//        longitude=gpsTracker.getLongitude()+"";
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
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
        super.onResume();
    }

}
