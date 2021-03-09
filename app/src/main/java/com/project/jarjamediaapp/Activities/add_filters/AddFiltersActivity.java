package com.project.jarjamediaapp.Activities.add_filters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.google.android.gms.common.internal.service.Common;
import com.google.gson.internal.LinkedTreeMap;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLastLogin;
import com.project.jarjamediaapp.Models.GetLastTouch;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadScore;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Models.GetPipeline;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AlertDialogUtil;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddFiltersBinding;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Response;

public class AddFiltersActivity extends BaseActivity implements AddFiltersContract.View {

    ActivityAddFiltersBinding bi;
    Context context = AddFiltersActivity.this;
    AddFiltersPresenter presenter;

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<MultiSelectModel> searchListItemsselected = new ArrayList<>();
    ArrayList<Integer> selectedIdsList = new ArrayList<>();

    ArrayList<GetLeadSource.Data> getLeadSourceList;
    ArrayList<MultiSelectModel> getLeadSourceNameList;
    ArrayList<MultiSelectModel> getLeadSourceNameListselected = new ArrayList<>();
    ArrayList<Integer> selectedSourceNameIdsList = new ArrayList<>();

    ArrayList<GetLeadTagList.Data> getLeadTagList;
    ArrayList<MultiSelectModel> getLeadTagModelList = new ArrayList<>();
    ArrayList<MultiSelectModel> getLeadTagModelListselected = new ArrayList<>();
    ArrayList<Integer> selectedTagIdsList = new ArrayList<>();

    ArrayList<GetPipeline.Data> getPipelineList;
    ArrayList<MultiSelectModel> getPipelineModelList;
    ArrayList<MultiSelectModel> getPipelineModelListselected = new ArrayList<>();
    ArrayList<Integer> selectedPipelineIdsList = new ArrayList<>();

    ArrayList<GetLeadTypeList.Data> getLeadTypeList;
    ArrayList<MultiSelectModel> getLeadTypeModelList;
    ArrayList<MultiSelectModel> getLeadTypeModelListselected = new ArrayList<>();
    ArrayList<Integer> getSelectedTypeIdsList = new ArrayList<>();

    ArrayList<GetLeadDripCampaignList.Data> getLeadDripCampaignList;
    ArrayList<MultiSelectModel> getLeadDripCampaignModelList;
    ArrayList<MultiSelectModel> getLeadDripCampaignModelListselected = new ArrayList<>();
    ArrayList<Integer> selectedDripIdsList = new ArrayList<>();

    ArrayList<GetLeadScore.Data> getGetLeadScoreList;
    ArrayList<String> getGetLeadScoreNames;

    ArrayList<GetLastTouch.Data> getGetLastTouchList = new ArrayList<>();
    ArrayList<String> getGetLastTouchNames = new ArrayList();

    ArrayList<GetLastLogin.Data> getGetLastLoginList;
    ArrayList<String> getGetLastLoginNames;

    MultiSelectModel tagModel, agentModel, dripModel;

    String priceTo = "", priceFrom = "", dateTo = "", dateFrom = "";
    String agentIdsString = "", tagsIdsString = "", pipelineIdsString = "", dripIdsString = "", sourceIdsString = "", typeIdsString = "";

    int month, year, day, mHour, mMinute;
    Calendar newCalendar;
    String startDate = "", endDate = "", startTime = "", endTime = "";
    String lastTouchValue, lastLoginValue, leadScoreValue;

    boolean isSaveSearch;

