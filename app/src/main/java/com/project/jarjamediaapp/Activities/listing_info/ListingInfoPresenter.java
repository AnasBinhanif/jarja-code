package com.project.jarjamediaapp.Activities.listing_info;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Call;

public class ListingInfoPresenter extends BasePresenter<ListingInfoContract.View> implements ListingInfoContract.Actions {

    Call<BaseResponse> _call;

    public ListingInfoPresenter(ListingInfoContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();

    }

    @Override
    public void getBuyingInfoDetails() {

    }

    @Override
    public void getListingInfoDetails() {

    }
}
