package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;


import android.annotation.SuppressLint;
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
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentTasksBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TasksFragment extends BaseFragment implements FragmentLifeCycle, TasksContract.View, View.OnClickListener {

    FragmentTasksBinding bi;
    Context context;
    TasksPresenter presenter;
    SwipeTasksDueRecyclerAdapter swipeTasksDueRecyclerAdapter;
    boolean isFromActivity;
    String leadID = "";

    ArrayList<GetTasksModel.Data> tasksList = new ArrayList<>();

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(String fragment_title, String leadID, boolean fromActivity) {
        TasksFragment followUpFragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putString("leadID", leadID);
        followUpFragment.setArguments(args);
        args.putBoolean("isFromActivity", fromActivity);
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

        bi.fbAddTask.setOnClickListener(this);

    }

    @Override
    public void updateUI(GetTasksModel response, String whichTask) {

        tasksList = response.data;

        RecyclerView.LayoutManager mLayoutManager;

        switch (whichTask) {

            case "due":
                if (tasksList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerTaskViewDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.GONE);

                } else {

                    bi.tvNoRecordFound.setVisibility(View.GONE);
                    bi.recyclerTaskViewDue.setVisibility(View.VISIBLE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.GONE);

                    swipeTasksDueRecyclerAdapter = new SwipeTasksDueRecyclerAdapter(context, getActivity(),tasksList, isFromActivity,false);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerTaskViewDue.setLayoutManager(mLayoutManager);
                    bi.recyclerTaskViewDue.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerTaskViewDue.setAdapter(swipeTasksDueRecyclerAdapter);
                }
                break;

            case "overdue":
                if (tasksList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerTaskViewDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.GONE);

                } else {

                    bi.tvNoRecordFound.setVisibility(View.GONE);
                    bi.recyclerTaskViewDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.VISIBLE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.GONE);

                    swipeTasksDueRecyclerAdapter = new SwipeTasksDueRecyclerAdapter(context,getActivity(), tasksList, isFromActivity,false);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewTaskOverDue.setLayoutManager(mLayoutManager);
                    bi.recyclerViewTaskOverDue.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewTaskOverDue.setAdapter(swipeTasksDueRecyclerAdapter);
                }
                break;

            case "future":
                if (tasksList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerTaskViewDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.GONE);

                } else {
                    bi.tvNoRecordFound.setVisibility(View.GONE);
                    bi.recyclerTaskViewDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskOverDue.setVisibility(View.GONE);
                    bi.recyclerViewTaskFutureTask.setVisibility(View.VISIBLE);

                    swipeTasksDueRecyclerAdapter = new SwipeTasksDueRecyclerAdapter(context, getActivity(),tasksList, isFromActivity,true);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewTaskFutureTask.setLayoutManager(mLayoutManager);
                    bi.recyclerViewTaskFutureTask.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewTaskFutureTask.setAdapter(swipeTasksDueRecyclerAdapter);
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
        GH.getInstance().ShowProgressDialog(getActivity());
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @SuppressLint("RestrictedApi")
    private void initViews() {

        isFromActivity = this.getArguments().getBoolean("isFromActivity");
        if (isFromActivity) {
            leadID = this.getArguments().getString("leadID");
            bi.tvTitle.setVisibility(View.GONE);
            bi.fbAddTask.setVisibility(View.GONE);
        }

        bi.btnTaskDue.setOnClickListener(this);
        bi.btnTaskOverDue.setOnClickListener(this);
        bi.btnTaskFutureTask.setOnClickListener(this);
        bi.fbAddTask.setOnClickListener(this);

        if (isFromActivity) {
            presenter.getLeadDueTasks(leadID);
        } else {
            presenter.getDueTasks();
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


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fbAddTask:

                Map<String, String> map = new HashMap<>();
                map.put("from", "4");
                switchActivityWithIntentString(context, AddTaskActivity.class, (HashMap<String, String>) map);
                break;

            case R.id.btnTaskDue:
                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                if (isFromActivity) {
                    presenter.getLeadDueTasks(leadID);
                } else {
                    presenter.getDueTasks();
                }

                break;

            case R.id.btnTaskOverDue:

                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                if (isFromActivity) {
                    presenter.getLeadOverDueTasks(leadID);
                } else {
                    presenter.getOverDueTasks();
                }

                break;

            case R.id.btnTaskFutureTask:

                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonYellowRight);
                if (isFromActivity) {
                    presenter.getLeadFutureTasks(leadID);
                } else {
                    presenter.getFutureTasks();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFromActivity) {
            presenter.getLeadDueTasks(leadID);
        } else {
            presenter.getDueTasks();
        }

    }
}
