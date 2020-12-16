package com.project.jarjamediaapp.Activities.all_leads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAllleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllLeadsActivity extends BaseActivity implements View.OnClickListener, AllLeadsContract.View {

    ActivityAllleadsBinding bi;
    Context context = AllLeadsActivity.this;
    AllLeadsPresenter presenter;
    ArrayList<GetAllLeads.LeadsList> _leadsList;
    ArrayList<GetPropertyLeads.LeadsList> propertyleadsList = new ArrayList<>();
    String mSearchQuery = "", propertyID = "";
    String resultSetType = "New Leads";
    Intent data = null;
    LinearLayoutManager linearLayoutManager;
    int page = 0;
    int totalPages, type = 0;
    RecyclerAdapterUtil recyclerAdapterUtil;
    boolean isLoading = false, isFilter = false;
    GetAllLeads modelGetAllLeads;
    boolean registeredDateAsc;
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    Runnable input_finish_checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_allleads);
        presenter = new AllLeadsPresenter(this);
        presenter.initScreen();
        _leadsList = new ArrayList<>();

        // stop redirection to dashboard screen when click back button
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(), "leadsFragment").save();

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
                setToolBarTitle(bi.epToolbar.toolbar, resultSetType, true);
                break;
            case 1:
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.leads), true);
                propertyID = getIntent().getStringExtra("propertyID");
                callPropertyLeadList();
                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 0;
        if (bi.edtSearch.getText().toString().equals("")) {
            handleIntent();
        } else {
            _leadsList = new ArrayList<>();
            page = 0;
            isFilter = true;
            searchLead(page, bi.edtSearch.getText().toString());
        }
    }

    @Override
    public void initViews() {

        _leadsList = new ArrayList<>();
        initPagination();

        bi.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {

                int length = bi.edtSearch.getText().length();
                try {
                    if (length > 0) {
                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    } else {
                        data = getIntent().getParcelableExtra("bundle");
                        totalPages = getIntent().getIntExtra("totalPages", 0);
                        _leadsList.clear();
                        isFilter = false;
                        callGetAllLeads(data, String.valueOf(page));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        input_finish_checker = () -> {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                page = 0;
                isFilter = true;
                searchLead(page, bi.edtSearch.getText().toString());
            }
        };


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
                            if (modelGetAllLeads != null) {
                                totalPages = modelGetAllLeads.data.noOfPages;
                            }
                        }

                        if (totalPages > _leadsList.size()) {
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
        String pageSize = "25";

        JSONObject obj = new JSONObject();
        try {
            obj.put("spouseName", spouseName);
            obj.put("address", address);
            obj.put("houseToSell", houseToSell);
            obj.put("conversationCellAsc", conversationCellAsc);
            obj.put("leadID", leadID);
            obj.put("cityAsc", city);
            obj.put("state", state);
            obj.put("company", company);
            obj.put("zip", zip);
            obj.put("propertyType", propertyType);
            obj.put("leadScoreMin", leadScoreMin);
            obj.put("lastLogin", lastLogin);
            obj.put("lastLoginedCountAsc", lastLoginedCountAsc);
            obj.put("conversationMsgAsc", conversationMsgAsc);
            obj.put("activitiesViewAsc", activitiesViewAsc);
            obj.put("dripCompaignID", dripCompaignID);
            obj.put("activitiesSavedSearchAsc", activitiesSavedSearchAsc);
            obj.put("phone", phone);
            obj.put("county", county);
            obj.put("lastLoginedInAsc", lastLoginedInAsc);
            obj.put("lastTouch", lastTouch);
            obj.put("timeFrameID", timeFrameID);
            obj.put("notes", notes);
            obj.put("lastNameAsc", lastNameAsc);
            obj.put("preApproval", preApproval);
            obj.put("leadTypeID", leadTypeID);
            obj.put("agentID", agentID);
            obj.put("tagsID", tagsID);
            obj.put("firstNameAsc", firstNameAsc);
            obj.put("searchBy", searchBy);
            obj.put("cityAsc", cityAsc);
            obj.put("fromDate", fromDate);
            obj.put("toDate", toDate);
            obj.put("isSaveSearch", isSaveSearch);
            obj.put("priceAsc", priceAsc);
            obj.put("emailAddressAsc", emailAddressAsc);
            obj.put("leadScoreMax", leadScoreMax);
            obj.put("resultSetType", resultSetType);
            obj.put("city", city);
            obj.put("activitiesFavoriteAsc", activitiesFavoriteAsc);
            obj.put("email", email);
            obj.put("sourceID", sourceID);
            if (resultSetType != null && resultSetType.equalsIgnoreCase("New Leads")) {
                obj.put("registeredDateAsc", false);
            } else {
                obj.put("registeredDateAsc", null);
            }
            obj.put("isFilterClear", isFilterClear);
            obj.put("pageSize", pageSize);
            obj.put("countryID", countryID);
            obj.put("priceMin", priceMin);
            obj.put("priceMax", priceMax);
            obj.put("timeFrameAsc", timeFrameAsc);
            obj.put("lastTouchedInAsc", lastTouchedInAsc);
            obj.put("pipelineID", pipelineID);
            obj.put("conversationEmailAsc", conversationEmailAsc);
            obj.put("pageNo", pageNo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonObjectString = obj.toString();
        Log.d("json", jsonObjectString);


        presenter.getAllLeads(jsonObjectString);

    }

    private void callPropertyLeadList() {
        presenter.GetPropertyLeads(propertyID);
    }

    private void populateListData(ArrayList<GetAllLeads.LeadsList> leadsList) {

        try {
        //    GH.getInstance().hideKeyboard(context, AllLeadsActivity.this);
            if (leadsList != null && leadsList.size() != 0) {
                //  if (page == 0) {
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

                    tvInitial.setText(allLeadsList.firstName.substring(0, 1) + allLeadsList.lastName.substring(0, 1) + "");

                    return Unit.INSTANCE;
                });

                bi.recyclerViewAllLeads.setAdapter(recyclerAdapterUtil);
                recyclerAdapterUtil.notifyDataSetChanged();
                isLoading = false;

                if (page > 0) {
                    bi.recyclerViewAllLeads.scrollToPosition(recyclerAdapterUtil.getItemCount() - 25);
                }

                bi.recyclerViewAllLeads.setVisibility(View.VISIBLE);

                recyclerAdapterUtil.addOnClickListener((Function2<GetAllLeads.LeadsList, Integer, Unit>) (viewComplainList, integer) -> {

                    try {
                        Map<String, String> map = new HashMap<>();
                        map.put("leadID", viewComplainList.id);
                        switchActivityWithIntentString(LeadDetailActivity.class, (HashMap<String, String>) map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return Unit.INSTANCE;
                });

            } else {
                bi.recyclerViewAllLeads.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void populatePropertyListData(ArrayList<GetPropertyLeads.LeadsList> leadsList) {

        if (leadsList != null && leadsList.size() != 0) {
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
        }

        recyclerAdapterUtil.addOnClickListener((Function2<GetPropertyLeads.LeadsList, Integer, Unit>) (viewComplainList, integer) -> {

            Map<String, String> map = new HashMap<>();
            map.put("leadID", viewComplainList.id);
            switchActivityWithIntentString(LeadDetailActivity.class, (HashMap<String, String>) map);

            return Unit.INSTANCE;
        });


    }

    public void searchLead(int pageNo, String query) {


        //    _view.showProgressBar();
        showProgressBar();
        Call<GetAllLeads> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).SearchLead(GH.getInstance().getAuthorization(),
                pageNo, query);
        _call.enqueue(new Callback<GetAllLeads>() {
            @Override
            public void onResponse(Call<GetAllLeads> call, Response<GetAllLeads> response) {

                //  _view.hideProgressBar();
                hideProgressBar();
                if (response.isSuccessful()) {

                    GetAllLeads getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        if (getAppointmentsModel.status.equals("Success")) {

                            _leadsList.clear();
                            _leadsList = new ArrayList<>();
                            _leadsList.addAll(getAppointmentsModel.data.leadsList);
                            if (_leadsList.size() != 0) {

                                bi.recyclerViewAllLeads.setVisibility(View.VISIBLE);
                                populateListData(_leadsList);

                            } else {
                                bi.recyclerViewAllLeads.setVisibility(View.GONE);
                                ToastUtils.showToast(context, "No Result Found");
                            }
                        } else {
                            ToastUtils.showToast(context, getAppointmentsModel.message);
                        }
                        //  _view.updateUI(getAppointmentsModel);

                    } else {

                        // _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    // _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAllLeads> call, Throwable t) {
                //  _view.hideProgressBar();
                hideProgressBar();
                //  _view.updateUIonFailure();

            }
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

        if (recyclerAdapterUtil != null) {
            recyclerAdapterUtil.notifyDataSetChanged();

        }
        _leadsList.addAll(response.data.leadsList);
        if (_leadsList.size() == 0) {
            bi.lnAllLeads.setVisibility(View.GONE);
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        } else {
            bi.lnAllLeads.setVisibility(View.VISIBLE);
            bi.tvNoRecordFound.setVisibility(View.GONE);
            populateListData(_leadsList);
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