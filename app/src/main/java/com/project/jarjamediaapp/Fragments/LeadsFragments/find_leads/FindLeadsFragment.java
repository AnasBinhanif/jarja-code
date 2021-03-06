package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.add_filters.AddFiltersActivity;
import com.project.jarjamediaapp.Activities.all_leads.AllLeadsActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.Models.GetFindLeads;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.Models.GetLeadSearchFiltersModel;
import com.project.jarjamediaapp.Models.KeyValueModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentFindleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FindLeadsFragment extends BaseFragment implements FindLeadsContract.View, View.OnClickListener {

    FragmentFindleadsBinding bi;
    Context context;
    FindLeadsPresenter presenter;
    ArrayList<GetLeadCounts.LeadsCount> getLeadCountList;
    Intent bundle = null;
    int totalPages = 0;

    SwipeRefreshLayout swipeRefreshLayout;

    public FindLeadsFragment() {
        // Required empty public constructor
    }

    public static FindLeadsFragment newInstance(String fragment_title, int position) {
        FindLeadsFragment findLeadsFragment = new FindLeadsFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putInt("position", position);
        findLeadsFragment.setArguments(args);
        return findLeadsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_findleads, container, false);
        presenter = new FindLeadsPresenter(this);
        presenter.initScreen();


        return bi.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeadSearchFilters();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


       /* if (easyPreference.getString("saveAndSearch", "").equals("saveData")) {
            getSaveLeadsData();
        } else if (bundle == null) {
            callGetLeadCounts(bundle);

        } else if (easyPreference.getString("search", "").equals("searchResult")) {
            callGetLeadCounts(bundle);
        } else {
            bundle = null;
            bundle = null;
            callGetLeadCounts(bundle);
        }*/


    }

    @Override
    public void setupViews() {

        initViews();

   /*     if (easyPreference.getString("saveAndSearch", "").equals("saveData")) {
            getSaveLeadsData();
        } else if (bundle == null) {
//            callGetLeadCounts(bundle);
            getLeadSearchFilters();
        } else if (easyPreference.getString("search", "").equals("searchResult")) {

            callGetLeadCounts(bundle);
        } else {

            bundle = null;
            callGetLeadCounts(bundle);
        }*/

        getLeadSearchFilters();
    }

    private void initViews() {

        bi.tvFilter.setOnClickListener(this);
    }

    private void getLeadSearchFilters() {
        presenter.getLeadSearchFilters();
    }

    private void callGetLeadCounts(Intent bundle) {

        String leadID = bundle != null ? bundle.getStringExtra("leadID") : "";
        String spouseName = "";
        String email = "";
        String company = "";
        String phone = "";
        String address = "";
        String city = "";
        String state = "";
        String county = "";
        String zip = "";
        String countryID = "";
        String propertyType = "";
        String timeFrameID = "";
        String preApproval = "";
        String houseToSell = "";
        String agentID = bundle != null ? bundle.getStringExtra("agentID") : "";
        String leadTypeID = bundle != null ? bundle.getStringExtra("leadTypeID") : "";
        String leadScore = bundle != null ? bundle.getStringExtra("leadScoreMax") : "";
        String leadScores[] = !leadScore.equals("") ? leadScore.split("-") : null;
        String leadScoreMin = leadScores != null ? leadScores[0] : "";
        String leadScoreMax = leadScores != null ? leadScores[1] : "";
        String tagsID = bundle != null ? bundle.getStringExtra("tagsID") : "";
        String priceMin = bundle != null ? bundle.getStringExtra("priceMin") : "";
        String priceMax = bundle != null ? bundle.getStringExtra("priceMax") : "";
        String notes = bundle != null ? bundle.getStringExtra("notes") : "";
        String dripCompaignID = bundle != null ? bundle.getStringExtra("dripCompaignID") : "";
        String lastTouch = bundle != null ? bundle.getStringExtra("lastTouch") : "";
        String lastLogin = bundle != null ? bundle.getStringExtra("lastLogin") : "";
        String pipelineID = bundle != null ? bundle.getStringExtra("pipelineID") : "";
        String sourceID = bundle != null ? bundle.getStringExtra("sourceID") : "";
        String fromDate = bundle != null ? bundle.getStringExtra("fromDate") : "";
        String toDate = bundle != null ? bundle.getStringExtra("toDate") : "";
        String searchBy = "";
        String firstNameAsc = "";
        String lastNameAsc = "";
        String emailAddressAsc = "";
        boolean registeredDateAsc = false;
        String lastLoginedInAsc = "";
        String lastLoginedCountAsc = "";
        String lastTouchedInAsc = "";
        String conversationCellAsc = "";
        String conversationEmailAsc = "";
        String conversationMsgAsc = "";
        String priceAsc = "";
        String cityAsc = "";
        String timeFrameAsc = "";
        String activitiesSavedSearchAsc = "";
        String activitiesViewAsc = "";
        String activitiesFavoriteAsc = "";
//        String isSaveSearch = "false";
        String isSaveSearch = bundle != null ? bundle.getStringExtra("isSaveSearch") : "false";
        String isFilterClear = "false";
        String resultSetType = "New Leads";
        String pageNo = "0";
        String pageSize = "25";

        presenter.getLeadCounts(leadID, spouseName, email, company, phone, address, city, state, county, zip, countryID, propertyType, timeFrameID, preApproval,
                houseToSell, agentID, leadTypeID, leadScoreMin, leadScoreMax, tagsID, priceMin, priceMax, notes, dripCompaignID, lastTouch, lastLogin, pipelineID,
                sourceID, fromDate, toDate, searchBy, firstNameAsc, lastNameAsc, emailAddressAsc, registeredDateAsc, lastLoginedInAsc, lastLoginedCountAsc,
                lastTouchedInAsc, conversationCellAsc, conversationEmailAsc, conversationMsgAsc, priceAsc, cityAsc, timeFrameAsc, activitiesSavedSearchAsc,
                activitiesViewAsc, activitiesFavoriteAsc, isSaveSearch, isFilterClear, resultSetType, pageNo, pageSize);
    }

    private void populateListData() {

        List<GetFindLeads> leadsList = new ArrayList<>();

        leadsList.add(new GetFindLeads("All Leads", getLeadCountList.get(0).allLeadsCount));
        leadsList.add(new GetFindLeads("New Leads", getLeadCountList.get(0).newLeadsCount));
        leadsList.add(new GetFindLeads("Hot Leads", getLeadCountList.get(0).hotLeadsCount));
        leadsList.add(new GetFindLeads("Active Clients", getLeadCountList.get(0).activeLeadsCount));
        leadsList.add(new GetFindLeads("Under Contract", getLeadCountList.get(0).underContractLeadsCount));
        leadsList.add(new GetFindLeads("Closed", getLeadCountList.get(0).closeLeadsCount));

        bi.recyclerViewFindLeads.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewFindLeads.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewFindLeads.addItemDecoration(new DividerItemDecoration(context, 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_find_leads_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvCount);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetFindLeads, Integer, Map<Integer, ? extends View>, Unit>) (view, findLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(findLeadsList.getName());

            TextView tvCount = (TextView) integerMap.get(R.id.tvCount);
            tvCount.setText(findLeadsList.getCount());

            return Unit.INSTANCE;
        });

        bi.recyclerViewFindLeads.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetFindLeads, Integer, Unit>) (viewComplainList, integer) -> {

            String resultTYpe = "";
            switch (integer) {
                case 0:
                    resultTYpe = "All";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
                case 1:
                    resultTYpe = "New Leads";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
                case 2:
                    resultTYpe = "Hot Leads";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
                case 3:
                    resultTYpe = "Active Clients";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
                case 4:
                    resultTYpe = "Under Contract";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
                case 5:
                    resultTYpe = "Closed Leads";
                    totalPages = Integer.valueOf(viewComplainList.getCount());
                    break;
            }
            Intent intent = new Intent(context, AllLeadsActivity.class);
            intent.putExtra("bundle", bundle);
            intent.putExtra("resultType", resultTYpe);
            intent.putExtra("totalPages", totalPages);
            intent.putExtra("type", 0);
            startActivity(intent);

            return Unit.INSTANCE;
        });
    }

    @Override
    public void updateUI(GetLeadCounts response) {
        getLeadCountList = new ArrayList<>();
        getLeadCountList = response.data.leadsCount;
        populateListData();
    }

    @Override
    public void updateUIonFalse(String message) {
        //ToastUtils.showToastLong(context, message);
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
        GH.getInstance().ShowProgressDialog(getActivity());
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    private void clearOldPreferences() {
        easyPreference.remove("agentIdsList").save();
        easyPreference.remove("leadTypeIDList").save();
        easyPreference.remove("leadScoreValue").save();
        easyPreference.remove("tagsIDList").save();
        easyPreference.remove("dripCompaignIDList").save();
        easyPreference.remove("pipelineIDList").save();
        easyPreference.remove("sourceIDList").save();
        easyPreference.remove("priceTo").save();
        easyPreference.remove("priceFrom").save();
        easyPreference.remove("notes").save();
        easyPreference.remove("lastTouchValue").save();
        easyPreference.remove("lastLoginValue").save();
        easyPreference.remove("dateFrom").save();
        easyPreference.remove("dateTo").save();
        easyPreference.remove("leadID").save();
    }

    @Override
    public void updateUIWithSearchFiltersResponse(GetLeadSearchFiltersModel getLeadSearchFiltersModel) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Intent intent = new Intent();

        clearOldPreferences();

        if (getLeadSearchFiltersModel.getStatus().equalsIgnoreCase("success")
                && getLeadSearchFiltersModel.getObjectsList().size() > 0) {

            String leadID = "";
            String agentID = "";
            String leadTypeID = "";
            String leadScoreMax = "";
            String tagsID = "";
            String priceMin = "";
            String priceMax = "";
            String notes = "";
            String dripCompaignID = "";
            String lastTouch = "";
            String lastLogin = "";
            String pipelineID = "";
            String sourceID = "";
            String fromDate = "";
            String toDate = "";


            for (KeyValueModel object : getLeadSearchFiltersModel.getObjectsList()) {
                if (object.getKey().equalsIgnoreCase("AgentID")) {
                    agentID = object.getValue();
                    ArrayList agentIdsList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("agentIdsList", agentIdsList).save();
                }
                if (object.getKey().equalsIgnoreCase("LeadType")) {
                    leadTypeID = object.getValue();
                    ArrayList<Integer> leadTypeIDList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("leadTypeIDList", leadTypeIDList).save();
                }
                if (object.getKey().equalsIgnoreCase("LeadScore")) {
                    leadScoreMax = object.getValue();
                    easyPreference.addObject("leadScoreValue", leadScoreMax).save();

                }
                if (object.getKey().equalsIgnoreCase("Tags")) {
                    tagsID = object.getValue();
                    ArrayList tagsIDList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("tagsIDList", tagsIDList).save();
                }
                if (object.getKey().equalsIgnoreCase("PriceFrom")) {
                    priceMin = object.getValue();
                    easyPreference.addString("priceTo", priceMin + "").save();
                }
                if (object.getKey().equalsIgnoreCase("PriceTo")) {
                    priceMax = object.getValue();
                    easyPreference.addString("priceFrom", priceMax + "").save();
                }
                if (object.getKey().equalsIgnoreCase("Notes")) {
                    notes = object.getValue();
                    easyPreference.addString("notes", notes + "").save();
                }
                if (object.getKey().equalsIgnoreCase("DripCampaingID")) {
                    dripCompaignID = object.getValue();
                    ArrayList dripCompaignIDList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("dripCompaignIDList", dripCompaignIDList).save();
                }
                if (object.getKey().equalsIgnoreCase("LastTouch")) {
                    lastTouch = object.getValue();
                    easyPreference.addString("lastTouchValue", lastTouch).save();
                }
                if (object.getKey().equalsIgnoreCase("LastLogin")) {
                    lastLogin = object.getValue();
                    easyPreference.addString("lastLoginValue", lastLogin).save();
                }
                if (object.getKey().equalsIgnoreCase("Pipeline")) {
                    pipelineID = object.getValue();
                    ArrayList pipelineIDList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("pipelineIDList", pipelineIDList).save();
                }
                if (object.getKey().equalsIgnoreCase("SourceID")) {
                    sourceID = object.getValue();
                    ArrayList sourceIDList = new ArrayList(Arrays.asList(object.getValue().split(",")));
                    easyPreference.addObject("sourceIDList", sourceIDList).save();
                }
                if (object.getKey().equalsIgnoreCase("DateFrom")) {
                    fromDate = object.getValue();
                    easyPreference.addString("dateFrom", fromDate + "").save();
                }
                if (object.getKey().equalsIgnoreCase("ToDate")) {
                    toDate = object.getValue();
                    easyPreference.addString("dateTo", toDate + "").save();
                }
                if (object.getKey().equalsIgnoreCase("LeadID")) {
                    leadID = object.getValue();
                    easyPreference.addString("leadID", leadID + "").save();
                }
            }
            easyPreference.addBoolean("dataFound", true).save();

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

            bundle = intent;
            callGetLeadCounts(bundle);
        } else {
            bundle = null;
            callGetLeadCounts(bundle);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_notify).setVisible(false);
        menu.findItem(R.id.action_clear).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvFilter:

                Intent i = new Intent(getActivity(), AddFiltersActivity.class);
                startActivityForResult(i, 05);
                //switchActivity(getActivity(), AddFiltersActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 05) {
            if (resultCode == RESULT_OK) {
                bundle = data;
                if (bundle.getStringExtra("isSaveSearch").equals("true")) {
                    callGetLeadCounts(data);
                } else {
                    clearOldPreferences();
                    callGetLeadCounts(data);
                }

            } else if (resultCode == RESULT_CANCELED) {
                getLeadSearchFilters();
            }
        }
    }

    public void getSaveLeadsData() {


        String leadID = easyPreference.getString("leadID", "");
        String agentID = easyPreference.getString("agentID", "");
        String leadTypeID = easyPreference.getString("leadTypeID", "");
        String leadScoreMax = easyPreference.getString("leadScoreMax", "");
        String tagsID = easyPreference.getString("tagsID", "");
        String priceMin = easyPreference.getString("priceMin", "");
        String priceMax = easyPreference.getString("priceMax", "");
        String notes = easyPreference.getString("notes", "");
        String dripCompaignID = easyPreference.getString("dripCompaignID", "");
        String lastTouch = easyPreference.getString("lastTouch", "");
        String lastLogin = easyPreference.getString("lastLogin", "");
        String pipelineID = easyPreference.getString("pipelineID1", "");
        String sourceID = easyPreference.getString("sourceID", "");
        String fromDate = easyPreference.getString("fromDate", "");
        String toDate = easyPreference.getString("toDate", "");


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

        // fro coming fom dashboard so it will call save bundle data
        bundle = intent;
        callGetLeadCounts(intent);

    }

 /*   public void getFilterObject() {

        ArrayList<LinkedTreeMap> agentIDs = easyPreference.getObject("agentIDs", ArrayList.class);
        if (agentIDs != null) {
            String agentIdsString = "";
            for (LinkedTreeMap d : agentIDs) {
                selectedIdsList.add(Integer.valueOf(Double.valueOf(d.get("id").toString()).intValue()));

            }
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}