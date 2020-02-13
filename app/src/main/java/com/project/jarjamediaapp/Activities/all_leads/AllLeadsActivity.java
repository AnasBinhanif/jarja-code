package com.project.jarjamediaapp.Activities.all_leads;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAllleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class AllLeadsActivity extends BaseActivity implements View.OnClickListener, AllLeadsContract.View {

    ActivityAllleadsBinding bi;
    Context context = AllLeadsActivity.this;
    AllLeadsPresenter presenter;
    ArrayList<GetAllLeads.LeadsList> leadsList;
    String mSearchQuery = "";
    String resultSetType = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_allleads);
        presenter = new AllLeadsPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.leads), true);


    }

    private void handleIntent() {
        resultSetType = getIntent().getStringExtra("resultType");
    }

    @Override
    public void initViews() {
        handleIntent();
        callGetAllLeads();
        bi.edtSearch.onActionViewExpanded();
        bi.edtSearch.clearFocus();
        bi.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchQuery = newText;
                ArrayList<GetAllLeads.LeadsList> filteredlist = filter(leadsList, newText);
                populateListData(filteredlist);
                return false;
            }
        });
    }

    private void callGetAllLeads() {

        String leadID = "";
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
        String agentID = "";
        String leadTypeID = "";
        String leadScoreMin = "";
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
        String pageNo = "0";
        String resultType = resultSetType;
        String pageSize = "25";

        presenter.getAllLeads(leadID, spouseName, email, company, phone, address, city, state, county, zip, countryID, propertyType, timeFrameID, preApproval,
                houseToSell, agentID, leadTypeID, leadScoreMin, leadScoreMax, tagsID, priceMin, priceMax, notes, dripCompaignID, lastTouch, lastLogin, pipelineID,
                sourceID, fromDate, toDate, searchBy, firstNameAsc, lastNameAsc, emailAddressAsc, registeredDateAsc, lastLoginedInAsc, lastLoginedCountAsc,
                lastTouchedInAsc, conversationCellAsc, conversationEmailAsc, conversationMsgAsc, priceAsc, cityAsc, timeFrameAsc, activitiesSavedSearchAsc,
                activitiesViewAsc, activitiesFavoriteAsc, isSaveSearch, isFilterClear, resultType, pageNo, pageSize);

    }

    private void populateListData(ArrayList<GetAllLeads.LeadsList> leadsList) {

        bi.recyclerViewAllLeads.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewAllLeads.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewAllLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewAllLeads.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_all_leads_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvPhone, R.id.tvEmail, R.id.tvInitial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetAllLeads.LeadsList, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            TextView tvPhone = (TextView) integerMap.get(R.id.tvPhone);
            TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
            TextView tvInitial = (TextView) integerMap.get(R.id.tvInitial);

            tvName.setText(allLeadsList.firstName + " " + allLeadsList.lastName);
            tvPhone.setText(allLeadsList.primaryPhone);
            tvEmail.setText(allLeadsList.primaryEmail);

            tvInitial.setText(allLeadsList.firstName.substring(0, 1) + allLeadsList.lastName.substring(0, 1));

            return Unit.INSTANCE;
        });

        bi.recyclerViewAllLeads.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetAllLeads.LeadsList, Integer, Unit>) (viewComplainList, integer) -> {

            switchActivity(LeadDetailActivity.class);

            return Unit.INSTANCE;
        });


    }

    private ArrayList<GetAllLeads.LeadsList> filter(ArrayList<GetAllLeads.LeadsList> models, String query) {
        query = query.toLowerCase();
        final ArrayList<GetAllLeads.LeadsList> filteredModelList = new ArrayList<>();
        if (query.equals("") | query.isEmpty()) {
            filteredModelList.addAll(models);
            return filteredModelList;
        }

        for (GetAllLeads.LeadsList model : models) {
            final String name = model.firstName.toLowerCase() + " " + model.lastName.toLowerCase();
            final String email = model.primaryEmail.toLowerCase();
            final String phone = model.primaryPhone.toLowerCase();
            if (name.contains(query) || email.contains(query) || phone.contains(query)) {
                filteredModelList.add(model);
            }
        }


        return filteredModelList;
    }

    @Override
    public void updateUI(GetAllLeads response) {

        leadsList = new ArrayList<>();
        leadsList = response.data.leadsList;

        if (leadsList.size() == 0) {
            bi.lnAllLeads.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        } else {
            bi.lnAllLeads.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
            populateListData(leadsList);
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

    }
}
