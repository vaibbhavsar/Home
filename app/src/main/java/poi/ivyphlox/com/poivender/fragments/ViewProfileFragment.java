package poi.ivyphlox.com.poivender.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.ProfileActivity;
import poi.ivyphlox.com.poivender.activity.ViewProfileActivity;
import poi.ivyphlox.com.poivender.model.BussinessProfileModel;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfileFragment extends Fragment implements View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private LinearLayout llPhotoGallary;
    private LinearLayout llContactInfo;

    private View viewContactInfo,viewPhotoGallary;
    private MapView mapView;

    private Bundle savedInstanceState;

    private Context mContext;

    private View view;

    private TextView tvBusinessName;
    private TextView tvBusinessCat;
    private TextView tvEdit;
    private BussinessProfileModel logInResponce=new BussinessProfileModel();

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewProfileFragment newInstance(String param1, String param2) {
        ViewProfileFragment fragment = new ViewProfileFragment();
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
        view=inflater.inflate(R.layout.fragment_view_profile, container, false);

        mContext=getActivity();
        logInResponce=new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER,mContext)
                ,BussinessProfileModel.class);
        tvEdit=view.findViewById(R.id.tvEdit);
        tvBusinessCat=view.findViewById(R.id.tvBusinessCat);
        tvBusinessName=view.findViewById(R.id.tvBusinessName);
        this.savedInstanceState=savedInstanceState;
        viewContactInfo=view.findViewById(R.id.viewContactInfo);
        viewPhotoGallary=view.findViewById(R.id.viewPhotoGallary);

        llPhotoGallary=view.findViewById(R.id.llPhotoGallary);
        llContactInfo=view.findViewById(R.id.llContactInfo);

        llPhotoGallary.setOnClickListener(this);
        llContactInfo.setOnClickListener(this);

        pushFragment(ProfileContactInfoFragment.newInstance(logInResponce),false);
        viewContactInfo.setBackgroundResource(R.color.white);
        viewPhotoGallary.setBackgroundResource(R.color.colorPrimary);
        tvBusinessName.setText(logInResponce.getBussinessName());
        tvBusinessCat.setText(logInResponce.getBussinessCat());
        tvEdit.setOnClickListener(this);
        return view;
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


    private void pushFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fram_container_profile, fragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.llPhotoGallary:
                pushFragment( ProfilePhotoGallaryFragment.newInstance(logInResponce),false);

                viewContactInfo.setBackgroundResource(R.color.colorPrimary);
                viewPhotoGallary.setBackgroundResource(R.color.white);
                break;

            case R.id.llContactInfo:
                pushFragment(ProfileContactInfoFragment.newInstance(logInResponce),false);
                viewContactInfo.setBackgroundResource(R.color.white);
                viewPhotoGallary.setBackgroundResource(R.color.colorPrimary);
                break;


            case R.id.tvEdit:
                startActivity(new Intent(mContext,ProfileActivity.class));
                getActivity().finish();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView!=null)
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

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        if(flag.equalsIgnoreCase( getString(R.string.loginDate)))
        {

            Log.e("ProfileResponce",result);

            JSONObject jsonObject =new JSONObject(result);

            logInResponce=new Gson().fromJson(result,BussinessProfileModel.class);

            AppPrefs.putStringPref(AppConstants.PREFS_USER,result,mContext);
            AppPrefs.putBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,true,mContext);

            if(logInResponce.getBussinessName().equals(""))
            {
                startActivity(new Intent(mContext,ProfileActivity.class));
            }

            tvBusinessName.setText(logInResponce.getBussinessName());
            tvBusinessCat.setText(logInResponce.getBussinessCat());

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
}
