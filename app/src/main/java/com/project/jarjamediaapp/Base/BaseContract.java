package com.project.jarjamediaapp.Base;


import retrofit2.Response;

public interface BaseContract {

    interface View {

        void initViews();

        void updateUI(Response<BaseResponse> response);

        void updateUIonFalse(String message);

        void updateUIonError(String error);

        void updateUIonFailure();

        void showProgressBar();

        void hideProgressBar();

    }

    interface Actions {

        void initScreen();

    }


}
