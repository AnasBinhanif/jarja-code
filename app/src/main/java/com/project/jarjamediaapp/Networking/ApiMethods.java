package com.project.jarjamediaapp.Networking;

import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordModel;
import com.project.jarjamediaapp.Activities.open_houses.GetAllOpenHousesModel;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiMethods {


    @FormUrlEncoded
    @POST("token")
    Call<AccessCode> getToken(
            @Field("UserName") String username,
            @Field("Password") String password,
            @Field("grant_type") String grantType
    );

    @FormUrlEncoded
    @POST("User/ForgetPassword")
    Call<ForgotPasswordModel> fogetPassword(
            @Field("email") String email
    );

    @GET("User/GetUserProfileData")
    Call<GetUserProfile> getUserProfileData(
            @Header("Authorization") String authorization
    );

    @GET("Appointment/GetTodayAppointment")
    Call<GetAppointmentsModel> GetTodayAppointment(
            @Header("Authorization") String authorization
    );

    @GET("Appointment/GetPreviousAppointment")
    Call<GetAppointmentsModel> GetPreviousAppointment(
            @Header("Authorization") String authorization
    );

    @GET("Appointment/GetUpcomingAppointment")
    Call<GetAppointmentsModel> GetUpcomingAppointment(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("Appointment/MarkComplete")
    Call<BaseResponse> MarkComplete(
            @Header("Authorization") String authorization,
            @Field("leadAppoinmentID") String leadAppoinmentID
    );

    @GET("FollowUp/GetDueFollowUps")
    Call<GetFollowUpsModel> GetFollowUpsDue(
            @Header("Authorization") String authorization
    );

    @GET("FollowUp/GetOverDueFollowUps")
    Call<GetFollowUpsModel> GetFollowUpsOverDue(
            @Header("Authorization") String authorization
    );

    @GET("FollowUp/GetFollowUpDetails")
    Call<ViewFollowUpModel> GetFollowUpDetails(
            @Header("Authorization") String authorization,
            @Query("id") String leadID
    );

    @FormUrlEncoded
    @POST("FollowUp/MarkComplete")
    Call<BaseResponse> MarkFollowUpComplete(
            @Header("Authorization") String authorization,
            @Field("encrypted_ReminderId") String reminderDI,
            @Field("state") String state
    );

    @GET("Tasks/GetDueTasks")
    Call<GetTasksModel> GetDueTasks(
            @Header("Authorization") String authorization
    );

    @GET("Tasks/GetOverDueTasks")
    Call<GetTasksModel> GetOverDueTasks(
            @Header("Authorization") String authorization
    );

    @GET("Tasks/GetFutureTasks")
    Call<GetTasksModel> GetFutureTasks(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("Tasks/MarkComplete")
    Call<BaseResponse> MarkTaskComplete(
            @Header("Authorization") String authorization,
            @Field("encrypted_TaskID") String reminderDI,
            @Field("state") String state
    );

    @GET("Dashboard/GetLeadTitles")
    Call<GetLeadTitlesModel> GetLeadTitlesModel(
            @Header("Authorization") String authorization,
            @Query("Name") String name
    );

    @GET("Dashboard/GetAgents")
    Call<GetAgentsModel> GetAgents(
            @Header("Authorization") String authorization
    );

    @GET("Dashboard/GetLeadTimeFrame")
    Call<GetLeadTimeFrame> GetLeadTimeFrame(
            @Header("Authorization") String authorization
    );

    @GET("Dashboard/GetLeadSource")
    Call<GetLeadSource> GetLeadSource(
            @Header("Authorization") String authorization
    );

    @GET("Dashboard/GetLeadDripCampaignList")
    Call<GetLeadDripCampaignList> GetLeadDripCampaignList(
            @Header("Authorization") String authorization
    );

    @GET("Dashboard/GetLeadTagList")
    Call<GetLeadTagList> GetLeadTagList(
            @Header("Authorization") String authorization
    );

    @GET("Dashboard/GetLeadTypeList")
    Call<GetLeadTypeList> GetLeadTypeList(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("Appointment/AddNew")
    Call<BaseResponse> AddAppointment(
            @Header("Authorization") String authorization,
            @Field("leadStringID") String leadStringID,
            @Field("agentsStringIDs") String agentsStringIDs,
            @Field("leadAppoinmentID") String leadAppoinmentID,
            @Field("eventTitle") String eventTitle,
            @Field("location") String location,
            @Field("desc") String desc,
            @Field("isAppointmentFixed") String isAppointmentFixed,
            @Field("isAppointmentAttend") String isAppointmentAttend,
            @Field("appointmentDate") String appointmentDate,
            @Field("datedFrom") String datedFrom,
            @Field("datedTo") String datedTo,
            @Field("isAllDay") String isAllDay,
            @Field("interval") String interval,
            @Field("isSend") String isSend,
            @Field("viaReminder") String viaReminder,
            @Field("agentIds") String agentIds,
            @Field("orderBy") String orderBy,
            @Field("startTime") String startTime,
            @Field("endTime") String endTime,
            @Field("isCompleted") String isCompleted
    );

    @FormUrlEncoded
    @POST("Tasks/AddNew")
    Call<BaseResponse> AddTask(
            @Header("Authorization") String authorization,
            @Field("id") String id,
            @Field("agentIds") String agentIds,
            @Field("leadIds") String leadIds,
            @Field("isAssignNow") String isAssignNow,
            @Field("monthType") String monthType,
            @Field("scheduleID") String scheduleID,
            @Field("name") String name,
            @Field("desc") String desc,
            @Field("type") String type,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate,
            @Field("recurDay") String recurDay,
            @Field("recureWeek") String recureWeek,
            @Field("noOfWeek") String noOfWeek,
            @Field("dayOfWeek") String dayOfWeek,
            @Field("weekNo") String weekNo,
            @Field("monthOfYear") String monthOfYear,
            @Field("nextRun") String nextRun,
            @Field("isEndDate") String isEndDate,
            @Field("reminderDate") String reminderDate,
            @Field("interval") String interval,
            @Field("isSend") String isSend,
            @Field("viaReminder") String viaReminder,
            @Field("propertyId") String propertyId,
            @Field("propertyAddress") String propertyAddress
    );

    @FormUrlEncoded
    @POST("Lead/AddNewLead")
    Call<BaseResponse> AddLead(
            @Header("Authorization") String authorization,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("spousname") String spousname,
            @Field("company") String company,
            @Field("cellPhone") String cellPhone,
            @Field("primaryPhone") String primaryPhone,
            @Field("primaryEmail") String primaryEmail,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("isBirthDayNotify") String isBirthDayNotify,
            @Field("dateOfMarriage") String dateOfMarriage,
            @Field("isAnniversaryNotify") String isAnniversaryNotify,
            @Field("leadAgentIDs") String leadAgentIDs,
            @Field("allAgentIds") String allAgentIds,
            @Field("alldripcampaignids") String alldripcampaignids,
            @Field("notes") String notes,
            @Field("b_PreQual") String b_PreQual,
            @Field("address") String address,
            @Field("street") String street,
            @Field("zipcode") String zipcode,
            @Field("city") String city,
            @Field("state") String state,
            @Field("description") String description,
            @Field("source") String source,
            @Field("county") String county,
            @Field("timeFrameId") String timeFrameId,
            @Field("state2") String state2,
            @Field("city2") String city2,
            @Field("zipcode2") String zipcode2,
            @Field("leadTypeID") String leadTypeID,
            @Field("emailList") String emailList,
            @Field("phoneList") String phoneList,
            @Field("labelsID") String labelsID,
            @Field("leadStringID") String leadStringID,
            @Field("leadID") String leadID,
            @Field("countryid") String countryid
    );

    @GET("Lead/GetAllOpenHouse")
    Call<GetAllOpenHousesModel> getAllOpenHouses(@Header("Authorization") String authorization, @Query("type") String type);

    @Multipart
    @POST("Lead/UploadDoc")
    Call<UploadImageModel> uploadFileToServer(@Header("Authorization") String authorization, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("Lead/AddOpenHouse")
    Call<BaseResponse> addOpenHouse(@Header("Authorization") String authorization,
                                    @Field("listPrice") String listPrice,
                                    @Field("city") String city,
                                    @Field("address") String address,
                                    @Field("state") String state,
                                    @Field("zip") String zip,
                                    @Field("image") String image,
                                    @Field("openHouseDate") String openHouseDate,
                                    @Field("openHouseEndDate") String openHouseEndDate);

}
