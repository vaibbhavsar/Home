package poi.ivyphlox.com.poivender.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.fragments.EditProfileFragment;
import poi.ivyphlox.com.poivender.fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_center);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Edit Profile");
        pushFragment(EditProfileFragment.newInstance("profile",""),false);
    }

    private void pushFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fram_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProfileActivity.this,NavigationActivity.class);
        startActivity(intent);
        finish();
    }
}
