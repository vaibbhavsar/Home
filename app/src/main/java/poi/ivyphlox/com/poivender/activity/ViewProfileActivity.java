package poi.ivyphlox.com.poivender.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.fragments.ProfileContactInfoFragment;
import poi.ivyphlox.com.poivender.fragments.ProfilePhotoGallaryFragment;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {


    private LinearLayout llPhotoGallary;
    private LinearLayout llContactInfo;

    private View viewContactInfo,viewPhotoGallary;
    private MapView mapView;

    private Bundle savedInstanceState;

    private Context mContext=ViewProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState=savedInstanceState;

        setContentView(R.layout.activity_edit_profile);

        viewContactInfo=findViewById(R.id.viewContactInfo);
        viewPhotoGallary=findViewById(R.id.viewPhotoGallary);

        llPhotoGallary=findViewById(R.id.llPhotoGallary);
        llContactInfo=findViewById(R.id.llContactInfo);

        llPhotoGallary.setOnClickListener(this);
        llContactInfo.setOnClickListener(this);

        pushFragment(new ProfileContactInfoFragment(),false);
        viewContactInfo.setBackgroundResource(R.color.white);
        viewPhotoGallary.setBackgroundResource(R.color.colorPrimary);

//        initMap();
    }

    private void pushFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fram_container_profile, fragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.llPhotoGallary:
                pushFragment(new ProfilePhotoGallaryFragment(),false);

                viewContactInfo.setBackgroundResource(R.color.colorPrimary);
                viewPhotoGallary.setBackgroundResource(R.color.white);
                break;

            case R.id.llContactInfo:
                pushFragment(new ProfileContactInfoFragment(),false);
                viewContactInfo.setBackgroundResource(R.color.white);
                viewPhotoGallary.setBackgroundResource(R.color.colorPrimary);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
        super.onResume();
    }


}
