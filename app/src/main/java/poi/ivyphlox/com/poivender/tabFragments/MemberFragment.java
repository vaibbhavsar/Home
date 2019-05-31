package poi.ivyphlox.com.poivender.tabFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.DBHelper;
import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.ContactDetailsActivity;
import poi.ivyphlox.com.poivender.adapter.ContactMainAdapter;
import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.model.ContactModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemberFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private RecyclerView rvContact;

    private ContactMainAdapter mContactAdapter;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private DBHelper dbHelper;



    private LinearLayout llBottomInvitation;
    private LinearLayout llsms;
    private LinearLayout llmail;

    public int positionItem;

    private View view;
    public MemberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberFragment newInstance(String param1, String param2) {
        MemberFragment fragment = new MemberFragment();
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
        Log.e("Screen","Members");
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_all_members,container,false);

        dbHelper=new DBHelper(getActivity());

        if(dbHelper.getMemberContacts().size()!=0)
        {
            setRecyclerView(view,dbHelper.getMemberContacts());
        }
//
        return view;
    }
    void setRecyclerView(View view, final List<Contact> contactList) {
        rvContact = view.findViewById(R.id.rv_all_member);
        mContactAdapter = new ContactMainAdapter(getActivity(), contactList, new ContactMainAdapter.FollowersDetailsAdapterListener() {
            @Override
            public void iconTextViewOnClick(View v, int position) {
                llBottomInvitation.setVisibility(View.VISIBLE);
                positionItem=position;
            }

            @Override
            public void iconImageViewOnClick(View v, int position) {
                startActivity(new Intent(getActivity(),
                        ContactDetailsActivity.class)
                        .putExtra("mobile",
                                contactList.get(position).getPhoneNumber())
                        .putExtra("name",
                                contactList.get(position).getName()));
            }
        },
        3);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContact.setLayoutManager(layoutManagerReview);
        rvContact.setAdapter(mContactAdapter);
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
