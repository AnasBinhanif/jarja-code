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

    Call<NotificationModel> call;

    public NotificationPresenter(NotificationContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getNotificationByTasks() {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByTasks(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    NotificationModel notificationModel = response.body();
                    if (notificationModel.getStatus().equals("Success")) {
                        _view.updateUIList(response);

                    } else {
                        _view.updateUIonFalse(notificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getNotificationByAppointments() {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByAppointments(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    NotificationModel notificationModel = response.body();
                    if (notificationModel.getStatus().equals("Success")) {
                        _view.updateUIList(response);

                    } else {
                        _view.updateUIonFalse(notificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getNotificationByFollowUps() {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getNotificationByFollowUps(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    NotificationModel notificationModel = response.body();
                    if (notificationModel.getStatus().equals("Success")) {
                        _view.updateUIList(response);

                    } else {
                        _view.updateUIonFalse(notificationModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
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
