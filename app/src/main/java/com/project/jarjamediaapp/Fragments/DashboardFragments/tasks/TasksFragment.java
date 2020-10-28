package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;


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
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeTasksRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentTasksBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TasksFragment extends BaseFragment implements FragmentLifeCycle, TasksContract.View, View.OnClickListener {

    FragmentTasksBinding bi;
    Context context;
    TasksPresenter presenter;
    SwipeTasksRecyclerAdapter swipeTasksRecyclerAdapter;
    boolean isFromActivity, isTabClick = true;
    String leadID = "";
    int page = 0;
    int totalPages;
    String taskType = "due";


    List<GetTasksModel.Data.TaskList> tasksList;

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

        tasksList = new ArrayList<>();
        initViews();
        bi.fbAddTask.setOnClickListener(this);

    }

    @Override
    public void updateUI(GetTasksModel response) {

        tasksList.addAll(response.getData().getTaskList());

        totalPages = response.getData().getTaskCount();
        if (tasksList.size() == 0) {

            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
            bi.rvTasks.setVisibility(View.GONE);

        } else {
            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.rvTasks.setVisibility(View.VISIBLE);
            if (isTabClick == true) {
                switch (taskType) {
                    case "due":
                        swipeTasksRecyclerAdapter = new SwipeTasksRecyclerAdapter(context, getActivity(), tasksList, isFromActivity, false);
                        bi.rvTasks.setAdapter(swipeTasksRecyclerAdapter);
                        break;
                    case "overDue":
                        swipeTasksRecyclerAdapter = new SwipeTasksRecyclerAdapter(context, getActivity(), tasksList, isFromActivity, false);
                        bi.rvTasks.setAdapter(swipeTasksRecyclerAdapter);
                        break;
                    case "future":
                        swipeTasksRecyclerAdapter = new SwipeTasksRecyclerAdapter(context, getActivity(), tasksList, isFromActivity, true);
                        bi.rvTasks.setAdapter(swipeTasksRecyclerAdapter);
                        break;
                }
            } else {
                if (swipeTasksRecyclerAdapter != null) {
                    swipeTasksRecyclerAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    private void initRecyclers() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        swipeTasksRecyclerAdapter = new SwipeTasksRecyclerAdapter(context, getActivity(), tasksList, isFromActivity, false);
        bi.rvTasks.setLayoutManager(layoutManager);
        bi.rvTasks.setItemAnimator(new DefaultItemAnimator());
        bi.rvTasks.setAdapter(swipeTasksRecyclerAdapter);

        bi.rvTasks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    Log.d("scroll", "scroll down");
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");
                        if (totalPages > tasksList.size()) {
                            page++;
                            try {
                                isTabClick = false;
                                hitApi();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }
                    }
                }
            }
        });

    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }


    @Override
    public void updateUIonError(String error) {
       /* if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);

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

        initRecyclers();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

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
                if (isFromActivity) {
                    map.put("from", "1");
                    // from dash board task add
                } else {
                    map.put("from", "4");
                }
                switchActivityWithIntentString(context, AddTaskActivity.class, (HashMap<String, String>) map);
                break;

            case R.id.btnTaskDue:

                taskType = "due";
                isTabClick = true;
                tasksList.clear();
                page = 0;
                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                if (isFromActivity) {
                    presenter.getLeadDueTasks(leadID, page);
                } else {
                    presenter.getDueTasks(page);
                }

                break;

            case R.id.btnTaskOverDue:

                taskType = "overDue";
                isTabClick = true;
                tasksList.clear();
                page = 0;
                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonTranparentRight);

                if (isFromActivity) {
                    presenter.getLeadOverDueTasks(leadID, page);
                } else {
                    presenter.getOverDueTasks(page);
                }

                break;

            case R.id.btnTaskFutureTask:

                taskType = "future";
                isTabClick = true;
                tasksList.clear();
                page = 0;
                Paris.style(bi.btnTaskDue).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnTaskOverDue).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnTaskFutureTask).apply(R.style.TabButtonYellowRight);
                if (isFromActivity) {
                    presenter.getLeadFutureTasks(leadID, page);
                } else {
                    presenter.getFutureTasks(page);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        page = 0;
        if (tasksList.size() != 0) {
            tasksList.clear();
            swipeTasksRecyclerAdapter.notifyDataSetChanged();
        }
        isTabClick = true;
        hitApi();

    }

    private void hitApi() {

        if (isFromActivity) {

            switch (taskType) {
                case "due":
                    presenter.getLeadDueTasks(leadID, page);
                    break;
                case "overDue":
                    presenter.getLeadOverDueTasks(leadID, page);
                    break;
                case "future":
                    presenter.getLeadFutureTasks(leadID, page);
                    break;
            }
        } else {
            switch (taskType) {
                case "due":
                    presenter.getDueTasks(page);
                    break;
                case "overDue":
                    presenter.getOverDueTasks(page);
                    break;
                case "future":
                    presenter.getFutureTasks(page);
                    break;
            }
        }
    }

}
