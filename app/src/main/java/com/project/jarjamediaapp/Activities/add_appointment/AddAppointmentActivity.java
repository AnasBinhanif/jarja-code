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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.project.jarjamediaapp.Utilities.Methods;
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
    String startDate = "", endDate = "", startTime = "", endTime = "";
    String via = "", reminder = "", leadId = "", location = "", agentIdsString = "";
    MultiSelectModel agentModel;
    boolean isReminderClicked = false, isViaClicked = false;
    String agentIds = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment);
        presenter = new AddAppointmentPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_appointment), true);

    }

    @Override
    public void initViews() {

        presenter.getAgentNames();
        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.atvReminder.setOnClickListener(this);
        bi.atvVia.setOnClickListener(this);
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.tvEndDate.setOnClickListener(this);
        bi.tvEndTime.setOnClickListener(this);
        bi.tvAgent.setOnClickListener(this);
        bi.tvName.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);

        bi.atvLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                try {
                    if (charSequence.length() > 3)
                        presenter.getDropDownLocation(charSequence.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.tvName:
                showSearchDialog(context);
                break;
            case R.id.tvAgent:
                showAgentDialog();
                break;
            case R.id.tvStartDate:
                showDateDialog(bi.tvStartDate, true);
                break;
            case R.id.tvEndDate:
                showDateDialog(bi.tvEndDate, false);
                break;
            case R.id.tvStartTime:
                showTimeDialog(bi.tvStartTime, true);
                break;
            case R.id.tvEndTime:
                showTimeDialog(bi.tvEndTime, false);
                break;
            case R.id.atvReminder:
                reminder();
                break;
            case R.id.atvVia:
                via();
                break;
            case R.id.btnSave:
                callAddAppointment();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.cbAllDay:
                allDay();
                break;
        }
    }

    private void reminder() {

        if (!isReminderClicked) {
            bi.atvReminder.showDropDown();
            isReminderClicked = true;
        } else {
            isReminderClicked = false;
            bi.atvReminder.dismissDropDown();
        }

    }

    private void via() {

        if (!isViaClicked) {
            bi.atvVia.showDropDown();
            isViaClicked = true;
        } else {
            isViaClicked = false;
            bi.atvVia.dismissDropDown();
        }

    }

    private void allDay() {

        if (bi.cbAllDay.isChecked()) {

            bi.lblStartTime.setVisibility(View.GONE);
            bi.lblEndTime.setVisibility(View.GONE);
            bi.tvEndTime.setVisibility(View.GONE);
            bi.tvStartTime.setVisibility(View.GONE);

        } else {

            bi.lblStartTime.setVisibility(View.VISIBLE);
            bi.lblEndTime.setVisibility(View.VISIBLE);
            bi.tvEndTime.setVisibility(View.VISIBLE);
            bi.tvStartTime.setVisibility(View.VISIBLE);
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
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
        }
        presenter.getReminder();
    }

    @Override
    public void updateUIListForReminders(AddAppointmentModel response) {

        ArrayList<String> arrayListReminderText = new ArrayList<>();
        ArrayList<String> arrayListReminderValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListReminderText.add(response.getData().get(i).getText());
            arrayListReminderValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListReminderText);
        bi.atvReminder.setAdapter(arrayAdapter);

        bi.atvReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.getVia();
                reminder = arrayListReminderValue.get(position);
            }
        });
    }

    @Override
    public void updateUIListForVia(AddAppointmentModel response) {

        ArrayList<String> arrayListViaText = new ArrayList<>();
        ArrayList<String> arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getText());
            arrayListViaValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaText);
        bi.atvVia.setAdapter(arrayAdapter);

        bi.atvVia.setVisibility(View.VISIBLE);
        bi.lblVia.setVisibility(View.VISIBLE);

        bi.atvVia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                via = arrayListViaValue.get(position);
            }
        });

    }

    @Override
    public void updateUIListForLocation(GetLocationModel.Data response) {

        try {

            ArrayList<String> filterData = new ArrayList<>();
            for (int i = 0; i < response.getGroupName().size(); i++) {
                filterData.add(response.getGroupName().get(i).getLabel());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, filterData);
            bi.atvLocation.setAdapter(arrayAdapter);
            bi.atvLocation.showDropDown();
            bi.atvLocation.setThreshold(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                        agentIdsString="";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (agentIdsString.equals("")) {
                                    agentIdsString = i;
                                } else {
                                    agentIdsString = agentIdsString + "," + i;
                                }
                            }
                        } else {
                            ToastUtils.showToast(context, "No EncryptedID Found");
                        }
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

    private void showTimeDialog(TextView textView, boolean isStart) {
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
        String agentsID = agentIdsString;
        String eventTitle = bi.atvEventTitle.getText().toString() + "";
        String location = bi.atvLocation.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";
        String datedFrom = startDate + "";
        String datedTo = endDate + "";
        String isAllDay = bi.cbAllDay.isChecked() ? "true" : "false";
        String viaReminder = via + "";
        String orderBy = "0";
        String timedFrom = startTime + "";
        String timedTo = endTime + "";
        String interval = reminder + "";

        String isCompleted = "false";
        String leadAppointmentID = "0";
        String isAppointmentFixed = "false";
        String isAppointmentAttend = "false";
        String appointmentDate = "";
        String isSend = "false";

        if (isValidate())
            presenter.addAppointment(leadStringID, agentsID, leadAppointmentID, eventTitle, location, desc, isAppointmentFixed, isAppointmentAttend, appointmentDate,
                    datedFrom, datedTo, isAllDay, interval, isSend, viaReminder, agentsID, orderBy, timedFrom, timedTo, isCompleted);

    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.tvName)) {
            ToastUtils.showToast(context, R.string.error_name);
            bi.tvName.requestFocus();
            return false;
        }
        if (agentIdsString.equalsIgnoreCase("")) {
            ToastUtils.showToast(context, R.string.error_agent);
            bi.tvAgent.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvEventTitle)) {
            ToastUtils.showToast(context, R.string.error_title);
            bi.atvEventTitle.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvDescription)) {
            ToastUtils.showToast(context, R.string.error_description);
            bi.atvDescription.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.tvStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_date);
            bi.tvStartTime.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.tvEndDate)) {
            ToastUtils.showToast(context, R.string.error_end_date);
            bi.tvName.requestFocus();
            return false;
        }

        if (Methods.isEmpty(bi.atvReminder)) {
            ToastUtils.showToast(context, R.string.error_reminder);
            bi.atvReminder.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvVia)) {
            ToastUtils.showToast(context, R.string.error_via);
            bi.atvVia.requestFocus();
            return false;
        }
        if (!bi.cbAllDay.isChecked()) {
            if (Methods.isEmpty(bi.tvStartTime)) {
                ToastUtils.showToast(context, R.string.error_start_time);
                bi.tvStartTime.requestFocus();
                return false;
            }
            if (Methods.isEmpty(bi.tvEndTime)) {
                ToastUtils.showToast(context, R.string.error_end_time);
                bi.tvEndTime.requestFocus();
                return false;
            }
        }


        return true;
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

            bi.tvName.setText(nameList.get(integer).name);
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