package com.project.jarjamediaapp.Activities.open_houses;

import android.util.Log;

import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenHousesPresenter extends BasePresenter<OpenHousesContract.View> implements OpenHousesContract.Actions {

    Call<GetAllOpenHousesModel> call;
    Call<BaseResponse> _call;
    Call<Upload_ProfileImage> _cCall;
    Call<AddressDetailModel> callAddressDetail;


    public OpenHousesPresenter(OpenHousesContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getAddressDetailByPrefix(String prefix, String type) {

        _view.showProgressBar();
        callAddressDetail = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getAddressDetailByPrefix(GH.getInstance().getAuthorization(), prefix,type);
     //   Log.d("param123",callAddressDetail.);
        callAddressDetail.enqueue(new Callback<AddressDetailModel>() {
            @Override
            public void onResponse(Call<AddressDetailModel> call, Response<AddressDetailModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    AddressDetailModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListForAddressDetail(getAppointmentsModel.data);

                    } else {

                        _view._updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<AddressDetailModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void addOpenHouse(String body) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addOpenHouse(GH.getInstance().getAuthorization(),
                body);

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

        Log.i("hello",GH.getInstance().getAuthorization());
        // changes in model UplaodImageModel to Upload_ProfileImage for api response
        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).uploadFileToServer(GH.getInstance().getAuthorization(),file,"image");

        _cCall.enqueue(new Callback<Upload_ProfileImage>() {
            @Override
            public void onResponse(Call<Upload_ProfileImage> call, Response<Upload_ProfileImage> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {


                    // changes in model UplaodImageModel to Upload_ProfileImage for api response
                    Upload_ProfileImage openHousesModel = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateAfterUploadFile(openHousesModel);

                    } else {
                        _view._updateUIonFalse(openHousesModel.message);
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view._updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<Upload_ProfileImage> call, Throwable t) {

                Log.i("Hello",t.getMessage());
                _view.hideProgressBar();
                _view._updateUIonFailure();
            }
        });

    }

    @Override
    public void getAllOpenHouses(String openHouseType,int position) {

        _view.showProgressBar();
        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getAllOpenHouses(GH.getInstance().getAuthorization(),openHouseType);

        call.enqueue(new Callback<GetAllOpenHousesModel>() {
            @Override
            public void onResponse(Call<GetAllOpenHousesModel> call, Response<GetAllOpenHousesModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAllOpenHousesModel openHousesModel = response.body();
                    if (response.body().getStatus().equals("Success")) {
                        _view.updateUIListForOpenHouses(response,position);

                    } else {
                        _view.updateUIonFalse(openHousesModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view._updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAllOpenHousesModel> call, Throwable t) {
                _view.hideProgressBar();
                _view._updateUIonFailure();
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
