package com.project.jarjamediaapp.Activities.transactions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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

import com.google.android.gms.common.internal.service.Common;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.DecimalDigitsInputFilter;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTransactionsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;
import com.tsongkha.spinnerdatepicker.CommonDateUtils;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class TransactionActivity extends BaseActivity implements View.OnClickListener, TransactionContract.View, DatePickerDialog.OnDateSetListener {

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
    int transaction = 1;
    Button btnSave, btnCancel;
    RecyclerView rvAgentCommission;
    List<TransactionModel.Data> dataList;
    int count = 0;
    Calendar newCalendar;
    int month, year, day, _month, _year, _day, mHour, mMinute;
    TextView tvCommisionDate, tvCloseDate;
    String commisionDate, closeDate;
    int type;

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

        count = 0;
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
                        if (transaction == 1) {
                            if (transactionOneListModel.size() != 0) {
                                if (transactionOneListModel.size() > count) {
                                    String date = GH.getInstance().formatter(transactionOneListModel.get(count).date, "MM/dd/yyyy", "MMM d yyyy h:mma");
                                    tvDate.setText(date);
                                }
                            }
                        } else {
                            if (transactionTwoListModel.size() != 0) {
                                if (transactionTwoListModel.size() > count) {
                                    String rpDate = transactionTwoListModel.get(count).date != null ? transactionTwoListModel.get(count).date : "";
                                    String date = !rpDate.equals("") ? GH.getInstance().formatter(rpDate, "MM/dd/yyyy", "MMM d yyyy h:mma") : "";
                                    tvDate.setText(date);
                                }
                            }
                        }
                        count = count + 1;
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
                            if (transaction == 1) {
                                String rpDate = transactionOneListModel != null ? transactionOneListModel.get(integer).date : "";
                                String date = !rpDate.equals("") ? GH.getInstance().formatter(rpDate, "MM/dd/yyyy", "MMM d yyyy h:mma") : "";
                                tvDate.setText(date);
                            } else {
                                String rpDate = transactionTwoListModel != null ? transactionTwoListModel.get(integer).date : "";
                                String date = !rpDate.equals("") ? GH.getInstance().formatter(rpDate, "MM/dd/yyyy", "MMM d yyyy h:mma") : "";
                                tvDate.setText(date);

                            }

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
                (allLeadsList, integer) -> {

                    String jsonObjectString = "";
                    pipelineID = String.valueOf(allLeadsList.id);
                    markedPipeline = allLeadsList.pipeline;

                    if (title.contains("Transaction 1")) {
                        presentationID = "1";
                    } else if (title.contains("Transaction 2")) {
                        presentationID = "2";
                    }
                    int c = count - 1;

                    if (integer + 1 > c) {
                        jsonObjectString = "{\"presentationID\": \"" + presentationID + "\"," +
                                " \"encrypted_LeadDetailID\": \"" + leadDetailId +
                                "\", \"pipelineID\":\"" + pipelineID + "\"}";
                        Log.d("json", jsonObjectString);

                        presenter.addPipelineMark(jsonObjectString);

                    } else {
                        // enable revert pipeline
                        jsonObjectString = "{\"presentationID\": \"" + presentationID + "\"," +
                                " \"encrypted_LeadDetailID\": \"" + leadDetailId +
                                "\", \"pipelineID\":\"" + pipelineID + "\"}";
                        Log.d("json", jsonObjectString);
                        presenter.addPipelineMark(jsonObjectString);
                        //  showLongToastMessage("Sorry, you cannot revert pipeline.");
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
        transaction = intent.getIntExtra("tansaction", 1);
        if (transaction == 1) {
            transactionOneListModel = (ArrayList<GetLeadTransactionStage.LeadTransactionOne>) args.getSerializable("ARRAYLIST");
        } else {
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
                presenter.getTransaction(leadID);
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

                        //creating input filter to limit the lenght and decimal point upto 2
                        InputFilter[] filterArray = new InputFilter[]{new DecimalDigitsInputFilter(10, 2),new InputFilter.LengthFilter(10)};

                        atvAgentCommission.setFilters(filterArray);

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
                                    data.setCommission(Double.valueOf(atvAgentCommission.getText().toString()));
                                    dataList.set(position, data);
                                }
                            }
                        });

                        return Unit.INSTANCE;
                    });

            rvAgentCommission.setAdapter(raCommission);
            dialog.setCancelable(false);
            dialog.show();

        } else {
            ToastUtils.showToast(context, "No agents found");
        }

    }

    @Override
    public void updateUI(GetLeadTransactionStage response) {

        if (pipelineID.equalsIgnoreCase("11")) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    hideProgressBar();
                } else {
                    showAgentCommissionDialog();
                }
            } else {
                showAgentCommissionDialog();
            }
        } else {
            hideProgressBar();
        }

        transactionOneListModel = new ArrayList<>();
        transactionTwoListModel = new ArrayList<>();

        transactionOneListModel = response.data.leadTransactionOne;
        transactionTwoListModel = response.data.leadTransactionTwo;

        count = 0;
        bf = true;
        currentPipeline = markedPipeline;
        recyclerAdapterUtil.notifyDataSetChanged();
        populateListData();
    }

    private String dateFormater(Date date, String outPutFormat) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(outPutFormat);

        return dateFormatter.format(date);

    }

    private void showAgentCommissionDialog() {

        //calling calendar instance
        calendarInstance();


        presenter.getAgentCommission(leadID, leadDetailId);

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_agent_commission);

        rvAgentCommission = dialog.findViewById(R.id.rvAgentCommission);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        tvCommisionDate = dialog.findViewById(R.id.atvCommissionDate);
        tvCommisionDate.setText(dateFormater(newCalendar.getTime(), "MMM dd,yyyy"));
        tvCloseDate = dialog.findViewById(R.id.atvCloseDate);
        tvCloseDate.setText(dateFormater(newCalendar.getTime(), "MMM dd,yyyy"));


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

        tvCommisionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                showSpinnerDateDialog();
            }
        });

        tvCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                showSpinnerDateDialog();
            }
        });

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

    private void calendarInstance() {

        newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);
    }

    private void showSpinnerDateDialog() {

        // Calendar cal = Calendar.getInstance();
        new SpinnerDatePickerDialogBuilder().context(TransactionActivity.this)
                .callback(TransactionActivity.this)
                // .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(year, month, day)
                .minDate(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
                .build()
                .show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        this.year = _year;
        month = monthOfYear;
        day = dayOfMonth;
        newCalendar.set(year, month, day);

        if (type == 1) {
            commisionDate = dateFormater(newCalendar.getTime(), "MM-dd-yyyy");
            tvCommisionDate.setText(commisionDate);

        } else if (type == 2) {
            closeDate = dateFormater(newCalendar.getTime(), "MM-dd-yyyy");
            tvCloseDate.setText(closeDate);
        }


    }
}