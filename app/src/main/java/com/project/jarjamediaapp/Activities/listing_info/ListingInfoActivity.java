package com.project.jarjamediaapp.Activities.listing_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetListingInfo;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityListingInfoBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
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
        handleIntent(getIntent());
    }

    private void populateListData() {

        List<GetListingInfo> leadsList = new ArrayList<>();

        leadsList.add(new GetListingInfo("Price Range :", "0 to 0"));
        leadsList.add(new GetListingInfo("Property Type :", "N/A"));
        leadsList.add(new GetListingInfo("Location :", "N/A"));
        leadsList.add(new GetListingInfo("Bedrooms :", "N/A"));
        leadsList.add(new GetListingInfo("Baths :", "N/A"));
        leadsList.add(new GetListingInfo("Year Built :", "N/A"));
        leadsList.add(new GetListingInfo("Square Feet :", "N/A"));
        leadsList.add(new GetListingInfo("Time Frame :", "N/A"));
        leadsList.add(new GetListingInfo("FTHB :", "N/A"));
        leadsList.add(new GetListingInfo("House To Sell :", "N/A"));
        leadsList.add(new GetListingInfo("With Agent :", "N/A"));
        leadsList.add(new GetListingInfo("Acres :", "N/A"));
        leadsList.add(new GetListingInfo("Garage :", "N/A"));
        leadsList.add(new GetListingInfo("Referred By :", "N/A"));
        leadsList.add(new GetListingInfo("Referred To :", "N/A"));
        leadsList.add(new GetListingInfo("Referral Amt :", "N/A"));
        leadsList.add(new GetListingInfo("MLS :", "N/A"));
        leadsList.add(new GetListingInfo("Title :", "N/A"));
        leadsList.add(new GetListingInfo("Escrow :", "N/A"));
        leadsList.add(new GetListingInfo("Viewed Properties :", "N/A"));
        leadsList.add(new GetListingInfo("Inquiries :", "N/A"));
        leadsList.add(new GetListingInfo("Favorited Properties :", "N/A"));
        leadsList.add(new GetListingInfo("Uploaded Attachments :", "N/A"));

        bi.recyclerViewListingInfo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewListingInfo.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewListingInfo.addItemDecoration(new DividerItemDecoration(bi.recyclerViewListingInfo.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_listing_info_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvDesc);

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


        switch (v.getId()) {

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

    private void handleIntent(Intent intent) {

        String title = intent.getStringExtra("title");
        setToolBarTitle(bi.epToolbar.toolbar, title, true);
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