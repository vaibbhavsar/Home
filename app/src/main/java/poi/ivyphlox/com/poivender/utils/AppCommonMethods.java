package poi.ivyphlox.com.poivender.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.ContactModel;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by Morya on 3/21/2018.
 */

public class AppCommonMethods implements AppConstants {
    private static Context mContext;
    private boolean mIsLogOn = true;
    private String TAG = "Commonmethods";

    public AppCommonMethods() {
    }

    public AppCommonMethods(Context context) {
        mContext = context;
    }

    public void LOG(int type, String tag, String message) {
        if (mIsLogOn) {
            switch (type) {
                case 0:
                    Log.d(tag, message);
                    break;
                case 1:
                    Log.e(tag, message);
                    break;
                case 2:
                    Log.i(tag, message);
                    break;
                case 3:
                    Log.v(tag, message);
                    break;
            }
        }
    }

    public static int getDiffYears(String first, String last) {
        try {
            Calendar a = getCalendar(stringToDate(last));
            Calendar b = getCalendar(stringToDate(first));
            int diff = b.get(YEAR) - a.get(YEAR);
            if (a.get(MONTH) > b.get(MONTH) ||
                    (a.get(MONTH) == b.get(MONTH)
                            && a.get(DATE) > b.get(DATE))) {
                diff--;
            }
            return diff;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }



    /**
     * <b>public void showMessage</b>
     * <p>This function is used to show message dialog to user. It will b removed once user click on ok</p>
     *
     * @param message - message to be displaed
     * @return instance on alertdialog
     */

    public AlertDialog showMessage(String message) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialogBuilder.setTitle(mContext.getString(R.string.app_name));
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            if (!((Activity) mContext).isFinishing()) {
                alertDialog.show();
            }
            return alertDialog;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static Date stringToDate(String strDate) {
        String sDate1 = strDate;
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTimeAgo(String currentFormat, String value) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currentFormat);
            Date past = format.parse(value);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

            if (seconds < 60) {
                return seconds + " seconds ago";
            } else if (minutes < 60) {
                return minutes + " minutes ago";
            } else if (hours < 24) {
                return hours + " hours ago";
            } else {
                return days + " days ago";
            }
        } catch (Exception j) {
            j.printStackTrace();
        }
        return "";
    }

    /*public AlertDialog showLogoutAlert(String message, final AppCompatActivity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Please Login Again");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SGKS_AppSettings.putBooleanPref(PREF_IS_LOGIN, false, activity);
                //Clearing all states
                SGKS_AppSettings.putStringPref(PREF_CREATE_FAMILY_TREE_JSON_STRING, "", mContext);
                SGKS_AppSettings.putStringPref(PREF_UPDATE_FAMILY_TREE_JSON_STRING, "", mContext);
                SGKS_AppSettings.putIntPref(PREF_CREATE_FAMILY_CURRENT_STATE, 0, mContext);
                SGKS_AppSettings.putIntPref(PREF_UPDATE_FAMILY_CURRENT_STATE, 0, mContext);
                Intent intentLogin = new Intent(activity, SGKS_LoginActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intentLogin);
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                dialog.cancel();
                Toast.makeText(mContext, "" + R.string.logout_success_toast, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }*/

   /* public AlertDialog showLogoutAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(R.string.logout);
        alertDialogBuilder.setMessage(R.string.logout_message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                AppPrefs.putBooleanPref(IS_LOGIN, false, mContext);
                //Clearing all states
//                SGKS_AppSettings.putStringPref(PREF_CREATE_FAMILY_TREE_JSON_STRING, "", context);
//                SGKS_AppSettings.putStringPref(PREF_UPDATE_FAMILY_TREE_JSON_STRING, "", context);
//                SGKS_AppSettings.putIntPref(PREF_CREATE_FAMILY_CURRENT_STATE, 0, context);
//                SGKS_AppSettings.putIntPref(PREF_UPDATE_FAMILY_CURRENT_STATE, 0, context);
                Intent intentLogin = new Intent(mContext, LoginActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentLogin);
                ((Activity) mContext).finish();
                ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                dialog.cancel();
                Toast.makeText(mContext, "" + mContext.getString(R.string.logout_success_toast), Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public AlertDialog showDataDiscardAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(R.string.warning);
        alertDialogBuilder.setMessage(R.string.data_discard_message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intentStops = new Intent(mContext, StopListActivity.class);
                intentStops.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentStops);
                ((Activity) mContext).finish();
                ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                dialog.cancel();
//                Toast.makeText(mContext, "discard Data", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }
*/

    /**
     * hide keyboard
     *
     * @param view
     */
    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Show keyboard explicitly
     *
     * @param view
     */
    public void showKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public String convertDateFormat(String currentDateFormat,
                                    String expectedDateFormat,
                                    String currentDate) {
        try {
            SimpleDateFormat read = new SimpleDateFormat(currentDateFormat);
            SimpleDateFormat write = new SimpleDateFormat(expectedDateFormat);
            String str;
            str = write.format(read.parse(currentDate));
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertTimeFormat(String currentTimeFormat, String expectedTimeFormat, String time) {
        if (time == null) {
            return "";
        }
        String newTime = null;
        SimpleDateFormat actual = new SimpleDateFormat(currentTimeFormat);
        SimpleDateFormat target = new SimpleDateFormat(expectedTimeFormat);
        Date date;
        try {
            date = actual.parse(time);
            newTime = target.format(date);
            return newTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertTimeFormat1(String currentTimeFormat, String expectedTimeFormat, String time) {
        if (time == null) {
            return "";
        }
        String newTime = null;
        SimpleDateFormat actual = new SimpleDateFormat(currentTimeFormat);
        SimpleDateFormat target = new SimpleDateFormat(expectedTimeFormat);
        Date date;
        try {
            date = actual.parse(time);
            newTime = target.format(date);
            return newTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())) {

        }
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * This function used to get UNIX time stamp
     *
     * @return
     */
    public String getTimeStamp() {
        return ((Long) (System.currentTimeMillis() / 1000)).toString();
    }

    /**
     * This function is used to convert unix time stamp to expected date format
     *
     * @param timestamp      - unix timestamp to convert
     * @param expectedFormat - date format which we expects
     * @return -
     */
    public String convertTimestampToDate(String timestamp, String expectedFormat) {
        Date date = new Date(Long.parseLong(timestamp) * 1000L); // *1000 is to convert seconds to milliseconds
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        Log.e(TAG, timestamp + " UNIX timestamp converted = " + formattedDate);
        return formattedDate;
    }

    /**
     * This function is used to convert unix time stamp to expected date format
     *
     * @param expectedFormat - date format which we expects
     * @return -
     */
    public static String getDate(String expectedFormat) {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp); // *1000 is to convert seconds to milliseconds
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        // Log.e(TAG, timestamp + " UNIX timestamp converted = " + formattedDate);
        return formattedDate;
    }

    public static String getDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        //      SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        Log.e("AppCommonMethods", timestamp + " UNIX timestamp converted = " + formattedDate);
        return formattedDate;
    }


    public int getAmount(int pieces, int denomination) {
        return pieces * denomination;
    }

    /*public void showToastMessage(Context context, String str) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null, false);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 30);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        TextView text = (TextView) layout.findViewById(R.id.toastMessage);
        text.setText(str);
        toast.show();
    }*/
    public static void showSnackBar(View paramView, String paramString) {
        Snackbar.make(paramView, paramString, 0).show();
    }

    public static void showToast(Context mContext, String paramString) {
        final Toast toast = Toast.makeText(mContext, paramString, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

//    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
//        int Radius = 6371;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
//
//        return Radius * c;
//    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    public static List<ContactModel> removeDuplicatesContacts(List<ContactModel> contactModels)
    {

        List<Integer> positions=new ArrayList<>();
        for(int i=0;i<contactModels.size();i++)
        {
            for(int j=0; j<contactModels.size();j++)
            {
                if(i!=j)
                {
                    if(contactModels.get(i).getPhone().equals(contactModels.get(j).getPhone()))
                    {
                        positions.add(i);
                    }
                }
            }
        }
        for(int i=positions.size()-1;i>-1;i--)
        {
            contactModels.remove(positions.get(i));
        }

        return contactModels;
    }


    public static List<ContactModel> duplicateItem(List<ContactModel> contactModelList)
    {
        Map<String, ContactModel> map = new HashMap<String, ContactModel>();
        for (ContactModel blog : contactModelList) {
            String key = blog.getPhone();
            if (!map.containsKey(key)) {
                map.put(key, blog);
            }
        }
        Collection<ContactModel> uniqueBlogs =map.values();
        Log.e("Removed Duplicate",uniqueBlogs.toString());
        List<ContactModel> unicContacts=new ArrayList<>(uniqueBlogs);

        Collections.sort(unicContacts, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                ContactModel map1=(ContactModel)o1;
                ContactModel map2=(ContactModel)o2;
                String s1=map1.getName();
                String s2=map2.getName();
                return s1.compareTo(s2);
            }
        });

        return unicContacts;

    }

}