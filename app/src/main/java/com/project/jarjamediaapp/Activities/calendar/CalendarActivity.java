package com.project.jarjamediaapp.Activities.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_calendar_task.AddCalendarTaskActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeCalendarAppointmentRecyclerAdapter;
import com.project.jarjamediaapp.Models.GmailCalender;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.EventDecorator;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.databinding.ActivityCalendarNewBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class CalendarActivity extends BaseActivity implements View.OnClickListener, CalendarContract.View {

    ActivityCalendarNewBinding bi;
    Context context = CalendarActivity.this;
    CalendarPresenter presenter;
    Calendar calendar;
    int yearSelected, MonthVisible, currentDate;
    SwipeCalendarAppointmentRecyclerAdapter swipeCalendarAppointmentRecyclerAdapter;
    List<CalendarModel.Data> dataList;
    List<CalendarModel.Data> currentDateList;
    ArrayList<Integer> markedDates;
    ArrayList<CalendarLabel> markedDatesFormatter;
    ArrayList<CalendarModel.Data> dataArrayList;
    int selectedDate = 0;
    int monthOfSelectedDate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_calendar_new);
        presenter = new CalendarPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.calendar), true);
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addInt(GH.KEYS.CALENDERUPDATELIST.name(), -1).save();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showMonthYearPicker();

    }

    @Override
    public void initViews() {

        bi.btnSync.setOnClickListener(this);
        //Set default values
        calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        MonthVisible = (calendar.get(Calendar.MONTH) + 1);
        currentDate = calendar.get(Calendar.DAY_OF_MONTH);

        bi.calendarView.setSelectedDate(CalendarDay.from(yearSelected, (MonthVisible - 1), currentDate));
        bi.calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                //bi.calendarView.clearSelection();
                //adding this line to clear selection when user changes month -- Akshay


                MonthVisible = (date.getMonth() + 1);
                yearSelected = date.getYear();
                String month = GH.getInstance().formatter(String.valueOf(MonthVisible), "m", "mm");
//                String year = GH.getInstance().formatter(String.valueOf(yearSelected), "YYYY", "yyyy");
                String year = GH.getInstance().formatter(String.valueOf(yearSelected), "yyyy", "yyyy");
                if (currentDateList != null){
                    currentDateList.clear();
                    swipeCalendarAppointmentRecyclerAdapter.notifyDataSetChanged();
                }
//                currentDateList.clear();
                if (dataArrayList != null) {
                    dataArrayList.clear();
                    swipeCalendarAppointmentRecyclerAdapter.notifyDataSetChanged();
                }
//                swipeCalendarAppointmentRecyclerAdapter.notifyDataSetChanged();
                presenter.getCalendarEvents(GH.getInstance().getCalendarAgentId(), month, year);

            }
        });
        bi.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                selectedDate = date.getDay();
                monthOfSelectedDate = date.getMonth() + 1;
                filterDateData(date.getDay(), date.getMonth(), date.getYear());

            }
        });

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.btnSync:

                presenter.getGmailCalender();

                break;
        }


    }

    private void showMonthYearPicker() {

        String month = GH.getInstance().formatter(String.valueOf(MonthVisible), "m", "mm");
//        String year = GH.getInstance().formatter(String.valueOf(yearSelected), "YYYY", "yyyy");
        String year = GH.getInstance().formatter(String.valueOf(yearSelected), "yyyy", "yyyy");
//        Log.i("calenderAgentId", GH.getInstance().getCalendarAgentId() + "month" + month + "year" + year);
        presenter.getCalendarEvents(GH.getInstance().getCalendarAgentId(), month, year);

    }


    private void filterDateData(int day, int month, int year) {

        int pos = GH.getInstance().getCalenderListPos();

        if (pos != -1) {

            dataList.remove(pos);
            EasyPreference.Builder pref = new EasyPreference.Builder(context);
            pref.addInt(GH.KEYS.CALENDERUPDATELIST.name(), -1).save();
        }

        dataArrayList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {
                int _day = markedDates.get(i);
                if (day == _day) {
                    dataArrayList.add(dataList.get(i));
                }
            }
        }

        if (dataArrayList.size() > 0) {
            /*context.startActivity(
                    new Intent(context, CalendarDetailActivity.class)
                            .putExtra("listData", (Serializable) dataArrayList)
                            .putExtra("day", day)
                            .putExtra("month", month)
                            .putExtra("year", year));*/
            swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarActivity.this, dataArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.rvEvents.getContext(), 1);
            bi.rvEvents.setLayoutManager(mLayoutManager);
            bi.rvEvents.setItemAnimator(new DefaultItemAnimator());
            bi.rvEvents.addItemDecoration(dividerItemDecoration);
            bi.rvEvents.setAdapter(swipeCalendarAppointmentRecyclerAdapter);
            bi.rvEvents.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
        } else {
            bi.rvEvents.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
            // ToastUtils.showToastLong(context, "No data found");
        }

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        bi.rvEvents.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

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

        dataList = new ArrayList<>();
        if (response.data.size() > 0) {

            currentDateList = new ArrayList<>();
            dataList = response.getData();
            for (int i = 0; i < response.getData().size(); i++) {

                if (response.getData().get(i).getStart() != null || response.getData().get(i).getStart().equalsIgnoreCase("Null")
                        || !response.getData().get(i).getStart().equals("")) {
                    // hide because of date "04" it change to "4"
                    //  String date = GH.getInstance().formatter(response.getData().get(i).getStart(), "dd", "yyyy-MM-dd'T'HH:mm:ss");
                    String date = GH.getInstance().formatter(response.getData().get(i).getStart(), "d", "yyyy-MM-dd'T'HH:mm:ss");

                    calendar = Calendar.getInstance();
                    currentDate = calendar.get(Calendar.DAY_OF_MONTH);
                    int currentMonth = calendar.get((Calendar.MONTH)) + 1;
                    String month = GH.getInstance().formatter(response.getData().get(i).getStart(), "M", "yyyy-MM-dd'T'HH:mm:ss");

                  /*  if (daySelectionWhenMonthChange != 0) {

                        if (date.equals(String.valueOf(daySelectionWhenMonthChange))) {

                            currentDateList.add(response.getData().get(i));

                        }
                    } else {

                        if (date.equals(String.valueOf(daySelected))) {

                            currentDateList.add(response.getData().get(i));

                        }
                    }*/
                   /* if (date.equals(String.valueOf(daySelected)) && month.equals(String.valueOf(currentMonth))) {
                        currentDateList.add(response.getData().get(i));

                    }*/


                    if (selectedDate != 0 && monthOfSelectedDate != 0) {
                        if (date.equals(String.valueOf(selectedDate)) && month.equals(String.valueOf(monthOfSelectedDate))) {

                            currentDateList.add(response.getData().get(i));

                        } else if (MonthVisible == currentMonth && MonthVisible != monthOfSelectedDate) {
                            if (date.equals(String.valueOf(currentDate)) && month.equals(String.valueOf(currentMonth))) {

                                currentDateList.add(response.getData().get(i));

                            }
                        }
                    } else {

                        if (date.equals(String.valueOf(currentDate)) && month.equals(String.valueOf(currentMonth))) {

                            currentDateList.add(response.getData().get(i));

                        }
                    }

///

                 /*   if (date.equals(String.valueOf(daySelectionWhenMonthChange))){

                        currentDateList.add(response.getData().get(i));

                    }else if(date.equals(String.valueOf(daySelected))) {

                        currentDateList.add(response.getData().get(i));

                    }*/
                }

            }

            swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarActivity.this, currentDateList);


            // for restric empty list on slected date when activity onResume state
          /*  if (dataArrayList != null && dataArrayList.size() > 0){

                swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarActivity.this, dataArrayList);

            }else {

                swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarActivity.this, currentDateList);

            }*/

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

    @Override
    public void updateUIGmailCalender(GmailCalender gmailCalender) {

        if (gmailCalender.getData()) {

            // call getCalender data for getting calender events
            showMonthYearPicker();

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

            calendar.set(yearSelected, (MonthVisible - 1), markedDatesFormatter.get(i).getDateFormat());
            CalendarDay calendarDay = CalendarDay.from(calendar);
            list.add(calendarDay);
        }

        bi.calendarView.invalidateDecorators();
        bi.calendarView.removeDecorators();
        bi.calendarView.addDecorators(new EventDecorator(R.color.colorGreen, list));


        //   bi.calendarView.setCurrentDate(calendar.getTime());
        // bi.calendarView.setDateSelected(CalendarDay.today(), true);
        //  bi.calendarView.setDateSelected(CalendarDay.today(), true);
        // for highlighted the current date in calender
        bi.calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                Calendar cal1 = day.getCalendar();
                Calendar cal2 = Calendar.getInstance();

                return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                        && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) ==
                        cal2.get(Calendar.DAY_OF_YEAR));
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(ContextCompat.getDrawable(CalendarActivity.this, R.drawable.calender_selector));
            }
        });


    }

}