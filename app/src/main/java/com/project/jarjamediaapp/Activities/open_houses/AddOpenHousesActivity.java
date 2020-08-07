package com.project.jarjamediaapp.Activities.open_houses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.project.jarjamediaapp.Activities.bottomsheet.BottomDialogFragment;
import com.project.jarjamediaapp.Activities.bottomsheet.HandleClickEvents;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.UserPermissions;
import com.project.jarjamediaapp.databinding.CustomOpenHouseDialogBinding;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class AddOpenHousesActivity extends BaseActivity implements View.OnClickListener, OpenHousesContract.View, HandleClickEvents, EasyPermissions.PermissionCallbacks {

    CustomOpenHouseDialogBinding bi;
    Context context = AddOpenHousesActivity.this;
    OpenHousesPresenter presenter;
    String listPrice = "", city = "", address = "", state = "", zip = "", image = "", dateTimeToServer = "";
    String timeS = "", timeE = "";
    String openHouseStartDate = "", openHouseEndDate = "", dateToServer = "", timeToServer = "";
    private BottomDialogFragment bottomDialogFragment;
    private final int RC_CAMERA_AND_STORAGE = 100;
    private final int RC_CAMERA_ONLY = 101;
    File actualImage;
    File compressedImage;
    int viewId;
    String openHouseType = "upcoming";
    int perm = 0;
    private int propertyId;
    GetAllOpenHousesModel.Data.OpenHouse openHouse;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.custom_open_house_dialog);
        presenter = new OpenHousesPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_open_house), true);

        if (getIntent().getExtras() != null) {

            GetAllOpenHousesModel.Data.OpenHouse openHouse = (GetAllOpenHousesModel.Data.OpenHouse) getIntent().getExtras().getSerializable("editLeadsObj");
            if (openHouse != null){

                // for update payload
                propertyId = openHouse.propertyId;
                populateOpenHouseData(openHouse);
                bi.btnSave.setText("Update");

            }
        }

    }

    public void populateOpenHouseData(GetAllOpenHousesModel.Data.OpenHouse openHouse){

        bi.atvCity.setText(openHouse.getCity());
        bi.atvAddress.setText(openHouse.getStreetName());
        bi.atvPrice.setText(openHouse.getListPrice());
        bi.atvState.setText(openHouse.getState());
        bi.atvZip.setText(openHouse.getZipCode());
        bi.atvOpenHouseStartDate.setText(openHouse.getOpenHouseDate());
        bi.atvOpenHouseEndDate.setText(openHouse.getOpenHouseEndDate());

        openHouseStartDate = openHouse.getOpenHouseDate();
        openHouseEndDate = openHouse.getOpenHouseEndDate();

        bi.tvRemovePictures.setVisibility(View.VISIBLE);
        bi.tvRemovePictures.setText("Image uploaded");
        bi.tvSelectPictures.setVisibility(View.GONE);

        // changes in model UplaodImageModel to Upload_ProfileImage for api response
        image = openHouse.getImgURL();

      //  bi.atvZip.setText(openHouse.get);

    }


    @Override
    public void initViews() {

        UserPermissions.isCameraStorageLocationPermissionGranted(AddOpenHousesActivity.this);

        initListeners();



    }

    private String getFormatedAmount(int amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    private void initListeners() {

        bi.atvPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (bi.atvPrice.getText().toString().length() > 0) {
                        bi.atvPrice.setText(getFormatedAmount(Integer.valueOf(bi.atvPrice.getText().toString())));
                    }

                }
            }
        });

        bi.atvAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                viewId = view.getId();
                return false;
            }
        });

        bi.atvAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.atvAddress.getText().toString().trim().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(bi.atvAddress.getText().toString(), "street");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });


        bi.atvCity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                viewId = view.getId();
                return false;
            }
        });
        bi.atvCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.atvCity.getText().toString().trim().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(bi.atvCity.getText().toString(), "city");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        bi.atvState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                viewId = view.getId();
                return false;
            }
        });

        bi.atvState.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.atvState.getText().toString().trim().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(bi.atvState.getText().toString(), "state");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        bi.atvZip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                viewId = view.getId();
                return false;
            }
        });

        bi.atvZip.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.atvZip.getText().toString().trim().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(bi.atvZip.getText().toString(), "zip");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        bi.atvOpenHouseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearFocus();
                ///DateDialog(context, bi.atvOpenHouseStartDate, true, "1");
                showSpinnerDateDialog(context, bi.atvOpenHouseStartDate, true, "1");
            }
        });

        bi.atvOpenHouseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearFocus();
                //DateDialog(context, bi.atvOpenHouseEndDate, true, "2");
                showSpinnerDateDialog(context, bi.atvOpenHouseEndDate, true, "2");
            }
        });

        bi.tvSelectPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearFocus();
                bottomDialogFragment = BottomDialogFragment.getInstance();
                bottomDialogFragment.setCLickHandleClickEvents(AddOpenHousesActivity.this);
                bottomDialogFragment.show(getSupportFragmentManager(), "Select Image");
            }
        });

        bi.tvRemovePictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearFocus();
                image = "";
                bi.tvRemovePictures.setVisibility(View.GONE);
                bi.tvSelectPictures.setVisibility(View.VISIBLE);

            }
        });

        bi.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss dialogue on api integration

                if (bi.btnSave.getText() == "Update"){

                    address = bi.atvAddress.getText().toString();
                    listPrice = bi.atvPrice.getText().toString();
                    city = bi.atvCity.getText().toString();
                    state = bi.atvState.getText().toString();
                    zip = bi.atvZip.getText().toString();

                //    if (isValidate()) {

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("listPrice", listPrice);
                            obj.put("city", city);
                            obj.put("propertyId", propertyId);
                            obj.put("address", address);
                            obj.put("state", state);
                            obj.put("zip", zip);
                            obj.put("image", image);
                            obj.put("openHouseDate", openHouseStartDate);
                            obj.put("openHouseEndDate", openHouseEndDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String jsonObjectString = obj.toString();
                        Log.d("json", jsonObjectString);


                        presenter.addOpenHouse(jsonObjectString);
                 //   }

                }else {

                        address = bi.atvAddress.getText().toString();
                        listPrice = bi.atvPrice.getText().toString();
                        city = bi.atvCity.getText().toString();
                        state = bi.atvState.getText().toString();
                        zip = bi.atvZip.getText().toString();

                           if (isValidate()) {

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("listPrice", listPrice);
                            obj.put("city", city);
                            obj.put("address", address);
                            obj.put("state", state);
                            obj.put("zip", zip);
                            obj.put("image", image);
                            obj.put("imageName", imageName);
                            obj.put("propertyId", 0);
                            obj.put("openHouseDate", openHouseStartDate);
                            obj.put("openHouseEndDate", openHouseEndDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String jsonObjectString = obj.toString();
                        Log.d("json", jsonObjectString);


                        presenter.addOpenHouse(jsonObjectString);
                          }

                }



            }
        });

        bi.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.atvAddress)) {
            ToastUtils.showToast(context, R.string.error_address);
            bi.atvAddress.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvCity)) {
            ToastUtils.showToast(context, R.string.error_city);
            bi.atvCity.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvState)) {
            ToastUtils.showToast(context, R.string.error_state);
            bi.atvState.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvOpenHouseStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_time);
            bi.atvOpenHouseStartDate.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvOpenHouseEndDate)) {
            ToastUtils.showToast(context, R.string.error_end_time);
            bi.atvOpenHouseEndDate.requestFocus();
            return false;
        }
        Date date1 = null, date2 = null, time1 = null, time2 = null, currentTime = null;

        String sDate = GH.getInstance().formatter(openHouseStartDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
        String eDate = GH.getInstance().formatter(openHouseEndDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");

        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String time = simpleDate.format(new Date());
            currentTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
            time1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(sDate);
            time2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(eDate);
            if (time2.before(time1)) {
                ToastUtils.showToast(context, "End date time cannot be less than start date time");
                bi.atvOpenHouseEndDate.requestFocus();
                return false;
            }
            if (time1.before(currentTime)) {
                ToastUtils.showToast(context, "Start time cannot be less than current time");
                bi.atvOpenHouseStartDate.requestFocus();
                return false;
            }

            String dateString[] = sDate.split("T");
            String startDate = dateString[0];

            SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDate1.format(new Date());

            if (date.compareTo(startDate) == 0) {
                if (time1.before(currentTime)) {
                    ToastUtils.showToast(context, "Start time cannot be less than current time");
                    bi.atvOpenHouseStartDate.requestFocus();
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            ToastUtils.showToast(context, "error" + e);
        }

        return true;
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (bi.btnSave.getText() == "Update"){

            ToastUtils.showToastLong(context, "Open House Updated Successfully");
            finish();
        }else {

            ToastUtils.showToastLong(context, "Open House Added Successfully");
            finish();
        }

    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(AddOpenHousesActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void updateUIListForOpenHouses(Response<GetAllOpenHousesModel> response, int position) {

    }

    @Override
    public void updateAfterUploadFile(Upload_ProfileImage response) {

        hideProgressBar();
        bi.tvRemovePictures.setVisibility(View.VISIBLE);
        bi.tvRemovePictures.setText("Image uploaded");
        bi.tvSelectPictures.setVisibility(View.GONE);
        ToastUtils.showToast(context, "Image uploaded");
        // changes in model UplaodImageModel to Upload_ProfileImage for api response
        image = response.data.picLink;
        imageName = response.data.picName;


    }

    @Override
    public void updateUIListForAddressDetail(AddressDetailModel.Data response) {

        switch (viewId) {

            case R.id.atvAddress: {

                ArrayList<String> arrayList = new ArrayList<>();
                if (response.getStreetFilter() != null && response.getStreetFilter().size() > 0) {
                    for (int i = 0; i < response.getStreetFilter().size(); i++) {
                        arrayList.add(response.getStreetFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    bi.atvAddress.setAdapter(arrayAdapter);
                    bi.atvAddress.showDropDown();
                } else {
                    ToastUtils.showToast(context, "No data found");
                }
            }
            break;
            case R.id.atvCity: {

                ArrayList<String> arrayList = new ArrayList<>();
                if (response.getCityFilter() != null && response.getCityFilter().size() > 0) {
                    for (int i = 0; i < response.getCityFilter().size(); i++) {
                        arrayList.add(response.getCityFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    bi.atvCity.setAdapter(arrayAdapter);
                    bi.atvCity.showDropDown();
                } else {
                    ToastUtils.showToast(context, "No data found");
                }

            }
            break;
            case R.id.atvState: {

                ArrayList<String> arrayList = new ArrayList<>();
                if (response.getStateFilter() != null && response.getStateFilter().size() > 0) {
                    for (int i = 0; i < response.getStateFilter().size(); i++) {
                        arrayList.add(response.getStateFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    bi.atvState.setAdapter(arrayAdapter);
                    bi.atvState.showDropDown();
                } else {
                    ToastUtils.showToast(context, "No data found");
                }

            }
            break;
            case R.id.atvZip: {

                ArrayList<String> arrayList = new ArrayList<>();
                if (response.getZipCode() != null && response.getZipCode().size() > 0) {
                    for (int i = 0; i < response.getZipCode().size(); i++) {
                        arrayList.add(response.getZipCode().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    bi.atvZip.setAdapter(arrayAdapter);
                    bi.atvZip.showDropDown();
                } else {
                    ToastUtils.showToast(context, "No data found");
                }

            }
            break;
        }

    }

    @Override
    public void _updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void _updateUIonError(String error) {

       /* if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);

    }

    @Override
    public void _updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));


    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {

       /* if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);


    }

    @Override
    public void updateUIonFailure() {
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));

    }

    @Override
    public void onGalleryClick() {

        bottomDialogFragment.dismiss();
        oPenGallery();
    }

    @Override
    public void onCameraClick() {

        bottomDialogFragment.dismiss();
        accessCamera();
    }


    private void oPenGallery() {
        perm = 2;
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


    private void accessCamera() {
        perm = 1;
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            ImagePicker.cameraOnly().start(this);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_AND_STORAGE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        switch (perm) {
            case 1:
                accessCamera();
                break;
            case 2:
                oPenGallery();
                break;
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {

            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            if (images != null) {
                if (bottomDialogFragment != null)
                    bottomDialogFragment.dismissAllowingStateLoss();
            }
            new ImageCompression(AddOpenHousesActivity.this).execute(images.get(0).getPath());
        }
    }

    private class ImageCompression extends AsyncTask<String, Void, File> {


        private WeakReference<AddOpenHousesActivity> activityReference;

        // only retain a weak reference to the activity
        ImageCompression(AddOpenHousesActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected File doInBackground(String... params) {
            return ImageCompression(params[0]);
        }

        @Override
        protected void onPostExecute(File result) {
            Log.d("Test", "onPreExecute: " + "" + result);

            AddOpenHousesActivity activity = activityReference.get();
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

    private void showSpinnerDateDialog(final Context context, final EditText tvDateTime, boolean disablePastDates, String type) {

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        cal.add(Calendar.DATE, 0); // add 10 days


        if (disablePastDates) {

            new SpinnerDatePickerDialogBuilder().context(context)
                    .callback(new com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                            showTimePicker(years, monthOfYear, dayOfMonth, tvDateTime, type);
                        }
                    })
                    .showTitle(true)
                    .defaultDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                    .minDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                    .build()
                    .show();
        } else {

            new SpinnerDatePickerDialogBuilder().context(context)
                    .callback(new com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                            showTimePicker(years, monthOfYear, dayOfMonth, tvDateTime, type);
                        }
                    })
                    .showTitle(true)
                    .defaultDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                    .build()
                    .show();
        }

    }

    public void DateDialog(final Context context, final EditText tvDateTime, boolean disablePastDates, String type) {

        try {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    showTimePicker(year, monthOfYear, dayOfMonth, tvDateTime, type);

                }
            };
            final DatePickerDialog dpDialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 0); // add 10 days
            if (disablePastDates) {
                dpDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            }
            dpDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    dpDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorBlack));
                    dpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            });
            dpDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTimePicker(final int year, final int monthOfYear, final int dayOfMonth, final EditText tvDateTime, String type) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddOpenHousesActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                int month = monthOfYear + 1;
                if (month < 10 && dayOfMonth < 10) {
                    dateToServer = year + "-" + ("0" + month) + "-" + ("0" + dayOfMonth);
                } else if (month < 10) {
                    dateToServer = year + "-" + ("0" + month) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    dateToServer = year + "-" + month + "-" + ("0" + dayOfMonth);
                } else {
                    dateToServer = year + "-" + month + "-" + dayOfMonth;
                }
                if (selectedHour < 10 && selectedMinute < 10) {
                    timeToServer = ("0" + selectedHour) + ":" + ("0" + selectedMinute) + ":" + "00.000Z";
                } else if (selectedHour < 10) {
                    timeToServer = ("0" + selectedHour) + ":" + selectedMinute + ":" + "00.000Z";
                } else if (selectedMinute < 10) {
                    timeToServer = selectedHour + ":" + ("0" + selectedMinute) + ":" + "00.000Z";
                } else {
                    timeToServer = selectedHour + ":" + selectedMinute + ":" + "00.000Z";
                }

                if (type.equalsIgnoreCase("1")) {
                    timeS = selectedHour + ":" + selectedMinute + ":00";
                    openHouseStartDate = dateToServer + "T" + timeToServer;
                } else {
                    timeE = selectedHour + ":" + selectedMinute + ":00";
                    openHouseEndDate = dateToServer + "T" + timeToServer;
                }

                tvDateTime.setText(formattedTime(dayOfMonth + "-" + month + "-" + year + " " + selectedHour + ":" + selectedMinute));

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public String formattedTime(String time) {

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("MM-dd-yyyy  hh:mm a", Locale.getDefault()).format(dateObj);

        } catch (final ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void clearFocus() {

        bi.atvPrice.clearFocus();
        bi.atvAddress.clearFocus();
        bi.atvZip.clearFocus();
        bi.atvCity.clearFocus();
        bi.atvState.clearFocus();

    }
}
