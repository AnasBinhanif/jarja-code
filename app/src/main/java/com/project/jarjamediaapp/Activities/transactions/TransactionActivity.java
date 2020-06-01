package com.project.jarjamediaapp.Activities.transactions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTransactionsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<GetLeadTransactionStage.PipeLine> transactionPipeline = new ArrayList<>();
    ArrayList<GetLeadTransactionStage.LeadTransactionOne> transactionOneListModel = new ArrayList<>();
    ArrayList<GetLeadTransactionStage.LeadTransactionTwo> transactionTwoListModel = new ArrayList<>();
    String currentPipeline = "", markedPipeline = "";
    boolean bf = true;
    String leadID = "", title = "", pipelineID = "", presentationID = "", leadDetailId = "", agentStringId = "";
    RecyclerAdapterUtil recyclerAdapterUtil, raCommission;
    Dialog dialog;
    int transaction=1;
    Button btnSave, btnCancel;
    RecyclerView rvAgentCommission;
    List<TransactionModel.Data> dataList;
    int count = 0;

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
                        if (transaction==1){
                            if (transactionOneListModel.size()>count)
                            {
                                String date = GH.getInstance().formatter(transactionOneListModel.get(count).date,"MM/dd/yyyy","MMM d yyyy h:mma");
                                tvDate.setText(date);
                            }
                        }else{
                            if (transactionTwoListModel.size()>count)
                            {
                                String date = GH.getInstance().formatter(transactionTwoListModel.get(count).date,"MM/dd/yyyy","MMM d yyyy h:mma");
                                tvDate.setText(date);
                            }
                        }
                        count++;
                        tvName.setTextColor(getResources().getColor(R.color.colorMateGreen));
                        imgInitial.setVisibility(View.VISIBLE);
                        tvDate.setVisibility(View.VISIBLE);
                        tvInitial.setVisibility(View.GONE);
                    } else {
                        tvName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        imgInitial.setVisibility(View.GONE);
                        tvInitial.setVisibility(View.VISIBLE);
                    }

                    if (allLeadsList.pipeline != null && currentPipeline != null) {
                        if (allLeadsList.pipeline.contains(currentPipeline)) {
                            tvName.setTextColor(getResources().getColor(R.color.colorMateGreen));
                            imgInitial.setVisibility(View.VISIBLE);
                            tvDate.setVisibility(View.VISIBLE);
                            tvInitial.setVisibility(View.GONE);
                            bf = false;
                        }
                    } else {
                        bf = false;
                    }

                    return Unit.INSTANCE;
                });

        bi.recyclerViewTransaction.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadTransactionStage.PipeLine, Integer, Unit>)
                (viewComplainList, position) -> {

                    String jsonObjectString = "";
                    pipelineID = String.valueOf(viewComplainList.id);
                    markedPipeline = viewComplainList.pipeline;

                    if (title.contains("Transaction 1")) {
                        presentationID = "1";
                    } else if (title.contains("Transaction 2")) {
                        presentationID = "2";
                    }
                    int c = count - 1;
                    if (position + 1  > c) {
                        jsonObjectString = "{\"presentationID\": \"" + presentationID + "\"," +
                                " \"encrypted_LeadDetailID\": \"" + leadDetailId +
                                "\", \"pipelineID\":\"" + pipelineID + "\"}";
                        Log.d("json", jsonObjectString);

                        presenter.addPipelineMark(jsonObjectString);
                    } else {
                        showLongToastMessage("Sorry, you cannot revert pipeline.");
                    }


                    return Unit.INSTANCE;
                });

    }

    private void handleIntent(Intent intent) {

        title = intent.getStringExtra("title");
        leadID = intent.getStringExtra("leadID");
        leadDetailId = intent.getStringExtra("leadDetailId");
        setToolBarTitle(bi.epToolbar.toolbar, title, true);
        currentPipeline = intent.getStringExtra("currentStage");
        transactionPipeline = (ArrayList<GetLeadTransactionStage.PipeLine>) intent.getExtras().getSerializable("Pipeline");

        Bundle args = intent.getBundleExtra("BUNDLE");
         transaction = intent.getIntExtra("tansaction",1);
        if (transaction==1) {
            transactionOneListModel = (ArrayList<GetLeadTransactionStage.LeadTransactionOne>) args.getSerializable("ARRAYLIST");
        }else{
            transactionTwoListModel = (ArrayList<GetLeadTransactionStage.LeadTransactionTwo>) args.getSerializable("ARRAYLIST");
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equals("Success")) {
            //ToastUtils.showToast(context, "Assigned Successfully");
            presenter.getTransaction(leadID);

        }

    }

    @Override
    public void addAgentCommission(Response<BaseResponse> response) {

        ToastUtils.showToast(context, response.body().getMessage());
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            presenter.getAgentCommission(leadID, leadDetailId);
        }
    }

    @Override
    public void getAgentCommission(Response<TransactionModel> response) {

        dataList = new ArrayList<>();
        dataList.addAll(response.body().getData());
        if (response.body().getData().size() > 0) {
            rvAgentCommission.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvAgentCommission.setItemAnimator(new DefaultItemAnimator());
            rvAgentCommission.addItemDecoration(new DividerItemDecoration(rvAgentCommission.getContext(), 1));
            raCommission = new RecyclerAdapterUtil(context, dataList, R.layout.custom_adp_agent_commission);
            raCommission.addViewsList(R.id.tvAgentName, R.id.atvAgentCommission);

            raCommission.addOnDataBindListener((Function4<View, TransactionModel.Data, Integer, Map<Integer, ? extends View>, Unit>)
                    (view, data, position, integerMap) -> {

                        TextView tvAgentName = (TextView) integerMap.get(R.id.tvAgentName);
                        tvAgentName.setText(data.getAgentName() != null ? data.getAgentName() : "");

                        AutoCompleteTextView atvAgentCommission = (AutoCompleteTextView) integerMap.get(R.id.atvAgentCommission);
                        atvAgentCommission.setText(data.getCommission() + "");

                        atvAgentCommission.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (s.length() > 0) {
                                    data.setCommission(Integer.valueOf(atvAgentCommission.getText().toString()));
                                    dataList.set(position, data);
                                }
                            }
                        });

                        return Unit.INSTANCE;
                    });

            rvAgentCommission.setAdapter(raCommission);
            dialog.show();

        } else {
            ToastUtils.showToast(context, "No agents found");
        }

    }

    @Override
    public void updateUI(GetLeadTransactionStage response) {


        transactionOneListModel = new ArrayList<>();
        transactionTwoListModel = new ArrayList<>();

        transactionOneListModel = response.data.leadTransactionOne;
        transactionTwoListModel = response.data.leadTransactionTwo;

        count=0;
        bf = true;
        currentPipeline = markedPipeline;
        recyclerAdapterUtil.notifyDataSetChanged();
        populateListData();
        if (pipelineID.equalsIgnoreCase("11")) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    showAgentCommissionDialog();
                }
            } else {
                showAgentCommissionDialog();
            }
        }

    }

    private void showAgentCommissionDialog() {

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_agent_commission);

        rvAgentCommission = dialog.findViewById(R.id.rvAgentCommission);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject obj, obj1 = null;
                JSONArray array = new JSONArray();
                try {
                    obj1 = new JSONObject();
                    obj1.put("encrypted_LeadID", leadID);
                    obj1.put("encryptedLeadDetailID", leadDetailId);
                    for (int i = 0; i < dataList.size(); i++) {
                        obj = new JSONObject();
                        obj.put("commission", dataList.get(i).getCommission());
                        obj.put("agentID", dataList.get(i).getAgentID());
                        obj.put("agentName", dataList.get(i).getAgentName() != null ? dataList.get(i).getAgentName() : "");
                        array.put(obj);
                    }
                    obj1.put("agentList", array);
                    agentStringId = obj1.toString();
                    Log.d("json", agentStringId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                presenter.addAgentCommission(agentStringId);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        presenter.getAgentCommission(leadID, leadDetailId);

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

        GH.getInstance().ShowProgressDialog(TransactionActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}