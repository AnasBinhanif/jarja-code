package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;


public class AppointmentPresenter extends BasePresenter<AppointmentContract.View> implements AppointmentContract.Actions {

    Call<BaseResponse> _call;

    public AppointmentPresenter(AppointmentContract.View view) {
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