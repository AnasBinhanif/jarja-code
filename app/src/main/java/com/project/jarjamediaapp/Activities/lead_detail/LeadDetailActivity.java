package com.project.jarjamediaapp.Activities.lead_detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_lead.AddLeadActivity;
import com.project.jarjamediaapp.Activities.appointments.AppointmentActivity;
import com.project.jarjamediaapp.Activities.followups.FollowupsActivity;
import com.project.jarjamediaapp.Activities.listing_info.ListingInfoActivity;
import com.project.jarjamediaapp.Activities.notes.NotesActivity;
import com.project.jarjamediaapp.Activities.social_profiles.Social_ProfilesActivity;
import com.project.jarjamediaapp.Activities.tags.TagsActivity;
import com.project.jarjamediaapp.Activities.tasks.TasksActivity;
import com.project.jarjamediaapp.Activities.transactions.TransactionActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadDetails;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.UserPermissions;
import com.project.jarjamediaapp.databinding.ActivityLeadDetailBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class LeadDetailActivity extends BaseActivity implements LeadDetailContract.View {

    ActivityLeadDetailBinding bi;
    Context context = LeadDetailActivity.this;
    LeadDetailPresenter presenter;
    String title = "";

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();

    GetLead.LeadList getLeadListData;
    ArrayList<GetLead.AgentsList> getAssignedAgentList;

    String leadID = "";

    ArrayList<GetLeadTransactionStage.PipeLine> transactionPipeline;
    ArrayList<GetLeadTransactionStage.LeadTransactionOne> transactionOneListModel;
    ArrayList<GetLeadTransactionStage.LeadTransactionTwo> transactionTwoListModel;

    String currentStage1 = "", currentStage2 = "";
    String agentIdsString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getString(R.string.transaction1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_lead_detail);
        presenter = new LeadDetailPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.lead_details), true);

    }

    private void populateListData() {

        List<GetLeadDetails> leadsList = new ArrayList<>();

        leadsList.add(new GetLeadDetails("Follow Ups"));
        leadsList.add(new GetLeadDetails("Social Profiles"));
        leadsList.add(new GetLeadDetails("Listing Info"));
        leadsList.add(new GetLeadDetails("Buying Info"));
        leadsList.add(new GetLeadDetails("Tags"));
        leadsList.add(new GetLeadDetails("Appointments"));
        leadsList.add(new GetLeadDetails("Notes"));
        leadsList.add(new GetLeadDetails("Tasks"));

        bi.recyclerLeadDetails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerLeadDetails.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerLeadDetails.addItemDecoration(new DividerItemDecoration(bi.recyclerLeadDetails.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_lead_details);
        recyclerAdapterUtil.addViewsList(R.id.tvName);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadDetails, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            return Unit.INSTANCE;
        });

        bi.recyclerLeadDetails.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadDetails, Integer, Unit>) (viewComplainList, position) -> {

            switch (position) {

                case 0:
                    Map<String, String> followMap = new HashMap<>();
                    followMap.put("leadID", leadID);
                    switchActivityWithIntentString(FollowupsActivity.class, (HashMap<String, String>) followMap);

                    break;

                case 1:

                    Map<String, String> socialMap = new HashMap<>();
                    socialMap.put("leadID", leadID);
                    switchActivityWithIntentString(Social_ProfilesActivity.class, (HashMap<String, String>) socialMap);

                    break;

                case 2:

                    Map<String, String> map = new HashMap<>();
                    map.put("title", getString(R.string.listing_info));
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map);

                    break;

                case 3:
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("title", getString(R.string.buying_info));
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map1);

                    break;

                case 4:
                    Map<String, String> tagMap = new HashMap<>();
                    tagMap.put("leadID", leadID);
                    switchActivityWithIntentString(TagsActivity.class, (HashMap<String, String>) tagMap);

                    break;
                case 5:

                    Map<String, String> appointMap = new HashMap<>();
                    appointMap.put("leadID", leadID);
                    switchActivityWithIntentString(AppointmentActivity.class, (HashMap<String, String>) appointMap);


                    break;

                case 6:
                    Map<String, String> noteMap = new HashMap<>();
                    noteMap.put("leadID", leadID);
                    switchActivityWithIntentString(NotesActivity.class, (HashMap) noteMap);

                    break;
                case 7:
                    Map<String, String> taskMap = new HashMap<>();
                    taskMap.put("leadID", leadID);
                    switchActivityWithIntentString(TasksActivity.class, (HashMap<String, String>) taskMap);

                    break;

            }

            return Unit.INSTANCE;
        });

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

                        presenter.assignAgents(agentIdsString, leadID, "true");
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

    private void openEmailComposer(String[] recipients) {

        if (recipients.length == 0 || recipients[0].equals("")) {
            ToastUtils.showToast(context, "No Primary Phone Found");
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.setType("text/html");
            startActivity(Intent.createChooser(intent, "Send mail"));
        }
    }

    private void openMessageComposer(String phoneNo, String message) {

        if (phoneNo.equals("") || phoneNo.equals("null") || phoneNo == null) {
            ToastUtils.showToast(context, "No Primary Phone Found");
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phoneNo);
            smsIntent.putExtra("sms_body", message);
            startActivity(smsIntent);
        }
    }

    private void callDialer(String phoneNo) {
        if (phoneNo.equals("") || phoneNo.equals("null") || phoneNo == null) {
            ToastUtils.showToast(context, "No Primary Phone Found");
        } else {
            if (UserPermissions.isPhonePermissionGranted(LeadDetailActivity.this)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo)));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgEmail:
                String[] recipients = {getLeadListData.primaryEmail + ""};
                openEmailComposer(recipients);
                break;

            case R.id.imgMessage:
                openMessageComposer(getLeadListData.primaryPhone + "", "");
                break;

            case R.id.imgCall:
                callDialer(getLeadListData.primaryPhone + "");
                break;

            case R.id.imgEditLead:
                Intent intent = new Intent(context, AddLeadActivity.class);
                intent.putExtra("Lead", getLeadListData);
                context.startActivity(intent);
                break;

            case R.id.fbAssignedTo:
                showAgentDialog();
                break;

            case R.id.rlTransaction1:

                Intent intentT1 = new Intent(context, TransactionActivity.class);
                intentT1.putExtra("title", title);
                intentT1.putExtra("currentStage", currentStage1);
                intentT1.putExtra("Pipeline", transactionPipeline);
                startActivity(intentT1);

                break;

            case R.id.rlTransaction2:

                Intent intentT2 = new Intent(context, TransactionActivity.class);
                intentT2.putExtra("title", title);
                intentT2.putExtra("currentStage", currentStage2);
                intentT2.putExtra("Pipeline", transactionPipeline);
                startActivity(intentT2);

                break;

            case R.id.btnActions:

                bi.lnActions.setVisibility(View.VISIBLE);
                bi.recyclerLeadDetails.setVisibility(View.GONE);
                Paris.style(bi.btnActions).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnDetails:

                bi.lnActions.setVisibility(View.GONE);
                bi.recyclerLeadDetails.setVisibility(View.VISIBLE);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnActions).apply(R.style.TabButtonTranparentLeft);

                break;

            case R.id.btnTransaction1:

                title = getString(R.string.transaction1);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);

                if (transactionOneListModel != null) {
                    if (transactionOneListModel.size() != 0) {
                        bi.rlTransaction1.setVisibility(View.VISIBLE);
                        bi.rlTransaction2.setVisibility(View.GONE);
                        bi.tvNoRecord.setVisibility(View.GONE);
                    } else {
                        bi.tvNoRecord.setVisibility(View.VISIBLE);
                        bi.rlTransaction1.setVisibility(View.GONE);
                        bi.rlTransaction2.setVisibility(View.GONE);
                    }
                } else {
                    bi.tvNoRecord.setVisibility(View.VISIBLE);
                    bi.rlTransaction1.setVisibility(View.GONE);
                    bi.rlTransaction2.setVisibility(View.GONE);
                }

                break;

            case R.id.btnTransaction2:

                title = getString(R.string.transaction2);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonTranparentLeft);

                if (transactionTwoListModel != null) {
                    if (transactionTwoListModel.size() != 0) {
                        bi.rlTransaction2.setVisibility(View.VISIBLE);
                        bi.rlTransaction1.setVisibility(View.GONE);
                        bi.tvNoRecord.setVisibility(View.GONE);
                    } else {
                        bi.tvNoRecord.setVisibility(View.VISIBLE);
                        bi.rlTransaction1.setVisibility(View.GONE);
                        bi.rlTransaction2.setVisibility(View.GONE);
                    }
                } else {
                    bi.tvNoRecord.setVisibility(View.VISIBLE);
                    bi.rlTransaction1.setVisibility(View.GONE);
                    bi.rlTransaction2.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {
        leadID = getIntent().getStringExtra("leadID");
        bi.scLeadDetail.setVisibility(View.GONE);
        bi.tvNoRecordFound.setVisibility(View.GONE);

        initListeners();
        populateListData();
        presenter.getAgentNames();
        presenter.getLead(leadID);
        presenter.getTransaction(leadID);
    }

    private void initListeners() {
        bi.btnActions.setOnClickListener(this);
        bi.btnDetails.setOnClickListener(this);
        bi.rlTransaction1.setOnClickListener(this);
        bi.rlTransaction2.setOnClickListener(this);
        bi.btnTransaction1.setOnClickListener(this);
        bi.btnTransaction2.setOnClickListener(this);
        bi.fbAssignedTo.setOnClickListener(this);
        bi.imgCall.setOnClickListener(this);
        bi.imgEmail.setOnClickListener(this);
        bi.imgMessage.setOnClickListener(this);
        bi.imgEditLead.setOnClickListener(this);
    }

    private void populateListData(ArrayList<GetLead.AgentsList> leadsList) {

        bi.recyclerLeadAgent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        bi.recyclerLeadAgent.setItemAnimator(new DefaultItemAnimator());
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_lead_details_agents_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvAssignedToName, R.id.imgAssignedTo);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLead.AgentsList, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvAssignedToName);
            tvName.setText(String.valueOf(allLeadsList.agentName));


            return Unit.INSTANCE;
        });

        bi.recyclerLeadAgent.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLead.AgentsList, Integer, Unit>) (viewComplainList, integer) -> {


            return Unit.INSTANCE;
        });


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
    public void updateUI(GetLead response) {

        getAssignedAgentList = new ArrayList<>();
        getLeadListData = response.data.leadList;
        getAssignedAgentList = response.data.leadList.agentsList;

        if (getLeadListData != null) {

            if (getAssignedAgentList.size() != 0) {
                for (GetLead.AgentsList model : getAssignedAgentList) {
                    selectedIdsList.add(model.agentID);
                }
            }


            bi.scLeadDetail.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);

            bi.tvID.setText(getLeadListData.leadStringID);
            bi.tvName.setText(getLeadListData.firstName + " " + getLeadListData.lastName);
            bi.tvScore.setText(getLeadListData.leadScore);
            //bi.tvScore.setText(getLeadListData.);
        } else {
            bi.scLeadDetail.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        }

        if (getAssignedAgentList.size() == 0 || getAssignedAgentList == null) {
            bi.tvNoAgentAssigned.setVisibility(View.VISIBLE);
            bi.recyclerLeadAgent.setVisibility(View.GONE);
        } else {
            bi.tvNoAgentAssigned.setVisibility(View.GONE);
            bi.recyclerLeadAgent.setVisibility(View.VISIBLE);
            populateListData(getAssignedAgentList);
        }
    }

    @Override
    public void updateUI(GetLeadTransactionStage response) {

        transactionPipeline = new ArrayList<>();
        transactionOneListModel = new ArrayList<>();
        transactionTwoListModel = new ArrayList<>();

        transactionPipeline = response.data.pipeLines;
        transactionOneListModel = response.data.leadTransactionOne;
        transactionTwoListModel = response.data.leadTransactionTwo;

        if (transactionOneListModel.size() != 0) {

            bi.tvNoRecord.setVisibility(View.GONE);
            bi.rlTransaction2.setVisibility(View.GONE);
            bi.rlTransaction1.setVisibility(View.VISIBLE);

            bi.tvStep1.setText(transactionOneListModel.get(0).currentStage);
            bi.tvDate1.setText(transactionOneListModel.get(0).date);

            currentStage1 = transactionOneListModel.get(0).currentStage;

        } else {
            bi.tvNoRecord.setVisibility(View.VISIBLE);
            bi.rlTransaction1.setVisibility(View.GONE);
            bi.rlTransaction2.setVisibility(View.GONE);
        }

        if (transactionTwoListModel != null) {
            currentStage2 = transactionTwoListModel.get(0).currentStage;
        }

    }

    public void showCallDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_assignedto_dialog);

        EditText txtCall1 = dialog.findViewById(R.id.edtAgent);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equals("Success")) {
            ToastUtils.showToast(context, "Assigned Successfully");
            presenter.getAgentNames();
            presenter.getLead(leadID);
            presenter.getTransaction(leadID);
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

}