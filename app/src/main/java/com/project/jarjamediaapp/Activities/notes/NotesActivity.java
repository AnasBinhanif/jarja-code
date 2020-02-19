package com.project.jarjamediaapp.Activities.notes;

import android.app.Activity;
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

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeNotesRecyclerAdapter;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotesBinding;

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

    String leadID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_notes);
        presenter = new NotesPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.notes), true);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNotes:
                presenter.getLeadNotes(leadID);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            presenter.getLeadNotes(leadID);
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
            swipeNotesRecyclerAdapter = new SwipeNotesRecyclerAdapter(context, getLeadNotes);
            bi.recyclerViewNotes.setAdapter(swipeNotesRecyclerAdapter);
        } else {
            swipeNotesRecyclerAdapter = new SwipeNotesRecyclerAdapter(context, getLeadNotes);
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

            //showCallDialog(context);

            Map<String, String> noteMap = new HashMap<>();
            noteMap.put("leadID", leadID);
            switchActivityWithIntentString(AddNotesActivity.class, (HashMap) noteMap);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog(context);
    }
}