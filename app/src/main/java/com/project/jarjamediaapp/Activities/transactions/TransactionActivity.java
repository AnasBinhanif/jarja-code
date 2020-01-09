package com.project.jarjamediaapp.Activities.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetTransaction;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTransactionsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class TransactionActivity extends BaseActivity implements View.OnClickListener, TransactionContract.View {

    ActivityTransactionsBinding bi;
    Context context = TransactionActivity.this;
    TransactionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_transactions);
        presenter = new TransactionPresenter(this);
        presenter.initScreen();
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

        List<GetTransaction> leadsList = new ArrayList<>();

        leadsList.add(new GetTransaction("1", "Protential Prospect", "12/31/2020", true));
        leadsList.add(new GetTransaction("2", "Attempted Contact", "12/31/2020", false));
        leadsList.add(new GetTransaction("3", "Made Contact", "12/31/2020", false));
        leadsList.add(new GetTransaction("4", "Appointment Set", "12/31/2020", false));
        leadsList.add(new GetTransaction("5", "Appointment Kept", "12/31/2020", false));
        leadsList.add(new GetTransaction("6", "Appointment Not Kept", "12/31/2020", false));
        leadsList.add(new GetTransaction("7", "Undecided Lead", "12/31/2020", false));
        leadsList.add(new GetTransaction("8", "Lost", "12/31/2020", false));
        leadsList.add(new GetTransaction("9", "Active Client", "12/31/2020", false));
        leadsList.add(new GetTransaction("10", "Under Contract", "12/31/2020", false));

        bi.recyclerViewTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewTransaction.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewTransaction.addItemDecoration(new DividerItemDecoration(bi.recyclerViewTransaction.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_transaction_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvDate, R.id.imgInitial, R.id.tvInitial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetTransaction, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            TextView tvDate = (TextView) integerMap.get(R.id.tvDate);
            tvDate.setText(allLeadsList.getDate());

            TextView tvInitial = (TextView) integerMap.get(R.id.tvInitial);
            int initails = integer + 1;
            tvInitial.setText(String.valueOf(initails));

            ImageView imgInitial = (ImageView) integerMap.get(R.id.imgInitial);

            if (allLeadsList.getIsDone()) {
                tvName.setTextColor(getResources().getColor(R.color.colorMateGreen));
                tvDate.setVisibility(View.VISIBLE);
                imgInitial.setVisibility(View.VISIBLE);
                tvInitial.setVisibility(View.GONE);
            }

            return Unit.INSTANCE;
        });

        bi.recyclerViewTransaction.setAdapter(recyclerAdapterUtil);


    }

    private void handleIntent(Intent intent) {

        String title = intent.getStringExtra("title");
        setToolBarTitle(bi.epToolbar.toolbar, title, true);
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
