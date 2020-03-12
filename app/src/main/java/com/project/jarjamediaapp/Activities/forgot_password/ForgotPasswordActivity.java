package com.project.jarjamediaapp.Activities.forgot_password;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.Validator;
import com.project.jarjamediaapp.databinding.ActivityForgotPasswordBinding;

import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, ForgotPasswordContract.View {

    ActivityForgotPasswordBinding bi;
    Context context = ForgotPasswordActivity.this;
    ForgotPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        presenter = new ForgotPasswordPresenter(this);
        presenter.initScreen();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnForgot:


                String email = bi.atvEmail.getText().toString();

                if (email.equals("")) {
                    ToastUtils.showToast(context, "Please Enter Email");
                } else if (!Validator.isEmailValid(email)) {

                    ToastUtils.showToast(context, "Please Enter Valid Email");

                } else {
                    presenter.forgetPassword(email);
                }

                break;
        }

    }

    @Override
    public void initViews() {

        bi.btnForgot.setOnClickListener(this);
        bi.tvBottom.setText("@" + getString(R.string.footer_text));

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
                if (!Validator.isEmailValid(email)) {

                    bi.atvEmail.setError("Invalid  Email");

                }

            }
        });
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

    }

    @Override
    public void updateUI(ForgotPasswordModel response) {

        ToastUtils.showToastLong(context, response.data.toString());
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

        GH.getInstance().ShowProgressDialog(ForgotPasswordActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}
