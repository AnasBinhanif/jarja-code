package com.project.jarjamediaapp.Activities.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.customCalendar.interfaces.OnCalendarScrolledListener;
import com.project.jarjamediaapp.databinding.ActivityCalendarBinding;

import java.util.Calendar;

import retrofit2.Response;

public class CalendarActivity extends BaseActivity implements View.OnClickListener, CalendarContract.View, OnCalendarScrolledListener {

    ActivityCalendarBinding bi;
    Context context = CalendarActivity.this;
    CalendarPresenter presenter;
    MonthYearPickerDialogFragment dialogFragment;
    Calendar calendar;
    int yearSelected;
    int monthSelected;
    String previewMonth = "", previewYear = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        presenter = new CalendarPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.calendar), true);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.tvMonthYear: {
                dialogFragment.show(getSupportFragmentManager(), null);
            }
            break;

        }

    }

    private void showMonthYearPicker() {

        //Set default values
        calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
        getMonth(monthSelected);
        bi.tvMonthYear.setText(previewMonth + " " + yearSelected);

        dialogFragment = MonthYearPickerDialogFragment.getInstance(monthSelected, yearSelected);

        dialogFragment.setOnDateSetListener((year, monthOfYear) -> {
            // do something
            monthSelected = monthOfYear;
            yearSelected = year;
            getMonth(monthOfYear);
            bi.tvMonthYear.setText(previewMonth + " " + year);
            bi.activityMainViewCustomCalendar.updateCalendarView(year, monthOfYear, 1);

        });

    }

    @Override
    public void initViews() {

        bi.tvMonthYear.setOnClickListener(this);
        bi.activityMainViewCustomCalendar.setOnPageScrolled(this);
        showMonthYearPicker();

    }

    public void showAddAppointDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_calendar_add_appoint_dialog);


        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected

            }

        });

        dialog.show();
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


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

        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            showAddAppointDialog(context);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void OnPageScrolled(ViewPager viewPager, int pos, String month) {

        bi.tvMonthYear.setText(month);

    }

    private void getMonth(int month) {

        switch (month) {
            case 0: {
                previewMonth = "January";
            }
            break;
            case 1: {
                previewMonth = "February";
            }
            break;
            case 2: {
                previewMonth = "March";
            }
            break;
            case 3: {
                previewMonth = "April";
            }
            break;
            case 4: {
                previewMonth = "May";
            }
            break;
            case 5: {
                previewMonth = "June";
            }
            break;
            case 6: {
                previewMonth = "July";
            }
            break;
            case 7: {
                previewMonth = "August";
            }
            break;
            case 8: {
                previewMonth = "September";
            }
            break;
            case 9: {
                previewMonth = "October";
            }
            break;
            case 10: {
                previewMonth = "November";
            }
            break;
            case 11: {
                previewMonth = "December";
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + month);

        }
    }

}
