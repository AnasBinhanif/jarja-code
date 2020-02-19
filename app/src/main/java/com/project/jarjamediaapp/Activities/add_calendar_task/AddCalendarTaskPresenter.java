package com.project.jarjamediaapp.Activities.add_calendar_task;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentContract;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCalendarTaskPresenter extends BasePresenter<AddCalendarTaskContract.View> implements AddCalendarTaskContract.Actions {

    Call<BaseResponse> call;

    public AddCalendarTaskPresenter(AddCalendarTaskContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void addCalendarTask(String title, String description, String startDate, String startTime, String allDay, String markComplete) {



    }

    @Override
    public void detachView() {

        if (call != null) {

            call.cancel();
        }
        super.detachView();
    }


}
