package com.project.jarjamediaapp.Activities.transactions;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionPresenter extends BasePresenter<TransactionContract.View> implements TransactionContract.Actions {

    Call<BaseResponse> _call;
    Call<TransactionModel> call;
    Call<GetLeadTransactionStage> _callGetLeadTransactionStage;

    public TransactionPresenter(TransactionContract.View view) {
        super(view);
    }

    @Override
    public void addPipelineMark(String body) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddPipeLineMark(GH.getInstance().getAuthorization(),
                body);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        _view.updateUI(response);

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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getTransaction(String leadID) {
        _view.showProgressBar();
        _callGetLeadTransactionStage = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTransactionStage(GH.getInstance().getAuthorization(), leadID);
        _callGetLeadTransactionStage.enqueue(new Callback<GetLeadTransactionStage>() {
            @Override
            public void onResponse(Call<GetLeadTransactionStage> call, Response<GetLeadTransactionStage> response) {

                if (response.isSuccessful()) {

                    GetLeadTransactionStage getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

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
            public void onFailure(Call<GetLeadTransactionStage> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void addAgentCommission(String data) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addAgentCommissionViaLead(GH.getInstance().getAuthorization(),data);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        _view.addAgentCommission(response);

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
    public void getAgentCommission(String leadId, String leadDetailId) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getAgentCommissionViaLead(GH.getInstance().getAuthorization(), leadId, leadDetailId);
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    TransactionModel transactionModel = response.body();
                    if (transactionModel.getStatus().equals("Success")) {

                        _view.getAgentCommission(response);

                    } else {

                        _view.updateUIonFalse(transactionModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {
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
