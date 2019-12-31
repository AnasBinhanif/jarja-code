package com.project.jarjamediaapp.Networking;

public interface ApiMethods {

    /*@FormUrlEncoded
    @POST("login")
    Call<ForgotPasswordModel> loginTransporter(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("device_token") String deviceToken,
                                      @Field("network_protocol") String networkProtocol,
                                      @Field("user_type") String userType);

    @FormUrlEncoded
    @POST("sign_up")
    Call<SignUpModel> signUpTransporter(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("password") String password,
                                        @Field("mobile_number") String phoneNo,
                                        @Field("state_id") String stateId,
                                        @Field("document_url") String documentUrl,
                                        @Field("vehicle_reg_no") String vehicleRegNo,
                                        @Field("user_type") String userType,
                                        @Field("device_token") String deviceToken,
                                        @Field("network_protocol") String networkProtocol);

    @POST("resend_code")
    Call<BaseModel> resendCode(@Header("Content-Type") String contentType,
                               @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("verify_code")
    Call<CodeVerifyModel> verifyCode(@Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorization,
                                     @Field("code") String code);

    @GET("get_states")
    Call<GetStateListModel> getStateList();

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotPasswordModel> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify_email_code")
    Call<BaseModel> verifyEmailCode(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String authorization,
                                    @Field("code") String code);

    @POST("resend_email_code")
    Call<BaseModel> resendEmailCode(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String authorization);


    @FormUrlEncoded
    @POST("reset_password")
    Call<BaseModel> resetPassword(@Header("Content-Type") String contentType,
                                  @Header("Authorization") String authorization,
                                  @Field("new_password") String currentPassword);

    @FormUrlEncoded
    @POST("get_ride_list")
    Call<GetRideListModel> getRideList(@Header("Content-Type") String contentType,
                                       @Header("Authorization") String authorization,
                                       @Field("ride_type") String rideType,
                                       @Field("page") int page,
                                       @Field("limit") String limit);

    @FormUrlEncoded
    @POST("get_ride_detail")
    Call<RideDetailModel> getRideDetail(@Header("Content-Type") String contentType,
                                        @Header("Authorization") String authorization,
                                        @Field("ride_Id") String rideId);

    @FormUrlEncoded
    @POST("report_a_problem")
    Call<RideDetailModel> reportAProblem(@Header("Content-Type") String contentType,
                                         @Header("Authorization") String authorization,
                                         @Field("ride_id") String rideId,
                                         @Field("rider_id") String riderId);

    @FormUrlEncoded
    @POST("accept_reject_ride")
    Call<RideDetailModel> acceptRejectRide(@Header("Content-Type") String contentType,
                                           @Header("Authorization") String authorization,
                                           @Field("ride_id") String rideId,
                                           @Field("is_accept") String isAccept);

    @FormUrlEncoded
    @POST("start_ride")
    Call<RideDetailModel> startRide(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String authorization,
                                    @Field("ride_id") String rideId);

    @FormUrlEncoded
    @POST("end_ride")
    Call<RideDetailModel> endRide(@Header("Content-Type") String contentType,
                                  @Header("Authorization") String authorization,
                                  @Field("ride_id") String rideId,
                                  @Field("kilo_meter") String kiloMeter);

    @GET("common_pages")
    Call<SplashModel> getCommonUrl();

    @FormUrlEncoded
    @POST("get_notification")
    Call<GetNotificationListModel> getNotificationList(@Header("Content-Type") String contentType,
                                                       @Header("Authorization") String authorization,
                                                       @Field("page") int page,
                                                       @Field("limit") String limit);

    @POST("remove_all_notification")
    Call<GetNotificationListModel> clearNotificationList(@Header("Content-Type") String contentType,
                                                         @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("change_password")
    Call<ChangePasswordModel> changePassword(@Header("Content-Type") String contentType,
                                             @Header("Authorization") String authorization,
                                             @Field("current_password") String currentPassword,
                                             @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST("update_profile")
    Call<ChangeProfileModel> updateRiderProfile(@Header("Content-Type") String contentType,
                                                @Header("Authorization") String authorization,
                                                @Field("name") String name,
                                                @Field("email") String email,
                                                @Field("image_url") String image,
                                                @Field("mobile_number") String mobileNumber,
                                                @Field("state_id") String stateId,
                                                @Field("vehicle_reg_no") String vehicleRegNo,
                                                @Field("document_url") String documentUrl,
                                                @Field("user_type") String userType);

    @FormUrlEncoded
    @POST("verify_update_code")
    Call<CodeVerifyModel> verifyAfterUpdateProfile(@Header("Content-Type") String contentType,
                                                   @Header("Authorization") String authorization,
                                                   @Field("code") String code);

    @FormUrlEncoded
    @POST("add_banking_details")
    Call<BaseModel> addBankingDetails(@Header("Content-Type") String contentType,
                                      @Header("Authorization") String authorization,
                                      @Field("account_no") String accountNo,
                                      @Field("branch_code") String code);

    @Multipart
    @POST("upload_docs")
    Call<ImageModel> uploadFile(@Part MultipartBody.Part file);

    @POST("get_earnings")
    Call<GetEarningModel> getRiderEarnings(@Header("Content-Type") String contentType,
                                           @Header("Authorization") String authorization);

    @POST("get_rating")
    Call<HomeModel> getRiderRatings(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("cancel_ride")
    Call<RideDetailModel> cancelRide(@Header("Content-Type") String contentType,
                                     @Header("Authorization") String authorization,
                                     @Field("ride_id") String rideId);*/

}
