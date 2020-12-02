package com.project.jarjamediaapp.Activities.add_calendar_task;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarTaskDetailModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddCalendarTaskBinding;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    CalendarTaskDetailModel.Data calendarDetailModel;
    int month, year, day, mHour, mMinute;
    Calendar newCalendar;
    boolean isEdited = false;
    TextWatcher textWatcher;

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

            textWatcher = new TextWatcher() {
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
            bi.atvDescription.addTextChangedListener(textWatcher);
            bi.atvEventTitle.addTextChangedListener(textWatcher);
            bi.tvStartDate.addTextChangedListener(textWatcher);
            bi.tvStartTime.addTextChangedListener(textWatcher);

            calendarId = getIntent().getStringExtra("calendarId");
            calendarDetailModel = getIntent().getParcelableExtra("modelData");
            bi.atvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
            bi.atvDescription.setText(calendarDetailModel.getDescription() != null ? calendarDetailModel.getDescription() : "");

            // for assCalenderTaskActivity
            startDate = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "yyyy-MM-dd", "MM/dd/yyyy HH:mm:ss aaa");
            startTime = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "HH:mm:ss", "MM/dd/yyyy HH:mm:ss aaa");


            // copy from addtaskActivity
        /*    startDate = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy hh:mm:ss a");
            startTime = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "HH:mm:ss", "MM/dd/yyyy hh:mm:ss a");
*/

            // from addAppointmentActivity
//            startDate = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
//            startTime = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");

            String[] startTimeArray = calendarDetailModel.getStartTime().split(" ");

            if (calendarDetailModel.isAllDay != null && !calendarDetailModel.isAllDay) {
                //   bi.tvStartTime.setText(startTime);

                bi.tvStartTime.setText(GH.getInstance().formatter(startTimeArray[1], "hh:mm", "HH:mm:ss") + " " + startTimeArray[2]);

            } else {

                bi.tvStartTime.setVisibility(View.GONE);
            }

            // just get date not timw
