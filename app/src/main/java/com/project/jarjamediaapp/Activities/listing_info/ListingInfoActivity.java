package com.project.jarjamediaapp.Activities.listing_info;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.followups.FollowupsActivity;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailContract;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailPresenter;
import com.project.jarjamediaapp.Activities.transactions.TransactionActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadDetails;
import com.project.jarjamediaapp.Models.GetListingInfo;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityLeadDetailBinding;
import com.project.jarjamediaapp.databinding.ActivityListingInfoBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class ListingInfoActivity extends BaseActivity implements ListingInfoContract.View {

    ActivityListingInfoBinding bi;
    Context context = ListingInfoActivity.this;
    ListingInfoPresenter presenter;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getString(R.string.transaction1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_listing_info);
        presenter = new ListingInfoPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.listing_info), true);
    }

    private void populateListData() {

        List<GetListingInfo> leadsList = new ArrayList<>();

        leadsList.add(new GetListingInfo("Price Range :","0 to 0"));
        leadsList.add(new GetListingInfo("Property Type :","N/A"));
        leadsList.add(new GetListingInfo("Location :","N/A"));
        leadsList.add(new GetListingInfo("Bedrooms :","N/A"));
        leadsList.add(new GetListingInfo("Bedrooms :","N/A"));
        leadsList.add(new GetListingInfo("Baths :","N/A"));
        leadsList.add(new GetListingInfo("Year Built :","N/A"));
        leadsList.add(new GetListingInfo("Square Feet :","N/A"));
        leadsList.add(new GetListingInfo("Time Frame :","N/A"));

        bi.recyclerViewListingInfo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewListingInfo.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewListingInfo.addItemDecoration(new DividerItemDecoration(bi.recyclerViewListingInfo.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_listing_info_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName,R.id.tvDesc);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetListingInfo, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            TextView tvDesc = (TextView) integerMap.get(R.id.tvDesc);
            tvDesc.setText(allLeadsList.getDescription());

            return Unit.INSTANCE;
        });

        bi.recyclerViewListingInfo.setAdapter(recyclerAdapterUtil);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btnTransaction1:

                title = getString(R.string.transaction1);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnTransaction2:

                title = getString(R.string.transaction2);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonTranparentLeft);

                break;

        }

    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        populateListData();
        bi.btnTransaction1.setOnClickListener(this);
        bi.btnTransaction2.setOnClickListener(this);
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