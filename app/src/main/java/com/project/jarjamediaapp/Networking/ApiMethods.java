package com.project.jarjamediaapp.Networking;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordModel;
import com.project.jarjamediaapp.Activities.notification.NotificationModel;
import com.project.jarjamediaapp.Activities.open_houses.AddressDetailModel;
import com.project.jarjamediaapp.Activities.open_houses.GetAllOpenHousesModel;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetAllSocialProfiles;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.Models.GetLastLogin;
import com.project.jarjamediaapp.Models.GetLastTouch;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetLeadScore;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.Models.GetPipeline;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;
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

    @GET("Lead/GetSources")
    Call<GetLeadSource> GetLeadSource(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetLeadScore")
    Call<GetLeadScore> GetLeadScore(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetLastTouch")
    Call<GetLastTouch> GetLastTouch(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetLastLogin")
    Call<GetLastLogin> GetLastLogin(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetPipeline")
    Call<GetPipeline> GetPipeline(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetLead")
    Call<GetLead> GetLead(
            @Header("Authorization") String authorization,
            @Query("LeadID") String leadID
    );

    @GET("Lead/GetLeadTransactionStage")
    Call<GetLeadTransactionStage> GetLeadTransactionStage(
            @Header("Authorization") String authorization,
            @Query("LeadId") String leadID
    );


    @GET("Lead/GetSocialProfileDropdown")
    Call<GetSocialProfileDropdown> GetSocialProfileDropdown(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetAllSocialProfiles")
    Call<GetAllSocialProfiles> GetAllSocialProfiles(
            @Header("Authorization") String authorization,
            @Query("Encrypted_LeadID") String leadID
    );

    @FormUrlEncoded
    @POST("Lead/AddSocialProfile")
    Call<BaseResponse> AddSocialProfile(
            @Header("Authorization") String authorization,
            @Field("leadID") String leadID,
            @Field("name") String name,
            @Field("siteName") String siteName,
            @Field("profilelink") String profilelink
    );

    @GET("Lead/GetNoteDropDown")
    Call<GetNoteDropDown> GetNoteDropDown(
            @Header("Authorization") String authorization
    );

    @GET("Lead/GetLeadNotes")
    Call<GetLeadNotes> GetLeadNotes(
            @Header("Authorization") String authorization,
            @Query("EncryptedLeadID") String leadID
    );

    @POST("Lead/EditNote")
    Call<BaseResponse> EditNote(
            @Header("Authorization") String authorization,
            @Query("Encrypted_NoteID") String NoteID,
            @Query("Encrypted_LeadStringID") String LeadID,
            @Query("Description") String Description
    );

    @POST("Lead/Make_Lead_Note_Sticky")
    Call<BaseResponse> Make_Lead_Note_Sticky(
            @Header("Authorization") String authorization,
            @Query("Encrypted_NoteID") String NoteID,
            @Query("Encrypted_LeadStringID") String LeadID,
            @Query("IsSticky") Boolean isSticky
    );

    @FormUrlEncoded
    @POST("Lead/DeleteNote")
    Call<BaseResponse> DeleteNote(
            @Header("Authorization") String authorization,
            @Field("noteID") String leadAppoinmentID
    );

    @GET("Lead/GetTagListByLeadID")
    Call<GetTagListByLeadID> GetTagListByLeadID(
            @Header("Authorization") String authorization,
            @Query("Encrypted_LeadID") String leadID
    );

    @POST("Lead/AssignTagsToLeads")
    Call<BaseResponse> AssignTagsToLeads(
            @Header("Authorization") String authorization,
            @Query("Encrypted_LeadStringID") String leadID,
            @Query("TagIds") String tagsId
    );

    @POST("Lead/DeleteLeadTag")
    Call<BaseResponse> DeleteLeadTag(
            @Header("Authorization") String authorization,
            @Query("LeadId") String leadID,
            @Query("LabelId") String tagsId
    );

    @FormUrlEncoded
    @POST("Lead/AddNote")
    Call<BaseResponse> AddNote(
            @Header("Authorization") String authorization,
            @Field("noteID") String noteID,
            @Field("leadID") String leadID,
            @Field("noteType") String noteType,
            @Field("desc") String desc,
            @Field("isSticky") String isSticky,
            @Field("dated") String dated,
            @Field("agentIDs") String agentIDs,
            @Field("leadStringID") String leadStringID
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
            @Field("allagentids") String allAgentIds,
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

    @FormUrlEncoded
    @POST("Lead/GetLeadCounts")
    Call<GetLeadCounts> GetLeadCounts(
            @Header("Authorization") String authorization,
            @Field("leadID") String leadID,
            @Field("spouseName") String spouseName,
            @Field("email") String email,
            @Field("company") String company,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("city") String city,
            @Field("state") String state,
            @Field("county") String county,
            @Field("zip") String zip,
            @Field("countryID") String countryID,
            @Field("propertyType") String propertyType,
            @Field("timeFrameID") String timeFrameID,
            @Field("preApproval") String preApproval,
            @Field("houseToSell") String houseToSell,
            @Field("agentID") String agentID,
            @Field("leadTypeID") String leadTypeID,
            @Field("leadScoreMin") String leadScoreMin,
            @Field("leadScoreMax") String leadScoreMax,
            @Field("tagsID") String tagsID,
            @Field("priceMin") String priceMin,
            @Field("priceMax") String priceMax,
            @Field("notes") String notes,
            @Field("dripCompaignID") String dripCompaignID,
            @Field("lastTouch") String lastTouch,
            @Field("lastLogin") String lastLogin,
            @Field("pipelineID") String pipelineID,
            @Field("sourceID") String sourceID,
            @Field("fromDate") String fromDate,
            @Field("toDate") String toDate,
            @Field("searchBy") String searchBy,
            @Field("firstNameAsc") String firstNameAsc,
            @Field("lastNameAsc") String lastNameAsc,
            @Field("emailAddressAsc") String emailAddressAsc,
            @Field("registeredDateAsc") String registeredDateAsc,
            @Field("lastLoginedInAsc") String lastLoginedInAsc,
            @Field("lastLoginedCountAsc") String lastLoginedCountAsc,
            @Field("lastTouchedInAsc") String lastTouchedInAsc,
            @Field("conversationCellAsc") String conversationCellAsc,
            @Field("conversationEmailAsc") String conversationEmailAsc,
            @Field("conversationMsgAsc") String conversationMsgAsc,
            @Field("priceAsc") String priceAsc,
            @Field("cityAsc") String cityAsc,
            @Field("timeFrameAsc") String timeFrameAsc,
            @Field("activitiesSavedSearchAsc") String activitiesSavedSearchAsc,
            @Field("activitiesViewAsc") String activitiesViewAsc,
            @Field("activitiesFavoriteAsc") String activitiesFavoriteAsc,
            @Field("isSaveSearch") String isSaveSearch,
            @Field("isFilterClear") String isFilterClear,
            @Field("resultSetType") String resultSetType,
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    @FormUrlEncoded
    @POST("Lead/GetAllLead")
    Call<GetAllLeads> GetAllLead(
            @Header("Authorization") String authorization,
            @Field("leadID") String leadID,
            @Field("spouseName") String spouseName,
            @Field("email") String email,
            @Field("company") String company,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("city") String city,
            @Field("state") String state,
            @Field("county") String county,
            @Field("zip") String zip,
            @Field("countryID") String countryID,
            @Field("propertyType") String propertyType,
            @Field("timeFrameID") String timeFrameID,
            @Field("preApproval") String preApproval,
            @Field("houseToSell") String houseToSell,
            @Field("agentID") String agentID,
            @Field("leadTypeID") String leadTypeID,
            @Field("leadScoreMin") String leadScoreMin,
            @Field("leadScoreMax") String leadScoreMax,
            @Field("tagsID") String tagsID,
            @Field("priceMin") String priceMin,
            @Field("priceMax") String priceMax,
            @Field("notes") String notes,
            @Field("dripCompaignID") String dripCompaignID,
            @Field("lastTouch") String lastTouch,
            @Field("lastLogin") String lastLogin,
            @Field("pipelineID") String pipelineID,
            @Field("sourceID") String sourceID,
            @Field("fromDate") String fromDate,
            @Field("toDate") String toDate,
            @Field("searchBy") String searchBy,
            @Field("firstNameAsc") String firstNameAsc,
            @Field("lastNameAsc") String lastNameAsc,
            @Field("emailAddressAsc") String emailAddressAsc,
            @Field("registeredDateAsc") String registeredDateAsc,
            @Field("lastLoginedInAsc") String lastLoginedInAsc,
            @Field("lastLoginedCountAsc") String lastLoginedCountAsc,
            @Field("lastTouchedInAsc") String lastTouchedInAsc,
            @Field("conversationCellAsc") String conversationCellAsc,
            @Field("conversationEmailAsc") String conversationEmailAsc,
            @Field("conversationMsgAsc") String conversationMsgAsc,
            @Field("priceAsc") String priceAsc,
            @Field("cityAsc") String cityAsc,
            @Field("timeFrameAsc") String timeFrameAsc,
            @Field("activitiesSavedSearchAsc") String activitiesSavedSearchAsc,
            @Field("activitiesViewAsc") String activitiesViewAsc,
            @Field("activitiesFavoriteAsc") String activitiesFavoriteAsc,
            @Field("isSaveSearch") String isSaveSearch,
            @Field("isFilterClear") String isFilterClear,
            @Field("resultSetType") String resultSetType,
            @Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize
    );

    @GET("Lead/GetAllOpenHouse")
    Call<GetAllOpenHousesModel> getAllOpenHouses(@Header("Authorization") String authorization, @Query("type") String type);

    @Multipart
    @POST("Lead/UploadImage")
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

    @GET("Notification/GetAppointmentNotification")
    Call<NotificationModel> getNotificationByAppointments(@Header("Authorization") String authorization);

    @GET("Notification/GetTaskNotification")
    Call<NotificationModel> getNotificationByTasks(@Header("Authorization") String authorization);

    @GET("Notification/GetFollowUpNotification")
    Call<NotificationModel> getNotificationByFollowUps(@Header("Authorization") String authorization);

    @POST("Appointment/GetReminder")
    Call<AddAppointmentModel> getReminder(@Header("Authorization") String authorization);

    @POST("Appointment/GetSendVia")
    Call<AddAppointmentModel> getVia(@Header("Authorization") String authorization);

    @GET("Lead/GetLocationAutoCompleteDropdown")
    Call<GetLocationModel> getLocationByPrefix(@Header("Authorization") String authorization,
                                               @Query("Prefix") String prefix);

    @GET("Lead/GetOpenHouseSearchDropdown")
    Call<AddressDetailModel> getAddressDetailByPrefix(@Header("Authorization") String authorization,
                                                      @Query("Prefix") String prefix,
                                                      @Query("Type") String type);

}