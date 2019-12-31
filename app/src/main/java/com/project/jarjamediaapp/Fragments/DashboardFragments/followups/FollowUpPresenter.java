package com.project.jarjamediaapp.Fragments.DashboardFragments.followups;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;


public class FollowUpPresenter extends BasePresenter<FollowUpContract.View> implements FollowUpContract.Actions {

    Call<BaseResponse> _call;

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
}