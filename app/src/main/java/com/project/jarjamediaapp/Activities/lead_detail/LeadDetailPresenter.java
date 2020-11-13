package com.project.jarjamediaapp.Activities.lead_detail;

import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadDetailPresenter extends BasePresenter<LeadDetailContract.View> implements LeadDetailContract.Actions {

    Call<GetLead> _call;
    Call<BaseResponse> call;
    Call<GetAgentsModel> _callGetAgentsModel;
    Call<GetLeadTransactionStage> _callGetLeadTransactionStage;
    Call<LeadDetailModel> _callLead;
    Call<UploadImageModel> _cCall;
    Call<GetCallerId> _cTwilio;

    public LeadDetailPresenter(LeadDetailContract.View view) {
        super(view);
    }

    @Override
    public void assignAgents(String agentsIDs, String leadID, boolean typeIndex) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AssignAgentToLead(GH.getInstance().getAuthorization(),
                agentsIDs, leadID, typeIndex);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

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
    public void getAgentNames() {

        _view.showProgressBar();
        _callGetAgentsModel = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAgents(GH.getInstance().getAuthorization());
        _callGetAgentsModel.enqueue(new Callback<GetAgentsModel>() {
            @Override
            public void onResponse(Call<GetAgentsModel> call, Response<GetAgentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAgentsModel getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetAgentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getLead(String leadID) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLead(GH.getInstance().getAuthorization(), leadID);
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
    public void setPrimaryAgent(String encryptedLeadId, String encryptedAgentId) {
        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).setPrimaryAgent(GH.getInstance().getAuthorization(),encryptedLeadId,encryptedAgentId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

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
    public void getTransaction(String leadID) {

        _view.showProgressBar();
        _callGetLeadTransactionStage = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTransactionStage(GH.getInstance().getAuthorization(), leadID);
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
    public void getLeadRecipient(String leadId) {

        _view.showProgressBar();
        _callLead = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLeadRecipient(GH.getInstance().getAuthorization(), leadId);
        _callLead.enqueue(new Callback<LeadDetailModel>() {
            @Override
            public void onResponse(Call<LeadDetailModel> call, Response<LeadDetailModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    LeadDetailModel leadDetailModel = response.body();
                    if (leadDetailModel.status.equals("Success")) {

                        _view.updateUIListForRecipient(leadDetailModel.getData());

                    } else {

                        _view.updateUIonFalse(leadDetailModel.message);
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<LeadDetailModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void sendEmailContent(String from, String[] to, String cc, String bcc, String subject, String body, String fileUrl, String leadId) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).composeEmail(GH.getInstance().getAuthorization(), from, to,
                cc, bcc, subject, body, "0", fileUrl, leadId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus().equals("Success")) {

                        _view.updateUIEmailSent(response);

                    } else {

                        _view.updateUIonFalse(baseResponse.getMessage());

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
    public void uploadFile(MultipartBody.Part file, String emailFrom) {

        _view.showProgressBar();
        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).uploadEmailAttachedFile(GH.getInstance().getAuthorization(),
                file, "image", emailFrom);

        _cCall.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(Call<UploadImageModel> call, Response<UploadImageModel> response) {


                if (response.isSuccessful()) {

                    UploadImageModel openHousesModel = response.body();
                    if (response.body().getStatus() != null && response.body().getStatus().equals("Success")) {
                        _view.updateUIAfterFileUpload(response);

                    } else {
                        _view.hideProgressBar();
                        _view.updateUIonFalse(openHousesModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void sendMessageContent(String fromPhoneNumber, String message, String leadId) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).sendMessage(GH.getInstance().getAuthorization(), fromPhoneNumber, message, leadId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus().equals("Success")) {

                        _view.updateUIMessageSent(response);

                    } else {

                        _view.updateUIonFalse(baseResponse.getMessage());

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
    public void getCallerId(String leadId) {

        _view.showProgressBar();
        _cTwilio = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCallerId(GH.getInstance().getAuthorization(), leadId);
        _cTwilio.enqueue(new Callback<GetCallerId>() {
            @Override
            public void onResponse(Call<GetCallerId> call, Response<GetCallerId> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetCallerId getCallerId = response.body();
                    if (getCallerId.getStatus().equals("Success")) {

                        _view.updateUIonCall(getCallerId);

                    } else {

                        _view.updateUIonFalse(getCallerId.getMessage());

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetCallerId> call, Throwable t) {
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
