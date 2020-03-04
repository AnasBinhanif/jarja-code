package com.project.jarjamediaapp.Activities.add_calendar_task;

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
    public void addCalendarTask(String title, String description, String startDateTime, String allDay, String markComplete) {

        /*_view.showProgressBar();

        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addAppointmentByCalendar(GH.getInstance().getAuthorization(),
                "undefined", "", "0", title, "", description, "false",
                "false", "", startDateTime, "", allDay, "0", "", "", "",
                "0", "", "", markComplete, "Task", "", "");


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });*/

    }

    @Override
    public void updateCalendarTask(String title, String description, String startDateTime, String allDay, String markComplete, String calendarId) {

        _view.showProgressBar();

       /* call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).updateAppointmentTaskByCalendar(GH.getInstance().getAuthorization(),
                "undefined", "", "0", title, "", description, "false",
                "false", "", startDateTime, "", allDay, "0", "", "", "",
                "0", "", "", markComplete, "Task", "", calendarId,"");


        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });*/


    }

    @Override
    public void detachView() {

        if (call != null) {

            call.cancel();
        }
        super.detachView();
    }


}