//            String startDateFormat = GH.getInstance().formatter(calendarDetailModel.getStartTime(), "yyyy-MM-dd", "MM/dd/yyyy HH:mm:ss aaa");
//            String dt = !startDate.equals("") ? GH.getInstance().formatter(startDateFormat, "MM/dd/yyyy", "yyyy-MM-dd") : "";

            //commenting above two lines as these were unnecessary
            String dt = !startDate.equals("") ? GH.getInstance().formatter(calendarDetailModel.getStartTime(),"MM/dd/yyyy","MM/dd/yyyy HH:mm:ss aaa") :"";


            bi.tvStartDate.setText(dt);

            bi.cbAllDay.setChecked(calendarDetailModel.isAllDay != null ? calendarDetailModel.isAllDay : false);

            if (bi.cbAllDay.isChecked()) {
                bi.tvStartTime.setVisibility(View.GONE);
                bi.lblStartTime.setVisibility(View.GONE);
            } else {
                bi.tvStartTime.setVisibility(View.VISIBLE);
                bi.lblStartTime.setVisibility(View.VISIBLE);
            }
            if (calendarDetailModel.isComplete != null) {
                bi.cbMarkComplete.setChecked(calendarDetailModel.isComplete);
            }

        } else {
            calendarId = "";
        }
        // receive data here from intent
        bi.tvStartDate.setOnClickListener(this);
        bi.tvStartTime.setOnClickListener(this);
        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.cbAllDay.setOnClickListener(this);
        bi.cbMarkComplete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Gson gson = new Gson();
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
                //showDateDialog(bi.tvStartDate);
                showSpinnerDateDialog(bi.tvStartDate);
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

                if (isEdit) {

                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapCalenderString = GH.getInstance().getUserPermissonCalender();
                    java.lang.reflect.Type typeCalender = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapCalender = gson.fromJson(storedHashMapCalenderString, typeCalender);

                    if (mapCalender.get("Edit Calendar")) {

                        callAddCalendarTask();

                    } else {

                        ToastUtils.showToast(context, getString(R.string.calender_EditCalender));
                    }
                } else {

                    callAddCalendarTask();
                }


                break;

            case R.id.btnCancel:
               // finish();
                //adding below block of if and else to replace by line of code --- > akshay
                if (isChangesDone()) {
                    GH.getInstance().discardChangesDialog(context);
                } else {
                    finish();
                }
                break;
            case R.id.cbAllDay:
                removeFocus();
                allDay();
                isEdited = true;
                break;
            case R.id.cbMarkComplete:
                isEdited = true;
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

    private void showSpinnerDateDialog(TextView textView) {

        final Calendar newCalendar = Calendar.getInstance();
        new SpinnerDatePickerDialogBuilder().context(context)
                .callback(new com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                        year = years;
                        month = monthOfYear;
                        day = dayOfMonth;
                        newCalendar.set(year, month, day);

                        int month = monthOfYear + 1;
                        startDate = year + "-" + month + "-" + dayOfMonth;
                        textView.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                })
                .showTitle(true)
                .defaultDate(year, month, day)
                .minDate(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
                .build()
                .show();

    }

    private void showTimeDialog(TextView textView) {

        TimePickerDialog mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = "";
                mHour = selectedHour;
                mMinute = selectedMinute;

                startTime = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "HH:mm:ss", "HH:mm:ss");
                time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");

                // time = GH.getInstance().formatter(selectedHour + ":" + selectedMinute + ":00", "hh:mm a", "HH:mm:ss");
                textView.setText(time);
                //  startTime = selectedHour + ":" + selectedMinute + ":" + "00.000Z";


            }
        }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void callAddCalendarTask() {

        title = bi.atvEventTitle.getText().toString();
        description = bi.atvDescription.getText().toString() + "";
        allDay = bi.cbAllDay.isChecked() ? true : false;
        markComplete = bi.cbMarkComplete.isChecked() ? true : false;

        if (bi.cbAllDay.isChecked() || startTime.equalsIgnoreCase("")) {

            startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            //  startTime = new SimpleDateFormat("hh:mm:ss.SSS'Z'", Locale.getDefault()).format(new Date());
            //  startDate = GH.getInstance().formatter(startDate + " " + startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");


        }/*else {

         //   startDate = GH.getInstance().formatApiDateTime(startDate + "T" + startTime);
          //  startDate = GH.getInstance().formatter(startDate + " " + startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd HH:mm:ss");

        }*/
        startDate = GH.getInstance().formatDateTime(startDate + "T" + startTime);


        Integer leadAppointmentID = 0;
        boolean isAppointmentAttend = false;
        String datedFrom = startDate;
        String datedTo = "";
        boolean isGmailAppActive = true;
        String calendarType = "Task";
        String gmailCalendarId = this.calendarId;

        JSONObject obj = new JSONObject();

        try {

            if (isEdit) {

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

            } else {

                obj.put("isCompleted", markComplete);
                obj.put("datedTo", datedTo);
                obj.put("eventTitle", title);
                obj.put("isAppointmentAttend", isAppointmentAttend);
                obj.put("leadAppoinmentID", leadAppointmentID);
                obj.put("datedFrom", datedFrom);
                obj.put("isAllDay", allDay);
                obj.put("desc", description);
                obj.put("isGmailApptActive", isGmailAppActive);
                obj.put("calendarType", calendarType);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonObjectString = obj.toString();
        Log.d("json", jsonObjectString);

        if (isValidate()) {

            presenter.addUpdateCalendarAppointmentViaTask(jsonObjectString, isEdit);

        }

    }

    private boolean isValidate() {

        if (Methods.isEmpty(bi.tvStartDate)) {
            ToastUtils.showToast(context, R.string.error_start_date);
            bi.tvStartDate.requestFocus();
            return false;
        }
        if (!bi.cbAllDay.isChecked()) {
            //commenting below if condition as star time is no longer required
            /*if (Methods.isEmpty(bi.tvStartTime)) {
                ToastUtils.showToast(context, R.string.error_start_time);
                bi.tvStartTime.requestFocus();
                return false;
            }*/

        }

        return true;
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
        GH.getInstance().ShowProgressDialog(AddCalendarTaskActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
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
        if (isChangesDone()) {
            GH.getInstance().discardChangesDialog(context);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isChangesDone() {

        if (isEdit) {
            if (!Methods.isEmpty(bi.atvEventTitle))
                return true;
            if (!Methods.isEmpty(bi.atvDescription))
                return true;
            if (!Methods.isEmpty(bi.tvStartDate))
                return true;
            if (!Methods.isEmpty(bi.tvStartTime))
                return true;
            if (bi.cbAllDay.isChecked())
                return true;
            if (bi.cbMarkComplete.isChecked())
                return true;
            return false;
        } else {
            if (isEdited) {
                return true;
            }
            return false;
        }
    }

}