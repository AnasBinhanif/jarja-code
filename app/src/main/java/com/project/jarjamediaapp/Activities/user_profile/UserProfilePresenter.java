package com.project.jarjamediaapp.Activities.user_profile;

import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetCountries;
import com.project.jarjamediaapp.Models.GetTimeZoneList;
import com.project.jarjamediaapp.Models.GetTwilioNumber;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Actions {


    public UserProfilePresenter(UserProfileContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

    Call<GetUserProfile> _call;
    Call<Upload_ProfileImage> _cCall;
    Call<GetTwilioNumber> _callGetTwilioNumber;
    Call<BaseResponse> _callUpdateUserProfile;
    Call<GetTimeZoneList> _callGetTimeZoneList;
    Call<GetCountries> _callGetCountries;

    @Override
    public void getUserProfile() {


        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getUserProfileData(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {
             ///   _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetUserProfile getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
                //_view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getTimeZoneList() {

        _callGetTimeZoneList = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                GetTimeZoneList(GH.getInstance().getAuthorization());
        _callGetTimeZoneList.enqueue(new Callback<GetTimeZoneList>() {
            @Override
            public void onResponse(Call<GetTimeZoneList> call, Response<GetTimeZoneList> response) {

                if (response.isSuccessful()) {

                    GetTimeZoneList getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                      //  _view.hideProgressBar();
                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                 //   _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTimeZoneList> call, Throwable t) {
               // _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getCountries() {

        _view.showProgressBar();
        _callGetCountries = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                GetCountries(GH.getInstance().getAuthorization());
        _callGetCountries.enqueue(new Callback<GetCountries>() {
            @Override
            public void onResponse(Call<GetCountries> call, Response<GetCountries> response) {

                if (response.isSuccessful()) {

                    GetCountries getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                     //   _view.hideProgressBar();
                        _view.updateUI(getUserProfile);


                    } else {
                        _view.hideProgressBar();
                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                   _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetCountries> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getTwilioNumber() {

     //   _view.showProgressBar();
        _callGetTwilioNumber = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getTwilioNumber(GH.getInstance().getAuthorization());
        _callGetTwilioNumber.enqueue(new Callback<GetTwilioNumber>() {
            @Override
            public void onResponse(Call<GetTwilioNumber> call, Response<GetTwilioNumber> response) {
              //  _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetTwilioNumber getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);

                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTwilioNumber> call, Throwable t) {
              //  _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void uploadImage(MultipartBody.Part file) {

        _view.showProgressBar();
        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Upload_ProfileImage(GH.getInstance().getAuthorization(),file,"image");

        _cCall.enqueue(new Callback<Upload_ProfileImage>() {
            @Override
            public void onResponse(Call<Upload_ProfileImage> call, Response<Upload_ProfileImage> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    Upload_ProfileImage openHousesModel = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(openHousesModel);

                    } else {
                        _view.updateUIonFalse(openHousesModel.message);
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<Upload_ProfileImage> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void updateUserProfile(String firstName, String state, String licenseNo, String picName, String companyAddress, String agentType,
                                  String zipCode, String streetAddress, String title, int countryId, String forwardedNumber,
                                  boolean leadDistributionMessageEnabled, String emailAddress, String company, String lastName, String tmzone,
                                  String picGuid, String phone, String city,String virtualNumber) {

        _view.showProgressBar();
        _callUpdateUserProfile = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                UpdateProfileInfo(GH.getInstance().getAuthorization(), firstName, state, licenseNo, picName, companyAddress, agentType,
                        zipCode, streetAddress, title, countryId, forwardedNumber,
                        leadDistributionMessageEnabled, emailAddress, company, lastName, tmzone,
                        picGuid, phone, city,virtualNumber);

        _callUpdateUserProfile.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        _view.updateUI(getUserProfile);


                    } else {

                        _view.updateUIonFalse(getUserProfile.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }
}