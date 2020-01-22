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

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.customCalendar.interfaces.OnCalendarScrolledListener;
import com.project.jarjamediaapp.customCalendar.interfaces.OnDateSelectedListener;
import com.project.jarjamediaapp.customCalendar.objects.CalendarDate;
import com.project.jarjamediaapp.databinding.ActivityCalendarBinding;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Response;

public class CalendarActivity extends BaseActivity implements View.OnClickListener, CalendarContract.View, OnDateSelectedListener, OnCalendarScrolledListener {

    ActivityCalendarBinding bi;
    Context context = CalendarActivity.this;
    CalendarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        presenter = new CalendarPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.calendar), true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }

    }

    @Override
    public void initViews() {

        populateSpinnerData();
        bi.activityMainViewCustomCalendar.setOnDateSelectedListener(this);
        bi.activityMainViewCustomCalendar.setOnPageScrolled(this);
    }


    private void populateSpinnerData() {


        List<String> dataset = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.months_array)));
        bi.spinnerMonth.attachDataSource(dataset);
        bi.spinnerMonth.setBackground(getResources().getDrawable(R.drawable.bg_search));

        List<String> dataset2 = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.years_array)));
        bi.spinnerYear.attachDataSource(dataset2);
        bi.spinnerYear.setBackground(getResources().getDrawable(R.drawable.bg_search));
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
    public void onDateSelected(CalendarDate date) {

        //bi.activityMainTextDayOfMonth.setText(date.dayToString());
        //bi.activityMainTextDayOfWeek.setText(date.dayOfWeekToStringName());

        bi.txtMonth.setText(date.monthToStringName() + " " + date.yearToString());

    }

    @Override
    public void OnPageScrolled(ViewPager viewPager, int pos, String month) {

        bi.txtMonth.setText(month);

    }
}
