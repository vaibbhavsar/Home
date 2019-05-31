package poi.ivyphlox.com.poivender.tabFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.InviteActivity;
import poi.ivyphlox.com.poivender.adapter.ContactMainAdapter;

import static poi.ivyphlox.com.poivender.utils.AppConstants.KEY_SOCIAL;
import static poi.ivyphlox.com.poivender.utils.AppConstants.KEY_USER_MSG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InviteSocialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InviteSocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteSocialFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;

    private LinearLayout lnrMsg;
    private LinearLayout lnrWhatsapp;
    private LinearLayout lnrEmail;
    private LinearLayout lnrFacebook;
    private LinearLayout lnrTwitter;
    private LinearLayout lnrWeChat;
    private Context mContext;

    public InviteSocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviteSocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviteSocialFragment newInstance(String param1, String param2) {
        InviteSocialFragment fragment = new InviteSocialFragment();
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
        mContext=getActivity();
        view=inflater.inflate(R.layout.fragment_invite_social, container, false);
        initContacts();
        return view;
    }
    void initContacts()
    {
        lnrMsg=view.findViewById(R.id.lnrMsg);
        lnrWhatsapp=view.findViewById(R.id.lnrWhatsapp);
        lnrEmail=view.findViewById(R.id.lnrEmail);
        lnrFacebook=view.findViewById(R.id.lnrFacebook);
        lnrTwitter=view.findViewById(R.id.lnrTwitter);
        lnrWeChat=view.findViewById(R.id.lnrWeChat);

        lnrMsg.setOnClickListener(this);
        lnrWhatsapp.setOnClickListener(this);
        lnrFacebook.setOnClickListener(this);
        lnrTwitter.setOnClickListener(this);
        lnrWeChat.setOnClickListener(this);
        lnrEmail.setOnClickListener(this);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.lnrMsg:
                startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_MSG));
                break;
            case R.id.lnrWhatsapp:
               // startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_WHTSAPP));
                onwhatsapp();
                break;
            case R.id.lnrFacebook:
//                startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_FACEBOOK));
                onFacebookMessanger();
                break;
            case R.id.lnrTwitter:
//                startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_WHTSAPP));
                onTwitter();
                break;
            case R.id.lnrWeChat:
//                startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_WE_CHAT));
                onWeChat();
                break;
            case R.id.lnrEmail:
//                startActivity(new Intent(mContext,InviteActivity.class).putExtra(KEY_SOCIAL,KEY_USER_EMAIL));
                sendEmail("");
                break;
        }

    }

    void onwhatsapp()
    {
//        String mobile="";
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+mobile+"&text="+getString(R.string.message_contain)+""));
//        startActivity(browserIntent);
//        Intent i = new Intent(Intent.ACTION_SENDTO);
//        i.putExtra(Intent.EXTRA_TEXT,
//                        getString(R.string.message_contain));
//        i.setType("text/plain");
//        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
//        i.putExtra(Intent.EXTRA_SUBJECT, "Invite");
//        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_contain));
//        startActivity(i);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.message_contain));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        try {
            startActivity(sendIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext,"Please Install Whatsapp Messenger", Toast.LENGTH_LONG).show();
        }
    }

    void onFacebookMessanger()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.message_contain));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");
        try {
            startActivity(sendIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
        }
    }
    void onTwitter()
    {
       Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.twitter.android");
        if (intent != null) {
            // The application exists
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.twitter.android");

            shareIntent.putExtra(Intent.EXTRA_TITLE, "POI");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_contain));
            // Start the specific social application
            mContext.startActivity(shareIntent);
        } else {
            // The application does not exist
            // Open GooglePlay or use the default system picker
            Toast.makeText(mContext,"Please Install Twitter",Toast.LENGTH_SHORT).show();
        }
    }
    void onWeChat()
    {
        Intent sendIntent1 = new Intent();
        sendIntent1.setAction(Intent.ACTION_SEND);
        sendIntent1
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.message_contain));
        sendIntent1.setType("text/plain");
        sendIntent1.setPackage("com.tencent.mm");
        try {
            startActivity(sendIntent1);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext,"Please Install Wechat Messenger", Toast.LENGTH_LONG).show();
        }
    }

    public void sendSMS1(String message, String phoneNumber)
    {
        for(String item: ContactMainAdapter.contactSelected)
        {
            phoneNumber=phoneNumber+","+item;
        }
        Uri uri = Uri.parse("smsto:"+phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", message);
        startActivity(it);
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
        emailIntent.setPackage("com.google.android.gm");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            Log.i("Finished ", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
