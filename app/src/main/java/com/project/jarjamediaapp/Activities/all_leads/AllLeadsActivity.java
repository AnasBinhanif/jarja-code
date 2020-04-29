package com.project.jarjamediaapp.Activities.all_leads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetPropertyLeads;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAllleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.HashMap;
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
    ArrayList<GetPropertyLeads.LeadsList> propertyleadsList = new ArrayList<>();
    String mSearchQuery = "", propertyID = "";
    String resultSetType = "All";
    Intent data = null;
    LinearLayoutManager linearLayoutManager;
    int page = 0;
    int totalPages, type = 0;
    RecyclerAdapterUtil recyclerAdapterUtil;
    boolean isLoading = false, isFilter = false;
    GetAllLeads modelGetAllLeads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_allleads);
        presenter = new AllLeadsPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.leads), true);

    }

    private void handleIntent() {

        type = getIntent().getIntExtra("type", 0);

        // 0 For All Leads
        //1 For PropertyLeads

        switch (type) {

            case 0:
                resultSetType = getIntent().getStringExtra("resultType");
                data = getIntent().getParcelableExtra("bundle");
                totalPages = getIntent().getIntExtra("totalPages", 0);

                callGetAllLeads(data, String.valueOf(page));
                break;
            case 1:
                propertyID = getIntent().getStringExtra("propertyID");
                callPropertyLeadList();
                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        leadsList.clear();
        page = 0;
        if (bi.edtSearch.getText().toString().equals("")){
            handleIntent();
        }else{
            leadsList = new ArrayList<>();
            page = 0;
            isFilter = true;
            presenter.SearchLead(page, bi.edtSearch.getText().toString());
        }
    }

    @Override
    public void initViews() {

        leadsList = new ArrayList<>();
        initPagination();
        bi.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.edtSearch.getText().length();
                    try {
                        if (length > 0) {
                            leadsList = new ArrayList<>();
                            page = 0;
                            isFilter = true;
                            presenter.SearchLead(page, bi.edtSearch.getText().toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });


    }

    private void initPagination() {

        bi.recyclerViewAllLeads.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                Log.d("scroll", "scroll down");
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                // Load more if we have reach the end to the recyclerView
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    Log.d("scroll", "last item");
                    if (!isLoading) {
                        if (isFilter) {
                            totalPages = modelGetAllLeads.data.noOfPages;
                        }

                        if (totalPages > leadsList.size()) {
                            page++;
                            int pg = page * 25;
                            try {
                                callGetAllLeads(data, String.valueOf(pg));
                                isLoading = true;
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }

                    }
                }
            }
        });
    }

    private void callGetAllLeads(Intent bundle, String pgNo) {

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
        String pageNo = pgNo;
        String resultType = resultSetType;
        String pageSize = "25";

        presenter.getAllLeads(leadID, spouseName, email, company, phone, address, city, state, county, zip, countryID, propertyType, timeFrameID, preApproval,
                houseToSell, agentID, leadTypeID, leadScoreMin, leadScoreMax, tagsID, priceMin, priceMax, notes, dripCompaignID, lastTouch, lastLogin, pipelineID,
                sourceID, fromDate, toDate, searchBy, firstNameAsc, lastNameAsc, emailAddressAsc, registeredDateAsc, lastLoginedInAsc, lastLoginedCountAsc,
                lastTouchedInAsc, conversationCellAsc, conversationEmailAsc, conversationMsgAsc, priceAsc, cityAsc, timeFrameAsc, activitiesSavedSearchAsc,
                activitiesViewAsc, activitiesFavoriteAsc, isSaveSearch, isFilterClear, resultType, pageNo, pageSize);

    }

    private void callPropertyLeadList() {
        presenter.GetPropertyLeads(propertyID);
    }

    private void populateListData(ArrayList<GetAllLeads.LeadsList> leadsList) {

        if (page == 0) {
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            bi.recyclerViewAllLeads.setLayoutManager(linearLayoutManager);
            bi.recyclerViewAllLeads.setItemAnimator(new DefaultItemAnimator());
            bi.recyclerViewAllLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewAllLeads.getContext(), 1));
            recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_all_leads_layout);
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
            isLoading = false;
        } else {
            isLoading = false;
            recyclerAdapterUtil.notifyDataSetChanged();
        }

        recyclerAdapterUtil.addOnClickListener((Function2<GetAllLeads.LeadsList, Integer, Unit>) (viewComplainList, integer) -> {

            Map<String, String> map = new HashMap<>();
            map.put("leadID", viewComplainList.id);
            switchActivityWithIntentString(LeadDetailActivity.class, (HashMap<String, String>) map);

            return Unit.INSTANCE;
        });


    }

    private void populatePropertyListData(ArrayList<GetPropertyLeads.LeadsList> leadsList) {

        if (page == 0) {
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            bi.recyclerViewAllLeads.setLayoutManager(linearLayoutManager);
            bi.recyclerViewAllLeads.setItemAnimator(new DefaultItemAnimator());
            bi.recyclerViewAllLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewAllLeads.getContext(), 1));
            recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_all_leads_layout);
            recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvPhone, R.id.tvEmail, R.id.tvInitial);

            recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetPropertyLeads.LeadsList, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

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
            isLoading = false;
        } else {
            isLoading = false;
            recyclerAdapterUtil.notifyDataSetChanged();
        }

        recyclerAdapterUtil.addOnClickListener((Function2<GetPropertyLeads.LeadsList, Integer, Unit>) (viewComplainList, integer) -> {

            Map<String, String> map = new HashMap<>();
            map.put("leadID", viewComplainList.id);
            switchActivityWithIntentString(LeadDetailActivity.class, (HashMap<String, String>) map);

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

        leadsList.addAll(response.data.leadsList);

        if (leadsList.size() == 0) {
            bi.lnAllLeads.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        } else {
            bi.lnAllLeads.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
            populateListData(leadsList);
            modelGetAllLeads = response;
        }
    }

    @Override
    public void updateUI(GetPropertyLeads response) {

        propertyleadsList.clear();
        propertyleadsList.addAll(response.data.leadsList);

        if (propertyleadsList.size() == 0) {
            bi.lnAllLeads.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        } else {
            bi.lnAllLeads.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
            populatePropertyListData(propertyleadsList);
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {
    }

    @Override
    public void updateUIonFalse(String message) {
        //ToastUtils.showToastLong(context, message);
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

        GH.getInstance().ShowProgressDialog(AllLeadsActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void onClick(View v) {

    }
}