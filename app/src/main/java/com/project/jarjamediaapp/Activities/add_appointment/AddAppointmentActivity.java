package com.project.jarjamediaapp.Activities.add_appointment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarAppointmentDetailModel;
import com.project.jarjamediaapp.Activities.search_activity.SearchResultsActivity;
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
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAppointmentActivity extends BaseActivity implements AddAppointmentContract.View, View.OnClickListener, DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {

    ActivityAddAppointmentBinding bi;
    Context context = AddAppointmentActivity.this;
    AddAppointmentPresenter presenter;
    RecyclerAdapterUtil recyclerAdapterUtil;
    RecyclerView recyclerSearch;
    ArrayList<String> reminderList = new ArrayList<>();
    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<MultiSelectModel> searchListItems = new ArrayList<>();
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<GetLeadTitlesModel.Data> nameList = new ArrayList<>();
    String startDate = "", endDate = "", startTime = "", endTime = "";
    String via = "", reminder = "0", leadId = "", location = "", agentIdsString = "", encryptedAppointmentId = "";
    String leadAppointmentId;
    boolean isReminderClicked = false, isViaClicked = false;
    String leadName = "";
    boolean isEdit, isEdited = false;
    String fromId = "";
    ArrayList<String> arrayListReminderValue, arrayListReminderText;
    CalendarAppointmentDetailModel.Data.CalendarData calendarData;
    String timedFrom = "", timedTo = "", datedFrom = "", datedTo = "";
    int month, year, day, _month, _year, _day, mHour, mMinute;
    Calendar newCalendar;
    boolean isStart;
    String gmailCalenderId;

    protected boolean enabled = true;

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

        Log.i("MyToke", GH.getInstance().getFirebaseToken());

        setSupportActionBar(bi.epToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        loadTitle();

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

    }

    private void loadTitle() {
        // 1 from Add Appointment by Lead Id
        // 3 from Add Calendar Appointment
        // 5 from Add Appointment
        // 2 from Update Appointment Lead Id
        // 4 from Update Appointment
        // 6 for Update Calendar Appointment
        // 7 for push notification redirection when tap on notification and notification screen both same fro opening notification
        fromId = getIntent().getStringExtra("from");
        gmailCalenderId = getIntent().getStringExtra("gmailCalenderId");
        switch (fromId) {
            case "1":
            case "3":
            case "5": {
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_appointment), true);
            }
            break;
            case "2":
            case "4":
            case "7":
            case "6": {
                setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.edit_appointment), true);
            }
            break;
        }
    }

    private void calendarInstance() {

        newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);


    }

    private void calendarEditInstance(int id) {

        // 1 for start date
        // 2 for end date
        // 3 for start time
        // 4 for end time
        try {

            switch (id) {
                case 1: {
                    String[] formattedDate = startDate.split("-");
                    year = Integer.parseInt(formattedDate[0]);
                    month = Integer.parseInt(formattedDate[1]);
                    day = Integer.parseInt(formattedDate[2]);
                    month = month - 1;
                    newCalendar = Calendar.getInstance();
                    newCalendar.set(year, month, day);
                }
                break;
                case 2: {
                    String[] formattedDate = endDate.split("-");
                    year = Integer.parseInt(formattedDate[0]);
                    month = Integer.parseInt(formattedDate[1]);
                    day = Integer.parseInt(formattedDate[2]);
                    month = month - 1;
                    newCalendar = Calendar.getInstance();
                    newCalendar.set(year, month, day);
                }
                break;
                case 3: {
                    String[] formattedTime = startTime.split(":");
                    mHour = Integer.parseInt(formattedTime[0]);
                    mMinute = Integer.parseInt(formattedTime[1]);

                }
                break;
                case 4: {
                    String[] formattedTime = endTime.split(":");
                    mHour = Integer.parseInt(formattedTime[0]);
                    mMinute = Integer.parseInt(formattedTime[1]);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUpdatedData() {

        // 1 from Add Appointment by Lead Id
        // 2 from Update Appointment Lead Id
        // 3 from Add Calendar Appointment
        // 4 from Update Appointment
        // 5 from Add Appointment
        // 6 for Update Calendar Appointment
        // 7 for push notification redirection when tap on notification and notification screen both same fro opening notification


        fromId = getIntent().getStringExtra("from");
        assert fromId != null;
        switch (fromId) {
            // from lead detail appontmnets
            case "1": {

                /* add slected agent show in agent filed*/
                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(GH.getInstance().getAgentName());
                bi.lnAgent.addView(child);

                selectedIdsList.add(GH.getInstance().getAgentID());
                if (agentIdsString.equals("")) {
                    agentIdsString = GH.getInstance().getCalendarAgentId();
                } else {
                    agentIdsString = agentIdsString + "," + GH.getInstance().getCalendarAgentId();
                }
                /* add slected agent show in agent filed */


                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                bi.tvName.setText(leadName + "");
                bi.tvName.setEnabled(false);
                isEdit = true;

            }
            break;
            case "2": {
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                isEdit = true;
                GetAppointmentsModel.Data.Datum modelData = getIntent().getParcelableExtra("models");
                prePopulateData(modelData);
            }
            break;
            case "4": {
                isEdit = true;
                GetAppointmentsModel.Data.Datum modelData = getIntent().getParcelableExtra("models");

                prePopulateData(modelData);
            }

            break;
            case "7": {
                isEdit = true;
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                isEdit = true;
                Data modelData = getIntent().getParcelableExtra("models");
                prePopulateDataFromNotification(modelData);
            }

            break;
            case "6": {
                isEdit = true;
                calendarData = getIntent().getParcelableExtra("modelData");
                if (calendarData != null) {
                    try {
                        prePopulateData(calendarData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast(context, "No Data Found");
                }
            }

            break;
            // auto assign agent to appointments from dashboard
            case "5":

                // auto assign agent to appointments from calender
            case "3": {
                // add slected agent show in agent filed
                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(GH.getInstance().getAgentName());
                bi.lnAgent.addView(child);

                selectedIdsList.add(GH.getInstance().getAgentID());
                if (agentIdsString.equals("")) {
                    agentIdsString = GH.getInstance().getCalendarAgentId();
                } else {
                    agentIdsString = agentIdsString + "," + GH.getInstance().getCalendarAgentId();
                }

            }
            break;
        }

        if (isEdit) {

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isEdited = true;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            bi.atvEventTitle.addTextChangedListener(textWatcher);
            bi.atvDescription.addTextChangedListener(textWatcher);

        }
    }

    private void setViewAndChildrenEnabled(LinearLayout view, boolean enabled) {
        view.setEnabled(enabled);
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            child.setEnabled(false);
        }
    }

    private void prePopulateData(CalendarAppointmentDetailModel.Data.CalendarData modelData) {


        encryptedAppointmentId = modelData.getEncryptedLeadAppoinmentID();

        leadId = String.valueOf(modelData.getEncryptedLeadID());

        bi.tvName.setText((modelData.getLeadName() != null ? modelData.getLeadName() : ""));

        if (fromId.equalsIgnoreCase("2") || fromId.equalsIgnoreCase("4") ||
                fromId.equalsIgnoreCase("6")) {
            //  bi.tvName.setEnabled(false);
            bi.tvName.setEnabled(true);
        } else {
            bi.tvName.setEnabled(true);
            bi.tvName.setHint("Contact");
        }
        bi.atvEventTitle.setText(modelData.getEventTitle() != null ? modelData.getEventTitle() : "");
        bi.atvLocation.setText(modelData.getLocation() != null ? modelData.getLocation() : "");
        bi.atvDescription.setText(modelData.getDesc() != null ? modelData.getDesc() : "");

        if (modelData.getDatedFrom() != null && modelData.getDatedTo() != null) {

            startDate = GH.getInstance().formatter(modelData.getDatedFrom(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
            endDate = GH.getInstance().formatter(modelData.getDatedTo(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
            startTime = GH.getInstance().formatter(modelData.getDatedFrom(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");
            endTime = GH.getInstance().formatter(modelData.getDatedTo(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");

            bi.tvStartDate.setText(GH.getInstance().formatDate(modelData.getDatedFrom()) != null ? GH.getInstance().formatDate(modelData.getDatedFrom()) : "");
            bi.tvStartTime.setText(GH.getInstance().formatTime(modelData.getDatedFrom()) != null ? GH.getInstance().formatTime(modelData.getDatedFrom()) : "");
            bi.tvEndDate.setText(GH.getInstance().formatDate(modelData.getDatedTo()) != null ? GH.getInstance().formatDate(modelData.getDatedTo()) : "");
            bi.tvEndTime.setText(GH.getInstance().formatTime(modelData.getDatedTo()) != null ? GH.getInstance().formatTime(modelData.getDatedTo()) : "");

            //adding these lines to append time with start and end date textview
            bi.tvStartDate.append(" " + bi.tvStartTime.getText());
            bi.tvEndDate.append(" " + bi.tvEndTime.getText());


        }

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
        if (reminder != null && !reminder.equalsIgnoreCase("0")) {
            bi.atvVia.setVisibility(View.VISIBLE);
            bi.lblVia.setVisibility(View.VISIBLE);
            via = modelData.getViaReminder();
        } else {
            bi.atvVia.setVisibility(View.GONE);
            bi.lblVia.setVisibility(View.GONE);
            via = "";
        }
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

    private void prePopulateData(GetAppointmentsModel.Data.Datum modelData) {


        leadId = modelData.getLeadID();
        leadAppointmentId = modelData.getLeadAppoinmentID();

        bi.tvName.setText((modelData.getVtCRMLeadCustom().getFirstName() != null ? modelData.getVtCRMLeadCustom().getFirstName() : "") + " " + (modelData.getVtCRMLeadCustom().getLastName() != null ? modelData.getVtCRMLeadCustom().getLastName() : ""));
        if (fromId.equalsIgnoreCase("2") || fromId.equalsIgnoreCase("4") ||
                fromId.equalsIgnoreCase("6")) {
            //   bi.tvName.setEnabled(false);
            bi.tvName.setEnabled(true);
        } else {
            bi.tvName.setEnabled(true);
            bi.tvName.setHint("Contact");
        }
        if (leadId != null) {
            bi.tvName.setEnabled(false);
            bi.tvName.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        }
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

        //adding these lines to append time with start and end date textview
        bi.tvStartDate.append(" " + bi.tvStartTime.getText());
        bi.tvEndDate.append(" " + bi.tvEndTime.getText());

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
        if (reminder != null && !reminder.equalsIgnoreCase("0")) {
            bi.atvVia.setVisibility(View.VISIBLE);
            bi.lblVia.setVisibility(View.VISIBLE);
            via = modelData.getViaReminder();
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
                selectedIdsList.add(modelData.getVtCRMLeadAppoinmentDetailCustom().get(i).getAgentDecryptedID());
            }
            agentIdsString = TextUtils.join(",", arrayList);
        }
    }

    private void prePopulateDataFromNotification(Data modelData) {

        leadId = modelData.getLeadID();
        leadAppointmentId = modelData.getLeadAppoinmentID();
        bi.tvName.setText((modelData.getVtCRMLeadCustom().getFirstName() != null ? modelData.getVtCRMLeadCustom().getFirstName() : "") + " " + (modelData.getVtCRMLeadCustom().getLastName() != null ? modelData.getVtCRMLeadCustom().getLastName() : ""));
        if (fromId.equalsIgnoreCase("2") || fromId.equalsIgnoreCase("4") ||
                fromId.equalsIgnoreCase("6")) {
            bi.tvName.setEnabled(false);
        } else {
            bi.tvName.setEnabled(true);
            bi.tvName.setHint("Contact");
        }
        if (leadId != null) {
            bi.tvName.setEnabled(false);
        }
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

        //adding these lines to append time with start and end date textview
        bi.tvStartDate.append(" " + bi.tvStartTime.getText());
        bi.tvEndDate.append(" " + bi.tvEndTime.getText());

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
        if (reminder != null && !reminder.equalsIgnoreCase("0")) {
            bi.atvVia.setVisibility(View.VISIBLE);
            bi.lblVia.setVisibility(View.VISIBLE);
            via = modelData.getViaReminder();
        } else {
            bi.atvVia.setVisibility(View.GONE);
            bi.lblVia.setVisibility(View.GONE);
            via = "";
        }

        bi.cbAllDay.setChecked(modelData.getIsAllDay());
        if (modelData.getIsAllDay()) {
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
                selectedIdsList.add(modelData.getVtCRMLeadAppoinmentDetailCustom().get(i).getAgentDecryptedID());
            }
            agentIdsString = TextUtils.join(",", arrayList);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 05) {
            if (resultCode == RESULT_OK) {
                bi.tvName.setText(data.getStringExtra("leadName"));
                leadId = data.getStringExtra("leadID");

                if (!leadId.equals("")) {
                    bi.imgClearName.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        Gson gson = new Gson();

        switch (view.getId()) {
            case R.id.tvName:
                //clearFocus();
                //showSearchDialog(context);
                isEdited = true;
                Intent i = new Intent(AddAppointmentActivity.this, SearchResultsActivity.class);
                i.putExtra("fromAppoint", true);
                startActivityForResult(i, 05);

                break;
            case R.id.imgClearName:

                leadId = "";
                bi.tvName.setText("");
                bi.imgClearName.setVisibility(View.GONE);

                break;
            case R.id.tvAgent:
                isEdited = true;
                clearFocus();
                showAgentDialog();
                break;
            case R.id.tvStartDate:
                isEdited = true;
                clearFocus();
                if (bi.tvStartDate.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                } else {
                    calendarEditInstance(1);
                }
                isStart = true;
                showSpinnerDateDialog();
                break;
            case R.id.tvEndDate:
                isEdited = true;
                clearFocus();
                if (bi.tvEndDate.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                } else {
                    calendarEditInstance(2);
                }
                isStart = false;
                showSpinnerDateDialog();
                break;
            case R.id.tvStartTime:
                isEdited = true;
                clearFocus();
                if (bi.tvStartTime.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                } else {
                    calendarEditInstance(3);
                }
                showTimeDialog(bi.tvStartTime, true);
                break;
            case R.id.tvEndTime:
                isEdited = true;
                clearFocus();
                if (bi.tvEndTime.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                } else {
                    calendarEditInstance(4);
                }
                showTimeDialog(bi.tvEndTime, false);
                break;
            case R.id.atvReminder:
                isEdited = true;
                clearFocus();
                reminder();
                hideSoftKeyboard(bi.atvReminder);
                break;
            case R.id.atvVia:
                isEdited = true;
                clearFocus();
                via();
                break;
            case R.id.btnSave:
                // from lead detail
                if (fromId.equals("2")) {
                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapLeadsString = GH.getInstance().getUserPermissonLead();
                    java.lang.reflect.Type typeLeads = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapLeads = gson.fromJson(storedHashMapLeadsString, typeLeads);

                    if (mapLeads.get("Edit Appointments")) {

                        callAddAppointment(fromId);

                    } else {

                        ToastUtils.showToast(context, getString(R.string.lead_EditAppoint));

                    }
                    // from calender
                } else if (fromId.equals("6")) {

                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapCalenderString = GH.getInstance().getUserPermissonCalender();
                    java.lang.reflect.Type typeCalender = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapCalender = gson.fromJson(storedHashMapCalenderString, typeCalender);

                    if (mapCalender.get("Edit Calendar")) {

                        callAddAppointment(fromId);

                    } else {

                        ToastUtils.showToast(context, getString(R.string.calender_EditCalender));
                    }

                } else {

                    callAddAppointment(fromId);
                }


                break;
            case R.id.btnCancel:
              /*  finish();
                // this line of code added here for homeactiviy components click after finish activty
                HomeActivity.onClick = true;*/

                //added below block of code for replacement

                if (isChangesDone()) {
                    GH.getInstance().discardChangesDialog(context);
                } else {
                    finish();
                }
                HomeActivity.onClick = true;
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

            if (fromId.equals("2") || fromId.equals("4") || fromId.equals("6") || fromId.equals("7")) {
                ToastUtils.showToast(context, "Updated Successfully");
            } else {
                ToastUtils.showToast(context, "Added Successfully");
            }
            finish();
            // this line of code added here for homeactiviy components click after finish activty
            HomeActivity.onClick = true;
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
        bi.imgClearName.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);
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

    private void showSpinnerDateDialog() {

        Calendar cal = Calendar.getInstance();
        new SpinnerDatePickerDialogBuilder().context(AddAppointmentActivity.this)
                .callback(AddAppointmentActivity.this)
                // .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(year, month, day)
                .minDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                .build()
                .show();

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
                    //adding this line to append time to date field
                    bi.tvStartDate.append(" " + time);
                } else {
                    endTime = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "HH:mm:ss", "HH:mm:ss");
                    time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");

                    //adding this line to append time to date field
                    bi.tvEndDate.append(" " + time);
                }
                textView.setText(time);


            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void callAddAppointment(String from) {

        String leadStringID = leadId != null ? leadId : "";
        // leadStringID = leadStringID.equals("") ? "null" : leadId ;
        String agentsID = agentIdsString;
        String eventTitle = bi.atvEventTitle.getText().toString() + "";
        String location = bi.atvLocation.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";

        boolean isAllDay = bi.cbAllDay.isChecked() ? true : false;
        String viaReminder = via == null || via.equals("") ? "" : via;

        if (bi.cbAllDay.isChecked()) {
            timedFrom = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            timedTo = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            startTime = timedFrom;
            endTime = timedTo;

        } else {
            timedFrom = startTime;
            timedTo = endTime;
        }

        datedFrom = GH.getInstance().formatter(startDate + " " + timedFrom, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy/MM/dd HH:mm:ss");
        datedTo = GH.getInstance().formatter(endDate + " " + timedTo, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy/MM/dd HH:mm:ss");

        Integer interval = reminder != null && !reminder.equalsIgnoreCase("") && !reminder.equalsIgnoreCase("null") ? Integer.parseInt(reminder) : 0;
        Integer orderBy = 0;
        boolean isCompleted = false;
        String leadAppointmentID = leadAppointmentId != null ? leadAppointmentId : "0";
        boolean isAppointmentFixed = false;
        boolean isAppointmentAttend = false;
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

            JSONObject obj = new JSONObject();

            // for calender appointment add
            if (fromId.equals("3")) {
                try {
                    obj.put("location", location);
                    obj.put("isCompleted", isCompleted);
                    //  obj.put("appointmentDate", "");
                    obj.put("leadStringID", leadStringID);
                    obj.put("orderBy", orderBy);
                    obj.put("startTime", startTime);
                    obj.put("interval", interval);
                    obj.put("datedTo", datedTo);
                    obj.put("eventTitle", eventTitle);
                    obj.put("isAppointmentAttend", isAppointmentAttend);
                    obj.put("isAppointmentFixed", isAppointmentFixed);
                    obj.put("leadAppoinmentID", leadAppointmentID);
                    obj.put("datedFrom", datedFrom);
                    obj.put("isAllDay", isAllDay);
                    obj.put("agentIds", agentsID);
                    obj.put("endTime", endTime);
                    obj.put("desc", desc);
                    obj.put("viaReminder", via);
                    obj.put("isSend", isSend);
                    obj.put("calendarType", "Event");
                    obj.put("gmailCalenderId", "");
                    // Log.i("gmailCalenderId","0");
                    obj.put("agentIDsString", agentIdsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // for calender appointment update
            } else if (fromId.equals("6")) {

                try {
                    obj.put("location", location);
                    obj.put("isCompleted", isCompleted);
                    obj.put("leadStringID", leadStringID);
                    obj.put("orderBy", orderBy);
                    obj.put("startTime", startTime);
                    obj.put("interval", interval);
                    obj.put("datedTo", datedTo);
                    obj.put("eventTitle", eventTitle);
                    obj.put("isAppointmentAttend", isAppointmentAttend);
                    obj.put("isAppointmentFixed", isAppointmentFixed);
                    obj.put("leadAppoinmentID", encryptedAppointmentId);
                    obj.put("datedFrom", datedFrom);
                    obj.put("isAllDay", isAllDay);
                    obj.put("agentIds", agentsID);
                    obj.put("endTime", endTime);
                    obj.put("desc", desc);
                    obj.put("viaReminder", via);
                    obj.put("isSend", isSend);
                    obj.put("calendarType", "Event");
                    obj.put("gmailCalenderId", calendarData.gmailCalenderId);
                    obj.put("agentIDsString", agentIdsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // both for notification popup and screen appointment update
            } else if (fromId.equals("7")) {
                try {
                    obj.put("location", location);
                    obj.put("isCompleted", isCompleted);
                    obj.put("appointmentDate", "");
                    obj.put("leadStringID", leadStringID);
                    obj.put("orderBy", orderBy);
                    obj.put("startTime", startTime);
                    obj.put("interval", interval);
                    obj.put("datedTo", datedTo);
                    obj.put("eventTitle", eventTitle);
                    obj.put("isAppointmentAttend", isAppointmentAttend);
                    obj.put("isAppointmentFixed", isAppointmentFixed);
                    obj.put("leadAppoinmentID", leadAppointmentID);
                    obj.put("datedFrom", datedFrom);
                    obj.put("isAllDay", isAllDay);
                    obj.put("agentIds", agentsID);
                    obj.put("endTime", endTime);
                    obj.put("desc", desc);
                    obj.put("isSend", isSend);
                    obj.put("viaReminder", via);
                    obj.put("leadID", "0");
                    obj.put("agentIDsString", agentIdsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // update appointmnet from dashboard
            } else if (fromId.equals("4") || fromId.equals("2")) {
                try {
                    obj.put("location", location);
                    obj.put("isCompleted", isCompleted);
                    obj.put("appointmentDate", "");
                    obj.put("leadStringID", leadStringID);
                    obj.put("orderBy", orderBy);
                    obj.put("startTime", startTime);
                    obj.put("interval", interval);
                    obj.put("datedTo", datedTo);
                    obj.put("eventTitle", eventTitle);
                    obj.put("isAppointmentAttend", isAppointmentAttend);
                    obj.put("isAppointmentFixed", isAppointmentFixed);
                    obj.put("leadAppoinmentID", leadAppointmentID);
                    obj.put("datedFrom", datedFrom);
                    obj.put("isAllDay", isAllDay);
                    obj.put("agentIds", agentsID);
                    obj.put("endTime", endTime);
                    obj.put("desc", desc);
                    obj.put("isSend", isSend);
                    obj.put("viaReminder", via);
                    obj.put("leadID", "0");
                    obj.put("gmailCalenderId", gmailCalenderId);
                    obj.put("agentIDsString", agentIdsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

                // add appointment from dashboard
                try {
                    obj.put("location", location);
                    obj.put("isCompleted", isCompleted);
                    obj.put("appointmentDate", "");
                    obj.put("leadStringID", leadStringID);
                    obj.put("orderBy", orderBy);
                    obj.put("startTime", startTime);
                    obj.put("interval", interval);
                    obj.put("datedTo", datedTo);
                    obj.put("eventTitle", eventTitle);
                    obj.put("isAppointmentAttend", isAppointmentAttend);
                    obj.put("isAppointmentFixed", isAppointmentFixed);
                    obj.put("leadAppoinmentID", leadAppointmentID);
                    obj.put("datedFrom", datedFrom);
                    obj.put("isAllDay", isAllDay);
                    obj.put("agentIds", agentsID);
                    obj.put("endTime", endTime);
                    obj.put("desc", desc);
                    obj.put("isSend", isSend);
                    obj.put("viaReminder", via);
                    obj.put("leadID", "0");
                    obj.put("gmailCalenderId", "");
                    obj.put("agentIDsString", agentIdsString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String jsonObjectString = obj.toString();
            Log.d("json", jsonObjectString);

            presenter.addAppointment(jsonObjectString, fromId);


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

        Date date1 = null, date2 = null, time1 = null, time2 = null, currentTime = null, currentDate = null;

        String datedFrom = GH.getInstance().formatter(startDate + " " + startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy/MM/dd HH:mm:ss");
        String datedTo = GH.getInstance().formatter(endDate + " " + endTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy/MM/dd HH:mm:ss");

        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(datedFrom);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(datedTo);
            String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
            currentTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
            time1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(datedFrom);
            time2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(datedTo);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            if (date2.compareTo(date1) < 0) {
                ToastUtils.showToast(context, "Start date cannot be less than end date");
                bi.tvEndDate.requestFocus();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
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


            if (date2.compareTo(date1) == 0 && date1.compareTo(currentDate) == 0) {

                if (time2.before(time1)) {
                    ToastUtils.showToast(context, "End time cannot be less than start time");
                    bi.tvStartTime.requestFocus();
                    return false;
                }
                if (time1.before(currentTime)) {
                    ToastUtils.showToast(context, "Start time cannot be less than current time");
                    bi.tvStartTime.requestFocus();
                    return false;
                }
            } else if (date2.compareTo(date1) == 0) {

                if (time2.before(time1)) {
                    ToastUtils.showToast(context, "End time cannot be less than start time");
                    bi.tvStartTime.requestFocus();
                    return false;
                }
            } else if (date2.compareTo(date1) != 0) {

                String startDate = this.startDate;
                SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String date = simpleDate1.format(new Date());

                if (date.compareTo(startDate) == 0) {
                    if (time1.before(currentTime)) {
                        ToastUtils.showToast(context, "Start time cannot be less than current time");
                        bi.tvStartTime.requestFocus();
                        return false;
                    }
                }
            }
            if (!isEdit) {
                if (time1.before(currentTime)) {
                    ToastUtils.showToast(context, "Start date/time cannot be less than current date/time");
                    bi.tvStartTime.requestFocus();
                    return false;
                }
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
        ImageView imgClear = dialog.findViewById(R.id.imgClear);

        edtQuery.setText(bi.tvName.getText().toString());
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        recyclerSearch = dialog.findViewById(R.id.recyclerSearch);


        if (!edtQuery.getText().toString().equals("")) {
            imgClear.setVisibility(View.VISIBLE);
        } else {
            imgClear.setVisibility(View.GONE);
        }

        imgClear.setOnClickListener(v -> {

            if (!edtQuery.getText().toString().equals("")) {
                bi.tvName.setText("");
                edtQuery.setText("");
                leadId = "";
            }

        });


        tvTitle.setText("Contact");
        edtQuery.requestFocus();
        if (edtQuery.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        edtQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int length = edtQuery.getText().length();
                    try {
                        if (length > 0)
                            getLeadByText(edtQuery.getText().toString(), dialog);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
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

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);

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

        showProgressBar();
        Call<GetLeadTitlesModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTitlesModel(GH.getInstance().getAuthorization(), query);
        _callToday.enqueue(new Callback<GetLeadTitlesModel>() {
            @Override
            public void onResponse(Call<GetLeadTitlesModel> call, Response<GetLeadTitlesModel> response) {
                hideProgressBar();
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    GetLeadTitlesModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        nameList = new ArrayList<>();
                        nameList.addAll(getAppointmentsModel.data);
                        if (nameList.size() != 0) {
                            setRecyclerSearch(dialog);
                        } else {
                            ToastUtils.showToast(context, "No Result Found");
                        }

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
                hideProgressBar();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    private void clearFocus() {

        bi.atvLocation.clearFocus();
        bi.atvEventTitle.clearFocus();
        bi.atvDescription.clearFocus();
        bi.tvName.clearFocus();

    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int _year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

        year = _year;
        month = monthOfYear;
        day = dayOfMonth;
        newCalendar.set(year, month, day);

        if (isStart) {
            startDate = dateFormatter.format(newCalendar.getTime());
            bi.tvStartDate.setText(dateFormatter2.format(newCalendar.getTime()));

            //adding below line to open time picker as user press ok on date picker
            bi.tvStartTime.performClick();

        } else {
            endDate = dateFormatter.format(newCalendar.getTime());
            bi.tvEndDate.setText(dateFormatter2.format(newCalendar.getTime()));

            //adding below line to open time picker as user press ok on date picker
            bi.tvEndTime.performClick();

        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        dialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        if (isChangesDone()) {
            GH.getInstance().discardChangesDialog(context);
        } else {
            super.onBackPressed();
        }
        HomeActivity.onClick = true;

    }

    private boolean isChangesDone() {

        if (!isEdit) {
            if (!Methods.isEmpty(bi.tvName))
                return true;
            if (!Methods.isEmpty(bi.atvEventTitle))
                return true;
            if (!Methods.isEmpty(bi.atvDescription))
                return true;
            if (!Methods.isEmpty(bi.atvLocation))
                return true;
            if (!Methods.isEmpty(bi.tvStartDate))
                return true;
            if (!Methods.isEmpty(bi.tvEndDate))
                return true;
            if (!Methods.isEmpty(bi.tvStartTime))
                return true;
            if (!Methods.isEmpty(bi.tvEndTime))
                return true;
            if (!Methods.isEmpty(bi.atvReminder))
                return true;
            if (!Methods.isEmpty(bi.atvVia))
                return true;
            return false;
        } else {
            if (isEdited)
                return true;
            return false;
        }
    }

}