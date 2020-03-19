package com.project.jarjamediaapp.Activities.add_calendar_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddCalendarTaskBinding;

import java.util.Calendar;

import retrofit2.Response;

public class AddCalendarTaskActivity extends BaseActivity implements AddCalendarTaskContract.View, View.OnClickListener {

    ActivityAddCalendarTaskBinding bi;
    Context context = AddCalendarTaskActivity.this;
    AddCalendarTaskPresenter presenter;
    String startDate, startTime, title, description;
    boolean allDay, markComplete;
    boolean isEdit;
    String calendarId = "",encryptedLeadAppointmentId="";

    int month, year, day, hour, minute;
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
        // receive data here from intent
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);

        newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);
        hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        minute = newCalendar.get(Calendar.MINUTE);

    }

    @Override
    public void onClick(View view) {

        super.onClick(view);

        switch (view.getId()) {
            case R.id.tvStartDate:
                showDateDialog(bi.tvStartDate);
                break;
            case R.id.tvStartTime:
                showTimeDialog(bi.tvStartTime);
                break;
            case R.id.btnSave:
                callAddCalendarTask();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.cbAllDay:
                allDay();
                break;
        }
    }

    private void allDay() {

        if (bi.cbAllDay.isChecked()) {
            startTime = "00:00:00.000Z";
            bi.tvStartTime.setVisibility(View.GONE);
        } else {
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
                hour = selectedHour;
                minute = selectedMinute;
                time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");
                textView.setText(time);
                startTime = selectedHour + ":" + selectedMinute + ":" + "00.000Z";


            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void callAddCalendarTask() {

        title = bi.atvEventTitle.getText().toString();
        description = bi.atvDescription.getText().toString() + "";
        allDay = bi.cbAllDay.isChecked() ? true : false;
        markComplete = bi.cbAllDay.isChecked() ? true : false;
        // encryptedLeadAppointmentId

        startDate = GH.getInstance().formatApiDateTime(startDate + "T" + startTime);

        if (isValidate()) {
            if (isEdit) {
                presenter.updateCalendarTask(title, description, startDate, allDay, markComplete, calendarId,encryptedLeadAppointmentId);
            } else {
                presenter.addCalendarTask(title, description, startDate, allDay, markComplete);
            }
        }

    }

    private boolean isValidate() {

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

        if (!bi.cbAllDay.isChecked()) {
            if (Methods.isEmpty(bi.tvStartTime)) {
                ToastUtils.showToast(context, R.string.error_start_time);
                bi.tvStartTime.requestFocus();
                return false;
            }

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