package poi.ivyphlox.com.poivender;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import poi.ivyphlox.com.poivender.activity.NavigationActivity;
import poi.ivyphlox.com.poivender.model.ContactModel;
import poi.ivyphlox.com.poivender.model.ContactResponce;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.RuntimePermissionActivity;

public class LoadContactsActivity extends RuntimePermissionActivity implements WLAPIcalls.OnAPICallCompleteListener {

    private ArrayList<ContactModel> contactModels = new ArrayList<>();
    private DBHelper dbHelper;
    private Context mContext = LoadContactsActivity.this;

    private static final int REQUEST_PERMISSIONS = 100;
    private TextView mTextMessage;
    boolean isPermission = false;
    int fromValue=0;
    int toValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_contacts);
        dbHelper=new DBHelper(this);
        permission();
    }
    void permission() {
        LoadContactsActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS},
                R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        isPermission=true;
        dbcheck();

    }

    void dbcheck() {
        if (dbHelper.getAllContacts().size() != 0) {
            // setRecyclerView(view,dbHelper.getAllContacts());
        } else {
          //  new AsynkTaskModel().execute();
            new FetchContacts().execute();
        }
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {


        toValue++;

        List<ContactResponce> contactList = getConfig(result);
        for (ContactResponce contact : contactList) {
            if (Boolean.valueOf(contact.getIsPOI())) {

                Log.e("true",new Gson().toJson(contact));
                dbHelper.updateContact(contact);
            }
        }
        if(fromValue==toValue)
        {
            Intent intent=new Intent(mContext,NavigationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * get Contact data from shared Prefference using Gson
     */
    public ArrayList<ContactResponce> getConfig(String str) {
        try {
            Gson gson = new Gson();
            ArrayList<ContactResponce> yourSerializableObject = new ArrayList<ContactResponce>();
            ContactResponce[] yourSerializableObject1 = gson.fromJson(str, ContactResponce[].class);
            yourSerializableObject = new ArrayList<>(Arrays.asList(yourSerializableObject1));
            return yourSerializableObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    private static final String TAG = "NavigationActivity";

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadMembers(List<ContactResponce> username) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            fromValue++;
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.getMembers(username);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }


    private class FetchContacts extends AsyncTask<Void, Void, ArrayList<ContactModel>> {

        private final String DISPLAY_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;

        private final String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";

        private final String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);

        @SuppressLint("InlinedApi")
        private final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        @Override
        protected ArrayList<ContactModel> doInBackground(Void... params) {
            try {
                ArrayList<ContactModel> contacts = new ArrayList<>();

                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, FILTER, null, ORDER);
                if (cursor != null && cursor.moveToFirst()) {

                    do {
                        // get the contact's information
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        // get the user's email address
                        String email = null;
                        Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        if (ce != null && ce.moveToFirst()) {
                            email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            ce.close();
                        }

                        // get the user's phone number
                        String phone = null;
                        if (hasPhone > 0) {
                            Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            if (cp != null && cp.moveToFirst()) {
                                phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                cp.close();
                            }
                        }

                        // if the user user has an email or phone then add it to contacts
                        if ((!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                                && !email.equalsIgnoreCase(name)) || (!TextUtils.isEmpty(phone))) {
                            if(phone!=null&&!phone.isEmpty()) {
                                ContactModel contact = new ContactModel();
                                contact.setName(name);
                                contact.setEmail(email);
                                contact.setPhone(phone.replace(" ", ""));
                                contacts.add(contact);
                                Log.i(TAG, "Name: " + name);
                                Log.i(TAG, "Phone Number: " + phone);
                            }

                        }

                    } while (cursor.moveToNext());

                    // clean up cursor
                    cursor.close();
                }

                return contacts;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<ContactModel> contacts) {
            if (contacts != null) {

                for(ContactModel contactModel:contacts)
                {
                    dbHelper.addContact(contactModel);
                }
                List<ContactResponce> tempContact = new ArrayList<>();

                for (int i = 0; i < contacts.size(); i++) {
                    ContactModel contact = contacts.get(i);
                    if (tempContact.size() < 100) {
                        ContactResponce contactResponce=new ContactResponce();
                        contactResponce.setIs_POI("false");
                        contactResponce.setMobile_number(contact.getPhone());
                        contactResponce.setName(contact.getName());
                        tempContact.add(contactResponce);
                    } else {

                        loadMembers(tempContact);
                        tempContact = new ArrayList<>();
                    }
                }
                loadMembers(tempContact);
            } else {
                // show failure
                // syncFailed();
            }
        }
    }
}
