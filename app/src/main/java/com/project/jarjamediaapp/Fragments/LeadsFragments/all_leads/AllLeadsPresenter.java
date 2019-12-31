package com.project.jarjamediaapp.Fragments.LeadsFragments.all_leads;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads.FindLeadsContract;

import retrofit2.Call;


public class AllLeadsPresenter extends BasePresenter<AllLeadsContract.View> implements AllLeadsContract.Actions {

    Call<BaseResponse> _call;

    public AllLeadsPresenter(AllLeadsContract.View view) {
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