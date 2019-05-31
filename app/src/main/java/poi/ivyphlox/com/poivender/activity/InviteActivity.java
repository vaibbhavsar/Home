package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.DBHelper;
import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.adapter.ContactMainInviteAdapter;
import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.tabFragments.InviteFragment;

import static poi.ivyphlox.com.poivender.utils.AppConstants.KEY_SOCIAL;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener,ContactMainInviteAdapter.RecyclerViewClickListener {

    private String mobile;
    private String name;

    private LinearLayout lnrMsg;
    private LinearLayout lnrWhatsapp;
    private LinearLayout lnrEmail;
    private LinearLayout lnrFacebook;
    private LinearLayout lnrTwitter;
    private LinearLayout lnrWeChat;
    private Context mContext = InviteActivity.this;


    //TODO:New Screens
    private InviteFragment.OnFragmentInteractionListener mListener;

    private RecyclerView rvContact;

    private ContactMainInviteAdapter mContactAdapter;
    private List<Contact> contactModels = new ArrayList<>();
    private DBHelper dbHelper;


    private LinearLayout llBottomInvitation;
    private LinearLayout llsms;
    private LinearLayout llmail;

    public int positionItem;


    private View view;


    private String socialArg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Invite");


        if (getIntent().getStringExtra("mobile") != null) {
            mobile = getIntent().getStringExtra("mobile");
        }
        if (getIntent().getStringExtra("name") != null) {
            name = getIntent().getStringExtra("name");
        }


        llBottomInvitation = findViewById(R.id.llBottomInvitation);
        llmail = findViewById(R.id.llmail);
        llsms = findViewById(R.id.llsms);
        llmail.setOnClickListener(this);
        llsms.setOnClickListener(this);
        dbHelper = new DBHelper(mContext);
        dbcheck();
        getArgument();
    }

    void getArgument() {
        socialArg = getIntent().getStringExtra(KEY_SOCIAL);
    }

    void dbcheck() {
        if (dbHelper.getAllContacts().size() != 0) {
            setRecyclerView( dbHelper.getInvitContacts());
        } else {
            // new AsynkTaskModel().execute();
        }
    }

    void setRecyclerView(final List<Contact> contactList) {
        rvContact = findViewById(R.id.rv_all_member);
        contactModels=contactList;
        mContactAdapter = new ContactMainInviteAdapter(mContext, contactList, new ContactMainInviteAdapter.FollowersDetailsAdapterListener() {

            @Override
            public void iconTextViewOnClick(View v, int position) {

            }

            @Override
            public void iconImageViewOnClick(View v, int position) {

            }
        },4);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvContact.setLayoutManager(layoutManagerReview);
        rvContact.setAdapter(mContactAdapter);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.llsms:
                String strNumberList="";
                int i=0;
                for(Contact contact:contactModels)
                {

                    if(contact.isSelected())
                    {
                        if(i==0){
                            strNumberList=contact.getPhoneNumber();
                            i++;
                        }else {
                            strNumberList=strNumberList+"; "+contact.getPhoneNumber();

                        }
                    }
                }
                sendSMS1(getString(R.string.message_contain),strNumberList);
                break;

            case R.id.llmail:
                sendEmail(dbHelper.getAllContacts().get(positionItem).getEmail());
                break;
        }


    }

    void onwhatsapp() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobile + "&text=" + getString(R.string.message_contain) + ""));
        startActivity(browserIntent);
    }

    void onFacebookMessanger() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.message_contain));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");
        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
        }
    }

    void onTwitter() {
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
            Toast.makeText(mContext, "Please Install Twitter", Toast.LENGTH_SHORT).show();
        }
    }

    void onWeChat() {
        Intent sendIntent1 = new Intent();
        sendIntent1.setAction(Intent.ACTION_SEND);
        sendIntent1
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.message_contain));
        sendIntent1.setType("text/plain");
        sendIntent1.setPackage("com.tencent.mm");
        try {
            startActivity(sendIntent1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "Please Install Wechat Messenger", Toast.LENGTH_LONG).show();
        }
    }

    public void sendSMS1(String message, String phoneNumber) {
//        for (String item : ContactMainAdapter.contactSelected) {
//            phoneNumber = phoneNumber + "," + item;
//        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void recyclerViewListClicked(int position) {
        if (contactModels.get(position).isSelected()){
            contactModels.get(position).setSelected(false);
        }
        else {
            contactModels.get(position).setSelected(true);
        }
        mContactAdapter.notifyDataSetChanged();

    }
}
