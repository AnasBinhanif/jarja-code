package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;

import android.util.Log;

import com.google.gson.Gson;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.Models.GetLeadSearchFiltersModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindLeadsPresenter extends BasePresenter<FindLeadsContract.View> implements FindLeadsContract.Actions {

    Call<GetLeadCounts> _call;
    Call<ResponseBody> _callResponseBody;

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

    @Override
    public void getLeadCounts(String leadID, String spouseName, String email, String company, String phone, String address, String city, String state,
                              String county, String zip, String countryID, String propertyType, String timeFrameID, String preApproval, String houseToSell,
                              String agentID, String leadTypeID, String leadScoreMin, String leadScoreMax, String tagsID, String priceMin, String priceMax,
                              String notes, String dripCompaignID, String lastTouch, String lastLogin, String pipelineID, String sourceID, String fromDate,
                              String toDate, String searchBy, String firstNameAsc, String lastNameAsc, String emailAddressAsc, boolean registeredDateAsc,
                              String lastLoginedInAsc, String lastLoginedCountAsc, String lastTouchedInAsc, String conversationCellAsc, String conversationEmailAsc,
                              String conversationMsgAsc, String priceAsc, String cityAsc, String timeFrameAsc, String activitiesSavedSearchAsc,
                              String activitiesViewAsc, String activitiesFavoriteAsc, String isSaveSearch, String isFilterClear, String resultSetType,
                              String pageNo, String pageSize) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadCounts(GH.getInstance().getAuthorization(),
                leadID, spouseName, email, company, phone, address, city, state, county, zip, countryID, propertyType, timeFrameID,
                preApproval, houseToSell, agentID, leadTypeID, leadScoreMin, leadScoreMax, tagsID, priceMin, priceMax,
                notes, dripCompaignID, lastTouch, lastLogin, pipelineID, sourceID, fromDate,
                toDate, searchBy, firstNameAsc, lastNameAsc, emailAddressAsc, registeredDateAsc,
                lastLoginedInAsc, lastLoginedCountAsc, lastTouchedInAsc, conversationCellAsc, conversationEmailAsc,
                conversationMsgAsc, priceAsc, cityAsc, timeFrameAsc, activitiesSavedSearchAsc,
                activitiesViewAsc, activitiesFavoriteAsc, isSaveSearch, isFilterClear, resultSetType,
                pageNo, pageSize);
        _call.enqueue(new Callback<GetLeadCounts>() {
            @Override
            public void onResponse(Call<GetLeadCounts> call, Response<GetLeadCounts> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadCounts getLeadCountsModel = response.body();

                    Log.i("GetLeadCounts", "" + response.body().data);
                    Log.i("countstatus", "" + response.body().status);
                    Log.i("countmessage", "" + response.body().message);
                    if (getLeadCountsModel.status.equals("Success")) {

                        _view.updateUI(getLeadCountsModel);

                    } else {

                        _view.updateUIonFalse(getLeadCountsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLeadCounts> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getLeadSearchFilters() {
        _callResponseBody = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getLeadSearchFilters(GH.getInstance().getAuthorization());
        _view.showProgressBar();
        _callResponseBody.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _view.hideProgressBar();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    GetLeadSearchFiltersModel getLeadSearchFiltersModel = null;
                    try {
                        getLeadSearchFiltersModel = gson.fromJson(response.body().string(), GetLeadSearchFiltersModel.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("onResponse: ", getLeadSearchFiltersModel.toString());
                    _view.updateUIWithSearchFiltersResponse(getLeadSearchFiltersModel);
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }
}