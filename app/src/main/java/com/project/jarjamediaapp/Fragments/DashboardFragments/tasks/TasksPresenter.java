package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;


public class TasksPresenter extends BasePresenter<TasksContract.View> implements TasksContract.Actions {

    Call<BaseResponse> _call;

    public TasksPresenter(TasksContract.View view) {
        super(view);
    }

    @Override
    public void detachView() {
        if (_call != null) {
            _call.cancel();
        }
        super.detachView();
    }

    @Override
    public void initScreen() {
        _view.setupViews();

    }
}