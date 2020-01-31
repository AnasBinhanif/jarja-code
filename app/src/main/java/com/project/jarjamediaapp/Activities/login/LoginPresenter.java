package com.project.jarjamediaapp.Activities.login;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Actions {

    Call<LoginModel> _call;

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.setupViews();
    }

   /* @Override
    public void loginUser() {

        _view.showProgressBar();
         Call<LoginModel> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).login(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    LoginModel baseResponse = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(response.body().data.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }*/

   /* @Override
    public void updateCardDetailStatus(String cardId, boolean status) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class). ();
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus()) {
                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(baseResponse.getError());

                    }
                } else {

                    _view.updateUIonError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }*/

    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }
}
