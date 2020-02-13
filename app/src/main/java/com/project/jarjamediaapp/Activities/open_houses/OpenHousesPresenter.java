package com.project.jarjamediaapp.Activities.open_houses;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenHousesPresenter extends BasePresenter<OpenHousesContract.View> implements OpenHousesContract.Actions {

    Call<GetAllOpenHousesModel> call;
    Call<BaseResponse> _call;
    Call<UploadImageModel> _cCall;


    public OpenHousesPresenter(OpenHousesContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void addOpenHouse(String listPrice, String city, String address, String state, String zip, String image, String openHouseDate, String openHouseEndDate) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addOpenHouse(GH.getInstance().getAuthorization(), listPrice, city, address, state, zip, image, openHouseDate, openHouseEndDate);

        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse openHousesModel = response.body();
                    if (response.body().getStatus().equals("Success")) {
                        _view.updateUI(response);

                    } else {
                        _view._updateUIonFalse(openHousesModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view._updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view._updateUIonFailure();
            }
        });

    }

    @Override
    public void uploadImage(MultipartBody.Part file) {

        _view.showProgressBar();
        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).uploadFileToServer(GH.getInstance().getAuthorization(),file);

        _cCall.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(Call<UploadImageModel> call, Response<UploadImageModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    UploadImageModel openHousesModel = response.body();
                    if (response.body().getStatus().equals("Success")) {
                        _view.updateAfterUploadFile(response);

                    } else {
                        _view._updateUIonFalse(openHousesModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view._updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                _view.hideProgressBar();
                _view._updateUIonFailure();
            }
        });

    }

    @Override
    public void getAllOpenHouses(String openHouseType) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getAllOpenHouses(GH.getInstance().getAuthorization(), openHouseType);
        call.enqueue(new Callback<GetAllOpenHousesModel>() {
            @Override
            public void onResponse(Call<GetAllOpenHousesModel> call, Response<GetAllOpenHousesModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAllOpenHousesModel openHousesModel = response.body();
                    if (response.body().getStatus().equals("Success")) {
                        _view.updateUIListForOpenHouses(response);

                    } else {
                        _view.updateUIonFalse(openHousesModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAllOpenHousesModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void detachView() {

        if (call != null) {

            call.cancel();
        }
        super.detachView();
    }


}
