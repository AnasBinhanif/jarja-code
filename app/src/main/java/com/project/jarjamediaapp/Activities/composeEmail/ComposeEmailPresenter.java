package com.project.jarjamediaapp.Activities.composeEmail;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;

public class ComposeEmailPresenter extends BasePresenter<ComposeEmailContract.View> implements ComposeEmailContract.Actions {

    Call<BaseResponse> _call;

    Call<ComposeEmailModel> call;


    public ComposeEmailPresenter(ComposeEmailContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getLeadRecipient(String leadId) {

    /*    _view.showProgressBar();
        callLocation = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLocationByPrefix(GH.getInstance().getAuthorization(), prefix);
        callLocation.enqueue(new Callback<GetLocationModel>() {
            @Override
            public void onResponse(Call<GetLocationModel> call, Response<GetLocationModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLocationModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForRecipient(getAppointmentsModel.getData());

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLocationModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });*/


    }

    @Override
    public void sendEmailContent() {


    }

    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }


}
