package poi.ivyphlox.com.poivender.main;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


//import android.support.multidex.MultiDex;
//import com.crashlytics.android.Crashlytics;
//import com.testfairy.TestFairy;
//
//import io.fabric.sdk.android.Fabric;


/**
 * Created by admin on 10/21/2016.
 */

public class WLAppController extends Application {

    public static final String TAG = WLAppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static WLAppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
//        TestFairy.begin(this, "e601c3090c4b54c9798ca346d3c140e01bcb7394");
//        TestFairy.begin(this, "653804991c7f4cb618d5cfce02a46c99e227db4a");
        mInstance = this;
    }

    public static synchronized WLAppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
     //   MultiDex.install(this);
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
