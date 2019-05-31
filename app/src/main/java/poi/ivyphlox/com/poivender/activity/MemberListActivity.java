package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.DBHelper;
import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.adapter.MemberAdapter;
import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.model.ContactModel;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

public class MemberListActivity extends AppCompatActivity implements View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {

    private RecyclerView rvContact;
    private MemberAdapter mContactAdapter;
    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private DBHelper dbHelper;
    private Context mContext=MemberListActivity.this;
    private FloatingActionButton fab;
    private List<Contact> contacts=new ArrayList<>();
    private String shareNumber;
    private LogInResponce logInResponce=new LogInResponce();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Members");
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(this);
        if(getIntent().getStringExtra("mobile")!=null)
        {
            shareNumber=getIntent().getStringExtra("mobile");
        }
        //getContactList();
        logInResponce=new Gson().fromJson(AppPrefs.getStringPref(AppConstants.PREFS_USER,mContext),LogInResponce.class);
        dbcheck();
    }
    void dbcheck()
    {
        dbHelper=new DBHelper(mContext);
        if(dbHelper.getMemberContacts().size()!=0) {
            contacts=dbHelper.getMemberContacts();
            setRecyclerView(contacts);
        }
        else {
            //  new AsynkTaskModel().execute();
        }
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void sendNotification(String sender_mobile, String receipient_mobile,String sharable_contact) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.sendNotification(sender_mobile,receipient_mobile,sharable_contact);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    void setRecyclerView(List<Contact> contactList) {
        rvContact = findViewById(R.id.rv_all_member);
        mContactAdapter = new MemberAdapter(mContext, contactList);

        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvContact.setLayoutManager(layoutManagerReview);
        rvContact.setAdapter(mContactAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab:
                for(Contact contact:contacts)
                {
                    if(contact.isSelected())
                    {
                        sendNotification(logInResponce.getMobile_number(),contact.getPhoneNumber(),shareNumber);
                    }
                }
                break;
        }
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {

        finish();
    }
}
