package poi.ivyphlox.com.poivender.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import poi.ivyphlox.com.poivender.R;
import poi.ivyphlox.com.poivender.activity.EditLocationActivity;
import poi.ivyphlox.com.poivender.adapter.ImageListAdapter;
import poi.ivyphlox.com.poivender.model.BussinessProfileModel;
import poi.ivyphlox.com.poivender.model.ImageModelBussiness;
import poi.ivyphlox.com.poivender.model.LogInResponce;
import poi.ivyphlox.com.poivender.model.ProfileMainProject;
import poi.ivyphlox.com.poivender.network.WLAPIcalls;
import poi.ivyphlox.com.poivender.service.ClickListener;
import poi.ivyphlox.com.poivender.service.RecyclerTouchListener;
import poi.ivyphlox.com.poivender.utils.AppCommonMethods;
import poi.ivyphlox.com.poivender.utils.AppConstants;
import poi.ivyphlox.com.poivender.utils.AppPrefs;
import poi.ivyphlox.com.poivender.utils.GPSGetLocation;
import poi.ivyphlox.com.poivender.utils.GPSTracker;

import static android.app.Activity.RESULT_OK;
import static poi.ivyphlox.com.poivender.utils.AppCommonMethods.datePicker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment implements WLAPIcalls.OnAPICallCompleteListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private View view;

    private ImageView ivProfile;
    private ImageView ivImgAdd;
    private EditText edtBusinesName;
    private EditText edtMobile;
    private EditText edtWebsiteLIsnk;
    private EditText edtDetailAddress;
    private EditText edtOpenTIme;
    private EditText edtCloseTime;
    private Spinner spnrCatBusiness;
    private Spinner spnrCity;
    private Spinner spnrArea;
    private Button btnSave;
    private LinearLayout llChangeLocation;
    private Bundle savedInstanceState;
    private GoogleMap mMap;
    public int REQ_CHANGE_LOC = 10;


    private List<String> stringPhotos;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mContext = getActivity();
        ivProfile = view.findViewById(R.id.ivProfile);
        this.savedInstanceState = savedInstanceState;
        initController();
        return view;
    }


    private void initController() {

        stringPhotos = new ArrayList<>();
        edtBusinesName = view.findViewById(R.id.edtBusinesName);
        edtCloseTime = view.findViewById(R.id.edtCloseTime);
        edtDetailAddress = view.findViewById(R.id.edtDetailAddress);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtOpenTIme = view.findViewById(R.id.edtOpenTIme);
        edtWebsiteLIsnk = view.findViewById(R.id.edtWebsiteLIsnk);
        ivImgAdd = view.findViewById(R.id.ivImgAdd);
        ivProfile = view.findViewById(R.id.ivProfile);
        btnSave = view.findViewById(R.id.btnSave);
        llChangeLocation = view.findViewById(R.id.llChangeLocation);

        btnSave.setOnClickListener(this);
        llChangeLocation.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        edtOpenTIme.setOnClickListener(this);
        edtCloseTime.setOnClickListener(this);
        ivImgAdd.setOnClickListener(this);

        setSpnrCatBusiness();
        setSpnrArea();
        setSpnrCity();
        initMap();
    }

    private MapView mapView;

    void initMap() {

// Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });
        // Gets to GoogleMap from the MapView and does initialization stuff
        //map = mapView.getMap();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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

    String[] businessCat = {"Electronics and telecom", "Arts, crafts, and collectibles", "Beauty and fragrances", "Books and magazines", "Entertainment and media"};
    String[] area = {"Deira", "Bur Dubai", "Oud Metha"};
    String[] city = {"Al Ain", "Hatta", "Jebel Ali", "Khorfakkan"};

    void setSpnrCatBusiness() {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrCatBusiness = (Spinner) view.findViewById(R.id.spnrCatBusiness);
        spnrCatBusiness.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, businessCat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrCatBusiness.setAdapter(aa);
    }

    void setSpnrArea() {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrArea = (Spinner) view.findViewById(R.id.spnrArea);
        spnrArea.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, area);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrArea.setAdapter(aa);
    }

    void setSpnrCity() {
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spnrCity = (Spinner) view.findViewById(R.id.spnrCity);
        spnrCity.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnrCity.setAdapter(aa);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This function is used to get recently viewed data from server
     */
    private void loadRecentImage(ProfileMainProject logInResponce) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, mContext.getString(R.string.loginDateImage), this);
            mAPIcall.profileUpdate(logInResponce);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAPICallCompleteListner(Object item, String flag, String result) throws JSONException {
        if (flag.equals(mContext.getString(R.string.loginDateImage))) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        GPSGetLocation gpsGetLocation = new GPSGetLocation(getActivity());
        GPSTracker gpsTracker = new GPSTracker(getActivity());
        gpsTracker = gpsGetLocation.getCurrentLatLongs();
        LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.5f));

        latitude=gpsTracker.getLatitude()+"";
        longitude=gpsTracker.getLongitude()+"";
//        // Add a marker in Sydney and move the camera
        //latlong object

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
//                showCameraDialog();

                onSave();

                break;
            case R.id.ivProfile:
                showCameraDialog();
                break;

            case R.id.llChangeLocation:
                Intent intent = new Intent(mContext, EditLocationActivity.class);
                startActivityForResult(intent, REQ_CHANGE_LOC);
                break;
            case R.id.edtOpenTIme:
                datePicker(mContext, edtOpenTIme);
                break;
            case R.id.edtCloseTime:
                datePicker(mContext, edtCloseTime);
                break;
            case R.id.ivImgAdd:
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1,"Select Picture"), PICK_IMAGE_MULTIPLE);

