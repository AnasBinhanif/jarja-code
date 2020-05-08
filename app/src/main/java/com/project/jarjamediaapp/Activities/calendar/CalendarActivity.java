package com.project.jarjamediaapp.Activities.calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_calendar_task.AddCalendarTaskActivity;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarDetailActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeCalendarAppointmentRecyclerAdapter;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EventDecorator;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class CalendarActivity extends BaseActivity implements View.OnClickListener, CalendarContract.View {

    ActivityCalendarBinding bi;
    Context context = CalendarActivity.this;
    CalendarPresenter presenter;
    Calendar calendar;
    int yearSelected, monthSelected, daySelected;
    SwipeCalendarAppointmentRecyclerAdapter swipeCalendarAppointmentRecyclerAdapter;
    List<CalendarModel.Data> dataList;
    ArrayList<Integer> markedDates;
    ArrayList<CalendarLabel> markedDatesFormatter;
    ArrayList<CalendarModel.Data> dataArrayList;

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

    }

    private void showMonthYearPicker() {

        String month = GH.getInstance().formatter(String.valueOf(monthSelected), "m", "mm");
        String year = GH.getInstance().formatter(String.valueOf(yearSelected), "YYYY", "yyyy");
        presenter.getCalendarEvents(GH.getInstance().getCalendarAgentId(), month, year);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showMonthYearPicker();
    }

    @Override
    public void initViews() {

        //Set default values
        calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = (calendar.get(Calendar.MONTH) + 1);
        daySelected = calendar.get(Calendar.DAY_OF_MONTH);
        bi.calendarView.setSelectedDate(CalendarDay.from(yearSelected, (monthSelected - 1), daySelected));

        bi.calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                monthSelected = (date.getMonth() + 1);
                yearSelected = date.getYear();
                String month = GH.getInstance().formatter(String.valueOf(monthSelected), "m", "mm");
                String year = GH.getInstance().formatter(String.valueOf(yearSelected), "YYYY", "yyyy");
                presenter.getCalendarEvents(GH.getInstance().getCalendarAgentId(), month, year);

            }
        });
        bi.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                filterDateData(date.getDay());

            }
        });

    }

    private void filterDateData(int day) {

        dataArrayList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            int _day = markedDates.get(i);
            if (day == _day) {
                dataArrayList.add(dataList.get(i));
            }
        }
        if (dataArrayList.size() > 0) {
            context.startActivity(new Intent(context, CalendarDetailActivity.class).putExtra("listData", (Serializable) dataArrayList));
        } else {
            ToastUtils.showToastLong(context, "No data found");
        }

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        if (message.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            bi.rvEvents.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIonError(String error) {

        bi.rvEvents.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateUIonFailure() {

        bi.rvEvents.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(CalendarActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
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

            showDialogForAppointment(context);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void showDialogForAppointment(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_select_type_dialog);

        Button btnEvent = dialog.findViewById(R.id.btnEvent);
        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Map<String, String> map = new HashMap<>();
                map.put("from", "3");
                switchActivityWithIntentString(AddAppointmentActivity.class, (HashMap<String, String>) map);

            }
        });

        Button btnTask = dialog.findViewById(R.id.btnTask);
        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switchActivity(AddCalendarTaskActivity.class);
            }
        });

        dialog.show();

    }

    @Override
    public void updateUIList(CalendarModel response) {

        if (response.data.size() > 0) {

            dataList = response.getData();
            swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarActivity.this, response.getData());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.rvEvents.getContext(), 1);
            bi.rvEvents.setLayoutManager(mLayoutManager);
            bi.rvEvents.setItemAnimator(new DefaultItemAnimator());
            bi.rvEvents.addItemDecoration(dividerItemDecoration);
            bi.rvEvents.setAdapter(swipeCalendarAppointmentRecyclerAdapter);
            bi.rvEvents.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
            try {
                getMarkedEvents();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            bi.rvEvents.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);

        }

    }

    private void getMarkedEvents() {

        markedDates = new ArrayList<>();
        markedDatesFormatter = new ArrayList<>();

        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                String formatString = dataList.get(i).getStart();
                markedDates.add(Integer.valueOf(GH.getInstance().formatter(formatString, "d", "yyyy-MM-dd'T'HH:mm:ss")));

            }
        }

        for (int i = 0; i < markedDates.size(); i++) {

            int count = Collections.frequency(markedDates, markedDates.get(i));
            if (count > 0) {
                markedDatesFormatter.add(new CalendarLabel(markedDates.get(i), count));
            }

        }

        LinkedHashSet<CalendarLabel> hashSet = new LinkedHashSet<CalendarLabel>(markedDatesFormatter);
        ArrayList<CalendarLabel> listWithoutDuplicates = new ArrayList<>(hashSet);
        markedDatesFormatter.clear();
        markedDatesFormatter.addAll(listWithoutDuplicates);

        List<CalendarDay> list = new ArrayList<CalendarDay>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < markedDatesFormatter.size(); i++) {

            calendar.set(yearSelected, (monthSelected - 1), markedDatesFormatter.get(i).getDateFormat());
            CalendarDay calendarDay = CalendarDay.from(calendar);
            list.add(calendarDay);
        }

        bi.calendarView.invalidateDecorators();
        bi.calendarView.removeDecorators();
        bi.calendarView.addDecorators(new EventDecorator(R.color.colorPrimary, list));

    }

}