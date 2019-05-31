package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;

import static poi.ivyphlox.com.poivender.utils.AppConstants.MOBILE_NO;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, WLAPIcalls.OnAPICallCompleteListener {

    private EditText edtPassword;
    private EditText edConfirmtPassword;


    private TextView tvSubmit;

    private String mobileStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Reset Password");
        edtPassword = findViewById(R.id.edtPass);
        edConfirmtPassword = findViewById(R.id.edtConfPassword);

        if(getIntent().getStringExtra(MOBILE_NO)!=null)
        {
            mobileStr=getIntent().getStringExtra(MOBILE_NO);
        }

        tvSubmit = findViewById(R.id.tvsubmit);
        tvSubmit.setOnClickListener(this);


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
    public void onClick(View view) {
        if (view.getId() == R.id.tvsubmit) {
            if (isValid()) {
                setPassword(mobileStr, edtPassword.getText().toString());
            }
        }
    }
    private boolean isValid() {

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
    Context mContext = ResetPasswordActivity.this;

    /**
     * This function is used to get recently viewed data from server
     */
    private void setPassword(String username, String password) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.resetPassword(username, password);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {


        JSONObject jsonObject=new JSONObject(result);
        if(jsonObject.getString("message")!=null)
        {
            startActivity(new Intent(mContext,LoginActivity.class));
            finish();
        }


    }
}
