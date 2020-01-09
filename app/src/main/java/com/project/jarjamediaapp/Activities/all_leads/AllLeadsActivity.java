package com.project.jarjamediaapp.Activities.all_leads;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class AllLeadsActivity extends BaseActivity implements View.OnClickListener, AllLeadsContract.View {

    ActivityAllleadsBinding bi;
    Context context = AllLeadsActivity.this;
    AllLeadsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_allleads);
        presenter = new AllLeadsPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.leads), true);
        handleIntent(getIntent());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);


    }

    @Override
    public void initViews() {
        populateListData();
    }

    private void populateListData() {


        List<GetAllLeads> leadsList = new ArrayList<>();

        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));

        bi.recyclerViewAllLeads.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewAllLeads.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewAllLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewAllLeads.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_all_leads_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvPhone, R.id.tvEmail, R.id.tvInitial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetAllLeads, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            TextView tvPhone = (TextView) integerMap.get(R.id.tvPhone);
            tvPhone.setText(allLeadsList.getPhone());

            TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
            tvEmail.setText(allLeadsList.getEmail());

            TextView tvInitial = (TextView) integerMap.get(R.id.tvInitial);
            tvInitial.setText(allLeadsList.getName().substring(0, 1));

            return Unit.INSTANCE;
        });

        bi.recyclerViewAllLeads.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetAllLeads, Integer, Unit>) (viewComplainList, integer) -> {

            switchActivity(LeadDetailActivity.class);

            return Unit.INSTANCE;
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        populateListData();
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
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

        ToastUtils.showToastLong(context, error);
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
