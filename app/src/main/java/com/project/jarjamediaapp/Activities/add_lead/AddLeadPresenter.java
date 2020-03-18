package com.project.jarjamediaapp.Activities.add_lead;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLeadPresenter extends BasePresenter<AddLeadContract.View> implements AddLeadContract.Actions {

    Call<BaseResponse> _callAddLead;
    Call<GetAgentsModel> _callGetAgentsModel;
    Call<GetLeadSource> _callGetLeadSource;
    Call<GetLeadTagList> _callGetLeadTagList;
    Call<GetLeadTypeList> _callGetLeadTypeList;
    Call<GetLeadTimeFrame> _callGetLeadTimeFrame;
    Call<GetLeadDripCampaignList> _callGetLeadDripCampaignList;

    public AddLeadPresenter(AddLeadContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
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
    public void GetLeadSource() {

        _view.showProgressBar();
        _callGetLeadSource = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadSource(GH.getInstance().getAuthorization());
        _callGetLeadSource.enqueue(new Callback<GetLeadSource>() {
            @Override
            public void onResponse(Call<GetLeadSource> call, Response<GetLeadSource> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadSource getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadSource> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void GetLeadTagList() {

        _view.showProgressBar();
        _callGetLeadTagList = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTagList(GH.getInstance().getAuthorization());
        _callGetLeadTagList.enqueue(new Callback<GetLeadTagList>() {
            @Override
            public void onResponse(Call<GetLeadTagList> call, Response<GetLeadTagList> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadTagList getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadTagList> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void GetLeadTypeList() {

        _view.showProgressBar();
        _callGetLeadTypeList = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTypeList(GH.getInstance().getAuthorization());
        _callGetLeadTypeList.enqueue(new Callback<GetLeadTypeList>() {
            @Override
            public void onResponse(Call<GetLeadTypeList> call, Response<GetLeadTypeList> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadTypeList getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadTypeList> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void GetLeadTimeFrame() {

        _view.showProgressBar();
        _callGetLeadTimeFrame = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTimeFrame(GH.getInstance().getAuthorization());
        _callGetLeadTimeFrame.enqueue(new Callback<GetLeadTimeFrame>() {
            @Override
            public void onResponse(Call<GetLeadTimeFrame> call, Response<GetLeadTimeFrame> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadTimeFrame getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadTimeFrame> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void GetLeadDripCampaignList() {

        _view.showProgressBar();
        _callGetLeadDripCampaignList = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadDripCampaignList(GH.getInstance().getAuthorization());
        _callGetLeadDripCampaignList.enqueue(new Callback<GetLeadDripCampaignList>() {
            @Override
            public void onResponse(Call<GetLeadDripCampaignList> call, Response<GetLeadDripCampaignList> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadDripCampaignList getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadDripCampaignList> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void addLead(String firstName, String lastName, String spousname, String company, String cellPhone, String primaryPhone,
                        String primaryEmail, String dateOfBirth, boolean isBirthDayNotify, String dateOfMarriage, boolean isAnniversaryNotify,
                        String leadAgentIDs, String allAgentIds, String alldripcampaignids, String notes, String b_PreQual, String address,
                        String street, String zipcode, String city, String state, String description, String source, String county, String timeFrameId,
                        String state2, String city2, String zipcode2, int leadTypeID, String emailList, String phoneList, String labelsID, String leadStringID,
                        String countryid) {

        _view.showProgressBar();
        _callAddLead = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddLead(GH.getInstance().getAuthorization(),
                firstName, lastName, spousname, company, cellPhone, primaryPhone,
                primaryEmail, dateOfBirth, isBirthDayNotify, dateOfMarriage, isAnniversaryNotify,
                leadAgentIDs, allAgentIds, alldripcampaignids, notes, b_PreQual, address,
                street, zipcode, city, state, description, source, county, timeFrameId,
                state2, city2, zipcode2, leadTypeID, emailList, phoneList, labelsID,leadStringID,
                countryid);

        _callAddLead.enqueue(new Callback<BaseResponse>() {
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
    public void detachView() {

        if (_callGetAgentsModel != null) {
            _callGetAgentsModel.cancel();
        }
        if (_callGetLeadSource != null) {
            _callGetLeadSource.cancel();
        }
        if (_callGetLeadTagList != null) {
            _callGetLeadTagList.cancel();
        }
        if (_callGetLeadTypeList != null) {
            _callGetLeadTypeList.cancel();
        }
        if (_callGetLeadTimeFrame != null) {
            _callGetLeadTimeFrame.cancel();
        }
        if (_callGetLeadDripCampaignList != null) {
            _callGetLeadDripCampaignList.cancel();
        }
        super.detachView();
    }
}