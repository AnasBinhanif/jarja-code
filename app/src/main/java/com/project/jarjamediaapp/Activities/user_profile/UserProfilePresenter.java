package com.project.jarjamediaapp.Activities.user_profile;

import com.project.jarjamediaapp.Base.BasePresenter;
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
}