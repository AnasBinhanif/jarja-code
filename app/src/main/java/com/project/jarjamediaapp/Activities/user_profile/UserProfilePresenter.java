package com.project.jarjamediaapp.Activities.user_profile;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTwilioNumber;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Actions {


    public UserProfilePresenter(UserProfileContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    Call<GetUserProfile> _call;
    Call<GetTwilioNumber> _callGetTwilioNumber;
    Call<BaseResponse> _callUpdateUserProfile;

    @Override
    public void getUserProfile() {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getUserProfileData(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {
                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetUserProfile getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getTwilioNumber() {

        _view.showProgressBar();
        _callGetTwilioNumber = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getTwilioNumber(GH.getInstance().getAuthorization());
        _callGetTwilioNumber.enqueue(new Callback<GetTwilioNumber>() {
            @Override
            public void onResponse(Call<GetTwilioNumber> call, Response<GetTwilioNumber> response) {
                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTwilioNumber getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTwilioNumber> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void updateUserProfile(String userId, String state, String licenseNo, String picName, String companyAddress, String agentType,
                                  String zipCode, String streetAddress, String title, String countryId, String forwardedNumber,
                                  String leadDistributionMessageEnabled, String emailAddress, String company, String lastName, String tmzone,
                                  String picGuid, String phone, String city) {

        _view.showProgressBar();
        _callUpdateUserProfile = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                UpdateProfileInfo(GH.getInstance().getAuthorization(), userId, state, licenseNo, picName, companyAddress, agentType,
                        zipCode, streetAddress, title, countryId, forwardedNumber,
                        leadDistributionMessageEnabled, emailAddress, company, lastName, tmzone,
                        picGuid, phone, city);

        _callUpdateUserProfile.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
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
}