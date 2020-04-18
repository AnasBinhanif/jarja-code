package com.project.jarjamediaapp.Activities.add_lead;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddLeadBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Response;

public class AddLeadActivity extends BaseActivity implements AddLeadContract.View {

    ActivityAddLeadBinding bi;
    Context context = AddLeadActivity.this;
    AddLeadPresenter presenter;

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();

    ArrayList<GetLeadSource.Data> getLeadSourceList;
    ArrayList<String> getLeadSourceNameList = new ArrayList<>();

    ArrayList<GetLeadTagList.Data> getLeadTagList;
    ArrayList<MultiSelectModel> getLeadTagModelList;
    ArrayList<Integer> selectedTagIdsList = new ArrayList<>();

    ArrayList<GetLeadTypeList.Data> getLeadTypeList;
    ArrayList<String> getLeadTypeNameList;

    ArrayList<GetLeadTimeFrame.Data> getLeadTimeFrameList;
    ArrayList<String> getLeadTimeFrameNames;

    ArrayList<String> preAprrovedList;

    ArrayList<GetLeadDripCampaignList.Data> getLeadDripCampaignList;
    ArrayList<MultiSelectModel> getLeadDripCampaignModelList;
    ArrayList<Integer> selectedDripIdsList = new ArrayList<>();

    MultiSelectModel tagModel, agentModel, dripModel;

    String leadID = "", agentIdsString = "", tagsIdsString = "", dripIdsString = "";

    String bday = "", sBday = "", anniversary = "", spouseBday = "";
    boolean isUpdate = false;
    GetLead.LeadList leadModel;

