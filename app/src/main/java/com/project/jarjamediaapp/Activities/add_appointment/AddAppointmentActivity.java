package com.project.jarjamediaapp.Activities.add_appointment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddAppointmentBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAppointmentActivity extends BaseActivity implements AddAppointmentContract.View, View.OnClickListener {

    ActivityAddAppointmentBinding bi;
    Context context = AddAppointmentActivity.this;
    AddAppointmentPresenter presenter;
    RecyclerAdapterUtil recyclerAdapterUtil;
    RecyclerView recyclerSearch;
    ArrayList<String> reminderList = new ArrayList<>();
    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<GetLeadTitlesModel.Data> nameList = new ArrayList<>();

    String startDate, endDate, startTime, endTime, via, reminder, leadId, isAllDay = "false";
    MultiSelectModel agentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment);
        presenter = new AddAppointmentPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_appointment), true);

    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        initAllDay();
        presenter.getAgentNames();
        bi.btnSave.setOnClickListener(this);
        bi.edtName.setOnClickListener(this);
        bi.edtAgent.setOnClickListener(this);
        bi.edtStartDate.setOnClickListener(this);
        bi.edtEndDate.setOnClickListener(this);
        bi.edtStartTime.setOnClickListener(this);
        bi.edtEndTime.setOnClickListener(this);
        bi.spnReminder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.edtName:
                showSearchDialog(context);
                break;
            case R.id.edtAgent:
                showAgentDialog();
                break;
            case R.id.edtStartDate:
                showDateDialog(bi.edtStartDate, true);
                break;
            case R.id.edtEndDate:
                showDateDialog(bi.edtEndDate, false);
                break;
            case R.id.edtStartTime:
                showTimeDiaolog(bi.edtStartTime, true);
                break;
            case R.id.edtEndTime:
                showTimeDiaolog(bi.edtEndTime, false);
                break;
            case R.id.spnReminder:
                setSpinnerData();
                break;
            case R.id.btnSave:
                callAddAppointment();
                break;
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equalsIgnoreCase("Success")) {
            ToastUtils.showToast(context, "Added Successfully");
            finish();
        }
    }

    @Override
    public void updateUI(GetAgentsModel response) {
        agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName));
        }
    }

    private void initAllDay() {

        bi.cbAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    isAllDay = "true";
                    startTime = "";
                    endTime = "";
                    bi.edtEndTime.setFocusable(false);
                    bi.edtEndTime.setClickable(false);
                    bi.edtEndTime.setFocusableInTouchMode(false);
                    bi.edtStartTime.setFocusable(false);
                    bi.edtStartTime.setClickable(false);
                    bi.edtStartTime.setFocusableInTouchMode(false);
                    bi.edtEndTime.setTextColor(getResources().getColor(R.color.colorGrey_light));
                    bi.edtStartTime.setTextColor(getResources().getColor(R.color.colorGrey_light));
                } else {
                    isAllDay = "false";
                    bi.edtEndTime.setFocusable(true);
                    bi.edtEndTime.setClickable(true);
                    bi.edtEndTime.setFocusableInTouchMode(true);
                    bi.edtStartTime.setFocusable(true);
                    bi.edtStartTime.setClickable(true);
                    bi.edtStartTime.setFocusableInTouchMode(true);
                    bi.edtEndTime.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    bi.edtStartTime.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });

    }

    private void setSpinnerData() {
        bi.spnVia.setBackground(getDrawable(R.drawable.bg_search));
        bi.spnReminder.setBackground(getDrawable(R.drawable.bg_search));

        for (String name : GH.getInstance().getArrayReminder().keySet()) {
            reminderList.add(name);
        }
        bi.spnVia.setItems(getResources().getStringArray(R.array.arrVia));
        bi.spnReminder.setItems(reminderList);
        bi.spnReminder.expand();

        via = bi.spnVia.getItems().get(bi.spnVia.getSelectedIndex()).toString();

        bi.spnVia.setOnItemSelectedListener((view, position, id, item) -> {

            switch (position) {
                case 0:
                    via = "Text";
                    break;
                case 1:
                    via = "Email";
                    break;
            }

        });
        bi.spnReminder.setHint("Select Reminder");
        reminder = String.valueOf(GH.getInstance().getArrayReminder().get(bi.spnReminder.getItems().get(bi.spnReminder.getSelectedIndex())));
        bi.spnReminder.setOnItemSelectedListener((view, position, id, item) ->
                reminder = String.valueOf(GH.getInstance().getArrayReminder().get(item.toString())));

    }

    private void showAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedIdsList = new ArrayList<>();
                        selectedIdsList = selectedIds;
                        if (bi.lnAgent.getChildCount() > 0) {
                            bi.lnAgent.removeAllViews();
                        }

                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnAgent.addView(child);

                        }

                        agentModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

        if (selectedIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedIdsList);
            multiSelectDialog.multiSelectList(searchListItems);
        } else {
            multiSelectDialog.multiSelectList(searchListItems);
        }

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

    }

    private void showDateDialog(TextView textView, boolean isStart) {

        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if (isStart) {
                    startDate = dateFormatter.format(newDate.getTime());
                } else {
                    endDate = dateFormatter.format(newDate.getTime());
                }

                textView.setText(dateFormatter2.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }

    private void showTimeDiaolog(TextView textView, boolean isStart) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = "";
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:m:ss");
                if (isStart) {
                    startTime = selectedHour + ":" + selectedMinute + ":00";

                    Date d = null;
                    try {
                        d = dateFormat.parse(startTime);
                        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(d);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }


                } else {
                    endTime = selectedHour + ":" + selectedMinute + ":00";
                    Date d = null;
                    try {
                        d = dateFormat.parse(endTime);
                        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                textView.setText(time);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void callAddAppointment() {

        String leadStringID = leadId + "";
        String agentsID = agentModel.getId() + "";
        String leadAppoinmentID = "0";
        String eventTitle = bi.edtEventTitle.getText().toString() + "";
        String location = bi.edtLocation.getText().toString() + "";
        String desc = bi.edtDescription.getText().toString() + "";
        String isAppointmentFixed = "false";
        String isAppointmentAttend = "false";
        String appointmentDate = "";
        String datedFrom = startDate + "";
        String datedTo = endDate + "";
        String isAllDay = bi.cbAllDay.isChecked() ? "true" : "false";
        String isSend = "false";
        String viaReminder = via + "";
        String orderBy = "0";
        String timedFrom = startTime + "";
        String timedTo = endTime + "";
        String interval = reminder + "";
        String agentIds = String.valueOf(agentModel.getId());
        String isCompleted = "false";

        presenter.addAppointment(leadStringID, agentsID, leadAppoinmentID, eventTitle, location, desc, isAppointmentFixed, isAppointmentAttend, appointmentDate,
                datedFrom, datedTo, isAllDay, interval, isSend, viaReminder, agentIds, orderBy, timedFrom, timedTo, isCompleted);

    }

    public void showSearchDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_search_dialog);

        EditText edtQuery = dialog.findViewById(R.id.edtQuery);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        recyclerSearch = dialog.findViewById(R.id.recyclerSearch);

        tvTitle.setText("Name of Contact");
        edtQuery.requestFocus();
        if (edtQuery.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        edtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                getLeadByText(editable.toString(), dialog);

            }
        });

        dialog.show();
    }

    private void setRecyclerSearch(Dialog dialog) {

        recyclerSearch.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerSearch.addItemDecoration(new DividerItemDecoration(recyclerSearch.getContext(), 1));
        recyclerAdapterUtil = new RecyclerAdapterUtil(context, nameList, R.layout.custom_search_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadTitlesModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, nameList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(nameList.name);

            return Unit.INSTANCE;
        });

        recyclerSearch.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadTitlesModel.Data, Integer, Unit>) (list, integer) -> {

            bi.edtName.setText(nameList.get(integer).name);
            leadId = nameList.get(integer).leadID;
            dialog.dismiss();

            return Unit.INSTANCE;
        });
    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
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
        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog(context);
    }

    private void getLeadByText(String query, Dialog dialog) {
        Call<GetLeadTitlesModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTitlesModel(GH.getInstance().getAuthorization(), query);
        _callToday.enqueue(new Callback<GetLeadTitlesModel>() {
            @Override
            public void onResponse(Call<GetLeadTitlesModel> call, Response<GetLeadTitlesModel> response) {
                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    GetLeadTitlesModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        nameList = new ArrayList<>();
                        nameList.addAll(getAppointmentsModel.data);
                        setRecyclerSearch(dialog);

                    } else {

                        ToastUtils.showToast(context, getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLeadTitlesModel> call, Throwable t) {
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }
}