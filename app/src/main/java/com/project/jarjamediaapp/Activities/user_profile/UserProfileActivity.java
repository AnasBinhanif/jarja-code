package com.project.jarjamediaapp.Activities.user_profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetCountries;
import com.project.jarjamediaapp.Models.GetTimeZoneList;
import com.project.jarjamediaapp.Models.GetTwilioNumber;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityUserProfileBinding;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity implements UserProfileContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    ActivityUserProfileBinding bi;
    Context context = UserProfileActivity.this;
    UserProfilePresenter presenter;
    String timeZone = "";
    boolean isTimeZoneClicked = false,isCountries = false;
    int countryID;
    ArrayList<Integer> arrayListCountryID = new ArrayList<>();
    ArrayList<String> arrayListCountryName = new ArrayList<>();
    ArrayList<String> arrayListStandardName = new ArrayList<>();
    ArrayList<String> arrayListDisplayName = new ArrayList<>();

    private final int RC_CAMERA_AND_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        presenter = new UserProfilePresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.my_profile), true);
    }

    @Override
    public void initViews() {

        initListeners();
        presenter.getCountries();
    }

    private void initListeners() {

        bi.btnAdd.setOnClickListener(this);
        bi.btnUpdate.setOnClickListener(this);
        bi.atvPassword.setOnClickListener(this);
        bi.atvCountry.setOnClickListener(this);
        bi.atvTimeZone.setOnClickListener(this);
        bi.imgProfilePic.setOnClickListener(this);
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

    }

    @Override
    public void updateUI(GetUserProfile getUserProfile) {

        populateData(getUserProfile);

    }

    @Override
    public void updateUI(GetTwilioNumber getUserProfile) {

        if (!getUserProfile.data.equals("")) {
            bi.atvVirtual.setText(getUserProfile.data);
        }
    }

    @Override
    public void updateUI(BaseResponse getUserProfile) {

    }

    @Override
    public void updateUI(GetTimeZoneList response) {

        presenter.getUserProfile();

        arrayListStandardName = new ArrayList<>();
        arrayListDisplayName = new ArrayList<>();

        for (int i = 0; i < response.data.size(); i++) {

            arrayListDisplayName.add(response.data.get(i).displayName);
            arrayListStandardName.add(response.data.get(i).standardName);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListDisplayName);
        bi.atvTimeZone.setAdapter(arrayAdapter);

        bi.atvTimeZone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isTimeZoneClicked = false;
                timeZone = arrayListStandardName.get(position);
            }
        });


    }

    @Override
    public void updateUI(GetCountries response) {

        presenter.getTimeZoneList();

         arrayListCountryID = new ArrayList<>();
         arrayListCountryName = new ArrayList<>();
        
        for (int i = 0; i < response.data.size(); i++) {

            arrayListCountryID.add(response.data.get(i).id);
            arrayListCountryName.add(response.data.get(i).countryName);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListCountryName);
        bi.atvCountry.setAdapter(arrayAdapter);

        bi.atvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isCountries = false;
                countryID = arrayListCountryID.get(position);
            }
        });

    }

    private void populateData(GetUserProfile getUserProfile) {

        GetUserProfile.UserProfile userProfileData = getUserProfile.data.userProfileData;
        bi.atvFirstName.setText(userProfileData.firstName + "");
        bi.atvLastName.setText(userProfileData.lastName + "");
        bi.atvTitle.setText(userProfileData.title + "");
        bi.atvLicesnse.setText(userProfileData.license + "");
        bi.atvEmail.setText(userProfileData.email + "");
        bi.atvPhone.setText(userProfileData.phone + "");
        bi.atvPassword.setText("");
        bi.atvVirtual.setText("");
        bi.atvForwarder.setText(userProfileData.forwardedNumber + "");
        bi.atvCompany.setText(userProfileData.company + "");
        bi.atvStreetAddress.setText(userProfileData.streetAddress + "");
        bi.atvCountry.setText(arrayListCountryName.get(arrayListCountryID.indexOf(userProfileData.countryID)),false);
        bi.atvState.setText(userProfileData.state + "");
        bi.atvCity.setText(userProfileData.city + "");
        bi.atvZip.setText(userProfileData.zipcode + "");
        bi.atvTimeZone.setText(arrayListDisplayName.get(arrayListStandardName.indexOf(userProfileData.tmzone)),false);
        bi.atvCompanyAddress.setText(userProfileData.companyAddress + "");

        if (!userProfileData.picPath.equals("")) {
            Glide.with(this).load(userProfileData.picPath).into(bi.imgProfilePic);
        }

    }

    private void oPenGallery() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .folderMode(true) // folder mode (false by default)
                    .toolbarFolderTitle("Select Image") // folder selection title
                    .toolbarImageTitle("Tap to select") // image selection title
                    .toolbarDoneButtonText("DONE")
                    .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                    .includeVideo(false) // Show video on image picker
                    .single() // single mode
                    .limit(1) // max images can be selected (99 by default)
                    .showCamera(true) // show camera or not (true by default)
                    .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                    .start(); // start image picker activity with request code
//                    .theme(R.style.ImagePickerTheme)


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_AND_STORAGE, perms);
        }
    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {
        ToastUtils.showToastLong(context, error);
    }

    @Override
    public void updateUIonFailure() {
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(UserProfileActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.imgProfilePic:
                oPenGallery();
                break;
            case R.id.btnAdd:
                presenter.getTwilioNumber();
                break;
            case R.id.atvPassword:
                switchActivity(ChangePassword.class);
                break;
            case R.id.atvTimeZone:
                time_zone();
                break;
            case R.id.atvCountry:
                country();
                break;
            case R.id.btnUpdate:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {

            if (data != null) {

                Image image = ImagePicker.getFirstImageOrNull(data);
                Bitmap myBitmap = BitmapFactory.decodeFile(image.getPath());
                bi.imgProfilePic.setImageBitmap(myBitmap);

            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        oPenGallery();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private void time_zone() {

        if (!isTimeZoneClicked) {
            bi.atvTimeZone.showDropDown();
            isTimeZoneClicked = true;
        } else {
            isTimeZoneClicked = false;
            bi.atvTimeZone.dismissDropDown();
        }
    }

    private void country() {

        if (!isCountries) {
            bi.atvCountry.showDropDown();
            isCountries = true;
        } else {
            isCountries = false;
            bi.atvCountry.dismissDropDown();
        }
    }
}