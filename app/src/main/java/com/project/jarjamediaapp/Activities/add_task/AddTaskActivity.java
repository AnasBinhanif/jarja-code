package com.project.jarjamediaapp.Activities.add_task;

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
import androidx.recyclerview.widget.RecyclerView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
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
import com.project.jarjamediaapp.databinding.ActivityAddTaskBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends BaseActivity implements AddTaskContract.View, View.OnClickListener {

    ActivityAddTaskBinding bi;
    Context context = AddTaskActivity.this;
    AddTaskPresenter presenter;

    RecyclerAdapterUtil recyclerAdapterUtil;
    RecyclerView recyclerSearch;

    ArrayList<String> arrayListViaText = new ArrayList<>();
    ArrayList<String> arrayListViaValue = new ArrayList<>();
    ArrayList<String> arrayListReminderText = new ArrayList<>();
    ArrayList<String> arrayListReminderValue = new ArrayList<>();
    HashMap<Integer, String> hashMapReminder = new HashMap<>();

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<GetLeadTitlesModel.Data> nameList;
    ArrayList<Integer> selectedIdsListLead = new ArrayList<>();
    ArrayList<MultiSelectModel> searchListItemsLead;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<MultiSelectModel> searchListItems;
    String startDate = "", endDate = "", reminder = "0", via = "", leadId = "", type = "", reoccur = "", scheduleId = "";
    String agentIdsString = "", leadName = "", taskId = "", searchLeadIdsString = "", startTime = "", endTime = "";
    ;
    MultiSelectModel agentModel;
    boolean isEdit;
    boolean isReminderClicked = false, isViaClicked = false, isTypeClicked = false, isRecurClicked = false;
    int month, year, day, mHour, mMinute;
    String from = "";
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_task);
        presenter = new AddTaskPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_task), true);

    }

    @Override
    public void initViews() {

        presenter.getAgentNames();
        presenter.getReminder();
        presenter.getVia();
        presenter.getType();
        presenter.getRecur();

        bi.atvVia.setOnClickListener(this);
        bi.tvName.setOnClickListener(this);
        bi.btnSave.setOnClickListener(this);
        bi.atvType.setOnClickListener(this);
        bi.atvRecur.setOnClickListener(this);
        bi.tvEndTime.setOnClickListener(this);
        bi.tvEndDate.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.tvAssignTo.setOnClickListener(this);
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.atvReminder.setOnClickListener(this);

        bi.cbEndDate.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                bi.tvEndDate.setVisibility(View.GONE);
                bi.lblEndDate.setVisibility(View.GONE);
            } else {
                bi.tvEndDate.setVisibility(View.VISIBLE);
                bi.lblEndDate.setVisibility(View.VISIBLE);
            }

        });

    }

    private void checkIntent() {

        // 1 from Add Task by Lead Id
        // 2 from Update Task Lead Id
        // 3 from Update Task
        // 4 from Add Task
        String id = getIntent().getStringExtra("from");
        from = id;
        switch (id) {
            case "1": {
                searchLeadIdsString = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
            }
            break;
            case "2": {
                searchLeadIdsString = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                taskId = getIntent().getStringExtra("taskId");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
                isEdit = true;

                bi.tvStartTime.setEnabled(false);
                bi.atvRecur.setEnabled(false);
                bi.tvStartTime.setClickable(false);
                bi.atvRecur.setClickable(false);
                bi.tvStartTime.setFocusableInTouchMode(false);
                bi.atvRecur.setFocusableInTouchMode(false);
                // hit api for task detail

                int whichTasks =getIntent().getIntExtra("whichTasks",1);
                switch (whichTasks){

                    case 1:
                        presenter.getTaskDetail(taskId);
                        break;
                    case 3:
                        presenter.getFutureTaskDetail(taskId);
                        break;
                }
            }
            break;
            case "3": {
                bi.tvName.setEnabled(true);
                isEdit = true;
                leadId = "";
                taskId = getIntent().getStringExtra("taskId");

                bi.tvStartTime.setEnabled(false);
                bi.atvRecur.setEnabled(false);
                bi.tvStartTime.setClickable(false);
                bi.atvRecur.setClickable(false);
                bi.tvStartTime.setFocusableInTouchMode(false);
                bi.atvRecur.setFocusableInTouchMode(false);
                int whichTasks =getIntent().getIntExtra("whichTasks",1);
                switch (whichTasks){
                    case 1:
                        presenter.getTaskDetail(taskId);
                        break;
                    case 3:
                        presenter.getFutureTaskDetail(taskId);
                        break;
                }
            }
            break;
            case "4": {
                bi.tvName.setEnabled(true);
                leadId = "";
                isEdit = true;
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


        if (from.equals("1") || from.equals("4")) {

            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
            startDate = dateFormatter2.format(newCalendar.getTime());
            bi.tvStartDate.setText(dateFormatter2.format(newCalendar.getTime()));

            startTime = GH.getInstance().formatter(mHour + ":" + mMinute + ":00", "HH:mm:ss", "HH:mm:ss");
            String time = GH.getInstance().formatter(mHour + ":" + mMinute + ":00", "hh:mm a", "HH:mm:ss");
            bi.tvStartTime.setText(time);
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

        if (from.equals("1") || from.equals("4")) {

            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
            TextView textView = child.findViewById(R.id.txtDynamic);
            textView.setText(String.valueOf(agentList.get(0).agentName));
            bi.lnAgent.addView(child);
            selectedIdsList.add(agentList.get(0).agentID);
            if (agentIdsString.equals("")) {
                agentIdsString = agentList.get(0).encryptedAgentID;
            } else {
                agentIdsString = agentIdsString + "," + agentList.get(0).encryptedAgentID;
            }

        }
    }

    @Override
    public void updateUIListForReminders(AddAppointmentModel response) {

        arrayListReminderText = new ArrayList<>();
        arrayListReminderValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListReminderText.add(response.getData().get(i).getText());
            arrayListReminderValue.add(response.getData().get(i).getValue());
            hashMapReminder.put(Integer.valueOf(response.getData().get(i).getValue()), response.getData().get(i).getText());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListReminderText);
        bi.atvReminder.setAdapter(arrayAdapter);

        bi.atvReminder.setOnItemClickListener((parent, view, position, id) -> {

            presenter.getVia();
            reminder = arrayListReminderValue.get(position);
            if (bi.atvReminder.getText().toString().contains("None")) {
                bi.atvVia.setVisibility(View.GONE);
                bi.lblVia.setVisibility(View.GONE);
            } else {
                bi.atvVia.setVisibility(View.VISIBLE);
                bi.lblVia.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void updateUIListForVia(AddAppointmentModel response) {

        arrayListViaText = new ArrayList<>();
        arrayListViaValue = new ArrayList<>();

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
    public void updateUIListForType(AddAppointmentModel response) {

        ArrayList<String> arrayListViaText = new ArrayList<>();
        ArrayList<String> arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getText());
            arrayListViaValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaText);
        bi.atvType.setAdapter(arrayAdapter);
        bi.atvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = arrayListViaValue.get(position);
            }
        });

        bi.atvType.setText(arrayListViaText.get(0),false);
        type = arrayListViaValue.get(0);
    }

    @Override
    public void updateUIListForReoccur(AddAppointmentModel response) {

        ArrayList<String> arrayListViaText = new ArrayList<>();
        ArrayList<String> arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getId());
            arrayListViaValue.add(response.getData().get(i).getType());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaValue);
        bi.atvRecur.setAdapter(arrayAdapter);


        bi.atvRecur.setOnItemClickListener((parent, view, position, id) -> {

            reoccur = arrayListViaText.get(position);
            if (position != 0) {
                bi.lnEndDate.setVisibility(View.VISIBLE);
            } else {
                bi.lnEndDate.setVisibility(View.GONE);
            }

        });
        checkIntent();
        if (from.equals("1") || from.equals("4")) {
            bi.atvRecur.setText(arrayListViaValue.get(0), false);
            reoccur = arrayListViaText.get(0);
            bi.lnEndDate.setVisibility(View.GONE);
        }
        calendarInstance();
    }

    @Override
    public void updateTaskDetail(GetTaskDetail response) {

        // data bind
        GetTaskDetail ft = response;
        retrieveData(ft);

    }

    private void retrieveData(GetTaskDetail taskDetail) {

        bi.atvNameTask.setText(taskDetail.data.name);
        bi.atvDescription.setText(taskDetail.data.description);
        bi.tvName.setText(taskDetail.data.firstName);
        bi.atvType.setText(taskDetail.data.type);
        bi.atvType.setText(taskDetail.data.type, false);
        bi.atvRecur.setText(taskDetail.data.recur, false);
        bi.atvAddProperty.setText(taskDetail.data.address);
        // api correction needed
    /*    bi.atvReminder.setText(arrayListReminderText.get(arrayListReminderValue.indexOf(String.valueOf(taskDetail.data.interval))), false);
        bi.atvVia.setText(arrayListViaText.get(arrayListViaValue.indexOf(taskDetail.data.viaReminder)), false);*/

        String sDateTime = addHour(taskDetail.data.startDate, 5);
        String eDateTime = addHour(taskDetail.data.endDate, 5);

        startDate = GH.getInstance().formatter(sDateTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy hh:mm:ss a");
        endDate = GH.getInstance().formatter(eDateTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy hh:mm:ss a");

        String sDate = GH.getInstance().formatter(sDateTime, "MM-dd-yyyy", "MM/dd/yyyy hh:mm:ss a");
        String eDate = GH.getInstance().formatter(eDateTime, "MM-dd-yyyy", "MM/dd/yyyy hh:mm:ss a");

        startTime = GH.getInstance().formatter(sDateTime, "hh:mm:ss", "MM/dd/yyyy hh:mm:ss a");
        endTime = GH.getInstance().formatter(eDateTime, "hh:mm:ss", "MM/dd/yyyy hh:mm:ss a");

        String sTime = GH.getInstance().formatter(sDateTime, "hh:mm a", "MM/dd/yyyy hh:mm:ss a");
        String eTime = GH.getInstance().formatter(eDateTime, "hh:mm a", "MM/dd/yyyy hh:mm:ss a");

        bi.tvStartDate.setText(sDate);
        bi.tvEndDate.setText(eDate);
        bi.tvStartTime.setText(sTime);
        bi.tvEndTime.setText(eTime);
        type = taskDetail.data.type;
        scheduleId = taskDetail.data.scheduleID;
        searchLeadIdsString = taskDetail.data.leadEncryptedId;
        reminder = String.valueOf(taskDetail.data.interval);
        via = taskDetail.data.viaReminder;

        bi.atvReminder.setText(hashMapReminder.get(Integer.valueOf(reminder)), false);
        if (Integer.valueOf(reminder) > 0) {
            bi.atvVia.setText(via, false);
            bi.lblVia.setVisibility(View.VISIBLE);
            bi.atvVia.setVisibility(View.VISIBLE);
        }


        if (taskDetail.data.agents.size() != 0) {

            if (bi.lnAgent.getChildCount() > 0) {
                bi.lnAgent.removeAllViews();
            }
            selectedIdsList = new ArrayList<>();

            for (GetTaskDetail.Data.Agent name : taskDetail.data.agents) {

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(String.valueOf(name.agentName));
                bi.lnAgent.addView(child);
                selectedIdsList.add(name.agentID);
                if (agentIdsString.equals("")) {
                    agentIdsString = name.agentIDEncrypted;
                } else {
                    agentIdsString = agentIdsString + "," + name.agentIDEncrypted;
                }
            }
        }
    }

    private String addHour(String myTime, int number) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, number);
            String newTime = df.format(cal.getTime());
            return newTime;
        } catch (ParseException e) {
            System.out.println(" Parsing Exception");
        }
        return null;

    }

    private void callAddTask() {

        String id = "1";
        String agentIds = agentIdsString;
        String leadStringID = searchLeadIdsString != null ? searchLeadIdsString : "";
        String isAssignNow = bi.tvName.getText().toString().equals("") ? "false" : "true";
        String monthType = "";
        String scheduleID = "0";
        String name = bi.atvNameTask.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";
        int scheduleRecurID = !reoccur.equals("") ? Integer.valueOf(reoccur) : 0;
        String type = this.type;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String sDate = GH.getInstance().formatter(startDate + " " + startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "MM-dd-yyyy HH:mm:ss");
        String eDate = GH.getInstance().formatter(endDate + " " + endTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "MM-dd-yyyy HH:mm:ss");

        String datedFrom = sDate;
        String datedto = eDate;
        String recurDay = "";
        String recureWeek = "";
        String noOfWeek = "";
        String dayOfWeek = "";
        String dayOfMonth = "";
        String weekNo = "";
        String monthOfYear = "";
        String nextRun = "";
        boolean isEndDate = false;//bi.cbEndDate.isChecked() ? false : true;
        String reminderDate = "";
        int interval = !reminder.equals("") ? Integer.valueOf(reminder) : 0;
        String isSend = "";
        String viaReminder = via;
        String propertyId = "";
        String propertyAddress = bi.atvAddProperty.getText().toString() + "";

        if (isValidate()) {

            JSONObject obj = new JSONObject();

            try {
                obj.put("id", id);
                obj.put("agentIds", agentIds);
                obj.put("leadIds", leadStringID);
                obj.put("isAssignNow", isAssignNow);
                obj.put("monthType", monthType);
                obj.put("scheduleID", scheduleID);
                obj.put("name", name);
                obj.put("desc", desc);
                obj.put("scheduleRecurID", scheduleRecurID);
                obj.put("type", type);
                obj.put("startDate", datedFrom);
                obj.put("endDate", datedto);
                obj.put("recurDay", recurDay);
                obj.put("recureWeek", recureWeek);
                obj.put("noOfWeek", noOfWeek);
                obj.put("dayOfWeek", dayOfWeek);
                obj.put("dayOfMonth", dayOfMonth);
                obj.put("weekNo", weekNo);
                obj.put("monthOfYear", monthOfYear);
                obj.put("nextRun", nextRun);
                obj.put("isEndDate", isEndDate);
                obj.put("reminderDate", reminderDate);
                obj.put("interval", interval);
                obj.put("isSend", isSend);
                obj.put("viaReminder", viaReminder);
                obj.put("propertyId", propertyId);
                obj.put("propertyAddress", propertyAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonObjectString = obj.toString();
            Log.d("json", jsonObjectString);

            presenter.addTask(jsonObjectString);

            /*presenter.addTask(id, agentIds, leadStringID, isAssignNow, monthType, scheduleID, name, desc, scheduleRecurID, type, datedFrom, datedto, recurDay, recureWeek, noOfWeek,
                    dayOfWeek, dayOfMonth, weekNo, monthOfYear, nextRun, isEndDate, reminderDate, interval, isSend, viaReminder, propertyId, propertyAddress);*/
        }
    }

    private void callUpdateTask() {

        String id = "1";
        String agentIds = agentIdsString;
        String leadStringID = searchLeadIdsString != null ? searchLeadIdsString : "";
        String isAssignNow = bi.tvName.getText().toString().equals("") ? "false" : "true";
        String monthType = "";
        String scheduleID = scheduleId;
        String name = bi.atvNameTask.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";
        int scheduleRecurID = !reoccur.equals("") ? Integer.valueOf(reoccur) : 0;
        String type = this.type;
        String datedFrom = startDate;
        String datedto = endDate;
        String recurDay = "";
        String recureWeek = "";
        String noOfWeek = "";
        String dayOfWeek = "";
        String dayOfMonth = "";
        String weekNo = "";
        String monthOfYear = "";
        String nextRun = "";
        boolean isEndDate = false;//bi.cbEndDate.isChecked() ? false : true;
        String reminderDate = "";
        int interval = !reminder.equals("") ? Integer.valueOf(reminder) : 0;
        String isSend = "";
        String viaReminder = via;
        String propertyId = "";
        String propertyAddress = bi.atvAddProperty.getText().toString() + "";

        if (isValidate()) {

            JSONObject obj = new JSONObject();

            try {
                obj.put("id", id);
                obj.put("agentIds", agentIds);
                obj.put("leadIds", leadStringID);
                obj.put("isAssignNow", isAssignNow);
                obj.put("monthType", monthType);
                obj.put("scheduleID", scheduleID);
                obj.put("name", name);
                obj.put("desc", desc);
                obj.put("scheduleRecurID", scheduleRecurID);
                obj.put("type", type);
                obj.put("startDate", datedFrom);
                obj.put("endDate", datedto);
                obj.put("recurDay", recurDay);
                obj.put("recureWeek", recureWeek);
                obj.put("noOfWeek", noOfWeek);
                obj.put("dayOfWeek", dayOfWeek);
                obj.put("dayOfMonth", dayOfMonth);
                obj.put("weekNo", weekNo);
                obj.put("monthOfYear", monthOfYear);
                obj.put("nextRun", nextRun);
                obj.put("isEndDate", isEndDate);
                obj.put("reminderDate", reminderDate);
                obj.put("interval", interval);
                obj.put("isSend", isSend);
                obj.put("viaReminder", viaReminder);
                obj.put("propertyId", propertyId);
                obj.put("propertyAddress", propertyAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonObjectString = obj.toString();
            Log.d("json", jsonObjectString);

            presenter.updateTask(id, agentIds, leadStringID, isAssignNow, monthType, scheduleID, name, desc, scheduleRecurID, type, datedFrom, datedto, recurDay, recureWeek, noOfWeek,
                    dayOfWeek, dayOfMonth, weekNo, monthOfYear, nextRun, isEndDate, reminderDate, interval, isSend, viaReminder, propertyId, propertyAddress);
        }
    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.atvNameTask)) {
            ToastUtils.showToast(context, "Please enter task name");
            bi.atvNameTask.requestFocus();
            return false;
        }

        if (Methods.isEmpty(bi.atvType)) {
            ToastUtils.showToast(context, "Please select type");
            bi.atvType.requestFocus();
            return false;
        }

        if (agentIdsString.equalsIgnoreCase("")) {
            ToastUtils.showToast(context, "Please assign agent");
            bi.tvAssignTo.requestFocus();
            return false;
        }

        if (Methods.isEmpty(bi.atvRecur)) {
            ToastUtils.showToast(context, "Please select recur");
            bi.atvRecur.requestFocus();
            return false;
        }

        if (Methods.isEmpty(bi.tvStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_date);
            bi.tvStartTime.requestFocus();
            return false;
        }

        if (!reoccur.equals("1") && !bi.cbEndDate.isChecked() && Methods.isEmpty(bi.tvEndDate)) {
            ToastUtils.showToast(context, R.string.error_end_date);
            bi.tvName.requestFocus();
            return false;
        }
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

        if (!bi.cbEndDate.isChecked()) {
            Date date1 = null, date2 = null, time1 = null, time2 = null, currentTime = null;
            String sDate =GH.getInstance().formatter(startDate + " " + startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "MM-dd-yyyy HH:mm:ss");

            String eDate =GH.getInstance().formatter(endDate + " " + endTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "MM-dd-yyyy HH:mm:ss");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                date1 = sdf.parse(sDate);
                date2 = sdf.parse(eDate);

                if (sDate.compareTo(eDate) <= 0) {

                } else {
                    ToastUtils.showToast(context, "Start date must be lesser than end date");
                    bi.tvEndDate.requestFocus();
                    return false;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!reoccur.equals("1") && endDate.compareTo(startDate) == 0) {
                try {
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    currentTime = new SimpleDateFormat("HH:mm:ss").parse(time);
                    time1 = new SimpleDateFormat("HH:mm:ss").parse(sDate);
                    time2 = new SimpleDateFormat("HH:mm:ss").parse(eDate);

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
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!bi.atvReminder.getText().toString().equalsIgnoreCase("") && !bi.atvReminder.getText().toString().equalsIgnoreCase("None")) {
            if (via.equalsIgnoreCase("")) {
                ToastUtils.showToast(context, R.string.error_via);
                bi.atvVia.requestFocus();
                return false;
            }
        }

        if (!bi.atvReminder.getText().toString().equalsIgnoreCase("") &&
                !bi.atvReminder.getText().toString().equalsIgnoreCase("None")) {
            if (via != null && via.equalsIgnoreCase("")) {
                ToastUtils.showToast(context, R.string.error_via);
                bi.atvVia.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void type() {

        if (!isTypeClicked) {
            bi.atvType.showDropDown();
            isTypeClicked = true;
        } else {
            isTypeClicked = false;
            bi.atvType.dismissDropDown();
        }
    }

    private void recur() {

        if (!isRecurClicked) {
            bi.atvRecur.showDropDown();
            isRecurClicked = true;
        } else {
            isRecurClicked = false;
            bi.atvRecur.dismissDropDown();
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

    @Override
    public void onClick(View view) {

        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvName:
                clearFocus();
                showSearchDialog(context);
                break;
            case R.id.tvStartDate:
                clearFocus();
                if (bi.tvStartDate.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                showDateDialog(bi.tvStartDate, true);
                break;
            case R.id.tvEndDate:
                clearFocus();
                if (bi.tvEndDate.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                showDateDialog(bi.tvEndDate, false);
                break;
            case R.id.tvStartTime:
                clearFocus();
                if (bi.tvStartTime.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                showTimeDialog(bi.tvStartTime, true);
                break;
            case R.id.tvEndTime:
                clearFocus();
                if (bi.tvEndTime.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                showTimeDialog(bi.tvEndTime, false);
                break;
            case R.id.tvAssignTo:
                clearFocus();
                showAgentDialog();
                break;
            case R.id.btnSave:
                if (taskId.equals("")) {
                    callAddTask();
                } else {
                    callUpdateTask();
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.atvReminder:
                clearFocus();
                reminder();
                break;
            case R.id.atvVia:
                clearFocus();
                via();
                break;
            case R.id.atvType:
                clearFocus();
                type();
                break;
            case R.id.atvRecur:
                clearFocus();
                recur();
                break;
        }
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

    private void clearFocus() {

        bi.atvAddProperty.clearFocus();
        bi.atvDescription.clearFocus();
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

    private void showDateDialog(TextView textView, boolean isStart) {

        final Calendar newCalendar = Calendar.getInstance();

        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {

                year = years;
                month = monthOfYear;
                day = dayOfMonth;
                newCalendar.set(year, month, day);

                if (isStart) {
                    startDate = dateFormatter2.format(newCalendar.getTime());
                } else {
                    endDate = dateFormatter2.format(newCalendar.getTime());
                }

                textView.setText(dateFormatter2.format(newCalendar.getTime()));

            }

        }, year, month, day);
        newCalendar.set(year, month, day);
        StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        StartTime.show();
    }

    private void showSearchLeadDialog(Dialog dialog) {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Leads") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                //.setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedIdsListLead = new ArrayList<>();
                        selectedIdsListLead = selectedIds;
                        if (bi.lnLead.getChildCount() > 0) {
                            bi.lnLead.removeAllViews();
                        }
                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnLead.addView(child);
                        }
                        dialog.dismiss();
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                        searchLeadIdsString = "";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (searchLeadIdsString.equals("")) {
                                    searchLeadIdsString = i;
                                } else {
                                    searchLeadIdsString = searchLeadIdsString + "," + i;
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

        if (selectedIdsListLead.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedIdsListLead);
            multiSelectDialog.multiSelectList(searchListItemsLead);
        } else {
            multiSelectDialog.multiSelectList(searchListItemsLead);
        }

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialogLead");

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equals("Success")) {
            ToastUtils.showToast(context, response.body().getMessage());
            finish();
        }
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

        GH.getInstance().ShowProgressDialog(AddTaskActivity.this);
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
                if (response.isSuccessful()) {

                    GetLeadTitlesModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        nameList = new ArrayList<>();
                        nameList.addAll(getAppointmentsModel.data);
                        searchListItemsLead = new ArrayList<>();
                        //setRecyclerSearch(dialog);
                        for (GetLeadTitlesModel.Data data : nameList) {
                            searchListItemsLead.add(new MultiSelectModel(data.decryptedLeadID, data.name, data.leadID));
                        }

                        showSearchLeadDialog(dialog);

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
}