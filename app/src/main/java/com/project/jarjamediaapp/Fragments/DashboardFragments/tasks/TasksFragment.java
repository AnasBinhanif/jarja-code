package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeTasksDueRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetDueTasks;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.FragmentTasksBinding;

import java.util.ArrayList;
import java.util.List;


public class TasksFragment extends BaseFragment implements FragmentLifeCycle, TasksContract.View, View.OnClickListener {

    FragmentTasksBinding bi;
    Context context;
    TasksPresenter presenter;
    SwipeTasksDueRecyclerAdapter swipeTasksDueRecyclerAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(String fragment_title) {
        TasksFragment followUpFragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        followUpFragment.setArguments(args);
        return followUpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);
        presenter = new TasksPresenter(this);
        presenter.initScreen();

        return bi.getRoot();

    }

    @Override
    public void setupViews() {

        initViews();
        populateDataDue();
        bi.fbAddTask.setOnClickListener(this);

    }

    private void initViews() {

        bi.btnTaskDue.setOnClickListener(this);
        bi.btnTaskOverDue.setOnClickListener(this);
        bi.btnTaskFutureTask.setOnClickListener(this);
        bi.fbAddTask.setOnClickListener(this);

    }

    private void populateDataDue() {

        List<GetDueTasks> appointmentList = new ArrayList<>();

        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueTasks("NAME NAME NAME NAME NAME NAME", "Address Address Address Address"));

        swipeTasksDueRecyclerAdapter = new SwipeTasksDueRecyclerAdapter(context, appointmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        bi.recyclerTaskViewDue.setLayoutManager(mLayoutManager);
        bi.recyclerTaskViewDue.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerTaskViewDue.setAdapter(swipeTasksDueRecyclerAdapter);


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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fbAddTask:
                switchActivity(context, AddTaskActivity.class);
                break;

            case R.id.btnTaskDue:

                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnTaskOverDue:

                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnTaskFutureTask:

                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonYellowRight);

                break;
        }
    }
}
