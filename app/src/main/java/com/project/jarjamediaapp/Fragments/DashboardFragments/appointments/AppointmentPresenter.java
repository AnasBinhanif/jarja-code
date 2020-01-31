package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentPresenter extends BasePresenter<AppointmentContract.View> implements AppointmentContract.Actions {

    Call<GetAppointmentsModel> _callToday;

    public AppointmentPresenter(AppointmentContract.View view) {
        super(view);
    }

    @Override
    public void detachView() {
        if (_callToday != null) {
            _callToday.cancel();
        }
        super.detachView();
    }

    @Override
    public void initScreen() {
        _view.setupViews();

    }

    @Override
    public void getTodayAppointments() {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetTodayAppointment(GH.getInstance().getAuthorization());
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel,"today");

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAppointmentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getUpcomingAppointments() {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetUpcomingAppointment(GH.getInstance().getAuthorization());
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel,"upcoming");

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAppointmentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getPreviousAppointments() {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetPreviousAppointment(GH.getInstance().getAuthorization());
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel,"previous");

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAppointmentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }
}