package com.project.jarjamediaapp.Activities.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
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
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

public class NotesActivity extends BaseActivity implements NotesContract.View {

    ActivityNotesBinding bi;
    Context context = NotesActivity.this;
    NotesPresenter presenter;
    SwipeNotesRecyclerAdapter swipeNotesRecyclerAdapter;
    ArrayList<GetLeadNotes.NotesList> getLeadNotes;
    String buttonType = "Notes";
    public static ArrayList<UploadFilesModel> arrayListData;
    SwipeDocumentRecyclerAdapter swipeDocumentRecyclerAdapter;
    String leadID = "";

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
                buttonType = "Notes";
                Paris.style(bi.btnNotes).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDocuments).apply(R.style.TabButtonTranparentRight);
                presenter.getLeadNotes(leadID);
            }
            break;
            case R.id.btnDocuments: {

                bi.recyclerViewNotes.setVisibility(View.GONE);
                buttonType = "Documents";
                Paris.style(bi.btnDocuments).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnNotes).apply(R.style.TabButtonTranparentLeft);
            }
            break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                int addedListSize = arrayListData.size();
                int selectedListSize = list.size();
                int totalSize = addedListSize + selectedListSize;

                if (totalSize <= 10) {
                    for (int i = 0; i < list.size(); i++) {
                        arrayListData.add(new UploadFilesModel(list.get(i).getPath(), list.get(i).getName()));
                    }
                    populateList(arrayListData);
                } else {
                    ToastUtils.showToast(context, getString(R.string.cannot_add_file));
                }

            } else {
                presenter.getLeadNotes(leadID);
            }

        }
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        presenter.getLeadNotes(leadID);
        bi.btnNotes.setOnClickListener(this);
        bi.btnDocuments.setOnClickListener(this);

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

                Intent documentIntent = new Intent(context, NormalFilePickActivity.class);
                documentIntent.putExtra(Constant.MAX_NUMBER, 10);
                documentIntent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
                startActivityForResult(documentIntent, Constant.REQUEST_CODE_PICK_FILE);

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

    private void populateList(ArrayList<UploadFilesModel> arrayListData) {

        swipeDocumentRecyclerAdapter = new SwipeDocumentRecyclerAdapter(context, NotesActivity.this, arrayListData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewDocuments.getContext(), 1);
        bi.recyclerViewDocuments.setLayoutManager(mLayoutManager);
        bi.recyclerViewDocuments.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewDocuments.addItemDecoration(dividerItemDecoration);
        bi.recyclerViewDocuments.setAdapter(swipeDocumentRecyclerAdapter);
        bi.recyclerViewDocuments.setVisibility(View.VISIBLE);

    }


}