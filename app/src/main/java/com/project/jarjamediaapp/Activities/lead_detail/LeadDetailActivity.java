package com.project.jarjamediaapp.Activities.lead_detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.airbnb.paris.Paris;
import com.bumptech.glide.Glide;
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
import com.project.jarjamediaapp.Utilities.Call.VoiceActivity;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityLeadDetailBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    ArrayList<GetLead.LeadDripCompaignList> getAssignedDripCompaign;
    ArrayList<GetLead.LeadTaskList> getAssignedTagList;

    String leadID = "", primaryPhoneNumber = "", primaryEmail = "";

    ArrayList<GetLeadTransactionStage.PipeLine> transactionPipeline;
    ArrayList<GetLeadTransactionStage.LeadTransactionOne> transactionOneListModel;
    ArrayList<GetLeadTransactionStage.LeadTransactionTwo> transactionTwoListModel;

    String currentStage1 = "", currentStage2 = "", transLeadID1 = "", transLeadID2 = "";
    String agentIdsString = "", agentLeadIdsString = "";

    BottomDialog bottomDialog;
    private final int RC_CAMERA_AND_STORAGE = 100;
    private final int RC_CAMERA_ONLY = 101;
    File actualImage;
    File compressedImage;

    AutoCompleteTextView atvCC, atvBCC, atvSubject, atvFrom;
    TextView tvTo, tvClose, filePreviewName;
    MultiAutoCompleteTextView mAtvBody;
    Button choosePicture, btnSendEmail;
    LinearLayout lnAgent;

    ArrayList<String> agentLeadList;
    ArrayList<MultiSelectModel> searchLeadListItems;
    Dialog dialog, mDialog;
    String fileUrl = "";
    MultipartBody.Part multipartFile = null;
    AutoCompleteTextView atvPhone;
    TextView _close;
    MultiAutoCompleteTextView mAtvMessage;
    Button btnSend, btnCancel;
    int perm = 0;
    boolean back = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getString(R.string.transaction1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_lead_detail);
        presenter = new LeadDetailPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.lead_details), true);

    }


    @Override
    public void initViews() {

        leadID = getIntent().getStringExtra("leadID");
        bi.scLeadDetail.setVisibility(View.GONE);
        bi.tvNoRecordFound.setVisibility(View.GONE);

        initListeners();
        populateListData();
        // presenter.getAgentNames();
        //  presenter.getLead(leadID);
        //  presenter.getTransaction(leadID);

    }

    @Override
    protected void onResume() {
        super.onResume();

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
    }

    private void populateListData() {

        List<GetLeadDetails> leadsList = new ArrayList<>();

        leadsList.add(new GetLeadDetails("Edit Lead"));
        leadsList.add(new GetLeadDetails("Tags"));
        leadsList.add(new GetLeadDetails("Social Profiles"));
        leadsList.add(new GetLeadDetails("Listing Info"));
        leadsList.add(new GetLeadDetails("Buying Info"));
        leadsList.add(new GetLeadDetails("Appointments"));
        leadsList.add(new GetLeadDetails("Follow Ups"));
        leadsList.add(new GetLeadDetails("Tasks"));
        leadsList.add(new GetLeadDetails("Notes"));


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
                    Intent intent = new Intent(context, AddLeadActivity.class);
                    intent.putExtra("Lead", getLeadListData);
                    intent.putExtra("isEdit", true);
                    context.startActivity(intent);
                    break;
                case 1:
                    Map<String, String> tagMap = new HashMap<>();
                    tagMap.put("leadID", leadID);
                    switchActivityWithIntentString(TagsActivity.class, (HashMap<String, String>) tagMap);
                    break;

                case 2:
                    Map<String, String> socialMap = new HashMap<>();
                    socialMap.put("leadID", leadID);
                    switchActivityWithIntentString(Social_ProfilesActivity.class, (HashMap<String, String>) socialMap);
                    break;
                case 3:
                    Map<String, String> map = new HashMap<>();
                    map.put("title", getString(R.string.listing_info));
                    map.put("leadID", leadID);
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map);
                    break;
                case 4:
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("title", getString(R.string.buying_info));
                    map1.put("leadID", leadID);
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map1);
                    break;
                case 5:
                    Map<String, String> appointMap = new HashMap<>();
                    appointMap.put("leadID", leadID);
                    appointMap.put("leadName", getLeadListData.firstName + " " + getLeadListData.lastName);
                    switchActivityWithIntentString(AppointmentActivity.class, (HashMap<String, String>) appointMap);
                    break;
                case 6:

                    Map<String, String> followMap = new HashMap<>();
                    followMap.put("leadID", leadID);
                    switchActivityWithIntentString(FollowupsActivity.class, (HashMap<String, String>) followMap);
                    break;

                case 7:
                    Map<String, String> taskMap = new HashMap<>();
                    taskMap.put("leadID", leadID);
                    taskMap.put("leadName", getLeadListData.firstName + " " + getLeadListData.lastName);
                    switchActivityWithIntentString(TasksActivity.class, (HashMap<String, String>) taskMap);
                    break;


                case 8:
                    Map<String, String> noteMap = new HashMap<>();
                    noteMap.put("leadID", leadID);
                    switchActivityWithIntentString(NotesActivity.class, (HashMap) noteMap);
                    break;

            }

            return Unit.INSTANCE;
        });

    }

    private void showAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents:  \n\nNote: Switching the primary agent will stop all current drip campaigns and any future campaigns will now come from new primary agent assigned. In addition, all birthday and/or anniversary notifications will be turned off for this lead and will need to be turned back on by the new primary agent.") //setting title for dialog
                .titleSize(16)
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

                        presenter.assignAgents(agentIdsString, leadID, true);
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

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_compose_email);

        atvFrom = dialog.findViewById(R.id.atvFrom);
        tvTo = dialog.findViewById(R.id.tvToAgent);
        tvClose = dialog.findViewById(R.id.tvClose);
        atvCC = dialog.findViewById(R.id.atvCC);
        atvBCC = dialog.findViewById(R.id.atvBCC);
        atvSubject = dialog.findViewById(R.id.atvSubject);
        mAtvBody = dialog.findViewById(R.id.atvBody);
        filePreviewName = dialog.findViewById(R.id.filePreviewName);
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

        atvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atvFrom.showDropDown();
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callComposeEmail();

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

    private void openMessageComposer(String phoneNo) {

        if (phoneNo == null || phoneNo.equals("") || phoneNo.equals("null")) {
            ToastUtils.showToast(context, "No Primary Phone Found");
        } else {

            mDialog = new Dialog(context, R.style.Dialog);
            mDialog.setCancelable(true);
            mDialog.setContentView(R.layout.dialog_compose_message);

            atvPhone = mDialog.findViewById(R.id.atvPhoneNumber);
            atvPhone.setText(phoneNo + "");
            mAtvMessage = mDialog.findViewById(R.id.atvMessage);
            tvClose = mDialog.findViewById(R.id.tvClose);
            btnSendEmail = mDialog.findViewById(R.id.btnSendEmail);
            btnCancel = mDialog.findViewById(R.id.btnCancel);

            btnSendEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = mAtvMessage.getText().toString();
                    if (!message.equalsIgnoreCase(""))
                        presenter.sendMessageContent(phoneNo, message, leadID);
                    else
                        showLongToastMessage("Please enter message body");

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            mDialog.show();


        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgEmail:
                if (primaryEmail.equals("") || primaryEmail.equals("null") || primaryEmail == null) {
                    ToastUtils.showToast(context, "No Primary Email Found");
                } else {
                    presenter.getLeadRecipient(leadID);
                }
                break;

            case R.id.imgMessage:
                openMessageComposer(primaryPhoneNumber);
                break;

            case R.id.imgCall:
                if (primaryPhoneNumber == null || primaryPhoneNumber.equals("") || primaryPhoneNumber.equals("null")) {
                    ToastUtils.showToast(context, "No Primary Phone Found");
                } else {
                    presenter.getCallerId(leadID);
                }
                break;
            case R.id.fbAssignedTo:
                showAgentDialog();
                break;
            case R.id.rlTransaction1:

                if (currentStage1 != null) {
                    Intent intentT1 = new Intent(context, TransactionActivity.class);
                    intentT1.putExtra("title", title);
                    intentT1.putExtra("tansaction", 1);
                    intentT1.putExtra("leadID", leadID);
                    intentT1.putExtra("leadDetailId", transLeadID1);
                    intentT1.putExtra("currentStage", currentStage1);
                    intentT1.putExtra("Pipeline", transactionPipeline);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) transactionOneListModel);
                    intentT1.putExtra("BUNDLE", args);
                    intentT1.putExtra("agents", agentList);


                    startActivity(intentT1);
                }

                break;

            case R.id.rlTransaction2:

                if (currentStage1 != null) {
                    Intent intentT2 = new Intent(context, TransactionActivity.class);
                    intentT2.putExtra("title", title);
                    intentT2.putExtra("leadID", leadID);
                    intentT2.putExtra("tansaction", 2);
                    intentT2.putExtra("leadDetailId", transLeadID2);
                    intentT2.putExtra("currentStage", currentStage2);
                    intentT2.putExtra("Pipeline", transactionPipeline);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) transactionTwoListModel);
                    intentT2.putExtra("BUNDLE", args);
                    intentT2.putExtra("agents", agentList);
                    startActivity(intentT2);
                }

                break;

            case R.id.btnActions:

                back = true;
                bi.lnActions.setVisibility(View.VISIBLE);
                bi.recyclerLeadDetails.setVisibility(View.GONE);
                Paris.style(bi.btnActions).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnDetails:

                back = false;
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


    private void populateListData(ArrayList<GetLead.AgentsList> leadsList, String primaryPhone) {

        bi.recyclerLeadAgent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        bi.recyclerLeadAgent.setItemAnimator(new DefaultItemAnimator());
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_lead_details_agents_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvAssignedToName, R.id.imgAssignedTo, R.id.tvPrimary);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLead.AgentsList, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {


            TextView tvName = (TextView) integerMap.get(R.id.tvAssignedToName);
            CircleImageView imageView = (CircleImageView) integerMap.get(R.id.imgAssignedTo);
            tvName.setText(String.valueOf(allLeadsList.agentName));

            TextView tvIsPrimary = (TextView) integerMap.get(R.id.tvPrimary);
            if (allLeadsList.isPrimaryAgent) {
                tvIsPrimary.setVisibility(View.VISIBLE);
                primaryPhoneNumber = primaryPhone;


            } else {
                tvIsPrimary.setVisibility(View.INVISIBLE);
            }

            if (allLeadsList.agentPicture != null && !allLeadsList.agentPicture.equalsIgnoreCase("null")
                    && !allLeadsList.agentPicture.equalsIgnoreCase("")) {

                Glide.with(context)
                        .load(allLeadsList.agentPicture)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(getResources().getDrawable(R.drawable.avataer_male))
                        .into(imageView);
            }


            return Unit.INSTANCE;
        });

        bi.recyclerLeadAgent.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLead.AgentsList, Integer, Unit>) (viewComplainList, integer) -> {

            if (leadsList.get(integer).isPrimaryAgent) {
                //  Toast.makeText(context, "Selected agent is already prim", Toast.LENGTH_SHORT).show();
            } else {
                presenter.setPrimaryAgent(leadID, leadsList.get(integer).agentID);
            }

            return Unit.INSTANCE;
        });

    }


    @Override
    public void onBackPressed() {
        if (back) {
            super.onBackPressed();
        } else {
            back = true;
            bi.lnActions.setVisibility(View.VISIBLE);
            bi.recyclerLeadDetails.setVisibility(View.GONE);
            Paris.style(bi.btnActions).apply(R.style.TabButtonYellowLeft);
            Paris.style(bi.btnDetails).apply(R.style.TabButtonTranparentRight);
        }
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

            primaryPhoneNumber = getLeadListData.primaryPhone == null ? "" : getLeadListData.primaryPhone;

            primaryEmail = getLeadListData.primaryEmail == null ? "" : getLeadListData.primaryEmail;

            if (getAssignedAgentList.size() != 0) {
                selectedIdsList.clear();
                for (GetLead.AgentsList model : getAssignedAgentList) {
                    selectedIdsList.add(model.agentDryptedID);

                    if (agentIdsString.equals("")) {
                        agentIdsString = model.agentID;
                    } else {
                        agentIdsString = agentIdsString + "," + model.agentID;
                    }
                }
            }

            bi.scLeadDetail.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.tvID.setText(getLeadListData.leadID);
            String firstName = getLeadListData.firstName;
            String lastName = getLeadListData.lastName;
            try {

                if (firstName != null && !firstName.equalsIgnoreCase("")) {

                    if (lastName != null && !lastName.equalsIgnoreCase("")) {
                        bi.tvInitial.setText(firstName.substring(0, 1) + "" + lastName.substring(0, 1));
                        bi.tvName.setText(firstName + " " + lastName);
                    } else {
                        bi.tvInitial.setText(firstName.substring(0, 1) + "-");
                        bi.tvName.setText(firstName);
                    }

                } else {

                    if (lastName != null && !lastName.equalsIgnoreCase("")) {
                        bi.tvInitial.setText("-" + " " + lastName.substring(0, 1));
                        bi.tvName.setText("-" + " " + lastName);
                    } else {
                        bi.tvInitial.setText("-" + "-");
                        bi.tvName.setText(firstName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                bi.tvInitial.setText("-" + "-");
                bi.tvName.setText("");
            }

            if (getLeadListData.scoreColor.equals("green")) {
                bi.tvScore.setTextColor(getResources().getColor(R.color.colorScoreGreen));
            } else if (getLeadListData.scoreColor.equals("red")) {
                bi.tvScore.setTextColor(getResources().getColor(R.color.colorScoreRed));
            } else if (getLeadListData.scoreColor.equals("blue")) {
                bi.tvScore.setTextColor(getResources().getColor(R.color.colorScoreBlue));
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
            populateListData(getAssignedAgentList, getLeadListData.primaryPhone);
        }
    }

    @Override
    public void updateUI(GetLeadTransactionStage response) {

        Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
        Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);

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

            bi.tvStep1.setText(transactionOneListModel.get(transactionOneListModel.size() - 1).currentStage);

            String rpDate = transactionOneListModel.get(transactionOneListModel.size() - 1).date != null ? transactionOneListModel.get(transactionOneListModel.size() - 1).date : "";
            String date = !rpDate.equals("") ? GH.getInstance().formatter(rpDate, "MM/dd/yyyy", "MMM d yyyy h:mma") : "";
            bi.tvDate1.setText(date);

            currentStage1 = transactionOneListModel.get(transactionOneListModel.size() - 1).currentStage;
            transLeadID1 = transactionOneListModel.get(transactionOneListModel.size() - 1).encrypted_LeadDetailID;

        } else {
            bi.tvNoRecord.setVisibility(View.VISIBLE);
            bi.rlTransaction1.setVisibility(View.GONE);
            bi.rlTransaction2.setVisibility(View.GONE);
        }

        if (transactionTwoListModel != null) {

            bi.tvStep2.setText(transactionTwoListModel.get(transactionTwoListModel.size() - 1).currentStage);

            String rpDate = transactionTwoListModel.get(transactionTwoListModel.size() - 1).date != null ? transactionTwoListModel.get(transactionTwoListModel.size() - 1).date : "";
            String date = !rpDate.equals("") ? GH.getInstance().formatter(rpDate, "MM/dd/yyyy", "MMM d yyyy h:mma") : "";
            bi.tvDate2.setText(date);

            currentStage2 = transactionTwoListModel.get(transactionTwoListModel.size() - 1).currentStage;
            transLeadID2 = transactionTwoListModel.get(transactionTwoListModel.size() - 1).encrypted_LeadDetailID;
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

    private void callComposeEmail() {

        String _from = atvFrom.getText().toString();
        String cc = atvCC.getText().toString();
        String bcc = atvBCC.getText().toString();
        String subject = atvSubject.getText().toString();
        String body = mAtvBody.getText().toString();
        String documentUrl = fileUrl;
        String[] _to = new String[0];
        if (searchLeadListItems != null && searchLeadListItems.size() > 0) {

            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < searchLeadListItems.size(); i++) {
                arrayList.add(searchLeadListItems.get(i).getName());
            }
            _to = arrayList.toArray(new String[searchLeadListItems.size()]);
        }
        if (!cc.equals("")) {
            if (!isValidEmail(cc.toString())) {
                ToastUtils.showToast(context, "Invalid Email in Cc");
            } else {
                if (isValidate()) {
                    if (multipartFile != null) {
                        presenter.uploadFile(multipartFile, _from);
                    } else {
                        presenter.sendEmailContent(_from, _to, cc, bcc, subject, body, documentUrl, leadID);
                    }
                }
            }
        } else if (!bcc.equals("")) {
            if (!isValidEmail(bcc.toString())) {
                ToastUtils.showToast(context, "Invalid Email in Bcc");
            } else {
                if (isValidate()) {
                    if (multipartFile != null) {
                        presenter.uploadFile(multipartFile, _from);
                    } else {
                        presenter.sendEmailContent(_from, _to, cc, bcc, subject, body, documentUrl, leadID);
                    }
                }
            }
        } else {
            if (isValidate()) {
                if (multipartFile != null) {
                    presenter.uploadFile(multipartFile, _from);
                } else {
                    presenter.sendEmailContent(_from, _to, cc, bcc, subject, body, documentUrl, leadID);
                }
            }
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidate() {

        if (Methods.isEmpty(atvFrom)) {
            ToastUtils.showToast(context, R.string.error_from);
            atvFrom.requestFocus();
            return false;
        }
        if (searchLeadListItems != null && searchLeadListItems.size() == 0) {
            ToastUtils.showToast(context, R.string.error_to);
            return false;
        }
        if (Methods.isEmpty(atvSubject)) {
            ToastUtils.showToast(context, R.string.error_subject);
            atvSubject.requestFocus();
            return false;
        }
        if (Methods.isEmpty(mAtvBody)) {
            ToastUtils.showToast(context, R.string.error_message);
            mAtvBody.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    public void updateUIEmailSent(Response<BaseResponse> response) {

        dialog.dismiss();
        selectedLeadIdsList.clear();
        searchLeadListItems.clear();
        ToastUtils.showToastLong(context, response.body().getMessage());

    }

    @Override
    public void updateUIAfterFileUpload(Response<UploadImageModel> response) {

        fileUrl = response.body().getData();
        callComposeEmail();
        multipartFile = null;

    }

    @Override
    public void updateUIMessageSent(Response<BaseResponse> response) {

        mDialog.dismiss();
        ToastUtils.showToastLong(context, response.body().getMessage());

    }

    @Override
    public void updateUIonCall(GetCallerId response) {

        String numberFromApi = response.getData().getCallerID();
        startActivity(new Intent(context, VoiceActivity.class)
                .putExtra("to", primaryPhoneNumber)
                .putExtra("from", numberFromApi)
                .putExtra("callerId", numberFromApi));

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

        /*if (error.contains("Authorization has been denied for this request")) {
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
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(LeadDetailActivity.this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

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
                    RC_CAMERA_AND_STORAGE, perms);
        }
    }

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
            case 3:
                accessDocuments();
                break;
        }
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
        RequestBody requestFile = RequestBody.create(mediaType, file);
        multipartFile = MultipartBody.Part.createFormData("", file.getName(), requestFile);
        filePreviewName.setPaintFlags(filePreviewName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        filePreviewName.setText(file.getName() + "");


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

            if (result != null && actualImage != null) {
                RequestBody requestFile = RequestBody.create(mediaType, result);
                multipartFile = MultipartBody.Part.createFormData("", result.getName(), requestFile);
                filePreviewName.setPaintFlags(filePreviewName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                filePreviewName.setText(result.getName() + "");
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