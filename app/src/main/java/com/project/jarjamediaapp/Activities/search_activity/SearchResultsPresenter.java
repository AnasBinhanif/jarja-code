package com.project.jarjamediaapp.Activities.search_activity;

import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordContract;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;

public class SearchResultsPresenter extends BasePresenter<SearchResultsContract.View> implements SearchResultsContract.Actions {

    Call<BaseResponse> _call;


    public SearchResultsPresenter(SearchResultsContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

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

    @Override
    public void addData() {

    }

}
