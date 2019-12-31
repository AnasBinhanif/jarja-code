package com.project.jarjamediaapp.Activities.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvForgotPassword:

                switchActivity(ForgotPasswordActivity.class);

                break;
            case R.id.btnLogin:

                switchActivity(HomeActivity.class);

                break;
        }
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);


    }

    @Override
    public void initViews() {
        bi.tvForgotPassword.setOnClickListener(this);
        bi.btnLogin.setOnClickListener(this);
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(String error) {

        ToastUtils.showToastLong(context, error);
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
