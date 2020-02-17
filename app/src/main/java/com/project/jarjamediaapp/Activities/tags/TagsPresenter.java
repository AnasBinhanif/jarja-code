package com.project.jarjamediaapp.Activities.tags;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagsPresenter extends BasePresenter<TagsContract.View> implements TagsContract.Actions {

    Call<GetTagListByLeadID> _call;
    Call<BaseResponse> _callAssignTags;


    public TagsPresenter(TagsContract.View view) {
        super(view);
    }

    @Override
    public void getTags(String encryptedLeadId) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetTagListByLeadID(GH.getInstance().getAuthorization(),
                encryptedLeadId
        );
        _call.enqueue(new Callback<GetTagListByLeadID>() {
            @Override
            public void onResponse(Call<GetTagListByLeadID> call, Response<GetTagListByLeadID> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTagListByLeadID getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetTagListByLeadID> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void assignTags(String encryptedLeadId, String tagsIds) {
        _view.showProgressBar();
        _callAssignTags = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AssignTagsToLeads(GH.getInstance().getAuthorization(),
                encryptedLeadId, tagsIds);
        _callAssignTags.enqueue(new Callback<BaseResponse>() {
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