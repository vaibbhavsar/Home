package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;

import static poi.ivyphlox.com.poivender.utils.AppConstants.IS_FROM_REGISTER;
import static poi.ivyphlox.com.poivender.utils.AppConstants.MOBILE_NO;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSubmit;
    Context mContext=OTPActivity.this;
    boolean isREgiter=false;
    EditText edtOtp;

    private String mobileStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Mobile Verification");
        if(getIntent().getBooleanExtra(IS_FROM_REGISTER,false)){
            isREgiter=true;
        }
        if(getIntent().getStringExtra(MOBILE_NO)!=null)
        {
            mobileStr=getIntent().getStringExtra(MOBILE_NO);
        }
        edtOtp=findViewById(R.id.edtOtp);
        tvSubmit=findViewById(R.id.tvSubmit);
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
        switch (view.getId())
        {
            case R.id.tvSubmit:
                if(isValid()) {
                    if (isREgiter) {
                        AppPrefs.putBooleanPref(AppConstants.KEY_USER_IS_LOGEDIN,true,mContext);
                        Intent intent=new Intent(OTPActivity.this, NavigationActivity.class);
                        intent.putExtra(MOBILE_NO,mobileStr);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent=new Intent(OTPActivity.this, ResetPasswordActivity.class);
                        intent.putExtra(MOBILE_NO,mobileStr);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        Toast.makeText(mContext,"Coming Soon",Toast.LENGTH_SHORT);

                    }
                }
                else {
                    Toast.makeText(mContext,"Please Enter Correct OTP",Toast.LENGTH_SHORT);
                }
                break;
        }
    }
    boolean isValid(){

        if(edtOtp.getText().toString().equalsIgnoreCase("1234"))
        {


            return true;
        }else {
            Toast.makeText(mContext,"Please Enter Correct OTP",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
