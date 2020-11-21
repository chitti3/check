package com.example.youthhub.profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.youthhub.R;
import com.example.youthhub.resModel.profile.Data;
import com.example.youthhub.resModel.profile.GenderMasterItem;
import com.example.youthhub.resModel.profile.ProfileMasterResponse;
import com.example.youthhub.resModel.profile.getInfo.ProfileGetInfoResponse;
import com.example.youthhub.retrofit.ApiClient;
import com.example.youthhub.utils.Constants;
import com.example.youthhub.utils.FontTypeFace;
import com.example.youthhub.utils.Loader;
import com.example.youthhub.utils.MyToast;
import com.example.youthhub.utils.NetWorkUtil;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProEdit extends AppCompatActivity {
    Activity activity;
    public static final String TAG = ProEdit.class.getSimpleName();
    @BindView(R.id.genral_information_textview)
    TextView genralInformationTextview;
    @BindView(R.id.firstname_textview)
    TextView firstnameTextview;
    @BindView(R.id.firstname_edit_text)
    EditText firstnameEditText;
    @BindView(R.id.lastname_textview)
    TextView lastnameTextview;
    @BindView(R.id.lastname_edit_text)
    EditText lastnameEditText;
    @BindView(R.id.dob_textview)
    TextView dobTextview;

    @BindView(R.id.gender_textview)
    TextView genderTextview;
    @BindView(R.id.gender_spinner)
    AppCompatEditText gender_spinner;
    @BindView(R.id.region_spinner)
    AppCompatEditText regionSpinner;
    @BindView(R.id.region_textview)
    TextView regionTextview;
    @BindView(R.id.district_city_textview)
    TextView districtCityTextview;
    @BindView(R.id.current_organisation_textview)
    TextView currentOrganisationTextview;
    @BindView(R.id.ethnicity_textview)
    TextView ethnicityTextview;
    @BindView(R.id.lwi_textview)
    TextView lwiTextview;
    @BindView(R.id.email_textview)
    TextView emailTextview;
    @BindView(R.id.mobile_number_textview)
    TextView mobileNumberTextview;
    @BindView(R.id.work_profile_textview)
    TextView workProfileTextview;
    @BindView(R.id.current_work_status_textview)
    TextView currentWorkStatusTextview;
    @BindView(R.id.work_availability_textview)
    TextView workAvailabilityTextview;
    @BindView(R.id.work_experience_textview)
    TextView workExperienceTextview;
    @BindView(R.id.your_availability_textview)
    TextView yourAvailabilityTextview;
    @BindView(R.id.prefered_region_textview)
    TextView preferedRegionTextview;
    @BindView(R.id.district_work_textview)
    TextView districtWorkTextview;
    @BindView(R.id.future_intended_destination_textview)
    TextView futureIntendedDestinationTextview;
    @BindView(R.id.license_and_transport_textview)
    TextView licenseAndTransportTextview;
    @BindView(R.id.residency_status_textview)
    TextView residencyStatusTextview;
    @BindView(R.id.communication_connections_textview)
    TextView communicationConnectionsTextview;
    @BindView(R.id.instagram_link_textview)
    TextView instagramLinkTextview;
    @BindView(R.id.instagram_link_edittext)
    EditText instagramLinkEdittext;
    @BindView(R.id.facebook_link_textview)
    TextView facebookLinkTextview;
    @BindView(R.id.facebook_link_edittext)
    EditText facebookLinkEdittext;
    @BindView(R.id.twitter_link_textview)
    TextView twitterLinkTextview;
    @BindView(R.id.twitter_link_edittext)
    EditText twitterLinkEdittext;
    @BindView(R.id.google_plus_link_textview)
    TextView googlePlusLinkTextview;
    @BindView(R.id.google_plus_link_edittext)
    EditText googlePlusLinkEdittext;
    @BindView(R.id.linkedin_link_textview)
    TextView linkedinLinkTextview;
    @BindView(R.id.linkedin_link_edittext)
    EditText linkedinLinkEdittext;
    @BindView(R.id.github_link_textview)
    TextView githubLinkTextview;
    @BindView(R.id.github_link_edittext)
    EditText githubLinkEdittext;
    @BindView(R.id.behance_link_textview)
    TextView behanceLinkTextview;
    @BindView(R.id.behance_link_edittext)
    EditText behanceLinkEdittext;
    @BindView(R.id.additional_info_textview)
    TextView additionalInfoTextview;

    @BindView(R.id.yes_textview)
    TextView yesTextview;
    @BindView(R.id.apply_button)
    Button applyButton;
    @BindView(R.id.cancel_button)
    Button cancelButton;


    @BindView(R.id.dob_edittext)
    AppCompatEditText dobEdittext;
    @BindView(R.id.city_spinner)
    AppCompatEditText citySpinner;
    @BindView(R.id.organisation_spinner)
    AppCompatEditText organisationSpinner;
    @BindView(R.id.ethnicity_spinner)
    AppCompatEditText ethnicitySpinner;
    @BindView(R.id.iwi_spinner)
    AppCompatEditText iwiSpinner;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_mobileno)
    EditText editMobileno;
    @BindView(R.id.current_work_status_spinner)
    AppCompatEditText currentWorkStatusSpinner;
    @BindView(R.id.work_availability_spinner)
    AppCompatEditText workAvailabilitySpinner;
    @BindView(R.id.work_experience_spinner)
    AppCompatEditText workExperienceSpinner;

    @BindView(R.id.prefered_region_spinner)
    AppCompatEditText preferedRegionSpinner;
    @BindView(R.id.district_work_spinner)
    AppCompatEditText districtWorkSpinner;
    @BindView(R.id.future_intended_destination_spinner)
    AppCompatEditText futureIntendedDestinationSpinner;
    @BindView(R.id.license_and_transport_spinner)
    AppCompatEditText licenseAndTransportSpinner;
    @BindView(R.id.residency_status_spinner)
    AppCompatEditText residencyStatusSpinner;
    @BindView(R.id.your_availability_spinner)
    EditText yourAvailabilitySpinner;
    @BindView(R.id.visatype_textview)
    TextView visatypeTextview;
    @BindView(R.id.visatype_spinner)
    AppCompatSpinner visatypeSpinner;
    @BindView(R.id.visaexpire_textview)
    TextView visaexpireTextview;
    @BindView(R.id.edt_visa_month)
    EditText edtVisaMonth;
    @BindView(R.id.edt_visa_year)
    EditText edtVisaYear;
    @BindView(R.id.linear_visa_expire)
    LinearLayoutCompat linearVisaExpire;
    @BindView(R.id.about_me_textview)
    EditText aboutMeTextview;
    ProfileMasterResponse profileInfoMaster;
    ProfileGetInfoResponse profileGetInfoResponse;
    private String UserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_edit);
        ButterKnife.bind(this);
        callTypeFace();

        UserType = getIntent().getStringExtra("code");
        System.out.println(UserType+"gtgtgtggtg");
        call_profile_info_master(UserType);




    }

    private void call_profile_info_master(String userType) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            Loader.showLoad(this, true);
            Call<ProfileMasterResponse> call = ApiClient.getApiInterface().getProfileInfoMaster(Constants.getApiKey(activity), Constants.getAccessKey(activity),
                    Constants.getToken(activity),
                    userType);

            call.enqueue(new Callback<ProfileMasterResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileMasterResponse> call, @NonNull Response<ProfileMasterResponse> response) {
                    if (response.isSuccessful()) {
                        //   Log.d(TAG, "onResponse:ProfileYouthInfoResponse 1 ");
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                profileInfoMaster = response.body();
                                update_ui(response.body());
                                Log.d(TAG, "onResponse:ProfileInfoDialog " + new Gson().toJson(response.body().getData().getYouthInfo().getYthFirstName()));
                                //       update_ui(profileInfoMaster);
                            } else {
                                //     Log.d(TAG, "onResponse:ProfileYouthInfoResponse else "+new Gson().toJson(profileInfoResp));
                                MyToast.errorMessage(response.body().getMessage(), activity);
                                System.out.println(response.body().getMessage()+"Profilf");
                            }

                        }
                    } else {
                        Log.d(Constants.failureResponse + " Profilf", response.toString());
                        Loader.showLoad(activity, false);
                    }
                    Loader.showLoad(activity, false);
                }

                @Override
                public void onFailure(@NonNull Call<ProfileMasterResponse> call, @NonNull Throwable t) {
                    call_profile_info_master(UserType);
                    Log.d(Constants.failureResponse + " Profilf", t.toString());
                    Loader.showLoad(activity, false);
                }
            });

        }
        }

    private void update_ui(ProfileMasterResponse profileInfoMaster) {
        firstnameEditText.setText(profileInfoMaster.getData().getYouthInfo().getYthFirstName());
        lastnameEditText.setText(profileInfoMaster.getData().getYouthInfo().getYthLastName());
        dobEdittext.setText(profileInfoMaster.getData().getYouthInfo().getYthDob());
        editEmail.setText(profileInfoMaster.getData().getYouthInfo().getYthContactEmail());
        editMobileno.setText(profileInfoMaster.getData().getYouthInfo().getYthMobileNo());
        yourAvailabilitySpinner.setText(profileInfoMaster.getData().getYouthInfo().getYthWorkAvailabilityHour());

     //  General Information
     gender_spinner_load(profileInfoMaster.getData().getGenderMaster(),profileInfoMaster.getData().getYouthInfo().getYthGender());
       regionSpinnerLoad(profileInfoMaster.getData());
         citySpinnerLoad(profileInfoMaster.getData());
       organisationSpinnerLoad(profileInfoMaster.getData());
      /*     IwiSpinnerLoad();
        ethnicitySpinnerLoad();  */
      ethnicitySpinner.setText(profileInfoMaster.getData().getYouthInfo().getYthEthnicityName());

       /*Work Profile*/
        currentworkstatusSpinnerLoad(profileInfoMaster.getData());
        workAvailabilitySpinner.setText(profileInfoMaster.getData().getYouthInfo().getYthWorkAvailabilityTimingName());
       /*   workavailabilitySpinnerLoad();
        workexperienceSpinnerLoad();
        perferedregionSpinnerLoad();
        perferredcitySpinnerLoad();
        intendedDestinationSpinnerLoad();
        licenseTrasportSpinnerLoad();
        residencystatusSpinnerLoad();
        visatypeSpinnerLoad();*/

        edtVisaMonth.setText(profileInfoMaster.getData().getYouthInfo().getYthVisaExpireMonth());
        edtVisaYear.setText(profileInfoMaster.getData().getYouthInfo().getYthVisaExpireYear());

        instagramLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluInstagram());
        facebookLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluFacebook());
        twitterLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluTwitter());
        googlePlusLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluGooglePlus());
        linkedinLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluLinkedin());
        behanceLinkEdittext.setText(profileInfoMaster.getData().getYouthInfo().getSluBehance());

        aboutMeTextview.setText(profileInfoMaster.getData().getYouthInfo().getYthFullDescription());


    }

    private void organisationSpinnerLoad(Data data) {
        for (int i=0;i<data.getOrganisationcategory().size();i++)
        {
            if (data.getOrganisationcategory().get(i).getOgmOrganisationId().equals(data.getYouthInfo().getYthCurrentStatus()));
            {
                organisationSpinner.setText(data.getOrganisationcategory().get(i).getOgmName());
            }
        }
    }

    private void currentworkstatusSpinnerLoad(Data data) {
        for (int i=0;i<data.getWorkStatusMaster().size();i++)
        {
            if (data.getWorkStatusMaster().get(i).getName().equals(data.getYouthInfo().getYthWorkStatusName()))
            {
               currentWorkStatusSpinner.setText(data.getWorkStatusMaster().get(i).getName());
            }
        }
    }

    private void citySpinnerLoad(Data data) {
        for (int i=0;i<data.getCities().size();i++)
        {
            if (data.getCities().get(i).getCiCityId().equals(data.getYouthInfo().getYthCity()))
            {
                citySpinner.setText(data.getCities().get(i).getCiName());
            }
        }
    }

    private void regionSpinnerLoad(Data data) {
        for (int i=0;i<data.getRegionList().size();i++)
        {
            if (data.getRegionList().get(i).getReRegionId().equals(data.getYouthInfo().getYthRegion()))
            {
                regionSpinner.setText(data.getRegionList().get(i).getReName());
            }
        }
    }

    private void gender_spinner_load(List<GenderMasterItem> genderMaster, String ythGender) {
        for (int i=0;i<genderMaster.size();i++)
        {
            if (genderMaster.get(i).getId().equals(ythGender))
            {
                gender_spinner.setText(genderMaster.get(i).getName());
            }
        }

    }

    @OnClick({R.id.apply_button, R.id.cancel_button, R.id.dob_edittext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.apply_button:
             //   pass_Data();
                break;
            case R.id.cancel_button:
                //dismiss();
                break;
            case R.id.dob_edittext:
               /* DatePickerDialog picker = new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                picker.show();*/
                calendarClick();
                break;
        }
    }
    private void calendarClick() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) ->
        {
            String age = getAge(year,monthOfYear,dayOfMonth);
            int AGE = Integer.valueOf(age);
            if(AGE>=11 && AGE<=26){
                dobEdittext.setText(String.valueOf(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
            }else {
                //MyToast.errorMessage(age,activity);
                MyToast.errorMessage("The dob field must contain a valid Youth Date of Birth (Age b/w 12 to 25)",this);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        int ageInt = age;

        return Integer.toString(ageInt);
    }


    private void callTypeFace() {
        additionalInfoTextview.setTypeface(FontTypeFace.fontBold(this));
        communicationConnectionsTextview.setTypeface(FontTypeFace.fontBold(this));
        currentOrganisationTextview.setTypeface(FontTypeFace.fontRegular(this));
        currentWorkStatusTextview.setTypeface(FontTypeFace.fontBold(this));
        districtCityTextview.setTypeface(FontTypeFace.fontRegular(this));
        districtWorkTextview.setTypeface(FontTypeFace.fontBold(this));
        communicationConnectionsTextview.setTypeface(FontTypeFace.fontBold(this));
        additionalInfoTextview.setTypeface(FontTypeFace.fontBold(this));
        dobTextview.setTypeface(FontTypeFace.fontRegular(this));
        emailTextview.setTypeface(FontTypeFace.fontRegular(this));
        ethnicityTextview.setTypeface(FontTypeFace.fontRegular(this));
        firstnameTextview.setTypeface(FontTypeFace.fontRegular(this));
        futureIntendedDestinationTextview.setTypeface(FontTypeFace.fontBold(this));
        genderTextview.setTypeface(FontTypeFace.fontRegular(this));
        genralInformationTextview.setTypeface(FontTypeFace.fontBold(this));
        lastnameTextview.setTypeface(FontTypeFace.fontRegular(this));
        licenseAndTransportTextview.setTypeface(FontTypeFace.fontBold(this));
        yourAvailabilityTextview.setTypeface(FontTypeFace.fontBold(this));
        workProfileTextview.setTypeface(FontTypeFace.fontBold(this));
        workExperienceTextview.setTypeface(FontTypeFace.fontBold(this));
        workAvailabilityTextview.setTypeface(FontTypeFace.fontBold(this));
        regionTextview.setTypeface(FontTypeFace.fontRegular(this));
        preferedRegionTextview.setTypeface(FontTypeFace.fontBold(this));
        preferedRegionTextview.setTypeface(FontTypeFace.fontBold(this));
        mobileNumberTextview.setTypeface(FontTypeFace.fontBold(this));
        lwiTextview.setTypeface(FontTypeFace.fontRegular(this));
        visatypeTextview.setTypeface(FontTypeFace.fontBold(this));
        visaexpireTextview.setTypeface(FontTypeFace.fontBold(this));
        firstnameEditText.setTypeface(FontTypeFace.fontRegular(this));
        lastnameEditText.setTypeface(FontTypeFace.fontRegular(this));
        dobEdittext.setTypeface(FontTypeFace.fontRegular(this));
        gender_spinner.setTypeface(FontTypeFace.fontRegular(this));
        regionSpinner.setTypeface(FontTypeFace.fontRegular(this));
        citySpinner.setTypeface(FontTypeFace.fontRegular(this));
        organisationSpinner.setTypeface(FontTypeFace.fontRegular(this));
        ethnicitySpinner.setTypeface(FontTypeFace.fontRegular(this));
        iwiSpinner.setTypeface(FontTypeFace.fontRegular(this));
        editEmail.setTypeface(FontTypeFace.fontRegular(this));
        editMobileno.setTypeface(FontTypeFace.fontRegular(this));
        workProfileTextview.setTypeface(FontTypeFace.fontBold(this));
        mobileNumberTextview.setTypeface(FontTypeFace.fontRegular(this));
        currentWorkStatusTextview.setTypeface(FontTypeFace.fontRegular(this));
        workAvailabilityTextview.setTypeface(FontTypeFace.fontRegular(this));
        workExperienceTextview.setTypeface(FontTypeFace.fontRegular(this));
        yourAvailabilityTextview.setTypeface(FontTypeFace.fontRegular(this));
        preferedRegionTextview.setTypeface(FontTypeFace.fontRegular(this));
        districtWorkTextview.setTypeface(FontTypeFace.fontRegular(this));
        futureIntendedDestinationTextview.setTypeface(FontTypeFace.fontRegular(this));
        licenseAndTransportTextview.setTypeface(FontTypeFace.fontRegular(this));
        residencyStatusTextview.setTypeface(FontTypeFace.fontRegular(this));
        currentWorkStatusSpinner.setTypeface(FontTypeFace.fontRegular(this));
        workAvailabilitySpinner.setTypeface(FontTypeFace.fontRegular(this));
        workExperienceSpinner.setTypeface(FontTypeFace.fontRegular(this));
        yourAvailabilitySpinner.setTypeface(FontTypeFace.fontRegular(this));
        preferedRegionSpinner.setTypeface(FontTypeFace.fontRegular(this));
        districtWorkSpinner.setTypeface(FontTypeFace.fontRegular(this));
        futureIntendedDestinationSpinner.setTypeface(FontTypeFace.fontRegular(this));
        licenseAndTransportSpinner.setTypeface(FontTypeFace.fontRegular(this));
        residencyStatusSpinner.setTypeface(FontTypeFace.fontRegular(this));
        instagramLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        twitterLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        googlePlusLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        linkedinLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        githubLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        behanceLinkTextview.setTypeface(FontTypeFace.fontRegular(this));
        instagramLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        instagramLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        twitterLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        googlePlusLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        linkedinLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        githubLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        behanceLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        instagramLinkEdittext.setTypeface(FontTypeFace.fontRegular(this));
        /* AppUtils.textAddAstreik(firstnameTextview,"First Name");
        AppUtils.textAddAstreiastnamek(lTextview,"Last Name");
        AppUtils.textAddAstreik(dobTextview,"Date of Birth");
        AppUtils.textAddAstreik(genderTextview,"Gender");
        AppUtils.textAddAstreik(regionTextview,"Region");
        AppUtils.textAddAstreik(districtCityTextview,"District / City");
        AppUtils.textAddAstreik(futureIntendedDestinationTextview,"Future intended destination");
        AppUtils.textAddAstreik(emailTextview,"Email");*/
    }
}
