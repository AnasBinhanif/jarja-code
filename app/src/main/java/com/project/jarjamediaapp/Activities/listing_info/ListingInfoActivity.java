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
    String buttonTitle = "", leadId = "", title = "";
    List<ListingInfoModel.Data.ListingInfo> leadsList;
    RecyclerAdapterUtil recyclerAdapterUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttonTitle = getString(R.string.transaction1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_listing_info);
        presenter = new ListingInfoPresenter(this);
        presenter.initScreen();
    }

    @Override
    public void initViews() {

        handleIntent(getIntent());
        bi.btnTransaction1.setOnClickListener(this);
        bi.btnTransaction2.setOnClickListener(this);
        populateListData();
    }

    private void populateListData() {

        leadsList = new ArrayList<ListingInfoModel.Data.ListingInfo>();

        bi.recyclerViewListingInfo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewListingInfo.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewListingInfo.addItemDecoration(new DividerItemDecoration(bi.recyclerViewListingInfo.getContext(), 1));
        recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_listing_info_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvDesc);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, ListingInfoModel.Data.ListingInfo, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, position, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            TextView tvDesc = (TextView) integerMap.get(R.id.tvDesc);


            tvName.setText(allLeadsList.getKey());
            tvDesc.setText(allLeadsList.getValue());

/*  if (allLeadsList.getKey().equals("Listing Price:")){

                String[] separated = allLeadsList.getValue().split("to");
                tvName.setText(allLeadsList.getKey());
                tvDesc.setText(separated[0]+" to "+separated[1]);

            }else {

                tvName.setText(allLeadsList.getKey());
                tvDesc.setText(allLeadsList.getValue());
            }
*/
            // hide uploaded attachments field


            return Unit.INSTANCE;
        });

        bi.recyclerViewListingInfo.setAdapter(recyclerAdapterUtil);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnTransaction1:

                bi.recyclerViewListingInfo.setVisibility(View.GONE);
                buttonTitle = getString(R.string.transaction1);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);
                if (title.equalsIgnoreCase(getString(R.string.buying_info))) {
                    presenter.getLeadBuyingInfoDetails(leadId);
                } else {
                    presenter.getLeadListingInfoDetails(leadId);
                }

                break;

            case R.id.btnTransaction2:

                bi.recyclerViewListingInfo.setVisibility(View.GONE);
                buttonTitle = getString(R.string.transaction2);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonTranparentLeft);
                if (title.equalsIgnoreCase(getString(R.string.buying_info))) {
                    presenter.getLeadBuyingInfoDetails(leadId);
                } else {
                    presenter.getLeadListingInfoDetails(leadId);
                }

                break;

        }

    }

    private void handleIntent(Intent intent) {

        title = intent.getStringExtra("title");
        leadId = intent.getStringExtra("leadID");
        setToolBarTitle(bi.epToolbar.toolbar, title, true);
        if (title.equalsIgnoreCase(getString(R.string.buying_info))) {
            presenter.getLeadBuyingInfoDetails(leadId);
        } else {
            presenter.getLeadListingInfoDetails(leadId);
        }
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }



    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        bi.recyclerViewListingInfo.setVisibility(View.GONE);
        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(String error) {

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
            bi.recyclerViewListingInfo.setVisibility(View.GONE);
            ToastUtils.showToastLong(context, error);

    }

    @Override
    public void updateUIonFailure() {

        bi.recyclerViewListingInfo.setVisibility(View.GONE);
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));

    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(ListingInfoActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void updateUIList(ListingInfoModel.Data response) {


        if (title.equalsIgnoreCase(getString(R.string.buying_info))) {

            if (buttonTitle.equalsIgnoreCase(getString(R.string.transaction1))) {

                if (response.getBuyingInfoTransactionOne() != null && response.getBuyingInfoTransactionOne().size() > 0) {
                    leadsList.addAll(response.getBuyingInfoTransactionOne());
                    bi.recyclerViewListingInfo.setVisibility(View.VISIBLE);
                } else {
                    bi.recyclerViewListingInfo.setVisibility(View.GONE);
                }

            } else {

                if (response.getBuyingInfoTransactionTwo() != null && response.getBuyingInfoTransactionTwo().size() > 0) {
                    leadsList.addAll(response.getBuyingInfoTransactionTwo());
                    bi.recyclerViewListingInfo.setVisibility(View.VISIBLE);
                } else {
                    bi.recyclerViewListingInfo.setVisibility(View.GONE);
                }

            }

        } else {

            if (buttonTitle.equalsIgnoreCase(getString(R.string.transaction1))) {

                if (response.getListingInfoTransactionOne() != null && response.getListingInfoTransactionOne().size() > 0) {
                    leadsList.addAll(response.getListingInfoTransactionOne());
                    bi.recyclerViewListingInfo.setVisibility(View.VISIBLE);
                } else {
                    bi.recyclerViewListingInfo.setVisibility(View.GONE);
                }

            } else {

                if (response.getListingInfoTransactionTwo() != null && response.getListingInfoTransactionTwo().size() > 0) {
                    leadsList.addAll(response.getListingInfoTransactionTwo());
                    bi.recyclerViewListingInfo.setVisibility(View.VISIBLE);
                } else {
                    bi.recyclerViewListingInfo.setVisibility(View.GONE);
                }
            }

        }
        bi.recyclerViewListingInfo.setAdapter(recyclerAdapterUtil);


    }

}