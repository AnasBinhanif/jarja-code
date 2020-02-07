package com.project.jarjamediaapp.Activities.add_task;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskPresenter extends BasePresenter<AddTaskContract.View> implements AddTaskContract.Actions {

    Call<GetAgentsModel> _call;
    Call<BaseResponse> callAddTask;

    public AddTaskPresenter(AddTaskContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getAgentNames() {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAgents(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetAgentsModel>() {
            @Override
            public void onResponse(Call<GetAgentsModel> call, Response<GetAgentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAgentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAgentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void addTask(String id, String agentIds, String leadIds, String isAssignNow, String monthType, String scheduleID, String name, String desc,
                        String type, String startDate, String endDate, String recurDay, String recureWeek, String noOfWeek, String dayOfWeek,
                        String weekNo, String monthOfYear, String nextRun, String isEndDate, String reminderDate, String interval, String isSend,
                        String viaReminder, String propertyId, String propertyAddress) {

        _view.showProgressBar();

        callAddTask = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddTask(GH.getInstance().getAuthorization(),
                id, agentIds, leadIds, isAssignNow, monthType, scheduleID, name, desc,
                type, startDate, endDate, recurDay, recureWeek, noOfWeek, dayOfWeek,
                weekNo, monthOfYear, nextRun, isEndDate, reminderDate, interval, isSend,
                viaReminder, propertyId, propertyAddress);

        callAddTask.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

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

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }

}
