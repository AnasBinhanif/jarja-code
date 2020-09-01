package com.project.jarjamediaapp.Activities.all_leads;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.Models.GetPropertyLeads;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllLeadsPresenter extends BasePresenter<AllLeadsContract.View> implements AllLeadsContract.Actions {

    Call<GetAllLeads> _call;
    Call<GetPropertyLeads> _callGetPropertyLeads;


    public AllLeadsPresenter(AllLeadsContract.View view) {
        super(view);
    }

    @Override
    public void GetPropertyLeads(String propertyID) {


        _view.showProgressBar();
        _callGetPropertyLeads = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetPropertyLeads(GH.getInstance().getAuthorization(),
                propertyID);
        _callGetPropertyLeads.enqueue(new Callback<GetPropertyLeads>() {
            @Override
            public void onResponse(Call<GetPropertyLeads> call, Response<GetPropertyLeads> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetPropertyLeads getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetPropertyLeads> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void SearchLead(int pageNo, String query) {


    //    _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).SearchLead(GH.getInstance().getAuthorization(),
                pageNo,query);
        _call.enqueue(new Callback<GetAllLeads>() {
            @Override
            public void onResponse(Call<GetAllLeads> call, Response<GetAllLeads> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAllLeads getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetAllLeads> call, Throwable t) {
              //  _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getAllLeads(String data) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAllLead(GH.getInstance().getAuthorization(), data);
        _call.enqueue(new Callback<GetAllLeads>() {
            @Override
            public void onResponse(Call<GetAllLeads> call, Response<GetAllLeads> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAllLeads getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetAllLeads> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }
}
