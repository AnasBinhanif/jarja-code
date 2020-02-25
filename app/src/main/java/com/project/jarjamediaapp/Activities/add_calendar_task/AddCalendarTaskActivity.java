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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;

public class AddCalendarTaskActivity extends BaseActivity implements AddCalendarTaskContract.View, View.OnClickListener {

    ActivityAddCalendarTaskBinding bi;
    Context context = AddCalendarTaskActivity.this;
    AddCalendarTaskPresenter presenter;
    String startDate, startTime, title, description, allDay, markComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_calendar_task);
        presenter = new AddCalendarTaskPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_task), true);

    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {


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
                break;
            case R.id.cbAllDay:
                allDay();
                break;
            case R.id.cbMarkComplete:
                //
                break;
        }
    }

    private void allDay() {
        if (bi.cbAllDay.isChecked()) {

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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                startDate = dateFormatter.format(newDate.getTime());

                textView.setText(dateFormatter2.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }

    private void showTimeDialog(TextView textView) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = "";
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:m:ss");
                startTime = selectedHour + ":" + selectedMinute + ":00";

                Date d = null;
                try {
                    d = dateFormat.parse(startTime);
                    time = DateFormat.getTimeInstance(DateFormat.SHORT).format(d);
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                textView.setText(time);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void callAddCalendarTask() {

        //  startDate, startTime
        title = bi.atvEventTitle.getText().toString();
        description = bi.atvDescription.getText().toString() + "";
        allDay = bi.cbAllDay.isChecked() ? "true" : "false";
        markComplete = bi.cbAllDay.isChecked() ? "true" : "false";

        if (isValidate())
            presenter.addCalendarTask(title, description, startDate, startTime, allDay, markComplete);

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
        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog(context);
    }

}