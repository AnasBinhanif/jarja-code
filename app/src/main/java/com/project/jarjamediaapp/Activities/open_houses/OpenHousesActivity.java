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

import org.json.JSONException;
import org.json.JSONObject;

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
    String timeS = "", timeE = "";
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
    String openHouseType = "upcoming";

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

                openHouseType = "upcoming";
                presenter.getAllOpenHouses(openHouseType);

                Paris.style(bi.btnUpcomingOH).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnPastOH).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnPastOH:

                openHouseType = "past";
                presenter.getAllOpenHouses(openHouseType);

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

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        ToastUtils.showToastLong(context, "Open House Added Successfully");
        if (dialog != null) {
            dialog.dismiss();
        }
        presenter.getAllOpenHouses(openHouseType);

    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(OpenHousesActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
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

            //showAddHouseDialog(context);
            switchActivity(AddOpenHousesActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void updateUIListForOpenHouses(Response<GetAllOpenHousesModel> response) {

        try {

            if (response.body().getData().openHouse.size() > 0) {
                bi.rvOpenHouse.setLayoutManager(new CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false));
                bi.rvOpenHouse.setItemAnimator(new DefaultItemAnimator());
                bi.rvOpenHouse.setAdapter(new HorizontalAdapter(context, response.body().getData().openHouse,openHouseType));
                bi.rvOpenHouse.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
            } else {
                bi.rvOpenHouse.setVisibility(View.GONE);
                bi.tvMessage.setVisibility(View.VISIBLE);
            }

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
                    if (response.getStreetFilter().size() > 0) {
                        for (int i = 0; i < response.getStreetFilter().size(); i++) {
                            arrayList.add(response.getStreetFilter().get(i).getN());
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                        atvAddress.setAdapter(arrayAdapter);
                        atvAddress.showDropDown();
                        atvAddress.setThreshold(1);
                    } else {
                        ToastUtils.showToast(context, "No data found");
                    }
                }
                break;
                case R.id.atvCity: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    if (response.getCityFilter().size() > 0) {
                        for (int i = 0; i < response.getCityFilter().size(); i++) {
                            arrayList.add(response.getCityFilter().get(i).getN());
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                        atvCity.setAdapter(arrayAdapter);
                        atvCity.showDropDown();
                        atvCity.setThreshold(1);
                    } else {
                        ToastUtils.showToast(context, "No data found");
                    }

                }
                break;
                case R.id.atvState: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    if (response.getStateFilter().size() > 0) {
                        for (int i = 0; i < response.getStateFilter().size(); i++) {
                            arrayList.add(response.getStateFilter().get(i).getN());
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                        atvState.setAdapter(arrayAdapter);
                        atvState.showDropDown();
                        atvState.setThreshold(1);
                    } else {
                        ToastUtils.showToast(context, "No data found");
                    }

                }
                break;
                case R.id.atvZip: {

                    ArrayList<String> arrayList = new ArrayList<>();
                    if (response.getZipCode().size() > 0) {
                        for (int i = 0; i < response.getZipCode().size(); i++) {
                            arrayList.add(response.getZipCode().get(i).getN());
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayList);
                        atvZip.setAdapter(arrayAdapter);
                        atvZip.showDropDown();
                        atvZip.setThreshold(1);
                    } else {
                        ToastUtils.showToast(context, "No data found");
                    }

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllOpenHouses(openHouseType);
    }

    public void hitApiForRefresh(){
        presenter.getAllOpenHouses(openHouseType);
    }
}
