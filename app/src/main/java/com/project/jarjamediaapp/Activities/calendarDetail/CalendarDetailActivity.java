package com.project.jarjamediaapp.Activities.calendarDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.calendar.CalendarModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeCalendarAppointmentRecyclerAdapter;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityCalendarDetailBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CalendarDetailActivity extends BaseActivity implements View.OnClickListener, CalendarDetailContract.View {

    ActivityCalendarDetailBinding bi;
    Context context = CalendarDetailActivity.this;
    CalendarDetailPresenter presenter;
    SwipeCalendarAppointmentRecyclerAdapter swipeCalendarAppointmentRecyclerAdapter;
    List<CalendarModel.Data> dataList;
    ArrayList<CalendarModel.Data> dataArrayList;
    ArrayList<Integer> markedDates;
    int day,monthSelected,yearSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_calendar_detail);
        presenter = new CalendarDetailPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.calendar), true);

    }

    @Override
    public void initViews() {

        dataList = (List<CalendarModel.Data>) getIntent().getSerializableExtra("listData");
        day = getIntent().getIntExtra("day",0);
        monthSelected = getIntent().getIntExtra("month",0);
        yearSelected = getIntent().getIntExtra("year",0);
        if (dataList != null && dataList.size() > 0)
            updateUIList(dataList);

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        /*if (message.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
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

        GH.getInstance().ShowProgressDialog(CalendarDetailActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    public void updateUIList(List<CalendarModel.Data> response) {

        if (response.size() > 0) {

            swipeCalendarAppointmentRecyclerAdapter = new SwipeCalendarAppointmentRecyclerAdapter(context, CalendarDetailActivity.this, response);
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

        }

    }

    @Override
    protected void onResume() {
        String month = GH.getInstance().formatter(String.valueOf(monthSelected+1), "m", "mm");
        String year = GH.getInstance().formatter(String.valueOf(yearSelected), "YYYY", "yyyy");
        presenter.getCalendarEvents(GH.getInstance().getCalendarAgentId(), String.valueOf(month),String.valueOf(year));
        super.onResume();
    }

    @Override
    public void updateUIList(CalendarModel calendarModel) {

        markedDates= new ArrayList<>();
        dataList = new ArrayList<>();
        dataList = calendarModel.getData();
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                String formatString = dataList.get(i).getStart();
                markedDates.add(Integer.valueOf(GH.getInstance().formatter(formatString, "d", "yyyy-MM-dd'T'HH:mm:ss")));

            }
        }

        filterDateData(day);


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

            updateUIList(dataArrayList);

        } else {
            ToastUtils.showToastLong(context, "No data found");
        }

    }
}
