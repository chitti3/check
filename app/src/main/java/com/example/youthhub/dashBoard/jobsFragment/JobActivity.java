package com.example.youthhub.dashBoard.jobsFragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhub.R;
import com.example.youthhub.myjobs.MyJobsActivity;
import com.example.youthhub.resModel.CommonRes;
import com.example.youthhub.resModel.jobs.JobInfo;
import com.example.youthhub.resModel.jobs.JobView;
import com.example.youthhub.resModel.jobs.applyJob.GetFileUpload;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.AppUtils;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.ImageFilePath;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobActivity extends AppCompatActivity implements JobApplyDialog.OnPassDataListener, OnMapReadyCallback {

    private static final String TAG = "JobActivity";
    @BindView(R.id.job_image)
    ImageView jobImage;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.back_constrain)
    ConstraintLayout backConstrain;
    @BindView(R.id.job_view1)
    View jobView1;
    @BindView(R.id.jobs_title)
    TextView jobsTitle;
    @BindView(R.id.pin_unpin_img)
    ImageView pinUnpinImg;
    @BindView(R.id.post_by)
    TextView postBy;
    @BindView(R.id.job_region)
    TextView jobRegion;
    @BindView(R.id.jobs_type)
    TextView jobsType;
    @BindView(R.id.jobs_date)
    TextView jobsDate;
    @BindView(R.id.job_view2)
    View jobView2;
    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.txt2)
    TextView txt2;
    @BindView(R.id.save_job_btn)
    Button saveJobBtn;
    @BindView(R.id.applied_btn)
    Button appliedBtn;
    @BindView(R.id.job_view3)
    View jobView3;
    @BindView(R.id.job_desc_txt)
    TextView jobDescTxt;
    @BindView(R.id.jobs_desc)
    TextView jobsDesc;
    @BindView(R.id.job_key_res_txt)
    TextView jobKeyResTxt;
    @BindView(R.id.job_key_res_desc)
    TextView jobKeyResDesc;
    @BindView(R.id.job_loc_txt)
    TextView jobLocTxt;
    @BindView(R.id.job_loc_desc)
    TextView jobLocDesc;

    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.jobs_main)
    ConstraintLayout jobs_main;
    @BindView(R.id.map_vie)
    ImageView map_image;


    String jobCode;
    JobInfo jobInfo;

    JobApplyDialog jobApplyDialog;
    Activity activity;

    String fileName = null;
    private final int NEW_OPT_CARE  = 1;
    LatLng latd = new LatLng(10.903018,76.995224);


    GoogleMap map;
    // MapView mapViw;
    String lat ;
    String lang ;
   // MapView mapViw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        activity = this;
        ButterKnife.bind(activity);
        callTypeFace();

       // mapViw = findViewById(R.id.map_view);







        if (getIntent() != null) {
            jobCode = getIntent().getStringExtra("JobCode");
            call_job_api();

        }
        jobApplyDialog = new JobApplyDialog(activity);



     /*   if (mapViw != null) {
            mapViw.onCreate(null);
            mapViw.onResume();
            mapViw.getMapAsync(this);

        }*/


    }

    private void call_job_api() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            Call<JobView> jobViewCall = ApiClient.getApiInterface().getJob(Constants.getApiKey(activity),Constants.getAccessKey(activity), Constants.getToken(activity), jobCode);
            jobViewCall.enqueue(new Callback<JobView>() {
                @Override
                public void onResponse(@NonNull Call<JobView> call, @NonNull Response<JobView> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                System.out.println("===jocode"+jobCode);
                                updateUi(response.body());
                                jobs_main.setVisibility(View.VISIBLE);
                            } else {
                                MyToast.normalMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " JobView", response.toString());
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<JobView> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " JobView", t.toString());
                    Loader.showLoad(activity, false);
                }
            });
        }
    }

    private void updateUi(JobView jobView) {

        jobInfo = jobView.getJobData().getJobInfo();


        if(jobInfo.getJobsLogoPath().contains("youthhub")) {
            Glide.with(activity)
                    .load(Constants.getLoadGlide(activity,jobInfo.getJobsLogoPath()))
                    .apply(AppUtils.getRequestOptionWithoutOverride())
                    .into(jobImage);
        }else {
            Glide.with(activity)
                    .load(jobInfo.getJobsLogoPath())
                    .apply(AppUtils.getRequestOptionWithoutOverride())
                    .into(jobImage);
        }

        jobsTitle.setText(jobInfo.getJmTitle());
        postBy.setText(jobInfo.getPostBy());
        jobRegion.setText(jobInfo.getRegionName());
        jobsType.setText(jobInfo.getJmJtTypeName());
        jobsDate.setText(jobInfo.getJmStartDate());
        txt1.setText(jobInfo.getIcaName());
        txt2.setText(jobInfo.getIscName());
        lat = jobInfo.getJloLattitude();
        lang = jobInfo.getJloLongitude();


  //      if(lat.isEmpty() && lang.isEmpty()){

            map_image.setVisibility(View.VISIBLE);
          //  mapViw.setVisibility(View.GONE);
        /*}else {
            map_image.setVisibility(View.GONE);*/
            //mapViw.setVisibility(View.VISIBLE);

         //   map.setMapType(NEW_OPT_CARE);
            /*   if(map!=null) {
                   map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lang))).title("").snippet(""));
                   CameraPosition liberty = CameraPosition.builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lang))).zoom(16).bearing(0).tilt(45).build();
                   map.moveCamera(CameraUpdateFactory.newCameraPosition(liberty));
               }*/
      //  }



        System.out.println("=========getthelocation" +jobInfo.getJloLattitude()+"uuu"+jobInfo.getJloLongitude());

        System.out.println("=========getthelocationstring" +lat+"uuu"+lang);


        jobsDesc.setText(jobInfo.getJmFullDescription());
        if(!jobInfo.getJmRequirementDetails().isEmpty()){
            jobKeyResDesc.setText(jobInfo.getJmRequirementDetails());
            jobKeyResTxt.setVisibility(View.VISIBLE);
            jobKeyResDesc.setVisibility(View.VISIBLE);
        }else {
            jobKeyResDesc.setText(getResources().getString(R.string.no_responsibility));
            jobKeyResTxt.setVisibility(View.GONE);
            jobKeyResDesc.setVisibility(View.GONE);
        }
        jobLocDesc.setText(jobInfo.getJloAddress());

        switch (jobInfo.getIsSave()) {
            case 0:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_unpin_bookmark));
                break;
            case 1:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_pin_bookmark));
                break;
            default:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_unpin_bookmark));
                break;
        }

        switch (jobInfo.getIsApplied()) {
            case 0:
                appliedBtn.setText("Apply");
                break;
            case 1:
                appliedBtn.setText("Applied");
                break;
            default:
                appliedBtn.setText("Apply");
                break;
        }
    }

    private void callTypeFace() {
        jobsTitle.setTypeface(FontTypeFace.fontBold(activity));
        postBy.setTypeface(FontTypeFace.fontSemiBold(activity));
        jobRegion.setTypeface(FontTypeFace.fontSemiBold(activity));
        appliedBtn.setTypeface(FontTypeFace.fontBold(activity));
        jobDescTxt.setTypeface(FontTypeFace.fontBold(activity));
        jobKeyResTxt.setTypeface(FontTypeFace.fontBold(activity));
        jobLocTxt.setTypeface(FontTypeFace.fontBold(activity));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.activity_slide_down);
    }

    @OnClick({R.id.back_constrain, R.id.back, R.id.pin_unpin_img, R.id.applied_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_constrain:
            case R.id.back:
                onBackPressed();
                break;
            case R.id.pin_unpin_img:
                callIsSaveApi(jobInfo);
                break;
            case R.id.applied_btn:
                if(jobCode!=null) {
                    if(jobInfo.getIsApplied()==0) {
                        jobApplyDialog.newInstance(jobCode);
                        jobApplyDialog.setOnPassDataListener(this);
                        jobApplyDialog.show();
                    }else {
                        MyJobsActivity myJobsActivity=new MyJobsActivity();
                        FragmentManager fm = getSupportFragmentManager();
                        Fragment currentFragment = fm.findFragmentById(R.id.frame_layout);
                        if (currentFragment != null && !myJobsActivity.getClass().toString().equals(currentFragment.getTag())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.frame_layout, myJobsActivity, myJobsActivity.getClass().toString()) // add and tag the new fragment
                                    .commit();
                        }
                     /*   FragmentManager fragmentManager=getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                        MyJobsActivity myJobsActivity=new MyJobsActivity();
                        fragmentTransaction.replace(android.R.id.icon_frame,myJobsActivity);
                        fragmentTransaction.commit();
                        overridePendingTransition(R.anim.activity_slide_up, R.anim.stay);*/
                    }
                }
                break;
        }
    }

    private void callIsSaveApi(JobInfo jobInfo) {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            Loader.showLoad(activity, true);
            String jobCode = jobInfo.getJmCode();
            final String isSave;
            if (jobInfo.getIsSave() == 0) {
                isSave = "1";
            } else {
                isSave = "0";
            }
            Call<CommonRes> resCall = ApiClient.getApiInterface().getIsSave(Constants.getApiKey(activity),Constants.getAccessKey(activity), Constants.getToken(activity), jobCode, isSave);
            resCall.enqueue(new Callback<CommonRes>() {
                @Override
                public void onResponse(@NonNull Call<CommonRes> call, @NonNull Response<CommonRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                updateIsSave(Integer.valueOf(isSave));
                            } else {
                                MyToast.errorMessage(response.body().getMessage(), activity);
                            }
                        }
                    } else {
                        Log.d(Constants.failureResponse + " JobIsSave", response.toString());
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<CommonRes> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse + " JobIsSave", t.toString());
                    Loader.showLoad(activity, false);
                }
            });
        }
    }

    private void updateIsSave(Integer isSave) {
        jobInfo.setIsSave(isSave);
        changeUi(jobInfo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){

            case 123:

                if(resultCode==RESULT_OK && data!=null){
                    Uri selectedFileURI = data.getData();
// Uri uri = Uri.parse(data);
                    Log.d(TAG, "onActivityResult: "+new Gson().toJson(selectedFileURI));
                    File file = new File(ImageFilePath.getPath(activity,selectedFileURI));
/* InputStream is = null;
try {
is = getContentResolver().openInputStream(selectedFileURI);
} catch (FileNotFoundException e) {
e.printStackTrace();
}
File file = new File(selectedFileURI.getPath().toString());
String Fpath = data.getDataString();
Log.d("", "FileUpload : " + file.getName());*/
                    call_Api(file);
                }else{
                    Log.d(TAG, "onActivityResult: data null");
                }
                break;

        }
    }
    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String getPath1(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    private void call_Api(File file) {
        if (NetWorkUtil.isNetworkConnected(activity));{
            Loader.showLoad(activity,true);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("cv_file", file.getName(), requestFile);

            Call<GetFileUpload> call = ApiClient.getApiInterface().getFileName(Constants.getApiKey(activity),
                    Constants.getAccessKey(activity),Constants.getToken(activity),body);
            call.enqueue(new Callback<GetFileUpload>() {
                @Override
                public void onResponse(@NonNull Call<GetFileUpload> call, @NonNull Response<GetFileUpload> response) {
                    if(response.isSuccessful()){
                        if (response.body() != null) {
                            if(response.body().getStatus()==1){
                                fileName = response.body().getFileUploadData().getFileName();
                                jobApplyDialog.getData(fileName,body);
                                MyToast.normalMessage(fileName +" file uploaded successfully",activity);
                            }else {
                                Log.d(TAG, "onResponse: "+response.body().getMessage());
                                MyToast.normalMessage(response.body().getMessage(),activity);
                            }
                        }
                    }else {
                        Log.d(Constants.failureResponse,response.toString());
                    }
                    Loader.showLoad(activity,false);
                }

                @Override
                public void onFailure(@NonNull Call<GetFileUpload> call, @NonNull Throwable t) {
                    Log.d(Constants.failureResponse,t.toString());
                    Loader.showLoad(activity,false);
                    MyToast.normalMessage(t.toString(),activity);
                }
            });
        }
    }

    private void changeUi(JobInfo jobInfo) {
        switch (jobInfo.getIsSave()) {
            case 0:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_unpin_bookmark));
                break;
            case 1:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_pin_bookmark));
                break;
            default:
                pinUnpinImg.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_unpin_bookmark));
                break;
        }
    }

    @Override
    public void onPassData(boolean successCall) {
        if(successCall){
            jobInfo.setIsApplied(1);

            switch (jobInfo.getIsApplied()) {
                case 0:
                    appliedBtn.setText("Apply");
                    break;
                case 1:
                    appliedBtn.setText("Applied");
                    break;
                default:
                    appliedBtn.setText("Apply");
                    break;
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        System.out.println("==================ng" +lat+"uuu"+lang);


        MapsInitializer.initialize(this);
        map = googleMap;



// LatLng sydney = new LatLng(-34, 151);
// map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
// map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

}