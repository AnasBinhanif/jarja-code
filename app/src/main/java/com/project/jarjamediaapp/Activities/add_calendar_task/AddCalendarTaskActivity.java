package com.project.jarjamediaapp.Activities.add_calendar_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Activities.calendarDetail.CalendarDetailModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddCalendarTaskBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Response;

public class AddCalendarTaskActivity extends BaseActivity implements AddCalendarTaskContract.View, View.OnClickListener {

    ActivityAddCalendarTaskBinding bi;
    Context context = AddCalendarTaskActivity.this;
    AddCalendarTaskPresenter presenter;
    String startDate = "", startTime = "", title = "", description = "";
    boolean allDay, markComplete;
    boolean isEdit;
    String calendarId = "";
    CalendarDetailModel.Data calendarDetailModel;

    int month, year, day, mHour, mMinute;
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_calendar_task);
        presenter = new AddCalendarTaskPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_task), true);

    }

    @Override
    public void initViews() {

        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            calendarId = getIntent().getStringExtra("calendarId");
            calendarDetailModel = getIntent().getParcelableExtra("modelData");
            bi.atvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
            bi.atvDescription.setText(calendarDetailModel.getDescription() != null ? calendarDetailModel.getDescription() : "");

            startDate = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
            startTime = GH.getInstance().formatter(calendarDetailModel.getEndTime(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");
            bi.tvStartDate.setText(GH.getInstance().formatDate(calendarDetailModel.getStartTime()) != null ? GH.getInstance().formatDate(calendarDetailModel.getStartTime()) : "");
            bi.tvStartTime.setText(GH.getInstance().formatTime(calendarDetailModel.getEndTime()) != null ? GH.getInstance().formatTime(calendarDetailModel.getEndTime()) : "");
            bi.cbAllDay.setChecked(calendarDetailModel.isAllDay != null ? calendarDetailModel.isAllDay : false);
            if (bi.cbAllDay.isChecked()) {
                bi.tvStartTime.setVisibility(View.GONE);
                bi.lblStartTime.setVisibility(View.GONE);
            } else {
                bi.tvStartTime.setVisibility(View.VISIBLE);
                bi.lblStartTime.setVisibility(View.VISIBLE);
            }
            //   bi.cbMarkComplete.setChecked(calendarDetailModel.isCompleted != null ? calendarDetailModel.isCompleted : false);

        } else {
            calendarId = "";
        }
        // receive data here from intent
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        super.onClick(view);

        switch (view.getId()) {
            case R.id.tvStartDate:
                GH.getInstance().hideKeyboard(context, AddCalendarTaskActivity.this);
                if (bi.tvStartDate.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                if (isEdit) {
                    calendarEditInstance(1);
                }
                showDateDialog(bi.tvStartDate);
                removeFocus();
                break;
            case R.id.tvStartTime:
                GH.getInstance().hideKeyboard(context, AddCalendarTaskActivity.this);
                if (bi.tvStartTime.getText().toString().equalsIgnoreCase("")) {
                    calendarInstance();
                }
                if (isEdit) {
                    calendarEditInstance(2);
                }
                showTimeDialog(bi.tvStartTime);
                removeFocus();
                break;
            case R.id.btnSave:
                callAddCalendarTask();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.cbAllDay:
                removeFocus();
                allDay();
                break;
        }
    }

    private void removeFocus() {

        bi.atvEventTitle.clearFocus();
        bi.atvDescription.clearFocus();

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
        // 2 for start time

        try {
            newCalendar = Calendar.getInstance();
            switch (id) {
                case 1: {
                    String[] formattedDate = startDate.split("-");
                    year = Integer.parseInt(formattedDate[0]);
                    month = Integer.parseInt(formattedDate[1]);
                    day = Integer.parseInt(formattedDate[2]);
                    month = month - 1;
                    newCalendar.set(year, month, day);
                }
                break;
                case 3: {
                    String[] formattedTime = startTime.split(":");
                    mHour = Integer.parseInt(formattedTime[0]);
                    mMinute = Integer.parseInt(formattedTime[1]);

                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void allDay() {

        if (bi.cbAllDay.isChecked()) {
            bi.tvStartTime.setVisibility(View.GONE);
            bi.lblStartTime.setVisibility(View.GONE);
        } else {
            bi.tvStartTime.setVisibility(View.VISIBLE);
            bi.lblStartTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equalsIgnoreCase("Success")) {
            ToastUtils.showToast(context, response.body().getMessage() != null ? response.body().getMessage() : "");
            finish();
        }
    }

    private void showDateDialog(TextView textView) {

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {

                year = years;
                month = monthOfYear;
                day = dayOfMonth;
                newCalendar.set(year, month, day);

                int month = monthOfYear + 1;
                startDate = year + "-" + month + "-" + dayOfMonth;
                textView.setText(dayOfMonth + "-" + month + "-" + year);

            }
        }, year, month, day);
        StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        StartTime.show();

    }

    private void showTimeDialog(TextView textView) {

        TimePickerDialog mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = "";
                mHour = selectedHour;
                mMinute = selectedMinute;
                time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");
                textView.setText(time);
                startTime = selectedHour + ":" + selectedMinute + ":" + "00.000Z";


            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void callAddCalendarTask() {

        title = bi.atvEventTitle.getText().toString();
        description = bi.atvDescription.getText().toString() + "";
        allDay = bi.cbAllDay.isChecked() ? true : false;
        markComplete = bi.cbAllDay.isChecked() ? true : false;

        if (bi.cbAllDay.isChecked() || startTime.equalsIgnoreCase("")) {

            startTime = new SimpleDateFormat("HH:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date());

        }
        startDate = GH.getInstance().formatApiDateTime(startDate + "T" + startTime);

        Integer leadAppointmentID = 0;
        String isAppointmentAttend = "false";
        String datedFrom = startDate;
        String datedTo = startDate;
        boolean isGmailAppActive = true;
        String calendarType = "Task";
        String gmailCalendarId = this.calendarId;

        JSONObject obj = new JSONObject();

        try {
            obj.put("isCompleted", markComplete);
            obj.put("datedTo", datedTo);
            obj.put("eventTitle", title);
            obj.put("isAppointmentAttend", isAppointmentAttend);
            obj.put("leadAppoinmentID", leadAppointmentID);
            obj.put("datedFrom", datedFrom);
            obj.put("isAllDay", allDay);
            obj.put("desc", description);
            obj.put("gmailCalenderId", gmailCalendarId);
            obj.put("isGmailApptActive", isGmailAppActive);
            obj.put("calendarType", calendarType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonObjectString = obj.toString();
        Log.d("json", jsonObjectString);

        if (isValidate()) {

            presenter.addUpdateCalendarAppointmentViaTask(leadAppointmentID, isAppointmentAttend, datedFrom, datedTo,
                    allDay, markComplete, title, description, isGmailAppActive, calendarType, gmailCalendarId);

        }

    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.tvStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_date);
            bi.tvStartTime.requestFocus();
            return false;
        }

        return true;
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
        GH.getInstance().ShowProgressDialog(AddCalendarTaskActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

}