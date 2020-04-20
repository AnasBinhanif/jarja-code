package com.project.jarjamediaapp.Activities.calendarDetail;

import com.project.jarjamediaapp.Base.BasePresenter;

import retrofit2.Call;

public class CalendarDetailPresenter extends BasePresenter<CalendarDetailContract.View> implements CalendarDetailContract.Actions {

    Call<CalendarAppointmentDetailModel> _call;

    public CalendarDetailPresenter(CalendarDetailContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }

}
