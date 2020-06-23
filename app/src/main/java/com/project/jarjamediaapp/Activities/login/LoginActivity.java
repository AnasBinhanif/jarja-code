package com.project.jarjamediaapp.Activities.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.iid.FirebaseInstanceId;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.CallbackInterface;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;
import com.project.jarjamediaapp.Networking.RetrofitCallback;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.Validator;
import com.project.jarjamediaapp.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    ActivityLoginBinding bi;
    Context context = LoginActivity.this;
    LoginPresenter presenter;
    String email = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.initScreen();

    }

    @Override
    public void setupViews() {

       /* bi.atvEmail.setText("bunny@outlook.com");
        bi.atvPassword.setText("admin");*/

        bi.tvForgotPassword.setOnClickListener(this);
        bi.btnLogin.setOnClickListener(this);

    }

    private void createNetWorkCallRequest(String userName, String password) {

        GH.getInstance().ShowProgressDialog(LoginActivity.this);
        NetworkController.getInstance().NetworkCall(NetworkController.getInstance().getApiMethods().getToken(userName, password, AppConstants.HTTP.GRANT_TYPE),
                new RetrofitCallback<>(new CallbackInterface<AccessCode>() {
                    @Override
                    public void onSuccess(AccessCode response) {
                        //MyLog.d("Response", "onSuccess: " + response.toString());
                        Log.d("token", response.accessToken + "");

                        easyPreference.addString(GH.KEYS.AUTHORIZATION.name(), "bearer" + " " + response.accessToken).save();
                        userAuthenticate(FirebaseInstanceId.getInstance().getToken(),"FCM");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //Log.d("Response", "onError: " + throwable.toString());
                        GH.getInstance().HideProgressDialog();
                        ToastUtils.showToastLong(context, "Invalid Username or Password");
                    }
                }));
    }

    private void userAuthenticate(String deviceToken, String network) {

        Call<BaseResponse> _call=NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Authanticate_UserDevice(GH.getInstance().getAuthorization(),deviceToken,network);
                _call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                       hideProgressBar();
                        if (response.isSuccessful()) {

                            BaseResponse getAppointmentsModel = response.body();
                            if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                                switchActivity(HomeActivity.class);
                                finish();

                            } else {

                                updateUIonFalse(getAppointmentsModel.message);

                            }
                        } else {

                            ApiError error = ErrorUtils.parseError(response);
                            updateUIonError(error);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                       hideProgressBar();
                       updateUIonFailure();
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvForgotPassword:

                switchActivity(ForgotPasswordActivity.class);

                break;
            case R.id.btnLogin:

                email = bi.atvEmail.getText().toString();
                password = bi.atvPassword.getText().toString();

                if (validate())
                    createNetWorkCallRequest(email, password);


                break;
        }
    }

    private boolean validate() {

        if (Methods.isEmpty(bi.atvEmail)) {
            showToastMessage("Please enter email");
            bi.atvEmail.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvPassword)) {
            showToastMessage("Please enter password");
            bi.atvPassword.requestFocus();
            return false;
        }
        if (!Validator.isEmailValid(email)) {
            showToastMessage("Invalid Email ");
            bi.atvEmail.requestFocus();
            return false;
        }
        return true;

    }

    @Override
    public void updateUI(Response<LoginModel> response) {
    }

    @Override
    public void updateUIList(Response<LoginModel> response) {

    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(ApiError error) {
        ToastUtils.showToastLong(context, error.message());
    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(LoginActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}
