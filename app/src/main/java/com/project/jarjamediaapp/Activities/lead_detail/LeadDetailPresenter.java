package com.project.jarjamediaapp.Activities.lead_detail;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadDetailPresenter extends BasePresenter<LeadDetailContract.View> implements LeadDetailContract.Actions {

    Call<GetLead> _call;
    Call<GetLeadTransactionStage> _callGetLeadTransactionStage;


    public LeadDetailPresenter(LeadDetailContract.View view) {
        super(view);
    }

    @Override
    public void getLead(String leadID) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLead(GH.getInstance().getAuthorization(),leadID);
        _call.enqueue(new Callback<GetLead>() {
            @Override
            public void onResponse(Call<GetLead> call, Response<GetLead> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLead getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLead> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getTransaction(String leadID) {


        _view.showProgressBar();
        _callGetLeadTransactionStage = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTransactionStage(GH.getInstance().getAuthorization(),leadID);
        _callGetLeadTransactionStage.enqueue(new Callback<GetLeadTransactionStage>() {
            @Override
            public void onResponse(Call<GetLeadTransactionStage> call, Response<GetLeadTransactionStage> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadTransactionStage getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadTransactionStage> call, Throwable t) {
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
