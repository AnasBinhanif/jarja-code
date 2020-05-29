package com.project.jarjamediaapp.Activities.calendarDetail;

import android.content.Context;
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

import java.util.List;

import retrofit2.Response;

public class CalendarDetailActivity extends BaseActivity implements View.OnClickListener, CalendarDetailContract.View {

    ActivityCalendarDetailBinding bi;
    Context context = CalendarDetailActivity.this;
    CalendarDetailPresenter presenter;
    SwipeCalendarAppointmentRecyclerAdapter swipeCalendarAppointmentRecyclerAdapter;
    List<CalendarModel.Data> dataList;

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

}
