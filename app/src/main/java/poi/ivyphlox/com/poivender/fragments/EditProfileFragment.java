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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.model.ProfileMainProject;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment implements WLAPIcalls.OnAPICallCompleteListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private View view;

    private ImageView ivProfile;
    private ImageView ivImgAdd;
    private EditText edtBusinesName;
    private EditText edtMobile;
    private EditText edtWebsiteLIsnk;
    private EditText edtDetailAddress;
    private EditText edtOpenTIme;
    private EditText edtCloseTime;
    private Spinner spnrCatBusiness;
    private Spinner spnrCity;
    private Spinner spnrArea;
    private Button btnSave;
    private Bundle savedInstanceState;
    private GoogleMap mMap;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mContext=getActivity();
        ivProfile=view.findViewById(R.id.ivProfile);
        this.savedInstanceState=savedInstanceState;
        initController();
        return view;
    }



    private void initController() {

        edtBusinesName=view.findViewById(R.id.edtBusinesName);
        edtCloseTime=view.findViewById(R.id.edtCloseTime);
        edtDetailAddress=view.findViewById(R.id.edtDetailAddress);
        edtMobile=view.findViewById(R.id.edtMobile);
        edtOpenTIme=view.findViewById(R.id.edtOpenTIme);
        edtWebsiteLIsnk=view.findViewById(R.id.edtWebsiteLIsnk);
        ivImgAdd=view.findViewById(R.id.ivImgAdd);
        ivProfile=view.findViewById(R.id.ivProfile);
        btnSave=view.findViewById(R.id.btnSave);

        setSpnrCatBusiness();
        setSpnrArea();
        setSpnrCity();
        initMap();
    }

    private MapView mapView;

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

    String[] country = { "India", "USA", "China", "Japan", "Other"};
    void setSpnrCatBusiness()
    {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrCatBusiness = (Spinner) view.findViewById(R.id.spnrCatBusiness);
        spnrCatBusiness.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrCatBusiness.setAdapter(aa);
    }

    void setSpnrArea()
    {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrArea = (Spinner) view.findViewById(R.id.spnrArea);
        spnrArea.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrArea.setAdapter(aa);
    }

    void setSpnrCity()
    {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrCity = (Spinner) view.findViewById(R.id.spnrCity);
        spnrCity.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrCity.setAdapter(aa);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecentImage(ProfileMainProject logInResponce) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, mContext.getString(R.string.loginDateImage), this);
            mAPIcall.profileUpdate(logInResponce);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        if(flag.equals(mContext.getString(R.string.loginDateImage)))
        {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.clear();
        LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));
//        // Add a marker in Sydney and move the camera
        //latlong object

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
}
