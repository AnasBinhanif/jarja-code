package com.project.jarjamediaapp.Activities.notification;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter extends BasePresenter<NotificationContract.View> implements NotificationContract.Actions {

    Call<TaskNotificationModel> call;
    Call<AppointmentNotificationModel> _call;
    Call<FollowUpNotificationModel> _cCall;

    public NotificationPresenter(NotificationContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getNotificationByTasks(int page) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByTasks(GH.getInstance().getAuthorization(),page);
        call.enqueue(new Callback<TaskNotificationModel>() {
            @Override
            public void onResponse(Call<TaskNotificationModel> call, Response<TaskNotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    TaskNotificationModel taskNotificationModel = response.body();
                    if (taskNotificationModel.getStatus().equals("Success")) {
                        _view.updateUIListT(taskNotificationModel.getData().getTaskList(),taskNotificationModel.getData().getTaskCount());

                    } else {
                        _view.updateUIonFalse(taskNotificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<TaskNotificationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getNotificationByAppointments(int page) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByAppointments(GH.getInstance().getAuthorization(),page);
        _call.enqueue(new Callback<AppointmentNotificationModel>() {
            @Override
            public void onResponse(Call<AppointmentNotificationModel> call, Response<AppointmentNotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    AppointmentNotificationModel notificationModel = response.body();


                    if (notificationModel.getStatus().equals("Success")) {
                        _view.updateUIListA(notificationModel.getData().getFollowUpsList(),notificationModel.getData().getFollowCount());

                    } else {
                        _view.updateUIonFalse(notificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AppointmentNotificationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getNotificationByFollowUps(int page) {

        _view.showProgressBar();
        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByFollowUps(GH.getInstance().getAuthorization(),page);
        _cCall.enqueue(new Callback<FollowUpNotificationModel>() {
            @Override
            public void onResponse(Call<FollowUpNotificationModel> call, Response<FollowUpNotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    FollowUpNotificationModel notificationModel = response.body();
                    if (notificationModel.getStatus().equals("Success")) {
                        _view.updateUIListF(notificationModel.getData().getFollowUpsList(),notificationModel.getData().getFollowCount());

                    } else {
                        _view.updateUIonFalse(notificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<FollowUpNotificationModel> call, Throwable t) {
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
