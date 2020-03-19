package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Activities.add_filters.AddFiltersActivity;
import com.project.jarjamediaapp.Activities.all_leads.AllLeadsActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.Models.GetFindLeads;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentFindleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

import static android.app.Activity.RESULT_OK;

public class FindLeadsFragment extends BaseFragment implements FindLeadsContract.View, View.OnClickListener {

    FragmentFindleadsBinding bi;
    Context context;
    FindLeadsPresenter presenter;
    ArrayList<GetLeadCounts.LeadsCount> getLeadCountList;
    Intent bundle = null;
    int totalPages =0;
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
    public void setupViews() {

        initViews();
        callGetLeadCounts(bundle);
    }

    private void initViews() {

        bi.tvFilter.setOnClickListener(this);
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
        String leadScoreMin = "";
        String leadScoreMax = bundle != null ? bundle.getStringExtra("leadScoreMax") : "";
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
        String registeredDateAsc = "";
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
        String isSaveSearch = "false";
        String isFilterClear = "false";
        String resultSetType = "All";
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

        leadsList.add(new GetFindLeads("New Leads", getLeadCountList.get(0).newLeadsCount));
        leadsList.add(new GetFindLeads("All Leads", getLeadCountList.get(0).allLeadsCount));
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
        GH.getInstance().ShowProgressDialog(getActivity());
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
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
                callGetLeadCounts(data);
            }
        }
    }
}