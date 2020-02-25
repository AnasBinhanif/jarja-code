package com.project.jarjamediaapp.Activities.lead_detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

import static com.vincent.filepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

public class LeadDetailActivity extends BaseActivity implements LeadDetailContract.View, EasyPermissions.PermissionCallbacks, HandleMultipleClickEvents {

    ActivityLeadDetailBinding bi;
    Context context = LeadDetailActivity.this;
    LeadDetailPresenter presenter;
    String title = "";

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<Integer> selectedLeadIdsList = new ArrayList<>();

    GetLead.LeadList getLeadListData;
    ArrayList<GetLead.AgentsList> getAssignedAgentList;

    String leadID = "";

    ArrayList<GetLeadTransactionStage.PipeLine> transactionPipeline;
    ArrayList<GetLeadTransactionStage.LeadTransactionOne> transactionOneListModel;
    ArrayList<GetLeadTransactionStage.LeadTransactionTwo> transactionTwoListModel;

    String currentStage1 = "", currentStage2 = "",transLeadID1="",transLeadID2="";
    String agentIdsString = "",agentLeadIdsString = "";

    BottomDialog bottomDialog;
    private final int RC_CAMERA_AND_STORAGE = 100;
    private final int RC_CAMERA_ONLY = 101;
    File actualImage;
    File compressedImage;

    AutoCompleteTextView atvCC, atvBCC, atvSubject, atvFrom;
    TextView tvTo,tvClose;
    MultiAutoCompleteTextView mAtvBody;
    Button choosePicture, btnSendEmail;
    LinearLayout lnAgent;

