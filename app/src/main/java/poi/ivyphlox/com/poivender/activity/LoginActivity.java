package poi.ivyphlox.com.poivender.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;
import poi.ivyphlox.com.poivender.utils.RuntimePermissionActivity;

public class LoginActivity extends RuntimePermissionActivity implements View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {

    private EditText edtPassword;

    private EditText edtMobile;
    private EditText edtMobCode;

    private TextView tvSubmit;
    private TextView tvSignUp;
    private TextView tvForgetPassword;
    private Context mContext=LoginActivity.this;
    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(AppPrefs.getBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,mContext))
        {
            startActivity(new Intent(mContext,NavigationActivity.class));
            finish();
        }
        edtMobCode = findViewById(R.id.edtMobCode);

        edtMobile=findViewById(R.id.edtMobile);
        edtPassword=findViewById(R.id.edtPassword);
        tvSubmit = findViewById(R.id.tvSignIn);
        tvSubmit.setOnClickListener(this);

        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);

        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvForgetPassword.setOnClickListener(this);
//        edtMobile=findViewById(R.id.edt)edt
        permission();

    }

    void permission() {
        LoginActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS},
                R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }
    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecent(String username,String password) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.loginRequest(username,password);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvSignIn) {
            if(isValid()){
                loadRecent(edtMobCode.getText().toString()+edtMobile.getText().toString(),edtPassword.getText().toString());
            }
        }
        if(view.getId()== R.id.tvSignUp)
        {
            startActivity(new Intent(this, SignUpActivity.class));

        }
        if(view.getId()== R.id.tvForgetPassword)
        {
            startActivity(new Intent(this, ForgetPasswordActivity.class));

        }

    }

    private boolean isValid() {
        if (edtMobCode.getText().toString().length() == 0) {
            edtMobCode.setError("Enter Mobile Code");
            return false;
        }
        if (!edtMobCode.getText().toString().contains("+") ){
            edtMobCode.setError("Enter Valid Country Code");
            return false;
        }
        if(edtMobile.getText().toString().length()<10)
        {
            edtMobile.setError("Enter Valid mobile No");
            return false;
        }
        if(edtMobile.getText().toString().length()==0)
        {
            edtMobile.setError("Enter Mobile No");
            return false;
        }
        if(edtPassword.getText().toString().length()==0)
        {
            edtPassword.setError("Enter Password No");
            return false;
        }

        return true;
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {

        try {
            if(flag.equalsIgnoreCase(mContext.getString(R.string.loginDate))){

                JSONObject jsonObject =new JSONObject(result);


                AppPrefs.putStringPref(AppConstants.PREFS_USER,result,mContext);
                AppPrefs.putBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,true,mContext);
                startActivity(new Intent(LoginActivity.this,NavigationActivity.class));
                finish();


            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext,"Mobile no and Password is not Matched",Toast.LENGTH_LONG).show();
        }

    }
}
