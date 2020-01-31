package com.project.jarjamediaapp.Activities.forgot_password;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.View> implements ForgotPasswordContract.Actions {

    Call<ForgotPasswordModel> _call;


    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void forgetPassword(String email) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).fogetPassword(email);
        _call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    ForgotPasswordModel forgotPasswordModel = response.body();
                    if (forgotPasswordModel.status.equals("Success")) {

                        _view.updateUI(forgotPasswordModel);

                    } else {

                        _view.updateUIonFalse(forgotPasswordModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
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
