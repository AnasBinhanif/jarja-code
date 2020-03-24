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
    public void addUpdateCalendarAppointmentViaTask(String leadStringID, String agentIDsString, String agentIds, String leadAppointmentID, String isAppointmentFixed,
                                                    String isAppointmentAttend, boolean isSend, boolean isAllDay, boolean isCompleted, String datedFrom, String datedTo,
                                                    String appointmentDate, String startTime, String eventTitle, String desc, String location, String viaReminder,
                                                    Integer interval, Integer orderBy, boolean isGmailApptActive, String calendarType, String gmailCalendarId) {

        _view.showProgressBar();

      //  call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addUpdateCalendarAppointmentViaTask(GH.getInstance().getAuthorization(), );

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
        });

    }

    @Override
    public void detachView() {

        if (call != null) {

            call.cancel();
        }
        super.detachView();
    }


}
