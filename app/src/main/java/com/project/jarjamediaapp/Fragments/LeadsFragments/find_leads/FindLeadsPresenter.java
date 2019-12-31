package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;


public class FindLeadsPresenter extends BasePresenter<FindLeadsContract.View> implements FindLeadsContract.Actions {

    Call<BaseResponse> _call;

    public FindLeadsPresenter(FindLeadsContract.View view) {
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