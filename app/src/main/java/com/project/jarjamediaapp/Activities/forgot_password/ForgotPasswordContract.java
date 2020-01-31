package com.project.jarjamediaapp.Activities.forgot_password;

import com.project.jarjamediaapp.Base.BaseContract;

import retrofit2.Response;

public interface ForgotPasswordContract {

    interface View extends BaseContract.View{

        void updateUI(ForgotPasswordModel response);

        void updateUIonFalse(String message);

        void updateUIonError(String error);

        void updateUIonFailure();

        void showProgressBar();

        void hideProgressBar();

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {
        void initScreen();
        void forgetPassword(String email);

    }

}
