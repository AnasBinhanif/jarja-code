package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeAppointPreviousRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetPreviousAppointments;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.FragmentAppointmentBinding;

import java.util.ArrayList;
import java.util.List;


public class FragmentAppointment extends BaseFragment implements FragmentLifeCycle, AppointmentContract.View, View.OnClickListener {

    Context context;
    FragmentAppointmentBinding bi;
    AppointmentPresenter presenter;
    SwipeAppointPreviousRecyclerAdapter swipeAppointPreviousRecyclerAdapter;

    public FragmentAppointment() {
        // Required empty public constructor
    }

    public static FragmentAppointment newInstance(String fragment_title) {
        FragmentAppointment fragmentAppointment = new FragmentAppointment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
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

        populateDataPrevious();
        bi.fbAddAppoint.setOnClickListener(this);
    }


    private void populateDataPrevious() {

        List<GetPreviousAppointments> appointmentList = new ArrayList<>();

        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetPreviousAppointments("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));

        swipeAppointPreviousRecyclerAdapter = new SwipeAppointPreviousRecyclerAdapter(context, appointmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewPrevious.getContext(), 1);
        bi.recyclerViewPrevious.setLayoutManager(mLayoutManager);
        bi.recyclerViewPrevious.setItemAnimator(new DefaultItemAnimator());
        // bi.recyclerViewPrevious.addItemDecoration(dividerItemDecoration);
        bi.recyclerViewPrevious.setAdapter(swipeAppointPreviousRecyclerAdapter);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fbAddAppoint:
                switchActivity(context, AddAppointmentActivity.class);
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

    }


}