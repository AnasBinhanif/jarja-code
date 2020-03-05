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

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTransactionsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class TransactionActivity extends BaseActivity implements View.OnClickListener, TransactionContract.View {

    ActivityTransactionsBinding bi;
    Context context = TransactionActivity.this;
    TransactionPresenter presenter;

    ArrayList<GetLeadTransactionStage.PipeLine> transactionPipeline = new ArrayList<>();
    String currentPipeline = "", markedPipeline = "";
    boolean bf = true;
    String leadID = "", title = "";
    RecyclerAdapterUtil recyclerAdapterUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_transactions);
        presenter = new TransactionPresenter(this);
        handleIntent(getIntent());
        presenter.initScreen();
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

        bi.recyclerViewTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewTransaction.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewTransaction.addItemDecoration(new DividerItemDecoration(bi.recyclerViewTransaction.getContext(), 1));
        recyclerAdapterUtil = new RecyclerAdapterUtil(context, transactionPipeline, R.layout.custom_transaction_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvDate, R.id.imgInitial, R.id.tvInitial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadTransactionStage.PipeLine, Integer, Map<Integer, ? extends View>, Unit>)
                (view, allLeadsList, integer, integerMap) -> {

                    TextView tvName = (TextView) integerMap.get(R.id.tvName);
                    tvName.setText(allLeadsList.pipeline);

                    TextView tvDate = (TextView) integerMap.get(R.id.tvDate);

                    TextView tvInitial = (TextView) integerMap.get(R.id.tvInitial);
                    int initails = integer + 1;
                    tvInitial.setText(String.valueOf(initails));

                    ImageView imgInitial = (ImageView) integerMap.get(R.id.imgInitial);

                    if (bf) {
                        tvName.setTextColor(getResources().getColor(R.color.colorMateGreen));
                        imgInitial.setVisibility(View.VISIBLE);
                        tvInitial.setVisibility(View.GONE);
                    }else{
                        tvName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        imgInitial.setVisibility(View.GONE);
                        tvInitial.setVisibility(View.VISIBLE);
                    }

                    if (allLeadsList.pipeline.contains(currentPipeline)) {
                        tvName.setTextColor(getResources().getColor(R.color.colorMateGreen));
                        imgInitial.setVisibility(View.VISIBLE);
                        tvInitial.setVisibility(View.GONE);
                        bf = false;
                    }

                    return Unit.INSTANCE;
                });

        bi.recyclerViewTransaction.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadTransactionStage.PipeLine, Integer, Unit>)
                (viewComplainList, integer) -> {

                    if (title.contains("Transaction 1")) {
                        String pipelineID = String.valueOf(viewComplainList.id);
                        String presentationID = "1";
                        markedPipeline = viewComplainList.pipeline;
                        presenter.addPipelineMark(pipelineID, leadID, presentationID);
                    } else if (title.contains("Transaction 2")) {
                        String pipelineID = String.valueOf(viewComplainList.id);
                        String presentationID = "2";
                        markedPipeline = viewComplainList.pipeline;
                        presenter.addPipelineMark(pipelineID, leadID, presentationID);
                    }

                    return Unit.INSTANCE;
                });
    }

    private void handleIntent(Intent intent) {
        title = intent.getStringExtra("title");
        leadID = intent.getStringExtra("leadID");
        setToolBarTitle(bi.epToolbar.toolbar, title, true);
        currentPipeline = intent.getStringExtra("currentStage");
        transactionPipeline = (ArrayList<GetLeadTransactionStage.PipeLine>) intent.getExtras().getSerializable("Pipeline");
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equals("Success")) {
            ToastUtils.showToast(context, "Assigned Successfully");
            bf = true;
            currentPipeline = markedPipeline;
            recyclerAdapterUtil.notifyDataSetChanged();
            populateListData();
        }
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