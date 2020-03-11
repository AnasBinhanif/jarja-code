package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeAppointPreviousRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentAppointmentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentAppointment extends BaseFragment implements FragmentLifeCycle, AppointmentContract.View, View.OnClickListener {

    Context context;
    FragmentAppointmentBinding bi;
    AppointmentPresenter presenter;
    SwipeAppointPreviousRecyclerAdapter swipeAppointPreviousRecyclerAdapter;
    boolean isFromActivity;
    String leadID = "";
    ArrayList<GetAppointmentsModel.Data> appointmentList = new ArrayList<>();

    public FragmentAppointment() {
        // Required empty public constructor
    }

    public static FragmentAppointment newInstance(String fragment_title, String leadID, boolean fromActivity) {
        FragmentAppointment fragmentAppointment = new FragmentAppointment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putString("leadID", leadID);
        args.putBoolean("isFromActivity", fromActivity);
        fragmentAppointment.setArguments(args);
        return fragmentAppointment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false);
        presenter = new AppointmentPresenter(this);
        presenter.initScreen();

        return bi.getRoot();
    }

    public FragmentAppointment getInstance() {
        return this;
    }

    @Override
    public void setupViews() {

        initViews();

    }

    @Override
    public void updateUI(GetAppointmentsModel response, String whichAppoint) {

        appointmentList = response.data;

        RecyclerView.LayoutManager mLayoutManager;

        switch (whichAppoint) {

            case "today":

                if (appointmentList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerViewToday.setVisibility(View.GONE);

                } else {
                    swipeAppointPreviousRecyclerAdapter = new SwipeAppointPreviousRecyclerAdapter(context, appointmentList, isFromActivity, false);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewToday.setLayoutManager(mLayoutManager);
                    bi.recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewToday.setAdapter(swipeAppointPreviousRecyclerAdapter);
                }
                break;
            case "upcoming":
                if (appointmentList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerViewUpcoming.setVisibility(View.GONE);

                } else {
                    swipeAppointPreviousRecyclerAdapter = new SwipeAppointPreviousRecyclerAdapter(context, appointmentList, isFromActivity, false);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewUpcoming.setLayoutManager(mLayoutManager);
                    bi.recyclerViewUpcoming.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewUpcoming.setAdapter(swipeAppointPreviousRecyclerAdapter);
                }
                break;
            case "previous":
                if (appointmentList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerViewPrevious.setVisibility(View.GONE);

                } else {
                    swipeAppointPreviousRecyclerAdapter = new SwipeAppointPreviousRecyclerAdapter(context, appointmentList, isFromActivity, true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewPrevious.setLayoutManager(mLayoutManager);
                    bi.recyclerViewPrevious.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewPrevious.setAdapter(swipeAppointPreviousRecyclerAdapter);
                }
                break;

        }

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

    @SuppressLint("RestrictedApi")
    private void initViews() {

        isFromActivity = this.getArguments().getBoolean("isFromActivity");
        if (isFromActivity) {
            leadID = this.getArguments().getString("leadID");
            bi.tvTitle.setVisibility(View.GONE);
            bi.fbAddAppoint.setVisibility(View.GONE);
        }

        bi.btnToday.setOnClickListener(this);
        bi.btnUpcoming.setOnClickListener(this);
        bi.btnPrevious.setOnClickListener(this);
        bi.fbAddAppoint.setOnClickListener(this);

        if (isFromActivity) {
            presenter.getLeadTodayAppointments(leadID);
        } else {
            presenter.getTodayAppointments();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fbAddAppoint:

                Map<String, String> map = new HashMap<>();
                map.put("from", "5");
                switchActivityWithIntentString(context, AddAppointmentActivity.class, (HashMap<String, String>) map);

                break;

            case R.id.btnToday:

                if (isFromActivity) {
                    presenter.getLeadTodayAppointments(leadID);
                } else {
                    presenter.getTodayAppointments();
                }

                Paris.style(bi.btnToday).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonTranparentRight);

                bi.tvNoRecordFound.setVisibility(View.GONE);
                bi.recyclerViewToday.setVisibility(View.VISIBLE);
                bi.recyclerViewUpcoming.setVisibility(View.GONE);
                bi.recyclerViewPrevious.setVisibility(View.GONE);

                break;

            case R.id.btnUpcoming:

                if (isFromActivity) {
                    presenter.getLeadUpcomingAppointments(leadID);
                } else {
                    presenter.getUpcomingAppointments();
                }

                Paris.style(bi.btnToday).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonTranparentRight);

                bi.tvNoRecordFound.setVisibility(View.GONE);
                bi.recyclerViewToday.setVisibility(View.GONE);
                bi.recyclerViewPrevious.setVisibility(View.GONE);
                bi.recyclerViewUpcoming.setVisibility(View.VISIBLE);

                break;

            case R.id.btnPrevious:

                if (isFromActivity) {
                    presenter.getLeadPreviousAppointments(leadID);
                } else {
                    presenter.getPreviousAppointments();
                }

                Paris.style(bi.btnToday).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonYellowRight);

                bi.tvNoRecordFound.setVisibility(View.GONE);
                bi.recyclerViewToday.setVisibility(View.GONE);
                bi.recyclerViewUpcoming.setVisibility(View.GONE);
                bi.recyclerViewPrevious.setVisibility(View.VISIBLE);

                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

        Log.d("resume","On Resume Called");

    }


}