    ArrayList<String> agentLeadList;
    ArrayList<MultiSelectModel> searchLeadListItems;
    ArrayList<Integer> selectedLeadIdsList = new ArrayList<>();

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
                    map.put("leadID", leadID);
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map);

                    break;

                case 3:
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("title", getString(R.string.buying_info));
                    map1.put("leadID", leadID);
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
                    appointMap.put("leadName", getLeadListData.firstName + " " + getLeadListData.lastName);
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
                    taskMap.put("leadName", getLeadListData.firstName + " " + getLeadListData.lastName);
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

    private void openEmailComposer(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_compose_email);

        atvFrom = dialog.findViewById(R.id.atvFrom);
        tvTo = dialog.findViewById(R.id.tvToAgent);
        tvClose = dialog.findViewById(R.id.tvClose);
        atvCC = dialog.findViewById(R.id.atvCC);
        atvBCC = dialog.findViewById(R.id.atvBCC);
        atvSubject = dialog.findViewById(R.id.atvSubject);
        mAtvBody = dialog.findViewById(R.id.atvBody);
        choosePicture = dialog.findViewById(R.id.btnChooseFile);
        btnSendEmail = dialog.findViewById(R.id.btnSendEmail);
        lnAgent = dialog.findViewById(R.id.lnAgent);

        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomDialog = BottomDialog.getInstance();
                bottomDialog.setClickHandleEvents(LeadDetailActivity.this);
                bottomDialog.show(getSupportFragmentManager(), "Select File");

            }
        });

        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeadsAgentDialog();
            }
        });

        atvFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        atvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvFrom.showDropDown();
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // HIT API
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

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
                presenter.getLeadRecipient(leadID);
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
                intentT1.putExtra("leadID",transLeadID1);
                intentT1.putExtra("currentStage", currentStage1);
                intentT1.putExtra("Pipeline", transactionPipeline);
                startActivity(intentT1);

                break;

            case R.id.rlTransaction2:

                Intent intentT2 = new Intent(context, TransactionActivity.class);
                intentT2.putExtra("title", title);
                intentT2.putExtra("leadID",transLeadID1);
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
            String firstName = getLeadListData.firstName;
            String lastName = getLeadListData.lastName;
            if (firstName != null && lastName != null) {
                bi.tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));
                bi.tvName.setText(firstName + " " + lastName);
            } else if (firstName == null && lastName != null) {
                bi.tvInitial.setText("-" + lastName.substring(0, 1));
                bi.tvName.setText(lastName);
            } else if (firstName != null && lastName == null) {
                bi.tvInitial.setText(firstName.substring(0, 1) + "-");
                bi.tvName.setText(firstName);
            } else if (firstName == null && lastName == null) {
                bi.tvInitial.setText("-" + "-");
                bi.tvName.setText("");
            }
            bi.tvScore.setText(getLeadListData.leadScore);
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

            bi.tvStep1.setText(transactionOneListModel.get(transactionOneListModel.size()-1).currentStage);
            bi.tvDate1.setText(transactionOneListModel.get(transactionOneListModel.size()-1).date);

            currentStage1 = transactionOneListModel.get(transactionOneListModel.size()-1).currentStage;
            transLeadID1 = transactionOneListModel.get(transactionOneListModel.size()-1).encrypted_LeadDetailID;

        } else {
            bi.tvNoRecord.setVisibility(View.VISIBLE);
            bi.rlTransaction1.setVisibility(View.GONE);
            bi.rlTransaction2.setVisibility(View.GONE);
        }

        if (transactionTwoListModel != null) {
            currentStage2 = transactionTwoListModel.get(transactionTwoListModel.size()-1).currentStage;
            transLeadID2 = transactionTwoListModel.get(transactionOneListModel.size()-1).encrypted_LeadDetailID;
        }

    }

    @Override
    public void updateUIListForRecipient(LeadDetailModel.Data response) {

        openEmailComposer(context);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(response.fromEmailList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
        atvFrom.setAdapter(arrayAdapter);

        agentLeadList = new ArrayList<>();
        searchLeadListItems = new ArrayList<>();

        for (int i = 0; i < response.toEmailList.size(); i++) {
            agentLeadList.add(response.toEmailList.get(i));
            searchLeadListItems.add(new MultiSelectModel((i + 1), response.toEmailList.get(i)));
        }

    }

    @Override
    public void updateUIEmailSent(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIAfterFileUpload(Response<UploadImageModel> response) {

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
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

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
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    ArrayList<NormalFile> arrayListData = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    sendDocument(arrayListData.get(0).getPath());
                }
                break;
        }
    }

    private void sendDocument(String result) {

        File file = new File(result);
        Uri uri = Uri.fromFile(file);
        String type1 = GH.getInstance().getMimeType(LeadDetailActivity.this, uri);
        MediaType mediaType = MediaType.parse(type1);
        MultipartBody.Part multipartFile = null;
        RequestBody requestFile = RequestBody.create(mediaType, file);
        multipartFile = MultipartBody.Part.createFormData("", file.getName(), requestFile);

        //  presenter.uploadFileService(multipartFile, caseId);

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
            String type1 = GH.getInstance().getMimeType(LeadDetailActivity.this, uri);
            MediaType mediaType = MediaType.parse(type1);
            MultipartBody.Part file = null;
            if (result != null && actualImage != null) {
                RequestBody requestFile = RequestBody.create(mediaType, result);
                file = MultipartBody.Part.createFormData("", result.getName(), requestFile);

                //   presenter.uploadFileService(file, caseId);
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

    private void showLeadsAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                .setMaxSelectionLimit(0)//you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedLeadIdsList = new ArrayList<>();
                        selectedLeadIdsList = selectedIds;

                        if (lnAgent.getChildCount() > 0) {
                            lnAgent.removeAllViews();
                        }

                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            lnAgent.addView(child);

                        }

                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                        agentLeadIdsString = "";
                    }

                    @Override
                    public void onCancel() {
                    }
                });
        if (selectedLeadIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedLeadIdsList);
            multiSelectDialog.multiSelectList(searchLeadListItems);
        } else {
            multiSelectDialog.multiSelectList(searchLeadListItems);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

}