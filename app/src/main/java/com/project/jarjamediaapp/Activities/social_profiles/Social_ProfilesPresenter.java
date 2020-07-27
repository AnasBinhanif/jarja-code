package com.project.jarjamediaapp.Activities.social_profiles;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadSocialProfile;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;
import com.project.jarjamediaapp.Models.UpgradeSocialProfile;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Social_ProfilesPresenter extends BasePresenter<Social_ProfilesContract.View> implements Social_ProfilesContract.Actions {

    Call<GetLeadSocialProfile> _call;
    Call<UpgradeSocialProfile> _callUpgradeSocialProfile;
    Call<BaseResponse> _callAddSocialProfile;
    Call<GetSocialProfileDropdown> _callGetSocialProfileDropdown;

    public Social_ProfilesPresenter(Social_ProfilesContract.View view) {
        super(view);
    }

    @Override
    public void getSocialProfileDropdown() {

        _view.showProgressBar();
        _callGetSocialProfileDropdown = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetSocialProfileDropdown(GH.getInstance().getAuthorization()
                );
        _callGetSocialProfileDropdown.enqueue(new Callback<GetSocialProfileDropdown>() {
            @Override
            public void onResponse(Call<GetSocialProfileDropdown> call, Response<GetSocialProfileDropdown> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetSocialProfileDropdown getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetSocialProfileDropdown> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getAllSocialProfiles(String leadId) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAllSocialProfiles(GH.getInstance().getAuthorization()
        ,leadId);
        _call.enqueue(new Callback<GetLeadSocialProfile>() {
            @Override
            public void onResponse(Call<GetLeadSocialProfile> call, Response<GetLeadSocialProfile> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadSocialProfile getAppointmentsModel = response.body();
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
            public void onFailure(Call<GetLeadSocialProfile> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void upgradeSocialProfile(String leadId) {

        _view.showProgressBar();
        _callUpgradeSocialProfile = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).UpgradeSocialProfile(GH.getInstance().getAuthorization()
                ,leadId);
        _callUpgradeSocialProfile.enqueue(new Callback<UpgradeSocialProfile>() {
            @Override
            public void onResponse(Call<UpgradeSocialProfile> call, Response<UpgradeSocialProfile> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    UpgradeSocialProfile getAppointmentsModel = response.body();
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
            public void onFailure(Call<UpgradeSocialProfile> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void addSocialProfile(String leadId, String name, String siteName, String profilelink) {
        _view.showProgressBar();
        _callAddSocialProfile = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddSocialProfile(GH.getInstance().getAuthorization(),
                leadId,name,siteName,profilelink);
        _callAddSocialProfile.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

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
