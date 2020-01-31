package com.project.jarjamediaapp.Activities.splash;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Actions {

    Call<AccessCode> _call;


    public MainPresenter(MainContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.setupViews();
    }

    @Override
    public void getToken(String username, String password, String grantType) {
        _view.showProgressBar();
        Call<AccessCode> call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getToken(username,
                password, grantType);
        call.enqueue(new Callback<AccessCode>() {
            @Override
            public void onResponse(Call<AccessCode> call, Response<AccessCode> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    _view.updateUI(response);

                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error);
                }
            }

            @Override
            public void onFailure(Call<AccessCode> call, Throwable t) {

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
