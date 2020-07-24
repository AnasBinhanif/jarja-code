package com.project.jarjamediaapp.Activities.add_appointment;

import android.util.Log;

import com.google.gson.Gson;
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

public class AddAppointmentPresenter extends BasePresenter<AddAppointmentContract.View> implements AddAppointmentContract.Actions {

    Call<GetAgentsModel> _call;

    Call<BaseResponse> _callAddAppointment;

    Call<AddAppointmentModel> call;

    Call<GetLocationModel> callLocation;

    public AddAppointmentPresenter(AddAppointmentContract.View view) {
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

                if (response.isSuccessful()) {

                    GetAgentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

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
            public void onFailure(Call<GetAgentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void addAppointment(String prefix, String fromid) {

        _view.showProgressBar();

        if (fromid.equals("3")) {
            _callAddAppointment = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addAppointmentByCalendar(GH.getInstance().getAuthorization(),
                    prefix);
        } else if (fromid.equalsIgnoreCase("4") || fromid.equalsIgnoreCase("2")) {
            _callAddAppointment = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).UpdateAppointment(GH.getInstance().getAuthorization(),
                    prefix);
        } else if (fromid.equalsIgnoreCase("6")) {
            _callAddAppointment = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).updateAppointmentTaskByCalendar(GH.getInstance().getAuthorization(),
                    prefix);
          //  Log.i("JsonPrefix",prefix);

        } else {
            _callAddAppointment = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddAppointment(GH.getInstance().getAuthorization(),
                    prefix);


        }
        _callAddAppointment.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Log.i("response", new Gson().toJson(response));

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
    public void getDropDownLocation(String prefix) {

        _view.showProgressBar();
        callLocation = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLocationByPrefix(GH.getInstance().getAuthorization(), prefix);
        callLocation.enqueue(new Callback<GetLocationModel>() {
            @Override
            public void onResponse(Call<GetLocationModel> call, Response<GetLocationModel> response) {


                if (response.isSuccessful()) {

                    GetLocationModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForLocation(getAppointmentsModel.getData());

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
            public void onFailure(Call<GetLocationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getReminder() {

        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getReminder(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {


                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForReminders(getAppointmentsModel);
                        getVia();
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
            public void onFailure(Call<AddAppointmentModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getVia() {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getVia(GH.getInstance().getAuthorization());
        call.enqueue(new Callback<AddAppointmentModel>() {
            @Override
            public void onResponse(Call<AddAppointmentModel> call, Response<AddAppointmentModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    AddAppointmentModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForVia(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

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
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }


}
