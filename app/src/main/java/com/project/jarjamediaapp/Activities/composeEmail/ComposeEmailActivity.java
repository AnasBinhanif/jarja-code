package com.project.jarjamediaapp.Activities.composeEmail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddAppointmentBinding;

import java.util.ArrayList;

import retrofit2.Response;

public class ComposeEmailActivity extends BaseActivity implements ComposeEmailContract.View, View.OnClickListener {

    ActivityAddAppointmentBinding bi;
    Context context = ComposeEmailActivity.this;
    ComposeEmailPresenter presenter;
    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<String> arrayListReminderValue, arrayListReminderText;
    String agentIdsString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment);
        presenter = new ComposeEmailPresenter(this);
        presenter.initScreen();

    }

    @Override
    public void initViews() {

        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.atvReminder.setOnClickListener(this);
        bi.atvVia.setOnClickListener(this);
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.tvEndDate.setOnClickListener(this);
        bi.tvEndTime.setOnClickListener(this);
        bi.tvAgent.setOnClickListener(this);
        bi.tvName.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);


    }

    private void getUpdatedData() {

        // fromId = getIntent().getStringExtra("from");

        GetAppointmentsModel.Data modelData = getIntent().getParcelableExtra("model");

        /*
         agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
        }
         */

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.tvAgent:
                showAgentDialog();
                break;
            case R.id.btnSave:
                composeEmail();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equalsIgnoreCase("Success")) {
            ToastUtils.showToast(context, "Added Successfully");
            finish();
        }
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
                        if (bi.lnAgent.getChildCount() > 0) {
                            bi.lnAgent.removeAllViews();
                        }

                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnAgent.addView(child);

                        }
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                        agentIdsString = "";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (agentIdsString.equals("")) {
                                    agentIdsString = i;
                                } else {
                                    agentIdsString = agentIdsString + "," + i;
                                }
                            }
                        } else {
                            ToastUtils.showToast(context, "No EncryptedID Found");
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

    private void composeEmail() {

        if (isValidate()) {
            // Methods for Compose Email

        }
    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.tvName)) {
            ToastUtils.showToast(context, R.string.error_name);
            bi.tvName.requestFocus();
            return false;
        }
        if (agentIdsString.equalsIgnoreCase("")) {
            ToastUtils.showToast(context, R.string.error_agent);
            bi.tvAgent.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvDescription)) {
            ToastUtils.showToast(context, R.string.error_description);
            bi.atvDescription.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void updateUIListForRecipient(GetLocationModel.Data response) {

        try {

            ArrayList<String> filterData = new ArrayList<>();
            for (int i = 0; i < response.getGroupName().size(); i++) {
                filterData.add(response.getGroupName().get(i).getLabel());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, filterData);
            bi.atvLocation.setAdapter(arrayAdapter);
            bi.atvLocation.showDropDown();
            bi.atvLocation.setThreshold(1);

        } catch (Exception e) {
            e.printStackTrace();
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

        GH.getInstance().ShowProgressDialog(ComposeEmailActivity.this);

    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();

    }

}