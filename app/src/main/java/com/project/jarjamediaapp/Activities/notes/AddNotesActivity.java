package com.project.jarjamediaapp.Activities.notes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeNotesRecyclerAdapter;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddNotesBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Response;

public class AddNotesActivity extends BaseActivity implements NotesContract.View {

    ActivityAddNotesBinding bi;
    Context context = AddNotesActivity.this;
    NotesPresenter presenter;
    SwipeNotesRecyclerAdapter swipeNotesRecyclerAdapter;

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();

    ArrayList<GetNoteDropDown.Data> getNoteDropDown;
    ArrayList<String> getNoteDropDownNames;

    String leadID;
    String agentIdsString = "";

    GetLeadNotes.NotesList notesListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_notes);
        presenter = new NotesPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_notes), true);
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        presenter.getAgentNames();
        presenter.getNoteDropDown();
        bi.edtAgent.setOnClickListener(this);
        bi.btnAdd.setOnClickListener(this);

        if (SwipeNotesRecyclerAdapter.isEditable) {
            notesListModel = (GetLeadNotes.NotesList) getIntent().getSerializableExtra("Note");
            bi.edtDescription.setText(notesListModel.desc);
            if (notesListModel.noteType.contains("Call")) {
                bi.spnNoteType.setSelectedIndex(1);
            } else {
                bi.spnNoteType.setSelectedIndex(0);
            }
            if (notesListModel.isSticky) {
                bi.cbNoteSticky.setChecked(true);
            } else {
                bi.cbNoteSticky.setChecked(true);
            }
            bi.btnAdd.setText("Update");
        }
    }


    private void callAddNote() {

        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String noteID = "0";
        String leadID = this.leadID + "";
        String noteType = getNoteDropDown.get(bi.spnNoteType.getSelectedIndex()).value + "";
        String desc = bi.edtDescription.getText().toString() + "";
        String isSticky = bi.cbNoteSticky.isChecked() ? "true" : "false";
        String dated = dateFormatter.format(newCalendar.getTime()) + "";
        String agentIDs = agentIdsString;
        String leadStringID = leadID;


        presenter.addNote(noteID, leadID, noteType, desc, isSticky, dated, agentIDs, leadStringID);

    }

    private void showAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedIdsList = new ArrayList<>();
                        selectedIdsList = selectedIds;
                        if (bi.lnAgents.getChildCount() > 0) {
                            bi.lnAgents.removeAllViews();
                        }

                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnAgents.addView(child);
                        }

                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                        for (String i : selectedEncyrptedIds) {

                            if (agentIdsString.equals("")) {
                                agentIdsString = String.valueOf(i);
                            } else {
                                agentIdsString = agentIdsString + "," + i;
                            }

                        }

                    }

                    @Override
                    public void onCancel() {
                    }
                });
        if (selectedIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedIdsList);
            multiSelectDialog.multiSelectList(searchListItems);
        } else {
            multiSelectDialog.multiSelectList(searchListItems);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    @Override
    public void updateUI(GetNoteDropDown response) {

        getNoteDropDown = new ArrayList<>();
        getNoteDropDownNames = new ArrayList<>();
        getNoteDropDown = response.data;
        for (GetNoteDropDown.Data model : getNoteDropDown) {
            getNoteDropDownNames.add(model.text);
        }
        bi.spnNoteType.setBackground(getDrawable(R.drawable.bg_search));
        bi.spnNoteType.setItems(getNoteDropDownNames);
    }

    @Override
    public void updateUI(GetLeadNotes response) {

    }

    @Override
    public void updateUI(GetAgentsModel response) {

        agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equalsIgnoreCase("Success")) {
            ToastUtils.showToast(context, "Added Successfully");
            finish();
        }

    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {
        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            ToastUtils.showToastLong(context, error);
        }
    }

    @Override
    public void updateUIonFailure() {
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edtAgent:
                showAgentDialog();
                break;
            case R.id.btnAdd:
                SwipeNotesRecyclerAdapter.isEditable=false;
                callAddNote();
                break;
        }
    }

}