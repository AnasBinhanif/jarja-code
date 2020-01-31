package com.project.jarjamediaapp.Activities.login;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Networking.CallbackInterface;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;
import com.project.jarjamediaapp.Networking.RetrofitCallback;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.Validator;
import com.project.jarjamediaapp.databinding.ActivityLoginBinding;

import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    ActivityLoginBinding bi;
    Context context = LoginActivity.this;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.initScreen();

    }

    @Override
    public void setupViews() {

        bi.atvEmail.setText("bunny@outlook.com");
        bi.atvPassword.setText("admin");

        bi.tvForgotPassword.setOnClickListener(this);
        bi.btnLogin.setOnClickListener(this);
        bi.atvEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String email = bi.atvEmail.getText().toString();
                if (!Validator.isEmailValid(email)){

                    bi.atvEmail.setError("Invalid  Email");

                }

            }
        });

    }

    private void createNetWorkCallRequest(String userName,String password) {
        GH.getInstance().ShowProgressDialog(context);
        NetworkController.getInstance().NetworkCall(NetworkController.getInstance().getApiMethods().getToken(userName,password,AppConstants.HTTP.GRANT_TYPE),
                new RetrofitCallback<>(new CallbackInterface<AccessCode>() {
            @Override
            public void onSuccess(AccessCode response) {
                GH.getInstance().HideProgressDialog(context);
                //MyLog.d("Response", "onSuccess: " + response.toString());
                Log.d("token", response.accessToken + "");

                easyPreference.addString(GH.KEYS.AUTHORIZATION.name(),"bearer" + " " + response.accessToken).save();
                switchActivity(HomeActivity.class);
                finish();
            }

            @Override
            public void onError(Throwable throwable) {
                //Log.d("Response", "onError: " + throwable.toString());
                GH.getInstance().HideProgressDialog(context);
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        }));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvForgotPassword:

                switchActivity(ForgotPasswordActivity.class);

                break;
            case R.id.btnLogin:

                String userName = bi.atvEmail.getText().toString();
                String password = bi.atvPassword.getText().toString();
                createNetWorkCallRequest(userName,password);

                //presenter.loginUser();


                break;
        }
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

    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog(context);
    }
}
