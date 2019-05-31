package poi.ivyphlox.com.poivender.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.adapter.ContactMainAdapter;
import poi.ivyphlox.com.poivender.adapter.ViewPagerAdpater;
import poi.ivyphlox.com.poivender.model.ContactModel;
import poi.ivyphlox.com.poivender.tabFragments.AllMemberFragment;
import poi.ivyphlox.com.poivender.tabFragments.InviteSocialFragment;
import poi.ivyphlox.com.poivender.tabFragments.MemberFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
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
    List<ContactModel> contactModels = new ArrayList<>();


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private ViewPagerAdpater adapter;

    private LinearLayout llAll,llMember,llInvite;
    private View viewAll,viewMember,viewInvite;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

//


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        llAll=view.findViewById(R.id.llall);
        llMember=view.findViewById(R.id.llMember);
        llInvite=view.findViewById(R.id.llInvite);
        viewAll=view.findViewById(R.id.viewAll);
        viewMember=view.findViewById(R.id.viewMember);
        viewInvite=view.findViewById(R.id.viewInvite);
        llAll.setOnClickListener(this);
        llMember.setOnClickListener(this);
        llInvite.setOnClickListener(this);
        init();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
      //  init();
    }


    void init() {
        resetView(viewAll);
        pushFragment(new AllMemberFragment(),false);
        //viewPager = (ViewPager) view.findViewById(R.id.viewpager);

//        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        adapter = new ViewPagerAdpater(getActivity().getSupportFragmentManager());
//
//        viewPager.setAdapter(adapter);
        //  setupViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdpater adapter = new ViewPagerAdpater(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }

    private void pushFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fram_container1, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(final int position) {
//            switch (position) {
//                case 0:
//                    AllMemberFragment tab1 = new AllMemberFragment();
//                    return tab1;
//                case 1:
//                    MemberFragment tab2 = new MemberFragment();
//                    return tab2;
//                case 2:
//                    InviteFragment tab3 = new InviteFragment();
//                    return tab3;
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(final Fragment fragment,final String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(final int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }


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
    public void onClick(View view) {
        if(view.getId()==R.id.llall){
            resetView(viewAll);
            pushFragment(new AllMemberFragment(),false);
        }if(view.getId()==R.id.llInvite){
            resetView(viewInvite);
            pushFragment(new InviteSocialFragment(),false);

        }if(view.getId()==R.id.llMember){
            resetView(viewMember);
            pushFragment(new MemberFragment(),false);
            Toast toast = Toast.makeText(getActivity(), "Contacts using the POI application", Toast.LENGTH_LONG);
//// Here we can set the Gravity to Top and Right
        toast.setGravity(Gravity.TOP | Gravity.CENTER,0,310);
        toast.show();

        }
    }

    void resetView(View view)
    {
        viewAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewInvite.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        viewMember.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view.setBackgroundColor(getResources().getColor(R.color.white));
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
