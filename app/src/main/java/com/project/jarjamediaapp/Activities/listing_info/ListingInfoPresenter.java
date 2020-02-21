package com.project.jarjamediaapp.Activities.listing_info;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingInfoPresenter extends BasePresenter<ListingInfoContract.View> implements ListingInfoContract.Actions {

    Call<ListingInfoModel> _call;

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
    public void getLeadBuyingInfoDetails(String leadId) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLeadBuyingInfo(GH.getInstance().getAuthorization(), leadId);
        _call.enqueue(new Callback<ListingInfoModel>() {
            @Override
            public void onResponse(Call<ListingInfoModel> call, Response<ListingInfoModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    ListingInfoModel infoModel = response.body();

                    if (infoModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIList(response.body().getData());

                    } else {

                        _view.updateUIonFalse(infoModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<ListingInfoModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void getLeadListingInfoDetails(String leadId) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLeadListingInfo(GH.getInstance().getAuthorization(), leadId);
        _call.enqueue(new Callback<ListingInfoModel>() {
            @Override
            public void onResponse(Call<ListingInfoModel> call, Response<ListingInfoModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    ListingInfoModel infoModel = response.body();

                    if (infoModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIList(response.body().getData());

                    } else {

                        _view.updateUIonFalse(infoModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<ListingInfoModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

}
