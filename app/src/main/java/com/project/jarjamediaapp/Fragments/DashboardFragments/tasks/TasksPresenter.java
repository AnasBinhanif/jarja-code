package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasksPresenter extends BasePresenter<TasksContract.View> implements TasksContract.Actions {

    Call<GetTasksModel> _call;

    public TasksPresenter(TasksContract.View view) {
        super(view);
    }

    @Override
    public void detachView() {
        if (_call != null) {
            _call.cancel();
        }
        super.detachView();
    }

    @Override
    public void initScreen() {
        _view.setupViews();

    }

    @Override
    public void getDueTasks(int page) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetDueTasks(GH.getInstance().getAuthorization(), page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getOverDueTasks(int page) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetOverDueTasks(GH.getInstance().getAuthorization(), page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void getFutureTasks(int page) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFutureTasks(GH.getInstance().getAuthorization(), page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void getLeadDueTasks(String leadID, int page) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadDueTasksNew(GH.getInstance().getAuthorization(), leadID, page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getLeadOverDueTasks(String leadID, int page) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadOverDueTasks(GH.getInstance().getAuthorization(), leadID, page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getLeadFutureTasks(String leadID, int page) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadFutureTasksNew(GH.getInstance().getAuthorization(), leadID, page);
        _call.enqueue(new Callback<GetTasksModel>() {
            @Override
            public void onResponse(Call<GetTasksModel> call, Response<GetTasksModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTasksModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTasksModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

}