package com.project.jarjamediaapp.Activities.add_appointment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarDetailModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    String via = "", reminder = "0", leadId = "", location = "", agentIdsString = "", encryptedAppointmentId = "";
    String leadAppointmentId;
    boolean isReminderClicked = false, isViaClicked = false;
    String leadName = "";
    boolean isEdit;
    String fromId = "";
    ArrayList<String> arrayListReminderValue, arrayListReminderText;
    CalendarDetailModel.Data.CalendarData calendarData;
    String timedFrom = "", timedTo = "", datedFrom = "", datedTo = "";
    int month, year, day, mHour, mMinute;
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment);
        presenter = new AddAppointmentPresenter(this);
        presenter.initScreen();


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

        bi.atvLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = bi.atvLocation.getText().length();
                    try {
                        if (length > 0)
                            presenter.getDropDownLocation(bi.atvLocation.getText().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        newCalendar = Calendar.getInstance();

        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);

    }

    private void getUpdatedData() {

        // 1 from Add Appointment by Lead Id
        // 2 from Update Appointment Lead Id
        // 3 from Add Calendar Appointment
        // 4 from Update Appointment
        // 5 from Add Appointment
        // 6 for Update Calendar Appointment

        fromId = getIntent().getStringExtra("from");
        switch (fromId) {
            case "1": {
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_appointment), true);
                bi.lblAppointment.setText(getString(R.string.add_appointment));
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
            }
            break;
            case "2": {
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.edit_appointment), true);
                bi.lblAppointment.setText(getString(R.string.edit_appointment));
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
                isEdit = true;
                GetAppointmentsModel.Data modelData = getIntent().getParcelableExtra("model");
                prePopulateData(modelData);

            }
            break;
            case "3":
            case "5": {
                bi.tvName.setEnabled(true);
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_appointment), true);
                bi.lblAppointment.setText(getString(R.string.add_appointment));
            }
            break;
            case "4": {

                bi.tvName.setEnabled(true);
                isEdit = true;
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.edit_appointment), true);
                bi.lblAppointment.setText(getString(R.string.edit_appointment));
                GetAppointmentsModel.Data modelData = getIntent().getParcelableExtra("models");
                prePopulateData(modelData);

            }
            break;
            case "6": {

                bi.tvName.setEnabled(true);
                isEdit = true;
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.edit_appointment), true);
                bi.lblAppointment.setText(getString(R.string.edit_appointment));
                calendarData = getIntent().getParcelableExtra("modelData");
                prePopulateData(calendarData);

            }
            break;
        }
    }

    private void prePopulateData(CalendarDetailModel.Data.CalendarData modelData) {

        encryptedAppointmentId = modelData.getEncrypted_LeadAppointmentID();
        leadId = String.valueOf(modelData.getEncryptedLeadID());
        bi.tvName.setText((modelData.getLeadName() != null ? modelData.getLeadName() : ""));
        bi.atvEventTitle.setText(modelData.getEventTitle() != null ? modelData.getEventTitle() : "");
        bi.atvLocation.setText(modelData.getLocation() != null ? modelData.getLocation() : "");
        bi.atvDescription.setText(modelData.getDesc() != null ? modelData.getDesc() : "");

        startDate = GH.getInstance().formatter(modelData.getDatedFrom(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
        endDate = GH.getInstance().formatter(modelData.getDatedTo(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
        startTime = GH.getInstance().formatter(modelData.getDatedFrom(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");
        endTime = GH.getInstance().formatter(modelData.getDatedTo(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");

        bi.tvStartDate.setText(GH.getInstance().formatDate(modelData.getDatedFrom()) != null ? GH.getInstance().formatDate(modelData.getDatedFrom()) : "");
        bi.tvStartTime.setText(GH.getInstance().formatTime(modelData.getDatedFrom()) != null ? GH.getInstance().formatTime(modelData.getDatedFrom()) : "");
        bi.tvEndDate.setText(GH.getInstance().formatDate(modelData.getDatedTo()) != null ? GH.getInstance().formatDate(modelData.getDatedTo()) : "");
        bi.tvEndTime.setText(GH.getInstance().formatTime(modelData.getDatedTo()) != null ? GH.getInstance().formatTime(modelData.getDatedTo()) : "");
        if (arrayListReminderValue != null && arrayListReminderValue.size() > 0) {
            reminder = String.valueOf(modelData.getInterval());
            via = modelData.getViaReminder();
            for (int position = 0; position < arrayListReminderValue.size(); position++) {
                if (arrayListReminderValue.get(position).equalsIgnoreCase(reminder)) {
                    bi.atvReminder.setText(arrayListReminderText.get(position));
                }
            }
        }
        bi.atvVia.setText(modelData.getViaReminder() != null ? modelData.getViaReminder() : "");
        bi.atvVia.setVisibility(View.VISIBLE);
        bi.lblVia.setVisibility(View.VISIBLE);
        bi.cbAllDay.setChecked(modelData.isAllDay);

        if (modelData.getAgentList() != null && modelData.getAgentList().size() > 0) {
            selectedIdsList = new ArrayList<>();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < modelData.getAgentList().size(); i++) {
                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(modelData.getAgentList().get(i).getAgentName());
                arrayList.add(modelData.getAgentList().get(i).getEncryptedAgentID());
                bi.lnAgent.addView(child);
                bi.lnAgent.setVisibility(View.VISIBLE);
                selectedIdsList.add(modelData.getAgentList().get(i).getAgentID());
            }
            agentIdsString = TextUtils.join(",", arrayList);

        }

    }

    private void prePopulateData(GetAppointmentsModel.Data modelData) {

        leadId = modelData.getLeadID();
        leadAppointmentId = modelData.getLeadAppoinmentID();
        bi.tvName.setText((modelData.getLeadsData().getFirstName() != null ? modelData.getLeadsData().getFirstName() : "") + " " + (modelData.getLeadsData().getLastName() != null ? modelData.getLeadsData().getLastName() : ""));
        bi.atvEventTitle.setText(modelData.getEventTitle() != null ? modelData.getEventTitle() : "");
        bi.atvLocation.setText(modelData.getLocation() != null ? modelData.getLocation() : "");
        bi.atvDescription.setText(modelData.getDesc() != null ? modelData.getDesc() : "");

        startDate = GH.getInstance().formatter(modelData.getDatedFrom(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
        endDate = GH.getInstance().formatter(modelData.getDatedTo(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
        startTime = GH.getInstance().formatter(modelData.getDatedFrom(), "h:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");
        endTime = GH.getInstance().formatter(modelData.getDatedTo(), "h:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");

        bi.tvStartDate.setText(GH.getInstance().formatDate(modelData.getDatedFrom()) != null ? GH.getInstance().formatDate(modelData.getDatedFrom()) : "");
        bi.tvStartTime.setText(GH.getInstance().formatTime(modelData.getDatedFrom()) != null ? GH.getInstance().formatTime(modelData.getDatedFrom()) : "");
        bi.tvEndDate.setText(GH.getInstance().formatDate(modelData.getDatedTo()) != null ? GH.getInstance().formatDate(modelData.getDatedTo()) : "");
        bi.tvEndTime.setText(GH.getInstance().formatTime(modelData.getDatedTo()) != null ? GH.getInstance().formatTime(modelData.getDatedTo()) : "");

        if (arrayListReminderValue != null && arrayListReminderValue.size() > 0) {
            reminder = String.valueOf(modelData.getInterval());
            via = modelData.getViaReminder();
            for (int position = 0; position < arrayListReminderValue.size(); position++) {
                if (arrayListReminderValue.get(position).equalsIgnoreCase(reminder)) {
                    bi.atvReminder.setText(arrayListReminderText.get(position));
                }
            }
        }
        bi.atvVia.setText(modelData.getViaReminder() != null ? modelData.getViaReminder() : "");
        if (reminder != null && !reminder.equalsIgnoreCase("None")) {
            bi.atvVia.setVisibility(View.VISIBLE);
            bi.lblVia.setVisibility(View.VISIBLE);
            via = "";
        } else {
            bi.atvVia.setVisibility(View.GONE);
            bi.lblVia.setVisibility(View.GONE);
            via = "";
        }

        bi.cbAllDay.setChecked(modelData.isAllDay);
        if (modelData.isAllDay) {
            allDay();
        }

        if (modelData.getVtCRMLeadAppoinmentDetailCustom() != null && modelData.getVtCRMLeadAppoinmentDetailCustom().size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            selectedIdsList = new ArrayList<>();
            for (int i = 0; i < modelData.getVtCRMLeadAppoinmentDetailCustom().size(); i++) {
                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(modelData.getVtCRMLeadAppoinmentDetailCustom().get(i).getAgentName());
                arrayList.add(modelData.getVtCRMLeadAppoinmentDetailCustom().get(i).getAgentID());
                bi.lnAgent.addView(child);
                bi.lnAgent.setVisibility(View.VISIBLE);
                selectedIdsList.add(modelData.getVtCRMLeadAppoinmentDetailCustom().get(i).getDecryptedAgentID());
            }
            agentIdsString = TextUtils.join(",", arrayList);
        }


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
                clearFocus();
                showTimeDialog(bi.tvStartTime, true);
                break;
            case R.id.tvEndTime:
                clearFocus();
                showTimeDialog(bi.tvEndTime, false);
                break;
            case R.id.atvReminder:
                clearFocus();
                reminder();
                break;
            case R.id.atvVia:
                clearFocus();
                via();
                break;
            case R.id.btnSave:
                callAddAppointment(fromId);
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

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListReminderText);
            bi.atvReminder.setAdapter(arrayAdapter);
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
        if(isEdit)
            presenter.getVia();
    }

    @Override
    public void updateUIListForReminders(AddAppointmentModel response) {

        arrayListReminderText = new ArrayList<>();
        arrayListReminderValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListReminderText.add(response.getData().get(i).getText());
            arrayListReminderValue.add(response.getData().get(i).getValue());
        }

        bi.atvReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GH.getInstance().hideKeyboard(context, AddAppointmentActivity.this);
                presenter.getVia();
                reminder = arrayListReminderValue.get(position);
                if (bi.atvReminder.getText().toString().contains("None")) {
                    bi.atvVia.setVisibility(View.GONE);
                    bi.lblVia.setVisibility(View.GONE);
                } else {
                    bi.atvVia.setVisibility(View.VISIBLE);
                    bi.lblVia.setVisibility(View.VISIBLE);
                }
            }
        });

        getUpdatedData();
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

                        agentIdsString = "";
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

        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {

                year = years;
                month = monthOfYear;
                day = dayOfMonth;
                newCalendar.set(year, month, day);

                if (isStart) {
                    startDate = dateFormatter.format(newCalendar.getTime());
                } else {
                    endDate = dateFormatter.format(newCalendar.getTime());
                }

                textView.setText(dateFormatter2.format(newCalendar.getTime()));

            }

        }, year, month, day);
        if (isStart) {
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        } else {
            newCalendar.set(year, month, day);
            StartTime.getDatePicker().setMinDate(newCalendar.getTime().getTime() - 1000);
        }
        StartTime.show();
    }

    private void showTimeDialog(TextView textView, boolean isStart) {

        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                mHour = selectedHour;
                mMinute = selectedMinute;

                String time = "";
                if (isStart) {
                    startTime = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "HH:mm:ss", "HH:mm:ss");
                    time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");


                } else {
                    endTime = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "HH:mm:ss", "HH:mm:ss");
                    time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");

                }
                textView.setText(time);

            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void callAddAppointment(String from) {

        String leadStringID = leadId + "";
        String agentsID = agentIdsString;
        String eventTitle = bi.atvEventTitle.getText().toString() + "";
        String location = bi.atvLocation.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";

        boolean isAllDay = bi.cbAllDay.isChecked() ? true : false;
        String viaReminder = via + "";

        if (bi.cbAllDay.isChecked()) {

            timedFrom = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            timedTo = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        } else {
            timedFrom = startTime;
            timedTo = endTime;
        }

        datedFrom = GH.getInstance().formatter(startDate + " " + timedFrom, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");
        datedTo = GH.getInstance().formatter(endDate + " " + timedTo, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");

        Integer interval = !reminder.equalsIgnoreCase("") ? Integer.parseInt(reminder) : 0;
        Integer orderBy = 0;
        boolean isCompleted = false;
        String leadAppointmentID = leadAppointmentId != null ? leadAppointmentId : "0";
        String isAppointmentFixed = "false";
        String isAppointmentAttend = "false";
        String appointmentDate = "";
        boolean isSend = false;

        String data = getIntent().getStringExtra("calendarId");
        String calendarId = data != null ? data : "";

        if (isValidate()) {
            // Methods for Add Update are different in presenter

            Log.v("http", "leadStringId: " + leadStringID + "\n AgentId: " + agentsID + "\n leadAppointmentId: " + leadAppointmentID + "eventTitle: " + eventTitle + "\nlocation: " + location +
                    "\ndescription" + desc + "\nisAppointmentFixed: " + isAppointmentFixed + "\n isAppointmentAttend: " + isAppointmentAttend + "\n appointmentDate: " + appointmentDate +
                    "\n datedFrom: " + datedFrom + "\n datedTo: " + datedTo + "\n isAllDay: " + isAllDay + "\n interval:  " + interval + "\n isSend: " + isSend + "\n viaReminder: " + viaReminder +
                    "\n agentid: " + agentsID + "\n orderBy: " + orderBy + "\n timedFrom: " + timedFrom + "\n timedTo: " + timedTo + "\n isCompleted" + isCompleted + "\nfromId: " + fromId + "\n CalendarId: " + calendarId +
                    "\n encryptedAppointmentId: " + encryptedAppointmentId);

            presenter.addAppointment(leadStringID, agentsID, leadAppointmentID, eventTitle, location, desc, isAppointmentFixed, isAppointmentAttend,
                    appointmentDate, datedFrom, datedTo, isAllDay, interval, isSend, viaReminder, agentsID, orderBy, timedFrom, timedTo,
                    isCompleted, fromId, calendarId, encryptedAppointmentId, leadId);


        }
    }

    private boolean isValidate() {

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

        Date date1 = null, date2 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2.compareTo(date1) < 0) {
            ToastUtils.showToast(context, "Start date cannot be greater than start date");
            bi.tvEndDate.requestFocus();
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
        if (!bi.atvReminder.getText().toString().equalsIgnoreCase("") &&
                !bi.atvReminder.getText().toString().equalsIgnoreCase("None")) {
            if (via.equalsIgnoreCase("")) {
                ToastUtils.showToast(context, R.string.error_via);
                bi.atvVia.requestFocus();
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
        GH.getInstance().ShowProgressDialog(AddAppointmentActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    private void getLeadByText(String query, Dialog dialog) {

        Call<GetLeadTitlesModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTitlesModel(GH.getInstance().getAuthorization(), query);
        _callToday.enqueue(new Callback<GetLeadTitlesModel>() {
            @Override
            public void onResponse(Call<GetLeadTitlesModel> call, Response<GetLeadTitlesModel> response) {
                GH.getInstance().HideProgressDialog();
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

    private void clearFocus() {

        bi.atvLocation.clearFocus();
        bi.atvEventTitle.clearFocus();
        bi.atvDescription.clearFocus();

    }

}