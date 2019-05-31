package poi.ivyphlox.com.poivender.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import poi.ivyphlox.com.poivender.R;

import static poi.ivyphlox.com.poivender.utils.AppConstants.MOBILE_NO;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSubmit;
    private EditText edtMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Forget Password");
        edtMobile=findViewById(R.id.edtMobile);
        tvSubmit=findViewById(R.id.tvSumbit);
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
            case R.id.tvSumbit:
                if(isValid()) {
                    Intent intent = new Intent(ForgetPasswordActivity.this, OTPActivity.class);
                    intent.putExtra(MOBILE_NO, edtMobile.getText().toString());
                    startActivity(intent);
                }
                break;
        }
    }

    private boolean isValid() {
        if(edtMobile.getText().toString().length()<10)
        {

            edtMobile.setError("Please Enter Valid Mobile No");

            return false;
        }
        return true;
    }
}
