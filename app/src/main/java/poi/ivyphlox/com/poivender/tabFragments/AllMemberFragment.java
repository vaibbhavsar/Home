package poi.ivyphlox.com.poivender.tabFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.DBHelper;
import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.ContactDetailsActivity;
import poi.ivyphlox.com.poivender.adapter.ContactMainAdapter;
import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.model.ContactModel;

import static poi.ivyphlox.com.poivender.utils.AppCommonMethods.duplicateItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMemberFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMemberFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private RecyclerView rvContact;

    private ContactMainAdapter mContactAdapter;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private DBHelper dbHelper;


    private View view;

    private LinearLayout llBottomInvitation;
    private LinearLayout llsms;
    private LinearLayout llmail;

    public int positionItem;

    public AllMemberFragment() {
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
    public static AllMemberFragment newInstance(String param1, String param2) {
        AllMemberFragment fragment = new AllMemberFragment();
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

        Log.e("Screen","AllMembers");
        view= inflater.inflate(R.layout.fragment_all_members,container,false);

        llBottomInvitation=view.findViewById(R.id.llBottomInvitation);
        llmail=view.findViewById(R.id.llmail);
        llsms=view.findViewById(R.id.llsms);
        llmail.setOnClickListener(this);
        llsms.setOnClickListener(this);
        dbHelper=new DBHelper(getActivity());
        dbcheck();
        return view;
    }

    void dbcheck()
    {
        if(dbHelper.getAllContacts().size()!=0) {
            setRecyclerView(view,dbHelper.getAllContacts());
        }
        else {
          //  new AsynkTaskModel().execute();
        }
    }



    void setRecyclerView(View view, final List<Contact> contactList) {
        rvContact = view.findViewById(R.id.rv_all_member);
        mContactAdapter = new ContactMainAdapter(getActivity(), contactList,
                new ContactMainAdapter.FollowersDetailsAdapterListener() {
            @Override
            public void iconTextViewOnClick(View v, int position) {
              //  llBottomInvitation.setVisibility(View.VISIBLE);
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
        1);

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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.llsms:
                composeSmsMessage(getString(R.string.message_contain),
                        dbHelper.getAllContacts().get(positionItem).getPhoneNumber());
                break;

            case R.id.llmail:
                sendEmail( dbHelper.getAllContacts().get(positionItem).getEmail());
                break;
        }
    }

    public void composeSmsMessage(String message, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"+phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    protected void sendEmail(String to) {
        Log.i("Send email", "");
        String[] TO = {to};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "POI App ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_contain));

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
            Log.i("Finished ", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
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



    public class AsynkTaskModel extends AsyncTask<String, String ,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            getContactList();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dbcheck();

        }
    }
    private static final String TAG ="NavigationActivity" ;


    /**
     * get COntact List from mobile contacts
     */
    private void getContactList() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                        ContactModel contactModel=new ContactModel();
                        contactModel.setName(name);
                        contactModel.setPhone(phoneNo);

                        contactModels.add(contactModel);

                       // dbHelper.addContact(mContact);
                    }

                    pCur.close();
                }
            }

            //removeDuplicates(contactModels);
            for(ContactModel item:duplicateItem(contactModels))
            {
                ContactModel contactModel1=(ContactModel)item;
                //Contact mContact=new Contact(contactModel1.getName(),contactModel1.getPhone(),"false");

                dbHelper.addContact(item);
            }

        }
        if(cur!=null){
            cur.close();
        }
    }



}
