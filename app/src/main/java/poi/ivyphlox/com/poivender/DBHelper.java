package poi.ivyphlox.com.poivender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.model.Contact;
import poi.ivyphlox.com.poivender.model.ContactModel;
import poi.ivyphlox.com.poivender.model.ContactResponce;

/**
 * Created by vaibhavbhavsar on 18/03/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_STATUS + " TEXT ,"
                + KEY_EMAIL + " TEXT ,"
                + KEY_PH_NO + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void deleteDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Add Contact In DB
      * @param contact
     */
    public void addContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhone()); // Contact Phone
        values.put(KEY_STATUS, "false"); // Contact Phone
        values.put(KEY_EMAIL, contact.getEmail()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return contact
        return contact;
    }

    /**
     * // code to get all contacts in a list view
     * @return
     */
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                contact.set_status(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        Log.e("Json",new Gson().toJson(contactList));
        // return contact list
        return contactList;
    }
    // code to get all contacts in a list view

    /**
     * get All NonMember
     * @return
     */
    public List<Contact> getInvitContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(cursor.getColumnIndex(KEY_STATUS)).equals("false")) {
                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                    contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                    contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
                    contact.set_status(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    /**
     * get All NonMember
     * @return
     */
    public List<Contact> getMemberContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(cursor.getColumnIndex(KEY_STATUS)).equals("true")) {
                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                    contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                    contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
                    contact.set_status(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(ContactResponce contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, contact.getIsPOI());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_PH_NO + " = ?",
                new String[]{String.valueOf(contact.getMobile_number())});
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