//                Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setType("image/*");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            break;


        }
    }


    String imageviewtoString(ImageView imageView) {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageInByte = baos.toByteArray();
            return Base64.encodeToString(imageInByte, Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }

    }

    private void onSave() {

        BussinessProfileModel bussinessProfileModel=new BussinessProfileModel();
        bussinessProfileModel.setProfileImage(imageviewtoString(ivProfile));
        bussinessProfileModel.setBussinessName(edtBusinesName.getText().toString());
        bussinessProfileModel.setArea((String)spnrArea.getSelectedItem());
        bussinessProfileModel.setBussinessCat((String)spnrCatBusiness.getSelectedItem());
        bussinessProfileModel.setCity((String)spnrCity.getSelectedItem());
        bussinessProfileModel.setBussinessMobile(edtMobile.getText().toString());
        bussinessProfileModel.setClosedTime(edtCloseTime.getText().toString());
        bussinessProfileModel.setOpenTime(edtOpenTIme.getText().toString());
        bussinessProfileModel.setDetailsAddress(edtDetailAddress.getText().toString());
        bussinessProfileModel.setLat(latitude);
        bussinessProfileModel.setLon(longitude);
        bussinessProfileModel.setWebsite(edtWebsiteLIsnk.getText().toString());
        List<String> imgList=new ArrayList<>();
        imgList.add("base64");
        imgList.add("base64");
        imgList.add("base64");
        bussinessProfileModel.setImageList(imgList);

        Log.e("updateBussinessProfile",
                new Gson().toJson(bussinessProfileModel));
        updateBussinessProfile(bussinessProfileModel);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private Uri imageUri;
    private final int RESULT_LOAD_IMAGE = 100;
    private final int TAKE_PICTURE = 99;
    private static final int REQ_CAMERA = 66;


    private String latitude;
    private String longitude;

    void showCameraDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.gallary_camera_dialog);
        TextView tvGallery = dialog.findViewById(R.id.tvGallery);
        TextView tvCamera = dialog.findViewById(R.id.tvCamera);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
                } else {

                    openCamera();
                    dialog.dismiss();
                }
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "in progress", Toast.LENGTH_SHORT).show();

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, TAKE_PICTURE);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        Uri photoURI = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".my.package.name.provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoURI);
        imageUri = photoURI;
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    private RecyclerView recyclerView;
    private ImageListAdapter madapter;

    void setRecyclerView(final View view, List<ImageModelBussiness> accountMainPOJOS) {
        if (accountMainPOJOS.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            return;
        }
        recyclerView = view.findViewById(R.id.rvPhotos);
        madapter = new ImageListAdapter(accountMainPOJOS, recyclerView,
                mContext);
        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(madapter);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                    Log.e("LOG_TAG", "Selected Images1 "+ imageEncoded);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        List<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn,
                                    null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        List<ImageModelBussiness> mArrayUriString = new ArrayList<ImageModelBussiness>();

                        for(Uri uri: mArrayUri)
                        {
                            ImageModelBussiness imageModelBussiness=new ImageModelBussiness();
                            Log.e("LOG_TAG", "Selected Images " +uri.toString());
                            imageModelBussiness.setUri(uri);
                            mArrayUriString.add(imageModelBussiness);
                        }

                        Log.e("LOG_TAG", "Selected Images1 " + mArrayUri.size());
                        setRecyclerView(view,mArrayUriString);
                    }
                }
            }
//            else {
//                Toast.makeText(getActivity(), "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
            if (requestCode == TAKE_PICTURE && data != null && data.getData() != null) {
                try {
                    //tvAddPhoto.setVisibility(View.GONE);
                    final Uri imageUri = data.getData();
                    if (imageUri == null) {
                        Toast.makeText(mContext, "Something went wrong,try again", Toast.LENGTH_LONG).show();
                        return;
                    }
                    final InputStream imageStream = mContext.getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ivProfile.setImageBitmap(selectedImage);
                    String path = imageUri.getPath();
                    // "/mnt/sdcard/FileName.mp3"
                    Log.e("imgUri", imageUri.getEncodedPath() + "\n" + path);
                    Uri selectedImage1 = data.getData();
//                    isImageChange = true;
//                    saveImage();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == RESULT_LOAD_IMAGE) {
                try {
                    // tvAddPhoto.setVisibility(View.GONE);
                    Uri selectedImage = imageUri;
                    if (selectedImage == null) {
                        Toast.makeText(mContext, "Something went wrong,try again", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ivProfile.setImageURI(selectedImage);
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = mContext.getContentResolver();
                    Bitmap bitmap;

                    bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Pic.jpg");
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                    ivProfile.setImageBitmap(bitmap);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);


                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    // isImageChange = true;
                    //saveImage();
                    //                Toast.makeText(this, selectedImage.toString(),
                    //                        Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mContext, "Failed to load", Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                }
            }
            if (requestCode == REQ_CHANGE_LOC) {


                latitude = data.getExtras().getString(EditLocationActivity.NAME_LAT);
                longitude = data.getExtras().getString(EditLocationActivity.NAME_LAN);
                if (latitude != null && mMap != null) {
                    LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    mMap.clear();
                    //add lat long object to add marker
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Selected location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));


                }

                //Toast.makeText(mContext, "latitude " + latitude + " longitude : " + longitude, Toast.LENGTH_LONG).show();
                //getProfile(logInResponce.getMobile_number());

            }
            if (requestCode == REQ_CAMERA) {
                openCamera();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This function is used to get recently viewed data from server
     */
    private void updateBussinessProfile(BussinessProfileModel logInResponce) {
        if (new AppCommonMethods(mContext).isNetworkAvailable()) {
            WLAPIcalls mAPIcall = new WLAPIcalls(mContext, getString(R.string.loginDate), this);
            mAPIcall.updateBussinessProfile(logInResponce);
        } else {
            Toast.makeText(mContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }


}
