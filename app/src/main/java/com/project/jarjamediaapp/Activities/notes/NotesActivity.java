package com.project.jarjamediaapp.Activities.notes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.lead_detail.BottomDialog;
import com.project.jarjamediaapp.Activities.lead_detail.HandleMultipleClickEvents;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeDocumentRecyclerAdapter;
import com.project.jarjamediaapp.CustomAdapter.SwipeNotesRecyclerAdapter;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotesBinding;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

public class NotesActivity extends BaseActivity implements NotesContract.View, EasyPermissions.PermissionCallbacks, HandleMultipleClickEvents {

    ActivityNotesBinding bi;
    Context context = NotesActivity.this;
    NotesPresenter presenter;
    SwipeNotesRecyclerAdapter swipeNotesRecyclerAdapter;
    ArrayList<GetLeadNotes.NotesList> getLeadNotes;
    String buttonType = "Notes";
    public static ArrayList<UploadFilesModel> arrayListData;
    SwipeDocumentRecyclerAdapter swipeDocumentRecyclerAdapter;
    String leadID = "";
    BottomDialog bottomDialog;
    private final int RC_CAMERA_AND_STORAGE = 100;
    private final int RC_CAMERA_ONLY = 101;
    File actualImage;
    File compressedImage;
    MultipartBody.Part multipartFile = null;
    int perm = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        presenter = new NotesPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.notes), true);
        arrayListData = new ArrayList<UploadFilesModel>();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNotes: {

                bi.recyclerViewDocuments.setVisibility(View.GONE);
                bi.recyclerViewNotes.setVisibility(View.VISIBLE);
                buttonType = "Notes";
                Paris.style(bi.btnNotes).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDocuments).apply(R.style.TabButtonTranparentRight);
                presenter.getLeadNotes(leadID);
            }
            break;
            case R.id.btnDocuments: {

                bi.recyclerViewNotes.setVisibility(View.GONE);
                bi.recyclerViewDocuments.setVisibility(View.VISIBLE);
                buttonType = "Documents";
                Paris.style(bi.btnDocuments).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnNotes).apply(R.style.TabButtonTranparentLeft);
                presenter.getDocumentByLeadId(leadID);
            }
            break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> arrayListData = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    new ImageCompression().execute(arrayListData.get(0).getPath());

                }
                break;
            case Constant.REQUEST_CODE_TAKE_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> arrayListData = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    new ImageCompression().execute(arrayListData.get(0).getPath());

                }
                break;
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    ArrayList<NormalFile> arrayListData = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    sendDocument(arrayListData.get(0).getPath());
                }
                break;
            default:
                presenter.getLeadNotes(leadID);
        }

    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        bi.btnNotes.setOnClickListener(this);
        bi.btnDocuments.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void updateUI(GetNoteDropDown response) {

    }

    @Override
    public void updateUI(GetLeadNotes response) {

        getLeadNotes = new ArrayList<>();
        getLeadNotes = response.data.notesList;

        if (getLeadNotes.size() == 0) {

            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
            bi.recyclerViewNotes.setVisibility(View.GONE);

        } else {
            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.recyclerViewNotes.setVisibility(View.VISIBLE);
            populateDataDue();

        }
    }

    @Override
    public void updateUI(GetAgentsModel response) {

    }

    @Override
    public void updateUIListDocuments(DocumentModel response) {

        // on api success response call this method
        if (response.getData().size() > 0) {
            swipeDocumentRecyclerAdapter = new SwipeDocumentRecyclerAdapter(context, NotesActivity.this, response.getData());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewDocuments.getContext(), 1);
            bi.recyclerViewDocuments.setLayoutManager(mLayoutManager);
            bi.recyclerViewDocuments.setItemAnimator(new DefaultItemAnimator());
            bi.recyclerViewDocuments.addItemDecoration(dividerItemDecoration);
            bi.recyclerViewDocuments.setAdapter(swipeDocumentRecyclerAdapter);
            bi.recyclerViewDocuments.setVisibility(View.VISIBLE);
            bi.recyclerViewNotes.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
        } else {
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIListAfterAddDoc(BaseResponse response) {

        showLongToastMessage("Document added successfully");

    }

    private void populateDataDue() {

        if (swipeNotesRecyclerAdapter != null) {
            swipeNotesRecyclerAdapter = new SwipeNotesRecyclerAdapter(context, NotesActivity.this, getLeadNotes);
            bi.recyclerViewNotes.setAdapter(swipeNotesRecyclerAdapter);
        } else {
            swipeNotesRecyclerAdapter = new SwipeNotesRecyclerAdapter(context, NotesActivity.this, getLeadNotes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewNotes.getContext(), 1);
            bi.recyclerViewNotes.setLayoutManager(mLayoutManager);
            bi.recyclerViewNotes.setItemAnimator(new DefaultItemAnimator());
            bi.recyclerViewNotes.addItemDecoration(dividerItemDecoration);
            bi.recyclerViewNotes.setAdapter(swipeNotesRecyclerAdapter);
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

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

            if (buttonType.equalsIgnoreCase("Notes")) {
                Map<String, String> noteMap = new HashMap<>();
                noteMap.put("leadID", leadID);
                switchActivityWithIntentString(AddNotesActivity.class, (HashMap) noteMap);
            } else {

                String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(context, perms)) {
                    // Already have permission, do the thing
                    bottomDialog = BottomDialog.getInstance();
                    bottomDialog.setClickHandleEvents(NotesActivity.this);
                    bottomDialog.show(getSupportFragmentManager(), "Select File");
                } else {
                    // Do not have permissions, request them now
                    EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                            RC_CAMERA_AND_STORAGE, perms);
                }


            }

            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(NotesActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void onGalleryClick() {

        bottomDialog.dismiss();
        oPenGallery();
    }

    @Override
    public void onCameraClick() {

        bottomDialog.dismiss();
        accessCamera();
    }

    @Override
    public void onDocumentClick() {

        bottomDialog.dismiss();
        accessDocuments();

    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    private void oPenGallery() {

        perm = 2;
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            Intent imageIntent = new Intent(context, ImagePickActivity.class);
            imageIntent.putExtra(IS_NEED_CAMERA, false);
            imageIntent.putExtra(Constant.MAX_NUMBER, 1);
            startActivityForResult(imageIntent, Constant.REQUEST_CODE_PICK_IMAGE);


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_AND_STORAGE, perms);
        }
    }

    @AfterPermissionGranted(RC_CAMERA_ONLY)
    private void accessCamera() {

        perm = 1;
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            Intent imageIntent = new Intent(context, ImagePickActivity.class);
            imageIntent.putExtra(IS_NEED_CAMERA, true);
            imageIntent.putExtra(Constant.MAX_NUMBER, 1);
            startActivityForResult(imageIntent, Constant.REQUEST_CODE_TAKE_IMAGE);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_ONLY, perms);
        }
    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    private void accessDocuments() {
        perm = 3;
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            Intent documentIntent = new Intent(context, NormalFilePickActivity.class);
            documentIntent.putExtra(Constant.MAX_NUMBER, 1);
            documentIntent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
            startActivityForResult(documentIntent, Constant.REQUEST_CODE_PICK_FILE);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message),
                    RC_CAMERA_ONLY, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        bottomDialog = BottomDialog.getInstance();
        bottomDialog.setClickHandleEvents(NotesActivity.this);
        bottomDialog.show(getSupportFragmentManager(), "Select File");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private void sendDocument(String result) {

        File file = new File(result);
        Uri uri = Uri.fromFile(file);
        String type1 = GH.getInstance().getMimeType(NotesActivity.this, uri);
        MediaType mediaType = MediaType.parse(type1);
        RequestBody requestFile = RequestBody.create(mediaType, file);
        multipartFile = MultipartBody.Part.createFormData("", file.getName(), requestFile);
        presenter.addDocumentByLeadId(multipartFile, leadID);


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

    public class ImageCompression extends AsyncTask<String, Void, File> {

        // only retain a weak reference to the activity
        ImageCompression() {

        }

        @Override
        protected File doInBackground(String... params) {
            return ImageCompression(params[0]);
        }

        @Override
        protected void onPostExecute(File result) {

            Uri uri = Uri.fromFile(result);
            String type1 = GH.getInstance().getMimeType(NotesActivity.this, uri);
            MediaType mediaType = MediaType.parse(type1);

            if (result != null && actualImage != null) {
                RequestBody requestFile = RequestBody.create(mediaType, result);
                multipartFile = MultipartBody.Part.createFormData("", result.getName(), requestFile);
                presenter.addDocumentByLeadId(multipartFile, leadID);
            }
        }

        @Override
        protected void onPreExecute() {

            Log.d("Test", "onPreExecute: " + "");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void refreshData() {

        if (buttonType.equalsIgnoreCase("Notes")) {
            presenter.getLeadNotes(leadID);
        } else {
            presenter.getDocumentByLeadId(leadID);

        }
    }

}