    String dateFroms, dateTos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_filters);
        presenter = new AddFiltersPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_filters), true);
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
        calendarInstance();
    }


    private void initSpinners() {
        bi.spnLastLogin.setBackground(getDrawable(R.drawable.bg_edt_dark));
        bi.spnLastTouch.setBackground(getDrawable(R.drawable.bg_edt_dark));
        bi.spnLeadScore.setBackground(getDrawable(R.drawable.bg_edt_dark));
    }

    private void initCallsData() {
        presenter.getAgentNames();
        presenter.GetLastLogin();
        presenter.GetLastTouch();
        presenter.GetLeadScore();
        presenter.GetLeadSource();
        presenter.GetLeadTagList();
        presenter.GetLeadTypeList();
        presenter.GetLeadPipeline();
        presenter.GetLeadDripCampaignList();
    }

    private void initListeners() {
        bi.edtTags.setOnClickListener(this);
        bi.edtType.setOnClickListener(this);
        bi.edtLeadScore.setOnClickListener(this);
        bi.edtAssignedTo.setOnClickListener(this);
        bi.edtPipeline.setOnClickListener(this);
        bi.edtPriceRange.setOnClickListener(this);
        bi.edtDripCompaigns.setOnClickListener(this);
        bi.btnSaveAndSearch.setOnClickListener(this);
        bi.btnSearch.setOnClickListener(this);
        bi.btnClear.setOnClickListener(this);
        bi.edtDateRange.setOnClickListener(this);
    }

    private void showTagsDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Tags") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
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
//                        tagModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                        tagsIdsString = "";
                        getLeadTagModelListselected = new ArrayList<>();
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (int i = 0; i < selectedEncyrptedIds.size(); i++) {

                                getLeadTagModelListselected.add(new MultiSelectModel(selectedIds.get(i), selectedNames.get(i),
                                        selectedEncyrptedIds.get(i)));

                                if (tagsIdsString.equals("")) {
                                    tagsIdsString = selectedIds.get(i).toString();
                                } else {
                                    // tagsIdsString = tagsIdsString + "," + selectedEncyrptedIds.get(i);
                                    tagsIdsString = tagsIdsString + "," + selectedIds.get(i);
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

    private void showPipelineDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Pipeline") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedPipelineIdsList = new ArrayList<>();
                        selectedPipelineIdsList = selectedIds;


                        if (bi.lnPipeline.getChildCount() > 0) {
                            bi.lnPipeline.removeAllViews();
                        }

                        pipelineIdsString = "";
                        getPipelineModelListselected = new ArrayList<>();
                        for (int i = 0; i < selectedNames.size(); i++) {

                            getPipelineModelListselected.add(new MultiSelectModel(selectedIds.get(i), selectedNames.get(i)));

                            if (pipelineIdsString.equals("")) {
                                pipelineIdsString = String.valueOf(selectedIds.get(i));
                            } else {
                                pipelineIdsString = pipelineIdsString + "," + selectedIds.get(i);
                            }

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(selectedNames.get(i));
                            bi.lnPipeline.addView(child);
                        }
                        //tagModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (selectedPipelineIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedPipelineIdsList);
            multiSelectDialog.multiSelectList(getPipelineModelList);
        } else {
            multiSelectDialog.multiSelectList(getPipelineModelList);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void showAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0) //you can set minimum checkbox selection limit (Optional)
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

//                        agentModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames,
                                           ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                        agentIdsString = "";
                        searchListItemsselected = new ArrayList<>();
                        for (int i = 0; i < selectedEncyrptedIds.size(); i++) {

                            if (agentIdsString.equals("")) {
                                agentIdsString = String.valueOf(selectedEncyrptedIds.get(i));
                            } else {
                                agentIdsString = agentIdsString + "," + selectedEncyrptedIds.get(i);
                            }
                            searchListItemsselected.add(new MultiSelectModel(selectedIds.get(i),
                                    selectedNames.get(i), selectedEncyrptedIds.get(i)));
                        }
                    }

                    @Override
                    public void onCancel() {
                    }
                });
        if (selectedIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedIdsList);

            // for checking empty list to prevent crahses
            if (searchListItems != null) {

                multiSelectDialog.multiSelectList(searchListItems);
            }

        } else if (searchListItems != null) {
            // for checking empty list to prevent crahses
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

                        dripIdsString = "";
                        getLeadDripCampaignModelListselected = new ArrayList<>();
                        for (int i = 0; i < selectedNames.size(); i++) {

                            getLeadDripCampaignModelListselected.add(new MultiSelectModel(selectedIds.get(i), selectedNames.get(i)));

                            if (dripIdsString.equals("")) {
                                dripIdsString = String.valueOf(selectedIds.get(i));
                            } else {
                                dripIdsString = dripIdsString + "," + selectedIds.get(i);
                            }

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(selectedNames.get(i));
                            bi.lnDrip.addView(child);
                        }
//                        dripModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames,
                                           ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                    }

                    @Override
                    public void onCancel() {
                    }
                });


        if (selectedDripIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedDripIdsList);
            if (getLeadDripCampaignModelList != null) {
                multiSelectDialog.multiSelectList(getLeadDripCampaignModelList);
            }
        } else {
            if (getLeadDripCampaignModelList != null) {
                multiSelectDialog.multiSelectList(getLeadDripCampaignModelList);
            }
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void showSourceDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Lead Sources") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                //.setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedSourceNameIdsList = new ArrayList<>();
                        getLeadSourceNameListselected = new ArrayList<>();
                        selectedSourceNameIdsList = selectedIds;

                        if (bi.lnSuurce.getChildCount() > 0) {
                            bi.lnSuurce.removeAllViews();
                        }
                        sourceIdsString = "";
                        for (int i = 0; i < selectedNames.size(); i++) {

                            getLeadSourceNameListselected.add(new MultiSelectModel(selectedIds.get(i), selectedNames.get(i)));

                            if (sourceIdsString.equals("")) {
                                sourceIdsString = String.valueOf(selectedIds.get(i));
                            } else {
                                sourceIdsString = sourceIdsString + "," + selectedIds.get(i);
                            }

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(selectedNames.get(i));
                            bi.lnSuurce.addView(child);
                        }
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds,
                                           String commonSeperatedData) {
                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (selectedSourceNameIdsList.size() != 0) {

            multiSelectDialog.preSelectIDsList(selectedSourceNameIdsList);
            if (getLeadSourceNameList != null) {


                multiSelectDialog.multiSelectList(getLeadSourceNameList);
            }

        } else {
            if (getLeadSourceNameList != null) {
                multiSelectDialog.multiSelectList(getLeadSourceNameList);
            }


        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void showTypeDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Lead Types") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                //.setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        getSelectedTypeIdsList = new ArrayList<>();
                        getSelectedTypeIdsList = selectedIds;

                        if (bi.lnType.getChildCount() > 0) {
                            bi.lnType.removeAllViews();
                        }
                        typeIdsString = "";
                        getLeadTypeModelListselected = new ArrayList<>();
                        for (int i = 0; i < selectedNames.size(); i++) {

                            getLeadTypeModelListselected.add(new MultiSelectModel(selectedIds.get(i), selectedNames.get(i)));

                            if (typeIdsString.equals("")) {
                                typeIdsString = String.valueOf(selectedIds.get(i));
                            } else {
                                typeIdsString = typeIdsString + "," + selectedIds.get(i);
                            }

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(selectedNames.get(i));
                            bi.lnType.addView(child);
                        }

                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (getSelectedTypeIdsList != null && getSelectedTypeIdsList.size() > 0) {
            multiSelectDialog.preSelectIDsList(getSelectedTypeIdsList);
            multiSelectDialog.multiSelectList(getLeadTypeModelList);
        } else {
            multiSelectDialog.multiSelectList(getLeadTypeModelList);
        }

        /*if (selectedSourceNameIdsList.size() != 0) {

            if (getSelectedTypeIdsList != null) {
                multiSelectDialog.preSelectIDsList(getSelectedTypeIdsList);
                multiSelectDialog.multiSelectList(getLeadTypeModelList);
            }

        } else {

            if (getLeadTypeModelList != null) {
                multiSelectDialog.multiSelectList(getLeadTypeModelList);
            }

        }*/
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void clearFilters() {

        easyPreference.remove("agentIDs").save();
        easyPreference.remove("typeIDs").save();
        easyPreference.remove("sourceIDs").save();
        easyPreference.remove("tagIDs").save();
        easyPreference.remove("dripIDs").save();
        easyPreference.remove("leadScoreID").save();
        easyPreference.remove("leadScoreValue").save();
        easyPreference.remove("lastTouchID").save();
        easyPreference.remove("lastTouchValue").save();
        easyPreference.remove("lastLoginID").save();
        easyPreference.remove("lastLoginValue").save();
        easyPreference.remove("pipelineID").save();
        easyPreference.remove("notes").save();
        easyPreference.remove("leadID").save();
        easyPreference.remove("dateTo").save();
        easyPreference.remove("dateFrom").save();
        easyPreference.remove("priceTo").save();
        easyPreference.remove("priceFrom").save();
        // clear saved search and simple search
        easyPreference.remove("saveAndSearch").save();
        easyPreference.remove("search").save();

        // for save search value remove
        easyPreference.remove("agentID").save();
        easyPreference.remove("leadTypeID").save();
        easyPreference.remove("leadScoreMax").save();
        easyPreference.remove("tagsID").save();
        easyPreference.remove("priceMin").save();
        easyPreference.remove("priceMax").save();
        easyPreference.remove("dripCompaignID").save();
        easyPreference.remove("lastTouch").save();
        easyPreference.remove("lastLogin").save();
        easyPreference.remove("pipelineID1").save();
        easyPreference.remove("sourceID").save();
        easyPreference.remove("fromDate").save();
        easyPreference.remove("toDate").save();

        selectedIdsList = new ArrayList<>();
        bi.lnAgents.removeAllViews();
        getSelectedTypeIdsList = new ArrayList<>();
        bi.lnType.removeAllViews();
        selectedDripIdsList = new ArrayList<>();
        bi.lnDrip.removeAllViews();
        selectedPipelineIdsList = new ArrayList<>();
        bi.lnPipeline.removeAllViews();
        selectedSourceNameIdsList = new ArrayList<>();
        bi.lnSuurce.removeAllViews();
        selectedTagIdsList = new ArrayList<>();
        bi.lnTags.removeAllViews();

        bi.spnLeadScore.setSelectedIndex(0);
        bi.spnLastTouch.setSelectedIndex(0);
        bi.spnLastLogin.setSelectedIndex(0);

        searchListItemsselected = new ArrayList<>();
        getLeadTypeModelListselected = new ArrayList<>();
        getLeadSourceNameListselected = new ArrayList<>();
        getLeadTagModelListselected = new ArrayList<>();
        getLeadDripCampaignModelListselected = new ArrayList<>();
        getLeadSourceNameListselected = new ArrayList<>();

        priceTo = "";
        priceFrom = "";
        dateTo = "";
        dateFrom = "";
        agentIdsString = "";
        tagsIdsString = "";
        typeIdsString = "";
        sourceIdsString = "";
        dripIdsString = "";
        pipelineIdsString = "";

//        EasyPreference.Builder pref = new EasyPreference.Builder(context);
//        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(),"leadsFragment").save();

        easyPreference.remove("agentIdsList").save();
        easyPreference.remove("leadTypeIDList").save();
        easyPreference.remove("leadScoreValue").save();
        easyPreference.remove("tagsIDList").save();
        easyPreference.remove("dripCompaignIDList").save();
        easyPreference.remove("pipelineIDList").save();
        easyPreference.remove("sourceIDList").save();

        retrieveFilters();
        saveAndSearchFilters1();
    }


    private void saveAndSearchFilters1() {


        if (!agentIdsString.equals("")) {
            ArrayList<Integer> agentIdsList = new ArrayList(Arrays.asList(agentIdsString.split(",")));
            easyPreference.addObject("agentIdsList", agentIdsList).save();
        } else {
            easyPreference.remove("agentIdsList").save();
        }

        if (!tagsIdsString.equals("")) {
            ArrayList<Integer> tagsIDList = new ArrayList(Arrays.asList(tagsIdsString.split(",")));
            easyPreference.addObject("tagsIDList", tagsIDList).save();
        } else {
            easyPreference.remove("tagsIDList").save();
        }

        if (!dripIdsString.equals("")) {
            ArrayList<Integer> dripCompaignIDList = new ArrayList(Arrays.asList(dripIdsString.split(",")));
            easyPreference.addObject("dripCompaignIDList", dripCompaignIDList).save();
        } else {
            easyPreference.remove("dripCompaignIDList").save();
        }

        if (!sourceIdsString.equals("")) {
            ArrayList<Integer> sourceIDList = new ArrayList(Arrays.asList(sourceIdsString.split(",")));
            easyPreference.addObject("sourceIDList", sourceIDList).save();
        } else {
            easyPreference.remove("sourceIDList").save();
        }

        if (!pipelineIdsString.equals("")) {
            ArrayList<Integer> pipelineIDList = new ArrayList(Arrays.asList(pipelineIdsString.split(",")));
            easyPreference.addObject("pipelineIDList", pipelineIDList).save();
        } else {
            easyPreference.remove("pipelineIDList").save();
        }
        if (!typeIdsString.equals("")) {
            ArrayList<Integer> leadTypeIDList = new ArrayList(Arrays.asList(typeIdsString.split(",")));
            easyPreference.addObject("leadTypeIDList", leadTypeIDList).save();
        } else {
            easyPreference.remove("leadTypeIDList").save();
        }

        easyPreference.addString("priceTo", priceTo + "").save();
        easyPreference.addString("priceFrom", priceFrom + "").save();
        easyPreference.addString("notes", bi.edtNotes.getText().toString() + "").save();
        easyPreference.addObject("leadScoreValue", getGetLeadScoreList.get(bi.spnLeadScore.getSelectedIndex()).value + "").save();
        easyPreference.addString("lastTouchValue", getGetLastTouchList.get(bi.spnLastTouch.getSelectedIndex()).value + "").save();
        easyPreference.addString("lastLoginValue", getGetLastLoginList.get(bi.spnLastLogin.getSelectedIndex()).value + "").save();
        easyPreference.addString("dateFrom", dateFrom + "").save();
        easyPreference.addString("dateTo", dateTo + "").save();
        easyPreference.addString("leadID", bi.edtLeadID.getText().toString() + "").save();

        searchFilters(true);
    }

    private void saveAndSearchFilters() {

        easyPreference.addObject("agentIDs", searchListItemsselected).save();
        easyPreference.addObject("typeIDs", getLeadTypeModelListselected).save();
        easyPreference.addObject("sourceIDs", getLeadSourceNameListselected).save();
        easyPreference.addObject("tagIDs", getLeadTagModelListselected).save();
        easyPreference.addObject("dripIDs", getLeadDripCampaignModelListselected).save();
        easyPreference.addObject("pipelineID", getPipelineModelListselected).save();
        easyPreference.addInt("leadScoreID", bi.spnLeadScore.getSelectedIndex()).save();
        easyPreference.addInt("lastTouchID", bi.spnLastTouch.getSelectedIndex()).save();
        easyPreference.addInt("lastLoginID", bi.spnLastLogin.getSelectedIndex()).save();
        easyPreference.addString("notes", bi.edtNotes.getText().toString() + "").save();
        easyPreference.addString("leadID", bi.edtLeadID.getText().toString() + "").save();
        easyPreference.addString("priceTo", priceTo + "").save();
        easyPreference.addString("priceFrom", priceFrom + "").save();
        easyPreference.addString("dateTo", dateTo + "").save();
        easyPreference.addString("dateFrom", dateFrom + "").save();

        /*  save and serach showing data until user clear the filte */
        // all fields here except notes and leadId

        easyPreference.addString("saveAndSearch", "saveData").save();


        easyPreference.addString("agentID", agentIdsString + "").save();
        easyPreference.addString("leadTypeID", typeIdsString + "").save();
        easyPreference.addString("leadScoreMax", getGetLeadScoreList.get(bi.spnLeadScore.getSelectedIndex()).value + "").save();
        easyPreference.addString("tagsID", tagsIdsString + "").save();
        easyPreference.addString("priceMin", priceTo.equals("0") ? "" : priceTo + "").save();
        easyPreference.addString("priceMax", priceFrom.equals("0") ? "" : priceFrom + "").save();
        easyPreference.addString("dripCompaignID", dripIdsString + "").save();
        easyPreference.addString("lastTouch", getGetLastTouchList.get(bi.spnLastTouch.getSelectedIndex()).value + "").save();
        easyPreference.addString("lastLogin", getGetLastLoginList.get(bi.spnLastLogin.getSelectedIndex()).value + "").save();
        easyPreference.addString("pipelineID", pipelineIdsString + "").save();
        easyPreference.addString("sourceID", sourceIdsString + "").save();
        easyPreference.addString("fromDate", startDate).save();
        easyPreference.addString("toDate", endDate).save();


        String st = startDate;
        String et = endDate;

        searchFilters(true);
    }

    private void searchFilters(boolean isSaveSearch) {

        String leadID = bi.edtLeadID.getText().toString() + "";
        String agentID = agentIdsString + "";
        String leadTypeID = typeIdsString + "";
        String leadScoreMax = getGetLeadScoreList.get(bi.spnLeadScore.getSelectedIndex()).value + "";
        String tagsID = tagsIdsString + "";
        String priceMin = priceTo.equals("0") ? "" : priceTo + "";
        String priceMax = priceFrom.equals("0") ? "" : priceFrom + "";
        String notes = bi.edtNotes.getText().toString() + "";
        String dripCompaignID = dripIdsString + "";
        String lastTouch = getGetLastTouchList.get(bi.spnLastTouch.getSelectedIndex()).value + "";
        String lastLogin = getGetLastLoginList.get(bi.spnLastLogin.getSelectedIndex()).value + "";
        String pipelineID = pipelineIdsString + "";
        String sourceID = sourceIdsString + "";
        String fromDate = startDate;
        String toDate = endDate;

        Intent intent = new Intent();
        intent.putExtra("leadID", leadID);
        intent.putExtra("agentID", agentID);
        intent.putExtra("leadTypeID", leadTypeID);
        intent.putExtra("leadScoreMax", leadScoreMax);
        intent.putExtra("tagsID", tagsID);
        intent.putExtra("priceMin", priceMin);
        intent.putExtra("priceMax", priceMax);
        intent.putExtra("notes", notes);
        intent.putExtra("dripCompaignID", dripCompaignID);
        intent.putExtra("lastTouch", lastTouch);
        intent.putExtra("lastLogin", lastLogin);
        intent.putExtra("pipelineID", pipelineID);
        intent.putExtra("sourceID", sourceID);
        intent.putExtra("fromDate", fromDate);
        intent.putExtra("toDate", toDate);

        //added by akshay
        if (isSaveSearch) {
            intent.putExtra("isSaveSearch", "true");
        } else {
            intent.putExtra("isSaveSearch", "false");

        }

        // stop redirection to dashboard screen when click back button
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(), "leadsFragment").save();

        // for search bundle otherwise it is calling null
        easyPreference.addString("search", "searchResult").save();

        setResult(RESULT_OK, intent);
        finish();

    }


    public void showPriceRangeDialog(Context context) {

        Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_price_dialog);

        EditText edtPriceTo = dialog.findViewById(R.id.edtPriceTo);
        EditText edtPriceFrom = dialog.findViewById(R.id.edtPriceFrom);

        edtPriceTo.setText(priceTo);
        edtPriceFrom.setText(priceFrom);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {

            priceTo = edtPriceTo.getText().toString().equals("") ? "0" : edtPriceTo.getText().toString();
            priceFrom = edtPriceFrom.getText().toString().equals("") ? "max" : edtPriceFrom.getText().toString();
            priceFrom = priceFrom.equals("0") ? "max" : priceFrom;

            String price = priceFrom.equals("") ? "" : " to " + priceFrom;
            price = priceTo + price;

            bi.edtPriceRange.setText(price);
            priceFrom = priceFrom.equals("max") ? "" : priceFrom;
            dialog.dismiss();

        });

        dialog.show();

    }

    public void showDateRangeDialog(Context context) {

        Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_date_range_dialog);

        TextView tvDateTO = dialog.findViewById(R.id.tvDateTO);
        TextView tvDateFrom = dialog.findViewById(R.id.tvDateFrom);

        if (!dateFroms.equalsIgnoreCase("min")) {
            tvDateFrom.setText(dateFrom);
        }
        if (!dateTos.equalsIgnoreCase("max")) {
            tvDateTO.setText(dateTo);
        }

        tvDateTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDateDialog(tvDateTO, false);
            }
        });
        tvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDateDialog(tvDateFrom, true);
            }
        });

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {

            dateTo = tvDateTO.getText().toString().equals("") ? "max" : tvDateTO.getText().toString();
            dateFrom = tvDateFrom.getText().toString().equals("") ? "min" : tvDateFrom.getText().toString();
            //dateFrom = dateFrom.equals("min") ? "min" : dateFrom;

            String date = dateFrom + " to " + dateTo;
            //date = date + dateTo;

            bi.edtDateRange.setText(date);
            dateFrom = dateFrom.equals("min") ? "" : dateFrom;
            dateTo = dateFrom.equals("max") ? "" : dateTo;
            dialog.dismiss();


        });

        dialog.show();

    }

    private void calendarInstance() {

        newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);
    }

    private void showSpinnerDateDialog(TextView textView, boolean isStart) {
        calendarInstance();
        new SpinnerDatePickerDialogBuilder().context(context)
                .callback(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int _year, int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                        year = _year;
                        month = monthOfYear;
                        day = dayOfMonth;
                        newCalendar.set(year, month, day);

                        if (isStart) {
                            startDate = dateFormatter2.format(newCalendar.getTime());
                            textView.setText(dateFormatter2.format(newCalendar.getTime()));
                        } else {
                            endDate = dateFormatter2.format(newCalendar.getTime());
                            textView.setText(dateFormatter2.format(newCalendar.getTime()));
                        }
                    }
                })
                .showTitle(true)
                .defaultDate(year, month, day)
                .build()
                .show();

    }

    @Override
    public void updateUI(GetAgentsModel response) {

        agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        searchListItemsselected = new ArrayList<>();
        easyPreference.remove("agentIDs").save();
        ArrayList<String> agentIdsList = easyPreference.getObject("agentIdsList", ArrayList.class);

        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
            if (agentIdsList != null && agentIdsList.size() > 0) {
                for (String s : agentIdsList) {
                    if (s.equalsIgnoreCase(model.encryptedAgentID)) {

                        searchListItemsselected.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));

                        selectedIdsList.add(Integer.valueOf(model.agentID).intValue());

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.agentName);
                        bi.lnAgents.addView(child);

                        if (agentIdsString.equals("")) {
                            agentIdsString = model.encryptedAgentID;
                        } else {
                            agentIdsString = agentIdsString + "," + model.encryptedAgentID;
                        }


                    }
                }

            }
        }
        easyPreference.addObject("agentIDs", searchListItemsselected).save();


    }

    @Override
    public void updateUI(GetLeadSource response) {

        getLeadSourceList = new ArrayList<>();
        getLeadSourceNameList = new ArrayList<>();
        getLeadSourceList = response.data;
        getLeadSourceNameListselected.clear();
        easyPreference.remove("sourceIDs").save();
        ArrayList<String> sourceIDList = easyPreference.getObject("sourceIDList", ArrayList.class);
        for (GetLeadSource.Data model : getLeadSourceList) {
            getLeadSourceNameList.add(new MultiSelectModel(model.sourceID, model.sourceName));
            if (sourceIDList != null && sourceIDList.size() > 0) {

                for (String s : sourceIDList) {
                    if (Integer.parseInt(s) == model.sourceID) {
                        getLeadSourceNameListselected.add(new MultiSelectModel(model.sourceID, model.sourceName));

                        selectedSourceNameIdsList.add(model.sourceID);

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.sourceName);
                        bi.lnSuurce.addView(child);

                        if (sourceIdsString.equals("")) {
                            sourceIdsString = model.sourceID + "";
                        } else {
                            sourceIdsString = sourceIdsString + "," + model.sourceID;
                        }

                    }
                }

            }

        }
        easyPreference.addObject("sourceIDs", getLeadSourceNameListselected).save();
    }

    @Override
    public void updateUI(GetLeadScore response) {

        getGetLeadScoreList = new ArrayList<>();
        getGetLeadScoreNames = new ArrayList<>();
        getGetLeadScoreList = response.data;
        leadScoreValue = easyPreference.getString("leadScoreValue", "");

//        for (GetLeadScore.Data model : getGetLeadScoreList) {
        int selectedPos = 0;
        for (int i = 0; i < getGetLeadScoreList.size(); i++) {
            GetLeadScore.Data model = getGetLeadScoreList.get(i);
            getGetLeadScoreNames.add(model.text);
            if (leadScoreValue.contains(model.value)) {
                selectedPos = i;
            }

        }
        bi.spnLeadScore.setItems(getGetLeadScoreNames);
        bi.spnLeadScore.setSelectedIndex(selectedPos);


    }

    @Override
    public void updateUI(GetLastLogin response) {

        getGetLastLoginList = new ArrayList<>();
        getGetLastLoginNames = new ArrayList<>();
        getGetLastLoginList = response.data;
        lastLoginValue = easyPreference.getString("lastLoginValue", "");
        int selectedPos = 0;
//        for (GetLastLogin.Data model : getGetLastLoginList) {
        for (int i = 0; i < getGetLastLoginList.size(); i++) {
            GetLastLogin.Data model = getGetLastLoginList.get(i);
            getGetLastLoginNames.add(model.text);
            if (model.value.equals(lastLoginValue)) {
                selectedPos = i;

            }
        }
        bi.spnLastLogin.setItems(getGetLastLoginNames);
        bi.spnLastLogin.setSelectedIndex(selectedPos);
    }

    @Override
    public void updateUI(GetLastTouch response) {

        getGetLastTouchList = new ArrayList<>();
        getGetLastTouchNames = new ArrayList<>();
        getGetLastTouchList = response.data;
        lastTouchValue = easyPreference.getString("lastTouchValue", "");
        int selectedPos = 0;
        for (int i = 0; i < getGetLastTouchList.size(); i++) {
//        for (GetLastTouch.Data model : getGetLastTouchList) {
            GetLastTouch.Data model = getGetLastTouchList.get(i);
            getGetLastTouchNames.add(model.text);
            if (model.value.equals(lastTouchValue)) {
                selectedPos = i;
            }
        }
        bi.spnLastTouch.setItems(getGetLastTouchNames);
        bi.spnLastTouch.setSelectedIndex(selectedPos);

    }

    @Override
    public void updateUI(GetPipeline response) {

        getPipelineList = new ArrayList<>();
        getPipelineModelList = new ArrayList<>();
        getPipelineList = response.data;
        getLeadTypeModelListselected.clear();
        easyPreference.remove("pipelineID").save();
        ArrayList<String> pipelineIDList = easyPreference.getObject("pipelineIDList", ArrayList.class);
        for (GetPipeline.Data model : getPipelineList) {
            getPipelineModelList.add(new MultiSelectModel(model.id, model.name));
            if (pipelineIDList != null && pipelineIDList.size() > 0) {
                for (String s : pipelineIDList) {
                    if (Integer.parseInt(s) == model.id) {
                        getLeadTypeModelListselected.add(new MultiSelectModel(model.id, model.name));

                        selectedPipelineIdsList.add(model.id);

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.name);
                        bi.lnPipeline.addView(child);

                        if (pipelineIdsString.equals("")) {
                            pipelineIdsString = String.valueOf(model.id);
                        } else {
                            pipelineIdsString = pipelineIdsString + "," + model.id;
                        }

                    }
                }
            }

        }
        easyPreference.addObject("pipelineID", getLeadTypeModelListselected).save();
    }

    @Override
    public void updateUI(GetLeadTagList response) {

        getLeadTagList = new ArrayList<>();
        getLeadTagModelList = new ArrayList<>();
        getLeadTagList = response.data;
        getLeadTagModelListselected.clear();
        easyPreference.remove("tagIDs").save();
        ArrayList<String> tagsIDList = easyPreference.getObject("tagsIDList", ArrayList.class);
        for (GetLeadTagList.Data model : getLeadTagList) {
            getLeadTagModelList.add(new MultiSelectModel(model.tagID, model.label, model.encryptedTagID));
            if (tagsIDList != null && tagsIDList.size() > 0) {
                for (String s : tagsIDList) {
                    if (Integer.parseInt(s) == model.tagID) {
                        getLeadTagModelListselected.add(new MultiSelectModel(model.tagID, model.label, model.encryptedTagID));

                        selectedTagIdsList.add(model.tagID);

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.label);
                        bi.lnTags.addView(child);

                        if (tagsIdsString.equals("")) {
                            tagsIdsString = String.valueOf(model.tagID);
                        } else {
                            tagsIdsString = tagsIdsString + "," + model.tagID;
                        }
                    }
                }

            }
            easyPreference.addObject("tagIDs", getLeadTagModelListselected).save();
        }
    }

    @Override
    public void updateUI(GetLeadTypeList response) {

        getLeadTypeList = new ArrayList<>();
        getLeadTypeModelList = new ArrayList<>();
        getLeadTypeList = response.data;
        getLeadTypeModelListselected.clear();
        easyPreference.remove("typeIDs").save();
        ArrayList<String> leadTypeIDList = easyPreference.getObject("leadTypeIDList", ArrayList.class);
        for (GetLeadTypeList.Data model : getLeadTypeList) {
            getLeadTypeModelList.add(new MultiSelectModel(model.id, model.leadType));
            if (leadTypeIDList != null && leadTypeIDList.size() > 0) {
                for (String s : leadTypeIDList) {
                    if (Integer.parseInt(s) == model.id) {
                        getLeadTypeModelListselected.add(new MultiSelectModel(model.id, model.leadType));

                        getSelectedTypeIdsList.add(model.id);

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.leadType);
                        bi.lnType.addView(child);

                        if (typeIdsString.equals("")) {
                            typeIdsString = String.valueOf(model.id);
                        } else {
                            typeIdsString = typeIdsString + "," + model.id;
                        }
                    }
                }

            }
        }
        easyPreference.addObject("typeIDs", getLeadTypeModelListselected).save();
        //bi.spnType.setItems(getLeadTypeNameList);
    }

    @Override
    public void updateUI(GetLeadDripCampaignList response) {

        getLeadDripCampaignList = new ArrayList<>();
        getLeadDripCampaignModelList = new ArrayList<>();
        getLeadDripCampaignList = response.data;
        getLeadDripCampaignModelListselected.clear();
        easyPreference.remove("dripIDs").save();
        ArrayList<String> dripCompaignIDList = easyPreference.getObject("dripCompaignIDList", ArrayList.class);

        for (GetLeadDripCampaignList.Data model : getLeadDripCampaignList) {
            getLeadDripCampaignModelList.add(new MultiSelectModel(model.dripCompaignID, model.name));

            if (dripCompaignIDList != null && dripCompaignIDList.size() > 0) {
                for (String s : dripCompaignIDList) {
                    if (Integer.parseInt(s) == model.dripCompaignID) {
                        getLeadDripCampaignModelListselected.add(new MultiSelectModel(model.dripCompaignID, model.name));

                        selectedDripIdsList.add(model.dripCompaignID);

                        View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                        TextView textView = child.findViewById(R.id.txtDynamic);
                        textView.setText(model.name);
                        bi.lnDrip.addView(child);

                        if (dripIdsString.equals("")) {
                            dripIdsString = String.valueOf(model.dripCompaignID);
                        } else {
                            dripIdsString = dripIdsString + "," + model.dripCompaignID;
                        }
                    }
                }
            }
        }
        easyPreference.addObject("dripIDs", getLeadDripCampaignModelListselected).save();

        retrieveFilters();
    }

    private void retrieveFilters() {

        showProgressBar();


        //commenting as this code has been implemented in api responses
       /* ArrayList<LinkedTreeMap> agentIDs = easyPreference.getObject("agentIDs", ArrayList.class);
        if (agentIDs != null) {
            agentIdsString = "";
            for (LinkedTreeMap d : agentIDs) {
                selectedIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnAgents.addView(child);

                if (agentIdsString.equals("")) {
                    agentIdsString = d.get("encyptedIDs").toString();
                } else {
                    agentIdsString = agentIdsString + "," + d.get("encyptedIDs").toString();
                }

                searchListItemsselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString(), d.get("encyptedIDs").toString()));
            }
        } else {
            selectedIdsList = new ArrayList<>();
            bi.lnAgents.removeAllViews();
        }

        ArrayList<LinkedTreeMap> typeIDs = easyPreference.getObject("typeIDs", ArrayList.class);
        if (typeIDs != null) {
            typeIdsString = "";
            for (LinkedTreeMap d : typeIDs) {
                getSelectedTypeIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnType.addView(child);

                if (typeIdsString.equals("")) {
                    typeIdsString = d.get("id").toString();
                } else {
                    typeIdsString = typeIdsString + "," + d.get("id").toString();
                }

                getLeadTypeModelListselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString()));
            }
        } else {
            getSelectedTypeIdsList = new ArrayList<>();
            bi.lnType.removeAllViews();
        }

        ArrayList<LinkedTreeMap> sourceIDs = easyPreference.getObject("sourceIDs", ArrayList.class);
        if (sourceIDs != null) {
            sourceIdsString = "";
            for (LinkedTreeMap d : sourceIDs) {
                selectedSourceNameIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnSuurce.addView(child);

                if (sourceIdsString.equals("")) {
                    sourceIdsString = d.get("id").toString();
                } else {
                    sourceIdsString = sourceIdsString + "," + d.get("id").toString();
                }

                getLeadSourceNameListselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString()));
            }
        } else {
            selectedSourceNameIdsList = new ArrayList<>();
            bi.lnSuurce.removeAllViews();
        }

        ArrayList<LinkedTreeMap> tagIDs = easyPreference.getObject("tagIDs", ArrayList.class);
        if (tagIDs != null) {
            tagsIdsString = "";
            for (LinkedTreeMap d : tagIDs) {
                selectedTagIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnTags.addView(child);

                if (tagsIdsString.equals("")) {
                    tagsIdsString = d.get("encyptedIDs").toString();
                } else {
                    tagsIdsString = tagsIdsString + "," + d.get("encyptedIDs").toString();
                }

                getLeadTagModelListselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString(), d.get("encyptedIDs").toString()));
            }
        } else {
            selectedTagIdsList = new ArrayList<>();
            bi.lnTags.removeAllViews();
        }

        ArrayList<LinkedTreeMap> dripIDs = easyPreference.getObject("dripIDs", ArrayList.class);
        if (dripIDs != null) {
            dripIdsString = "";
            for (LinkedTreeMap d : dripIDs) {
                selectedDripIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnDrip.addView(child);

                if (dripIdsString.equals("")) {
                    dripIdsString = d.get("id").toString();
                } else {
                    dripIdsString = dripIdsString + "," + d.get("id").toString();
                }

                getLeadDripCampaignModelListselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString()));
            }
        } else {
            selectedDripIdsList = new ArrayList<>();
            bi.lnDrip.removeAllViews();
        }

        ArrayList<LinkedTreeMap> pipelineIDS = easyPreference.getObject("pipelineID", ArrayList.class);
        if (pipelineIDS != null) {
            pipelineIdsString = "";
            for (LinkedTreeMap d : pipelineIDS) {
                selectedDripIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(d.get("name").toString());
                bi.lnPipeline.addView(child);

                if (pipelineIdsString.equals("")) {
                    pipelineIdsString = d.get("id").toString();
                } else {
                    pipelineIdsString = pipelineIdsString + "," + d.get("id").toString();
                }

                getPipelineModelListselected.add(new MultiSelectModel(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()),
                        d.get("name").toString()));
            }
        } else {
            selectedPipelineIdsList = new ArrayList<>();
            bi.lnPipeline.removeAllViews();
        }*/

        /*int leadScoreID = easyPreference.getInt("leadScoreID", 0);
        int lastTouchID = easyPreference.getInt("lastTouchID", 0);
        int lastLoginID = easyPreference.getInt("lastLoginID", 0);*/


        String notes = easyPreference.getString("notes", "");
        String leadID = easyPreference.getString("leadID", "");
        String priceTos = easyPreference.getString("priceTo", "");
        String priceFroms = easyPreference.getString("priceFrom", "");
        dateTos = easyPreference.getString("dateTo", "");
        dateFroms = easyPreference.getString("dateFrom", "");


        /*bi.spnLeadScore.setSelectedIndex(leadScoreID);
        bi.spnLastTouch.setSelectedIndex(lastTouchID);
        bi.spnLastLogin.setSelectedIndex(lastLoginID);*/

        bi.edtLeadID.setText(leadID);
        bi.edtNotes.setText(notes);

        priceTos = priceTos.equals("") ? "0" : priceTos;
        priceFroms = priceFroms.equals("0") ? "max" : priceFroms;
        priceFroms = priceFroms.equals("") ? "max" : priceFroms;
        String price = priceFroms.equals("") ? "" : " to " + priceFroms;
        price = priceTos + price;
        bi.edtPriceRange.setText(price);

        dateTos = dateTos.equals("") ? "max" : dateTos;
        dateFroms = dateFroms.equals("") ? "min" : dateFroms;
        String date = dateFroms + " to " + dateTos;
        bi.edtDateRange.setText(date);

        priceTos = priceTos.equals("0") ? "" : priceTos;
        priceFroms = priceFroms.equals("max") ? "" : priceFroms;
        priceTo = priceTos;
        priceFrom = priceFroms;

        dateTos = dateTos.equals("max") ? "" : dateTos;
        dateFroms = dateFroms.equals("min") ? "" : dateFroms;
        dateTo = dateTos;
        dateFrom = dateFroms;

        startDate = dateFrom.equals("") ? "" : GH.getInstance().formatter(dateFrom, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM-dd-yyyy");
        endDate = dateTo.equals("") ? "" : GH.getInstance().formatter(dateTo, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM-dd-yyyy");


        hideProgressBar();
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
       /* if (error.contains("Authorization has been denied for this request")) {
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
        GH.getInstance().ShowProgressDialog(AddFiltersActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.edtAssignedTo:
                showAgentDialog();
                break;
            case R.id.edtTags:
                showTagsDialog();
                break;
            case R.id.edtDripCompaigns:
                showDripDialog();
                break;
            case R.id.edtLeadScore:
                showSourceDialog();
                break;
            case R.id.edtPipeline:
                showPipelineDialog();
                break;
            case R.id.edtType:
                showTypeDialog();
                break;
            case R.id.edtPriceRange:
                showPriceRangeDialog(context);
                break;
            case R.id.edtDateRange:
                showDateRangeDialog(context);
                break;
            case R.id.btnSaveAndSearch:
                saveAndSearchFilters1();
                break;
            case R.id.btnSearch:
                searchFilters(false);
                break;
            case R.id.btnClear:
//                clearFilters();
                showClearConfirmationDialog(AddFiltersActivity.this,
                        "Confirmation", "Are you sure you want to remove all saved filters?", "Yes", "No");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_notify).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_clear).setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {

//            clearFilters();
            showClearConfirmationDialog(AddFiltersActivity.this,
                    "Confirmation", "Are you sure you want to remove all saved filters?", "Yes", "No");

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // for testing
        // saving status of fragment other wise leads redirect to dash board
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(), "leadsFragment").save();
    }

    public void showClearConfirmationDialog(Context context, String title, String message,
                                            String positiveButtonText, String negativeButtonText) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                clearFilters();

            }
        });

        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


}