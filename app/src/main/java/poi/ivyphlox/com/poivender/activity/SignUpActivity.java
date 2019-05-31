package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

import static poi.ivyphlox.com.poivender.utils.AppConstants.IS_FROM_REGISTER;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {

    private EditText edtPassword;
    private EditText edConfirmtPassword;

    private EditText edtMobile;
    private EditText edtMobCode;

    private TextView tvSubmit;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtMobile = findViewById(R.id.edtMobile);
        edtMobCode = findViewById(R.id.edtMobCode);
        edtPassword = findViewById(R.id.edtPass);
        edConfirmtPassword = findViewById(R.id.edtConfPassword);

        tvSubmit = findViewById(R.id.tvsubmit);
        tvSubmit.setOnClickListener(this);

        tvSignIn=findViewById(R.id.tvSignIn);
        tvSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvsubmit) {
            if (isValid()) {
                loadRecent(edtMobCode.getText().toString()+edtMobile.getText().toString(), edtPassword.getText().toString());
            }
        }
        if (view.getId() == R.id.tvSignIn) {
            onBackPressed();
        }
    }

    private Context mContext = SignUpActivity.this;

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecent(String username, String password) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.RegisterRequest(username, password);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
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
        if (edtMobile.getText().toString().length() < 6) {

            edtMobile.setError("Enter Valid Mobile No");
            return false;
        }
        if (edtMobile.getText().toString().length() == 0) {
            edtMobile.setError("Enter Mobile No");

            return false;
        }
        if (edtPassword.getText().toString().length() == 0) {
            edtPassword.setError("Enter Password No");

        }
        if (edConfirmtPassword.getText().toString().length() == 0) {
            edtPassword.setError("Enter Confirm Password No");
            return false;

        }
        if (!edtPassword.getText().toString().equals(edConfirmtPassword.getText().toString())) {
            edConfirmtPassword.setError("Entered Password / Confirm password not Matched");
            return false;
        }

        return true;
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {

        if(flag.equalsIgnoreCase(mContext.getString(R.string.loginDate)))
        {
            JSONObject jsonObject=new JSONObject(result);
            if(jsonObject.getString("messsage").equalsIgnoreCase("Registered successfully"))
            {

                LogInResponce logInResponce=new LogInResponce();
                logInResponce.setMobile_number(edtMobCode.getText().toString()+edtMobile.getText().toString());
                AppPrefs.putStringPref(AppConstants.PREFS_USER,new Gson().toJson(logInResponce),mContext);

                Toast.makeText(mContext,jsonObject.getString("messsage"),Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(mContext,OTPActivity.class);
                intent.putExtra(IS_FROM_REGISTER,true);

                startActivity(intent);

            }else {
                Toast.makeText(mContext,jsonObject.getString("messsage"),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
