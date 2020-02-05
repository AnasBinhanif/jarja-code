package com.project.jarjamediaapp.Fragments.DashboardFragments.followups;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FollowUpPresenter extends BasePresenter<FollowUpContract.View> implements FollowUpContract.Actions {

    Call<GetFollowUpsModel> _call;

    public FollowUpPresenter(FollowUpContract.View view) {
        super(view);
    }

    @Override
    public void detachView() {
        if (_call != null) {
            _call.cancel();
        }
        super.detachView();
    }

    @Override
    public void initScreen() {
        _view.setupViews();

    }

    @Override
    public void getDueFollowUps() {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFollowUpsDue(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetFollowUpsModel>() {
            @Override
            public void onResponse(Call<GetFollowUpsModel> call, Response<GetFollowUpsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetFollowUpsModel getFollowUpsModel = response.body();
                    if (getFollowUpsModel.status.equals("Success")) {

                        _view.updateUI(getFollowUpsModel,"due");

                    } else {

                        _view.updateUIonFalse(getFollowUpsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetFollowUpsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getOverDueFollowUps() {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFollowUpsOverDue(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetFollowUpsModel>() {
            @Override
            public void onResponse(Call<GetFollowUpsModel> call, Response<GetFollowUpsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetFollowUpsModel getFollowUpsModel = response.body();
                    if (getFollowUpsModel.status.equals("Success")) {

                        _view.updateUI(getFollowUpsModel,"overdue");

                    } else {

                        _view.updateUIonFalse(getFollowUpsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetFollowUpsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }
}