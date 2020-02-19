package com.project.jarjamediaapp.Activities.calendar;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;

public class CalendarPresenter extends BasePresenter<CalendarContract.View> implements CalendarContract.Actions {

    Call<BaseResponse> _call;


    public CalendarPresenter(CalendarContract.View view) {
        super(view);
    }

    @Override
    public void getCalendarEvents() {



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
