package com.project.jarjamediaapp.Activities.user_profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

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
import com.project.jarjamediaapp.Models.Upload_ProfileImage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityUserProfileBinding;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity implements UserProfileContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    ActivityUserProfileBinding bi;
    Context context = UserProfileActivity.this;
    UserProfilePresenter presenter;
    String timeZone = "", imagePath = "", agentType = "", picName = "", picGuid = "";
    boolean isTimeZoneClicked = false, isCountries = false, isImagePicked = false;
    int countryID;
    File actualImage, compressedImage;
    String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
    ArrayList<Integer> arrayListCountryID = new ArrayList<>();
    ArrayList<String> arrayListCountryName = new ArrayList<>();
    ArrayList<String> arrayListStandardName = new ArrayList<>();
    ArrayList<String> arrayListDisplayName = new ArrayList<>();

    private final int RC_CAMERA_AND_STORAGE = 100;

    boolean mFormatting,mFormattingF;
    int mAfter,mAfterF;
    boolean isLeadDistributionMessageEnabled;

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
     //   bi.btnCancel.setOnClickListener(this);

        bi.cbRecieve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isLeadDistributionMessageEnabled = b;
            }
        });

        bi.atvPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mAfter = after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mFormatting) {
                    mFormatting = true;
                    // using US or RU formatting...
                    if (mAfter != 0) // in case back space ain't clicked...
                    {
                        String num = s.toString();
                        String data = PhoneNumberUtils.formatNumber(num, "US");
                        if (data != null) {
                            s.clear();
                            s.append(data);
                            Log.i("Number", data);//8 (999) 123-45-67 or +7 999 123-45-67
                        }

                    }
                    mFormatting = false;
                }
            }
        });

        bi.atvForwarder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mAfterF = after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mFormattingF) {
                    mFormattingF = true;
                    // using US or RU formatting...
                    if (mAfterF != 0) // in case back space ain't clicked...
                    {
                        String num = s.toString();
                        String data = PhoneNumberUtils.formatNumber(num, "US");
                        if (data != null) {
                            s.clear();
                            s.append(data);
                            Log.i("Number", data);//8 (999) 123-45-67 or +7 999 123-45-67
                        }

                    }
                    mFormattingF = false;
                }
            }
        });
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

        isImagePicked = false;
        ToastUtils.showToast(context, getUserProfile.message);
        // for redirect to dashboard screen
        finish();
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

    @Override
    public void updateUI(Upload_ProfileImage getUserProfile) {

        picName = getUserProfile.data.picName;
        picGuid = getUserProfile.data.picGuid;
        imagePath = getUserProfile.data.picLink;
        updateProfile();
    }


    private void populateData(GetUserProfile getUserProfile) {

        GetUserProfile.UserProfile userProfileData = getUserProfile.data.userProfileData;
        bi.atvFirstName.setText(userProfileData.firstName + "");
        bi.atvLastName.setText(userProfileData.lastName + "");



        if (userProfileData.title == null){
            bi.atvTitle.setText("");
        }else {
            bi.atvTitle.setText(userProfileData.title + "");
        }
        if (userProfileData.license == null){
            bi.atvLicesnse.setText("");
        }else {
            bi.atvLicesnse.setText(userProfileData.license + "");
        }
        if (userProfileData.email == null){
            bi.atvEmail.setText("");
        }else {
            bi.atvEmail.setText(userProfileData.email + "");
        }

        if (userProfileData.phone == null){
            bi.atvPhone.setText("");
        }else {
            bi.atvPhone.setText(userProfileData.phone + "");
        }

        if (userProfileData.twilioPhone == null){
            bi.atvVirtual.setText("");
        }else {

            bi.atvVirtual.setText(userProfileData.twilioPhone + "");

        }


        if (userProfileData.forwardedNumber == null){
            bi.atvForwarder.setText("");
        }else {
            bi.atvForwarder.setText(userProfileData.forwardedNumber + "");
        }

        if (userProfileData.company == null){
            bi.atvCompany.setText("");
        }else {
            bi.atvCompany.setText(userProfileData.company + "");
        }
        if (userProfileData.streetAddress == null){
            bi.atvStreetAddress.setText("");
        }else {
            bi.atvStreetAddress.setText(userProfileData.streetAddress + "");
        }
        bi.atvCountry.setText(arrayListCountryName.get(arrayListCountryID.indexOf(userProfileData.countryID)), false);
        if (userProfileData.state == null){
            bi.atvState.setText("");
        }else {
            bi.atvState.setText(userProfileData.state + "");
        }
        if (userProfileData.city == null){
            bi.atvCity.setText("");
        }else {
            bi.atvCity.setText(userProfileData.city + "");
        }
        if (userProfileData.zipcode == null){
            bi.atvZip.setText("");
        }else {
            bi.atvZip.setText(userProfileData.zipcode + "");
        }

        if (userProfileData.tmzone != null && !userProfileData.tmzone.equals("")){

            bi.atvTimeZone.setText(arrayListDisplayName.get(arrayListStandardName.indexOf(userProfileData.tmzone)), false);

        }else {

            bi.atvTimeZone.setText("");

        }
      //  bi.atvTimeZone.setText(arrayListDisplayName.get(arrayListStandardName.indexOf(userProfileData.tmzone)), false);
        if (userProfileData.companyAddress == null){
            bi.atvCompanyAddress.setText("");
        }else {
            bi.atvCompanyAddress.setText(userProfileData.companyAddress + "");
        }

        if (userProfileData.leadDistributionMessageEnabled){
            bi.cbRecieve.setChecked(true);
        }

        countryID = userProfileData.countryID;
        timeZone = userProfileData.tmzone;
        agentType = userProfileData.agentType;
        picGuid = userProfileData.picGuid;
        picName = userProfileData.picName;
        imagePath = userProfileData.picPath;

        if (!userProfileData.picPath.equals("")) {
            Glide.with(this).load(userProfileData.picPath).into(bi.imgProfilePic);
        }

        GH.getInstance().HideProgressDialog();

    }

    private void updateProfile() {

        String fname = bi.atvFirstName.getText().toString();
        String lname = bi.atvLastName.getText().toString();
        String title = bi.atvTitle.getText().toString();
        String license = bi.atvLicesnse.getText().toString();
        String email = bi.atvEmail.getText().toString();
        String phone = bi.atvPhone.getText().toString();
        String vNumber = bi.atvVirtual.getText().toString();
        String fNumber = bi.atvForwarder.getText().toString();
        String company = bi.atvCompany.getText().toString();
        String street = bi.atvStreetAddress.getText().toString();
        //bi.atvCountry.setText(arrayListCountryName.get(arrayListCountryID.indexOf(userProfileData.countryID)),false);
        String state = bi.atvState.getText().toString();
        String city = bi.atvCity.getText().toString();
        String zip = bi.atvZip.getText().toString();
        //bi.atvTimeZone.setText(arrayListDisplayName.get(arrayListStandardName.indexOf(userProfileData.tmzone)),false);
        String companyAddress = bi.atvCompanyAddress.getText().toString();
        String timezone = timeZone;
        int country = countryID;



        if(fname.trim().equals("") || lname.trim().equals("")){

            ToastUtils.showToast(context, "first name and last name is required");

        }else if(email.trim().equals("")){

            ToastUtils.showToast(context, "email is required");

        }else if(bi.atvForwarder.length() < 14){

            ToastUtils.showToast(context, "provide valid forwarded number");

        }else if(!email.matches(emailPattern)){

            ToastUtils.showToast(context, "provide valid email address");

         } else if(bi.atvPhone.length() < 14){

            ToastUtils.showToast(context, "provide valid phone number");
        }else {

            presenter.updateUserProfile(fname, state, license, picName, companyAddress, agentType, zip, street, title, countryID, fNumber,
                    isLeadDistributionMessageEnabled, email, company, lname, timeZone, picGuid, phone, city,vNumber);
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
    private void clearFocus() {

        bi.atvFirstName.clearFocus();
        bi.atvLastName.clearFocus();
        //bi.tvName.clearFocus();
        bi.atvPassword.clearFocus();
        bi.atvCity.clearFocus();
        bi.atvCompany.clearFocus();
        bi.atvCompanyAddress.clearFocus();
        bi.atvEmail.clearFocus();
        bi.atvZip.clearFocus();
        bi.atvState.clearFocus();
        bi.atvStreetAddress.clearFocus();
        bi.atvPhone.clearFocus();
        bi.atvLicesnse.clearFocus();
        bi.atvTitle.clearFocus();
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
                clearFocus();
                time_zone();
                // click on drop down so softkeyboard hide
                hideSoftKeyboard(bi.atvTimeZone);
                break;
            case R.id.atvCountry:
                clearFocus();
                country();
                // click on drop down so softkeyboard hide
               hideSoftKeyboard(bi.atvCountry);
                break;
            /*case R.id.btnCancel:
                finish();
                break;*/
            case R.id.btnUpdate:

                if (isImagePicked) {
                    new ImageCompression(UserProfileActivity.this).execute(imagePath);
                } else {
                    updateProfile();
                }

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
                imagePath = image.getPath();
                isImagePicked = true;

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

    private class ImageCompression extends AsyncTask<String, Void, File> {


        private WeakReference<UserProfileActivity> activityReference;

        // only retain a weak reference to the activity
        ImageCompression(UserProfileActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected File doInBackground(String... params) {
            return ImageCompression(params[0]);
        }

        @Override
        protected void onPostExecute(File result) {
            Log.d("Test", "onPreExecute: " + "" + result);

            UserProfileActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            Uri uri = Uri.fromFile(result);
            String type1 = GH.getInstance().getMimeType(context, uri);
            MediaType mediaType = MediaType.parse(type1);
            MultipartBody.Part file = null;
            if (result != null && actualImage != null) {
                RequestBody requestFile = RequestBody.create(mediaType, result);
                file = MultipartBody.Part.createFormData("file", result.getName(), requestFile);
                try {
                    presenter.uploadImage(file);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute() {
            Log.d("Test", "onPreExecute: " + "");
            showProgressBar();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private File ImageCompression(String filePath) {

        actualImage = new File(filePath);
        Log.i("imageCompresser", "Original image file size: " + actualImage.length());
        try {
            String compressedFileName = System.currentTimeMillis() + ".jpg";
            compressedImage = new Compressor(this).compressToFile(actualImage, compressedFileName);
            Log.i("imageCompresser", "Compressed image file size: " + compressedImage.length());
        } catch (Exception e) {
            e.printStackTrace();
            compressedImage = null;
        }
        return compressedImage;
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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}