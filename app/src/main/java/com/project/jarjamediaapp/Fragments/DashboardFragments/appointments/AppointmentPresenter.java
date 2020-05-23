package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import com.project.jarjamediaapp.Base.BasePresenter;
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
    public void getTodayAppointments(int page) {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetTodayAppointment(GH.getInstance().getAuthorization(), page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    _view.hideProgressBar();
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
    public void getUpcomingAppointments(int page) {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetUpcomingAppointment(GH.getInstance().getAuthorization(), page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

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
    public void getPreviousAppointments(int page) {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetPreviousAppointment(GH.getInstance().getAuthorization(), page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

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
    public void getLeadTodayAppointments(String leadID, int page) {

        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetTodayAppointmentByLeadID(GH.getInstance().getAuthorization(),
                leadID, page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

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
    public void getLeadUpcomingAppointments(String leadID, int page) {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetUpcomingAppointmentByLeadID(GH.getInstance().getAuthorization(),
                leadID, page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

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
    public void getLeadPreviousAppointments(String leadID, int page) {
        _view.showProgressBar();
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetPreviousAppointmentByLeadID(GH.getInstance().getAuthorization(),
                leadID, page);
        _callToday.enqueue(new Callback<GetAppointmentsModel>() {
            @Override
            public void onResponse(Call<GetAppointmentsModel> call, Response<GetAppointmentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAppointmentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateAppointmentUI(getAppointmentsModel);

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