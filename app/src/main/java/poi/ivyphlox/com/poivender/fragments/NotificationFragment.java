package poi.ivyphlox.com.poivender.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.adapter.NotificationAdapter;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.model.NotificationModel;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment implements WLAPIcalls.OnAPICallCompleteListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;

    private RecyclerView rvNotification;
    private TextView tvNomoreContacts;
    private Context mContext;
    private List<NotificationModel> notificationModelList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    LogInResponce logInResponce = new LogInResponce();

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        mContext = getActivity();
        //setRecyclerView();
        logInResponce = new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER, mContext), LogInResponce.class);

        loadNotification(logInResponce.getMobile_number());
        return view;
    }

    void setRecyclerView() {
        tvNomoreContacts=view.findViewById(R.id.tvNotification);
        if(notificationModelList.size()==0)
        {
            tvNomoreContacts.setVisibility(View.VISIBLE);
        }else {
            tvNomoreContacts.setVisibility(View.GONE);

        }
        rvNotification = view.findViewById(R.id.rvNotification);
        notificationAdapter = new NotificationAdapter(mContext, notificationModelList);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManagerReview);
        rvNotification.setAdapter(notificationAdapter);
    }

    /**
     * get Contact data from shared Prefference using Gson
     */
    public ArrayList<NotificationModel> getNotificationList(String str) {
        try {
            Gson gson = new Gson();
            ArrayList<NotificationModel> yourSerializableObject = new ArrayList<NotificationModel>();
            NotificationModel[] yourSerializableObject1 = gson.fromJson(str, NotificationModel[].class);
            yourSerializableObject = new ArrayList<>(Arrays.asList(yourSerializableObject1));
            return yourSerializableObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadNotification(String mobileno) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.getNotification(mobileno);
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

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        notificationModelList = getNotificationList(result);
        setRecyclerView();
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