    String source = "";
    int timeFrame, leadType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_lead);
        presenter = new AddLeadPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_lead), true);
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        initSpinners();
        initListeners();
        initCallsData();
    }

    private void initData() {

        if (getIntent().getExtras() != null) {

            leadModel = (GetLead.LeadList) getIntent().getExtras().getSerializable("Lead");

            isUpdate = true;
            bi.btnSave.setText("Update");
            leadID = leadModel.leadStringID;
            bi.edtFName.setText(leadModel.firstName);
            bi.edtLName.setText(leadModel.lastName);
            bi.edtSName.setText(leadModel.spousname);
            bi.edtCompany.setText(leadModel.company);
            bi.edtPhone.setText(leadModel.primaryPhone);
            bi.edtEmail.setText(leadModel.primaryEmail);

            if (leadModel.dateOfBirth == null || leadModel.dateOfBirth.equals("") || leadModel.dateOfBirth.equals("")) {
                bi.edtBday.setText("");
            } else {
                String _bday = GH.getInstance().formatter(leadModel.dateOfBirth, "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
                bi.edtBday.setText(_bday);
                bday = leadModel.dateOfBirth;

            }
            if (leadModel.dateOfMarriage == null || leadModel.dateOfMarriage.equals("") || leadModel.dateOfMarriage.equals("")) {
                bi.edtAnniversary.setText("");
            } else {
                String anniv = GH.getInstance().formatter(leadModel.dateOfMarriage, "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
                bi.edtAnniversary.setText(anniv);
                anniversary = leadModel.dateOfMarriage;
            }
            if (leadModel.spouseBirthday == null || leadModel.spouseBirthday.equals("") || leadModel.spouseBirthday.equals("")) {
                bi.edtSpouseBday.setText("");
            } else {
                String bday = GH.getInstance().formatter(leadModel.spouseBirthday, "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
                bi.edtSpouseBday.setText(bday);
                sBday = leadModel.spouseBirthday;
            }
            bi.edtAddress1.setText(leadModel.street);
            bi.edtCity1.setText(leadModel.city);
            bi.edtState1.setText(leadModel.state);
            bi.edtPostalCode1.setText(leadModel.zipcode);
            bi.edtAddress2.setText(leadModel.state2);
            bi.edtCity2.setText(leadModel.city2);
            bi.edtState2.setText(leadModel.state2);
            bi.edtPostalCode2.setText(leadModel.zipcode2);
            bi.edtCountry.setText(leadModel.county);
            bi.edtNotes.setText(leadModel.description);

            if (leadModel.isBirthDayNotify != null) {
                if (leadModel.isBirthDayNotify) {
                    bi.chkBdayNotify.setChecked(true);
                } else {
                    bi.chkBdayNotify.setChecked(false);
                }
            }

            if (leadModel.isAnniversaryNotify != null) {
                if (leadModel.isAnniversaryNotify) {
                    bi.chkAnnivNotify.setChecked(true);
                } else {
                    bi.chkAnnivNotify.setChecked(false);
                }
            }

            if (leadModel.source != null) {
                int pos = getLeadSourceNameList.indexOf(leadModel.source);
                if (pos >= 0) {

                    source = leadModel.source;
                    bi.spnSource.setText(leadModel.source);
                }

            }

            if (leadModel.preApproved) {
                bi.spnPreApprove.setText("Yes");
            } else {
                bi.spnPreApprove.setText("No");
            }

            if (leadModel.leadTypeID != null) {

                if (getLeadTypeList != null && getLeadTypeList.size() > 0) {
                    for (int i = 0; i < getLeadTypeList.size(); i++) {
                        GetLeadTypeList.Data typemodel = getLeadTypeList.get(i);
                        Integer id = typemodel.id;
                        if (id.equals(leadModel.leadTypeID)) {
                            leadType = id;
                            bi.spnType.setText(typemodel.leadType);
                        }
                    }
                } else {
                    bi.spnType.setSelectedIndex(0);
                }

            }


            if (leadModel.timeFrameId != null) {

                if (getLeadTimeFrameList != null && getLeadTimeFrameList.size() > 0) {
                    for (int i = 0; i < getLeadTimeFrameList.size(); i++) {
                        GetLeadTimeFrame.Data typemodel = getLeadTimeFrameList.get(i);
                        Integer id = typemodel.timeFrameId;
                        if (id.equals(leadModel.timeFrameId)) {

                            timeFrame = typemodel.timeFrameId;
                            bi.spnTimeFrame.setText(typemodel.timeFrame);
                        }
                    }
                } else {
                    bi.spnTimeFrame.setSelectedIndex(0);
                }

            }

            if (leadModel.agentsList.size() != 0) {

                if (bi.lnAgent.getChildCount() > 0) {
                    bi.lnAgent.removeAllViews();
                }
                selectedIdsList = new ArrayList<>();

                for (GetLead.AgentsList name : leadModel.agentsList) {

                    View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                    TextView textView = child.findViewById(R.id.txtDynamic);
                    textView.setText(String.valueOf(name.agentName));
                    bi.lnAgent.addView(child);
                    // selectedIdsList.add(Integer.valueOf(name.agentID));
                    selectedIdsList.add(name.assignAgentsID);
                    if (agentIdsString.equals("")) {
                        agentIdsString = name.agentID;
                    } else {
                        agentIdsString = agentIdsString + "," + name.agentID;
                    }
                }
            }

            if (leadModel.leadDripCompaignList.size() != 0) {

                if (bi.lnDrip.getChildCount() > 0) {
                    bi.lnDrip.removeAllViews();
                }
                selectedDripIdsList = new ArrayList<>();

                for (GetLead.LeadDripCompaignList name : leadModel.leadDripCompaignList) {

                    View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                    TextView textView = child.findViewById(R.id.txtDynamic);
                    textView.setText(String.valueOf(name.name));
                    bi.lnDrip.addView(child);
                    // selectedIdsList.add(Integer.valueOf(name.agentID));
                    selectedDripIdsList.add(name.dripCompaignID);
                    if (dripIdsString.equals("")) {
                        dripIdsString = name.encryptedID;
                    } else {
                        dripIdsString = dripIdsString + "," + name.encryptedID;
                    }
                }
            }

            if (leadModel.leadTaskList.size() != 0) {

                if (bi.lnTags.getChildCount() > 0) {
                    bi.lnTags.removeAllViews();
                }
                selectedTagIdsList = new ArrayList<>();

                for (GetLead.LeadTaskList name : leadModel.leadTaskList) {

                    View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                    TextView textView = child.findViewById(R.id.txtDynamic);
                    textView.setText(String.valueOf(name.label));
                    bi.lnTags.addView(child);
                    // selectedIdsList.add(Integer.valueOf(name.agentID));
                    selectedTagIdsList.add(name.id);
                    if (tagsIdsString.equals("")) {
                        tagsIdsString = name.encryptedTagID;
                    } else {
                        tagsIdsString = tagsIdsString + "," + name.encryptedTagID;
                    }
                }
            }

        }
    }

    private void initCallsData() {

        presenter.getAgentNames();

    }

    private void initListeners() {

        bi.edtTags.setOnClickListener(this);
        bi.btnSave.setOnClickListener(this);
        bi.edtBday.setOnClickListener(this);
        bi.edtAgent.setOnClickListener(this);
        bi.edtSpouseBday.setOnClickListener(this);
        bi.edtAnniversary.setOnClickListener(this);
        bi.edtDripCompaign.setOnClickListener(this);
    }

    private void initSpinners() {

        bi.spnSource.setBackground(getDrawable(R.drawable.bg_search));
        bi.spnType.setBackground(getDrawable(R.drawable.bg_search));
        bi.spnTimeFrame.setBackground(getDrawable(R.drawable.bg_search));
        bi.spnPreApprove.setBackground(getDrawable(R.drawable.bg_search));

        preAprrovedList = new ArrayList<>();
        preAprrovedList.add("Yes");
        preAprrovedList.add("No");

        bi.spnPreApprove.setItems(preAprrovedList);
    }

    private void showTagsDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Tags") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                //.setMinSelectionLimit(1)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedTagIdsList = new ArrayList<>();
                        selectedTagIdsList = selectedIds;

                        if (bi.lnTags.getChildCount() > 0) {
                            bi.lnTags.removeAllViews();
                        }

                        for (String name : selectedNames) {
                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnTags.addView(child);
                        }

                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                        tagsIdsString = "";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (tagsIdsString.equals("")) {
                                    tagsIdsString = i;
                                } else {
                                    tagsIdsString = tagsIdsString + "," + i;
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

        if (selectedTagIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedTagIdsList);
            multiSelectDialog.multiSelectList(getLeadTagModelList);
        } else {
            multiSelectDialog.multiSelectList(getLeadTagModelList);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
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

                        //agentModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
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

    private void showDripDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Drip Campaigns") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                //.setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedDripIdsList = new ArrayList<>();
                        selectedDripIdsList = selectedIds;

                        if (bi.lnDrip.getChildCount() > 0) {
                            bi.lnDrip.removeAllViews();
                        }

                        for (String name : selectedNames) {
                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnDrip.addView(child);
                        }
                        dripIdsString = "";
                        for (Integer i : selectedIds) {
                            if (dripIdsString.equals("")) {
                                dripIdsString = String.valueOf(i);
                            } else {
                                dripIdsString = dripIdsString + "," + i;
                            }
                        }

                        // dripModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (selectedDripIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedDripIdsList);
            multiSelectDialog.multiSelectList(getLeadDripCampaignModelList);
        } else {
            multiSelectDialog.multiSelectList(getLeadDripCampaignModelList);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void callAddNewLead() {

        String firstName = bi.edtFName.getText().toString();
        String lastName = bi.edtLName.getText().toString();
        String spousname = bi.edtSName.getText().toString();
        String company = bi.edtCompany.getText().toString();
        String cellPhone = bi.edtPhone.getText().toString();
        String primaryPhone = bi.edtPhone.getText().toString();
        String primaryEmail = bi.edtEmail.getText().toString();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dateOfBirth = bday.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : bday;
        boolean isBirthDayNotify = bi.chkBdayNotify.isChecked() ? true : false;
        String dateOfMarriage = anniversary.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : anniversary;
        String spouseMarriage = sBday.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : sBday;
        boolean isAnniversaryNotify = bi.chkAnnivNotify.isChecked() ? true : false;
        String leadAgentIDs = agentIdsString;//selectedIdsList.size() == 0 ? "" : agentIdsString ;
        String allAgentIds = agentIdsString;//agentModel == null ? "" : String.valueOf(agentModel.getId());
        String alldripcampaignids = dripIdsString;
        String notes = bi.edtNotes.getText().toString();
        boolean b_PreQual = bi.spnPreApprove.getText().toString().equals("Yes") ? true : false;
        String address = bi.edtAddress1.getText().toString();
        String street = bi.edtAddress1.getText().toString();
        String zipcode = bi.edtPostalCode1.getText().toString();
        String city = bi.edtCity1.getText().toString();
        String state = bi.edtState1.getText().toString();
        String description = bi.edtNotes.getText().toString();
        String source = this.source;
        String county = bi.edtCountry.getText().toString();
        int timeFrameId = this.timeFrame;
        String state2 = bi.edtState2.getText().toString();
        String city2 = bi.edtCity2.getText().toString();
        String zipcode2 = bi.edtPostalCode2.getText().toString();
        int leadTypeID = this.leadType;
        String labelsID = tagsIdsString;
        String leadStringID = leadID;
        String leadID = "0";
        String countryid = "";

        JSONObject emailObject = new JSONObject();
        try {
            emailObject.put("email", primaryEmail);
            emailObject.put("isNotify", true);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray emailArray = new JSONArray();
        emailArray.put(emailObject);

        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("phone", cellPhone);
            phoneObject.put("isNotify", true);
            phoneObject.put("phoneType", "phoneType");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray phoneArray = new JSONArray();
        phoneArray.put(phoneObject);

        String emailList = emailArray.toString();
        String phoneList = phoneArray.toString();

        if (firstName.equals("") && lastName.equals("") && spousname.equals("") && company.equals("") && primaryEmail.equals("") && cellPhone.equals("")) {
            ToastUtils.showToast(context, getString(R.string.errSinglePiece));
        } else if (selectedIdsList.size() == 0) {
            ToastUtils.showToast(context, getString(R.string.errSelectAgent));
        } else if (!zipcode.equals("") && zipcode.trim().length() < 5) {
            ToastUtils.showToast(context, "Postal code cannot be less than 5");
        } else if (!zipcode2.equals("") && zipcode2.trim().length() < 5) {
            ToastUtils.showToast(context, "Postal code cannot be less than 5");
        } else {


            JSONObject obj = new JSONObject();

            try {
                obj.put("firstName", firstName);
                obj.put("lastName", lastName);
                obj.put("spousname", spousname);
                obj.put("company", company);
                obj.put("cellPhone", cellPhone);
                obj.put("primaryPhone", primaryPhone);
                obj.put("primaryEmail", primaryEmail);
                obj.put("dateOfBirth", dateOfBirth);
                obj.put("isBirthDayNotify", isBirthDayNotify);
                obj.put("dateOfMarriage", dateOfMarriage);
                obj.put("spouseBirthday", spouseMarriage);
                obj.put("isAnniversaryNotify", isAnniversaryNotify);
                obj.put("leadAgentIDs", leadAgentIDs);
                obj.put("allAgentIds", allAgentIds);
                obj.put("alldripcampaignids", alldripcampaignids);
                obj.put("notes", notes);
                obj.put("b_PreQual", b_PreQual);
                obj.put("address", address);
                obj.put("street", street);
                obj.put("zipcode", zipcode);
                obj.put("city", city);
                obj.put("state", state);
                obj.put("description", description);
                obj.put("source", source);
                obj.put("county", county);
                obj.put("timeFrameId", timeFrameId);
                obj.put("state2", state2);
                obj.put("city2", city2);
                obj.put("zipcode2", zipcode2);
                obj.put("leadTypeID", leadTypeID);
                obj.put("emailList", emailArray);
                obj.put("phoneList", phoneArray);
                obj.put("labelsID", labelsID);
                obj.put("leadStringID", leadStringID);
                obj.put("countryid", countryid);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonObjectString = obj.toString();
            Log.d("json", jsonObjectString);

            presenter.addLead(jsonObjectString);
        }
    }

    private void callUpdateLead() {

        String firstName = bi.edtFName.getText().toString();
        String lastName = bi.edtLName.getText().toString();
        String spousname = bi.edtSName.getText().toString();
        String company = bi.edtCompany.getText().toString();
        String cellPhone = bi.edtPhone.getText().toString();
        String primaryPhone = bi.edtPhone.getText().toString();
        String primaryEmail = bi.edtEmail.getText().toString();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dateOfBirth = bday.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : bday;
        boolean isBirthDayNotify = bi.chkBdayNotify.isChecked() ? true : false;
        String dateOfMarriage = anniversary.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : anniversary;
        String spouseMarriage = sBday.equals("") ? dateFormatter.format(Calendar.getInstance().getTime()) : sBday;
        boolean isAnniversaryNotify = bi.chkAnnivNotify.isChecked() ? true : false;
        String leadAgentIDs = agentIdsString;//selectedIdsList.size() == 0 ? "" : agentIdsString ;
        String allAgentIds = agentIdsString;//agentModel == null ? "" : String.valueOf(agentModel.getId());
        String alldripcampaignids = dripIdsString;
        String notes = bi.edtNotes.getText().toString();
        boolean b_PreQual = bi.spnPreApprove.getText().toString().equals("Yes") ? true : false;
        String address = bi.edtAddress1.getText().toString();
        String street = bi.edtAddress1.getText().toString();
        String zipcode = bi.edtPostalCode1.getText().toString();
        String city = bi.edtCity1.getText().toString();
        String state = bi.edtState1.getText().toString();
        String description = bi.edtNotes.getText().toString();
        String source = this.source;
        String county = bi.edtCountry.getText().toString();
        int timeFrameId = this.timeFrame;
        String state2 = bi.edtState2.getText().toString();
        String city2 = bi.edtCity2.getText().toString();
        String zipcode2 = bi.edtPostalCode2.getText().toString();
        int leadTypeID = this.leadType;
        String labelsID = tagsIdsString;
        String leadStringID = leadID;
        String leadID = "0";
        String countryid = "";

        JSONObject emailObject = new JSONObject();
        try {
            emailObject.put("email", primaryEmail);
            emailObject.put("isNotify", true);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray emailArray = new JSONArray();
        emailArray.put(emailObject);

        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("phone", cellPhone);
            phoneObject.put("isNotify", true);
            phoneObject.put("phoneType", "phoneType");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray phoneArray = new JSONArray();
        phoneArray.put(phoneObject);

        String emailList = emailArray.toString();
        String phoneList = phoneArray.toString();

        if (firstName.equals("") && lastName.equals("") && spousname.equals("") && company.equals("") && primaryEmail.equals("") && cellPhone.equals("")) {
            ToastUtils.showToast(context, getString(R.string.errSinglePiece));
        } else if (selectedIdsList.size() == 0) {
            ToastUtils.showToast(context, getString(R.string.errSelectAgent));
        } else if (!zipcode.equals("") && zipcode.trim().length() < 5) {
            ToastUtils.showToast(context, "Postal code cannot be less than 5");
        } else if (!zipcode2.equals("") && zipcode2.trim().length() < 5) {
            ToastUtils.showToast(context, "Postal code cannot be less than 5");
        } else {


            JSONObject obj = new JSONObject();

            try {
                obj.put("firstName", firstName);
                obj.put("lastName", lastName);
                obj.put("spousname", spousname);
                obj.put("company", company);
                obj.put("cellPhone", cellPhone);
                obj.put("primaryPhone", primaryPhone);
                obj.put("primaryEmail", primaryEmail);
                obj.put("dateOfBirth", dateOfBirth);
                obj.put("isBirthDayNotify", isBirthDayNotify);
                obj.put("dateOfMarriage", dateOfMarriage);
                obj.put("spouseBirthday", spouseMarriage);
                obj.put("isAnniversaryNotify", isAnniversaryNotify);
                obj.put("leadAgentIDs", leadAgentIDs);
                obj.put("allAgentIds", allAgentIds);
                obj.put("alldripcampaignids", alldripcampaignids);
                obj.put("notes", notes);
                obj.put("b_PreQual", b_PreQual);
                obj.put("address", address);
                obj.put("street", street);
                obj.put("zipcode", zipcode);
                obj.put("city", city);
                obj.put("state", state);
                obj.put("description", description);
                obj.put("source", source);
                obj.put("county", county);
                obj.put("timeFrameId", timeFrameId);
                obj.put("state2", state2);
                obj.put("city2", city2);
                obj.put("zipcode2", zipcode2);
                obj.put("leadTypeID", leadTypeID);
                obj.put("emailList", emailArray);
                obj.put("phoneList", phoneArray);
                obj.put("labelsID", labelsID);
                obj.put("leadStringID", leadStringID);
                obj.put("countryid", countryid);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonObjectString = obj.toString();
            Log.d("json123", jsonObjectString);

            presenter.updateLead(jsonObjectString);
        }
    }

    private void showDateDialog(TextView textView, String whichBday) {

        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if (whichBday.equals("bday")) {
                    bday = dateFormatter.format(newDate.getTime());
                } else if (whichBday.equals("sBday")) {
                    sBday = dateFormatter.format(newDate.getTime());
                } else if (whichBday.equals("anniv")) {
                    anniversary = dateFormatter.format(newDate.getTime());
                }
                textView.setText(dateFormatter2.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }

    @Override
    public void updateUI(GetAgentsModel response) {

        agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
        }
        presenter.GetLeadSource();
    }

    @Override
    public void updateUI(GetLeadSource response) {

        getLeadSourceList = new ArrayList<>();
        getLeadSourceNameList = new ArrayList<>();
        getLeadSourceList = response.data;

        for (GetLeadSource.Data model : getLeadSourceList) {
            getLeadSourceNameList.add(model.sourceName);
        }

        bi.spnSource.setItems(getLeadSourceNameList);
        bi.spnSource.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                source = String.valueOf(getLeadSourceList.get(position).sourceID);
            }
        });
        presenter.GetLeadTagList();

    }

    @Override
    public void updateUI(GetLeadTagList response) {

        getLeadTagList = new ArrayList<>();
        getLeadTagModelList = new ArrayList<>();
        getLeadTagList = response.data;

        for (GetLeadTagList.Data model : getLeadTagList) {
            getLeadTagModelList.add(new MultiSelectModel(model.tagID, model.label, model.encryptedTagID));
        }
        presenter.GetLeadTypeList();
    }

    @Override
    public void updateUI(GetLeadTypeList response) {

        getLeadTypeList = new ArrayList<>();
        getLeadTypeNameList = new ArrayList<>();
        getLeadTypeList = response.data;

        for (GetLeadTypeList.Data model : getLeadTypeList) {
            getLeadTypeNameList.add(model.leadType);
        }

        bi.spnType.setItems(getLeadTypeNameList);
        bi.spnType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                leadType = getLeadTypeList.get(position).id;
            }
        });
        presenter.GetLeadTimeFrame();

    }

    @Override
    public void updateUI(GetLeadTimeFrame response) {

        getLeadTimeFrameList = new ArrayList<>();
        getLeadTimeFrameNames = new ArrayList<>();
        getLeadTimeFrameList = response.data;

        for (GetLeadTimeFrame.Data model : getLeadTimeFrameList) {
            getLeadTimeFrameNames.add(model.timeFrame);
        }

        bi.spnTimeFrame.setItems(getLeadTimeFrameNames);

        bi.spnTimeFrame.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                timeFrame = getLeadTimeFrameList.get(position).timeFrameId;
            }
        });
        presenter.GetLeadDripCampaignList();
    }

    @Override
    public void updateUI(GetLeadDripCampaignList response) {

        getLeadDripCampaignList = new ArrayList<>();
        getLeadDripCampaignModelList = new ArrayList<>();
        getLeadDripCampaignList = response.data;
        for (GetLeadDripCampaignList.Data model : getLeadDripCampaignList) {
            getLeadDripCampaignModelList.add(new MultiSelectModel(model.dripCompaignID, model.name));
        }
        GH.getInstance().ShowProgressDialog(context);
        addTimer();
    }

    private void addTimer() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                GH.getInstance().HideProgressDialog();
                initData();
            }
        }, 3000);

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {
        if (response.body().getStatus().equals("Success")) {
            if (isUpdate) {
                ToastUtils.showToast(context, "Updated");
            } else {
                ToastUtils.showToast(context, "Added Successfully");
            }
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
        GH.getInstance().ShowProgressDialog(AddLeadActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.edtAgent:
                showAgentDialog();
                break;
            case R.id.edtTags:
                showTagsDialog();
                break;
            case R.id.edtBday:
                showDateDialog(bi.edtBday, "bday");
                break;
            case R.id.edtSpouseBday:
                showDateDialog(bi.edtSpouseBday, "sBday");
                break;
            case R.id.edtAnniversary:
                showDateDialog(bi.edtAnniversary, "anniv");
                break;
            case R.id.edtDripCompaign:
                showDripDialog();
                break;
            case R.id.btnSave:
                if (isUpdate) {
                    callUpdateLead();
                } else {
                    callAddNewLead();
                }
                break;
        }
    }

}
