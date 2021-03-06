package com.project.jarjamediaapp.Activities.add_task;

import android.util.Log;
import android.widget.Toast;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
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
    Call<AddAppointmentModel> call;
    Call<GetTaskDetail> apiCall;

    public AddTaskPresenter(AddTaskContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getAgentNames() {

      //  _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAgents(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetAgentsModel>() {
            @Override
            public void onResponse(Call<GetAgentsModel> call, Response<GetAgentsModel> response) {

                if (response.isSuccessful()) {

                    GetAgentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);
                        getType();

                    } else {

                       // _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                  //  _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAgentsModel> call, Throwable t) {
               // _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getType() {

        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTypes(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {

                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForType(getAppointmentsModel);
                        getRecur();

                    } else {
                     //   _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {
                  //  _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AddAppointmentModel> call, Throwable t) {
               // _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getRecur() {

        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getRecur(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {


                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForReoccur(getAppointmentsModel);
                        getVia();

                    } else {

                      //  _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                   // _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AddAppointmentModel> call, Throwable t) {
              //  _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
        ///adajshdkhagsdjhagsd

    }

    @Override
    public void getReminder() {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getReminder(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {

                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForReminders(getAppointmentsModel);

                    } else {

                        _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AddAppointmentModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getVia() {

      //  _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getVia(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {


                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForVia(getAppointmentsModel);
                      //  getReminder();
                    } else {
                    //    _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {
                  //  _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AddAppointmentModel> call, Throwable t) {
             //   _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void updateTask(String jsonObject) {

        _view.showProgressBar();

        callAddTask = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).UpdateTask(GH.getInstance().getAuthorization(),
                jsonObject);

        callAddTask.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (response.body().getStatus().equals("Success")) {

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
    public void getTaskDetail(String taskId) {

       // _view.showProgressBar();
        apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTaskDetail(GH.getInstance().getAuthorization(), taskId);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {

                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateTaskDetail(getTaskDetail);
                        Log.i("Hello","Success");

                    } else {

                        _view.hideProgressBar();
                        _view.updateUIonFalse(getTaskDetail.message);
                        Log.i("Hello","not Success");

                    }
                } else {

                    _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                    Log.i("Hello","Error");
                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
                Log.i("Hello","Failure");
            }
        });

    }

    @Override
    public void getFutureTaskDetail(String scheduleID) {


      //  _view.showProgressBar();
        apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class)
                .getFutureTaskDetail(GH.getInstance().getAuthorization(), scheduleID);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {

                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateTaskDetail(getTaskDetail);

                    } else {
                        _view.hideProgressBar();
                        _view.updateUIonFalse(getTaskDetail.message);

                    }
                } else {
                    _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void addTask(String body) {
        _view.showProgressBar();
        callAddTask= NetworkController.getInstance().getRetrofit().create(ApiMethods.class)
                .AddTask(GH.getInstance().getAuthorization(), body);
        callAddTask.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (response.body().getStatus().equals("Success")) {

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
