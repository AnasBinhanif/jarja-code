package com.project.jarjamediaapp.Activities.open_houses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.project.jarjamediaapp.Activities.bottomsheet.BottomDialogFragment;
import com.project.jarjamediaapp.Activities.bottomsheet.HandleClickEvents;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.HorizontalAdapter;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.CenterZoomLayoutManager;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.UserPermissions;
import com.project.jarjamediaapp.databinding.ActivityOpenHousesBinding;

import java.io.File;
import java.lang.ref.WeakReference;
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
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

public class OpenHousesActivity extends BaseActivity implements View.OnClickListener, OpenHousesContract.View, HandleClickEvents, EasyPermissions.PermissionCallbacks {

    ActivityOpenHousesBinding bi;
    Context context = OpenHousesActivity.this;
    OpenHousesPresenter presenter;
    Dialog dialog;
    String listPrice = "", city = "", address = "", state = "", zip = "", image = "", dateTimeToServer = "";
    String openHouseStartDate = "", openHouseEndDate = "", dateToServer = "", timeToServer = "";
    private BottomDialogFragment bottomDialogFragment;
    private final int RC_CAMERA_AND_STORAGE = 100;
    private final int RC_CAMERA_ONLY = 101;
    File actualImage;
    File compressedImage;
    TextView tvSelectPictures, tvRemovePictures;
    Button btnSave, btnCancel;
    AutoCompleteTextView atvPrice, atvAddress, atvCity, atvState, atvZip, atvOpenHouseStartDate, atvOpenHouseEndDate;
    int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_open_houses);
        presenter = new OpenHousesPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.openHouses), true);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.btnUpcomingOH:

                presenter.getAllOpenHouses("upcoming");

                Paris.style(bi.btnUpcomingOH).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnPastOH).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnPastOH:

                presenter.getAllOpenHouses("past");

                Paris.style(bi.btnPastOH).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnUpcomingOH).apply(R.style.TabButtonTranparentLeft);

                break;

        }
    }

    @Override
    public void initViews() {

        UserPermissions.isCameraStorageLocationPermissionGranted(OpenHousesActivity.this);
        bi.btnPastOH.setOnClickListener(this);
        bi.btnUpcomingOH.setOnClickListener(this);
        presenter.getAllOpenHouses("upcoming");

    }

    public void showAddHouseDialog(Context context) {

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_open_house_dialog);

        atvPrice = dialog.findViewById(R.id.atvListingPrice);
        atvAddress = dialog.findViewById(R.id.atvAddress);
        atvCity = dialog.findViewById(R.id.atvCity);
        atvState = dialog.findViewById(R.id.atvState);
        atvZip = dialog.findViewById(R.id.atvPostalCode);
        atvOpenHouseStartDate = dialog.findViewById(R.id.atvStartDateTime);
        atvOpenHouseEndDate = dialog.findViewById(R.id.atvEndDateTime);

        tvSelectPictures = dialog.findViewById(R.id.tvSelectPicture);
        tvRemovePictures = dialog.findViewById(R.id.tvRemovePicture);

        atvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewId = view.getId();

            }
        });

        atvAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = atvAddress.getText().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(atvAddress.getText().toString(), "street");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });


        atvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewId = view.getId();

            }
        });

        atvCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = atvCity.getText().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(atvCity.getText().toString(), "city");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        atvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewId = view.getId();

            }
        });

        atvState.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = atvState.getText().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(atvState.getText().toString(), "state");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        atvZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewId = view.getId();

            }
        });

        atvZip.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = atvZip.getText().length();
                    try {
                        if (length > 0)
                            presenter.getAddressDetailByPrefix(atvZip.getText().toString(), "zip");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        atvOpenHouseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog(context, atvOpenHouseStartDate, true, "1");
            }
        });

        atvOpenHouseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog(context, atvOpenHouseEndDate, true, "2");
            }
        });

        tvSelectPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomDialogFragment = BottomDialogFragment.getInstance();
                bottomDialogFragment.setCLickHandleClickEvents(OpenHousesActivity.this);
                bottomDialogFragment.show(getSupportFragmentManager(), "Select Image");
            }
        });

        tvRemovePictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image = "";
                tvRemovePictures.setVisibility(View.GONE);
                tvSelectPictures.setVisibility(View.VISIBLE);

            }
        });

        btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss dialogue on api integration

                address = atvAddress.getText().toString();
                listPrice = atvPrice.getText().toString();
                city = atvCity.getText().toString();
                state = atvState.getText().toString();
                zip = atvZip.getText().toString();

                if (isValidate()) {
                    presenter.addOpenHouse(listPrice, city, address, state, zip, image, openHouseStartDate, openHouseEndDate);
                }

            }
        });

        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private boolean isValidate() {

        if (Methods.isEmpty(atvAddress)) {
            ToastUtils.showToast(context, R.string.error_address);
            atvAddress.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvCity)) {
            ToastUtils.showToast(context, R.string.error_city);
            atvCity.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvState)) {
            ToastUtils.showToast(context, R.string.error_state);
            atvState.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvZip)) {
            ToastUtils.showToast(context, R.string.error_zip);
            atvZip.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvOpenHouseStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_time);
            atvOpenHouseStartDate.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvOpenHouseEndDate)) {
            ToastUtils.showToast(context, R.string.error_end_time);
            atvOpenHouseEndDate.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        ToastUtils.showToastLong(context, "Open House Added Successfully");
        if (dialog != null) {
            dialog.dismiss();
        }
        presenter.getAllOpenHouses("upcoming");

    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_notify).setVisible(false);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            showAddHouseDialog(context);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void updateUIListForOpenHouses(Response<GetAllOpenHousesModel> response) {

        try {

            bi.rvOpenHouse.setLayoutManager(new CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false));
            bi.rvOpenHouse.setItemAnimator(new DefaultItemAnimator());
            bi.rvOpenHouse.setAdapter(new HorizontalAdapter(context, response.body().getData().openHouse));
            bi.rvOpenHouse.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAfterUploadFile(Response<UploadImageModel> response) {

        hideProgressBar();
        tvRemovePictures.setVisibility(View.VISIBLE);
        tvRemovePictures.setText("Image uploaded");
        tvSelectPictures.setVisibility(View.GONE);
        ToastUtils.showToast(context, "Image uploaded");
        image = response.body().getData();

    }

    @Override
    public void updateUIListForAddressDetail(AddressDetailModel.Data response) {

        try {

            switch (viewId) {

                case R.id.atvAddress: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < response.getStreetFilter().size(); i++) {
                        arrayList.add(response.getStreetFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    atvAddress.setAdapter(arrayAdapter);
                    atvAddress.showDropDown();
                    atvAddress.setThreshold(1);
                }
                break;
                case R.id.atvCity: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < response.getCityFilter().size(); i++) {
                        arrayList.add(response.getCityFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    atvCity.setAdapter(arrayAdapter);
                    atvCity.showDropDown();
                    atvCity.setThreshold(1);

                }
                break;
                case R.id.atvState: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < response.getStateFilter().size(); i++) {
                        arrayList.add(response.getStateFilter().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    atvState.setAdapter(arrayAdapter);
                    atvState.showDropDown();
                    atvState.setThreshold(1);

                }
                break;
                case R.id.atvPostalCode: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < response.getZipCode().size(); i++) {
                        arrayList.add(response.getZipCode().get(i).getN());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                    atvZip.setAdapter(arrayAdapter);
                    atvZip.showDropDown();
                    atvZip.setThreshold(1);

                }
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void _updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void _updateUIonError(String error) {

        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            ToastUtils.showToastLong(context, error);
        }

    }

    @Override
    public void _updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void updateUIonFalse(String message) {

        bi.rvOpenHouse.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUIonError(String error) {

        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            bi.rvOpenHouse.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIonFailure() {

        bi.rvOpenHouse.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

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

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
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

    @AfterPermissionGranted(RC_CAMERA_ONLY)
    private void accessCamera() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            ImagePicker.cameraOnly().start(this);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_ONLY, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
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
                bottomDialogFragment.dismissAllowingStateLoss();
            }
            new ImageCompression(OpenHousesActivity.this).execute(images.get(0).getPath());
        }
    }

    private class ImageCompression extends AsyncTask<String, Void, File> {


        private WeakReference<OpenHousesActivity> activityReference;

        // only retain a weak reference to the activity
        ImageCompression(OpenHousesActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected File doInBackground(String... params) {
            return ImageCompression(params[0]);
        }

        @Override
        protected void onPostExecute(File result) {
            Log.d("Test", "onPreExecute: " + "" + result);

            OpenHousesActivity activity = activityReference.get();
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
        mTimePicker = new TimePickerDialog(OpenHousesActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                    openHouseStartDate = dateToServer + "T" + timeToServer;
                } else {
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
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault());
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("MM-dd-yyyy  hh:mm a", Locale.getDefault()).format(dateObj);

        } catch (final ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
