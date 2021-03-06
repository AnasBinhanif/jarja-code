package com.project.jarjamediaapp.Networking;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Activities.add_task.GetTaskDetail;
import com.project.jarjamediaapp.Activities.calendar.CalendarModel;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarAppointmentDetailModel;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarTaskDetailModel;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordModel;
import com.project.jarjamediaapp.Activities.lead_detail.GetCallerId;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailModel;
import com.project.jarjamediaapp.Activities.listing_info.ListingInfoModel;
import com.project.jarjamediaapp.Activities.notes.DocumentModel;
import com.project.jarjamediaapp.Activities.notification.AppointmentNotificationModel;
import com.project.jarjamediaapp.Activities.notification.FollowUpNotificationModel;
import com.project.jarjamediaapp.Activities.notification.TaskNotificationModel;
import com.project.jarjamediaapp.Activities.open_houses.AddressDetailModel;
import com.project.jarjamediaapp.Activities.open_houses.GetAllOpenHousesModel;
import com.project.jarjamediaapp.Activities.open_houses.GetTimeFrameModel;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Activities.open_houses.UserWebsites;
import com.project.jarjamediaapp.Activities.transactions.TransactionModel;
import com.project.jarjamediaapp.Activities.user_profile.GetPermissionModel;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetCountries;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.Models.GetLastLogin;
import com.project.jarjamediaapp.Models.GetLastTouch;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadCounts;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetLeadScore;
import com.project.jarjamediaapp.Models.GetLeadSocialProfile;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.Models.GetPipeline;
import com.project.jarjamediaapp.Models.GetPropertyLeads;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.Models.GetTimeZoneList;
import com.project.jarjamediaapp.Models.GetTwilioNumber;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Models.GmailCalender;
import com.project.jarjamediaapp.Models.UpgradeSocialProfile;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiMethods {


    @FormUrlEncoded
    @POST("token")
    Call<AccessCode> getToken(@Field("UserName") String username,
                              @Field("Password") String password,
                              @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("User/ForgetPassword")
    Call<ForgotPasswordModel> fogetPassword(@Field("email") String email);

    @GET("User/GetUserProfileData")
    Call<GetUserProfile> getUserProfileData(@Header("Authorization") String authorization);

    @GET("Lead/GetTwilioPhoneNumber")
    Call<GetTwilioNumber> getTwilioNumber(@Header("Authorization") String authorization);

    @GET("User/GetTimeZoneList")
    Call<GetTimeZoneList> GetTimeZoneList(@Header("Authorization") String authorization);

    @GET("Lead/GetCountries")
    Call<GetCountries> GetCountries(@Header("Authorization") String authorization);

    @GET("User/Authanticate_UserDevice")
    Call<BaseResponse> Authanticate_UserDevice(@Header("Authorization") String authorization,
                                               @Query("DeviceToken") String deviceToken,
                                               @Query("network_protocol") String network_protocol,
                                               @Query("email") String email,
                                               @Query("UserToken") String accesToken);

    @GET("User/UnAuthanticate_UserDevice")
    Call<BaseResponse> UnAuthanticate_UserDevice(@Header("Authorization") String authorization,
                                                 @Query("DeviceToken") String deviceToken,
                                                 @Query("network_protocol") String network_protocol);

    @FormUrlEncoded
    @POST("Lead/UpdateProfileInfo")
    Call<BaseResponse> UpdateProfileInfo(@Header("Authorization") String authorization,
                                         @Field("firstName") String userId,
                                         @Field("state") String state,
                                         @Field("licenseNo") String licenseNo,
                                         @Field("picName") String picName,
                                         @Field("companyAddress") String companyAddress,
                                         @Field("agentType") String agentType,
                                         @Field("zipCode") String zipCode,
                                         @Field("streetAddress") String streetAddress,
                                         @Field("title") String title,
                                         @Field("countryId") int countryId,
                                         @Field("forwardedNumber") String forwardedNumber,
                                         @Field("leadDistributionMessageEnabled") boolean leadDistributionMessageEnabled,
                                         @Field("emailAddress") String emailAddress,
                                         @Field("company") String company,
                                         @Field("lastName") String lastName,
                                         @Field("tmzone") String tmzone,
                                         @Field("picGuid") String picGuid,
                                         @Field("phone") String phone,
                                         @Field("city") String city,
                                         @Field("virtualNumber") String virtualNumber);

    @Headers("Content-Type: application/json")
    @POST("Lead/UpdateProfileInfo")
    Call<BaseResponse> UpdateProfileInfo(@Header("Authorization") String hdr, @Body String body);


    @GET("User/GetUserPermission")
    Call<GetPermissionModel> GetUserPermission(@Header("Authorization") String authorization);

    @GET("Appointment/GetTodayAppointment")
    Call<GetAppointmentsModel> GetTodayAppointment(@Header("Authorization") String authorization,
                                                   @Query("pageNumber") int page);

    @GET("Appointment/GetAppointmentByID")
    Call<GetAppointmentByIDModel> GetAppointmentByID(@Header("Authorization") String authorization,
                                                     @Query("EncryptedAppointmentID") String appointmentID);

    @GET("Appointment/GetPreviousAppointment")
    Call<GetAppointmentsModel> GetPreviousAppointment(@Header("Authorization") String authorization,
                                                      @Query("PageNo") int page);

    @GET("Appointment/GetUpcomingAppointment")
    Call<GetAppointmentsModel> GetUpcomingAppointment(@Header("Authorization") String authorization,
                                                      @Query("pageNumber") int page);

    @FormUrlEncoded
    @POST("Appointment/MarkComplete")
    Call<BaseResponse> MarkComplete(@Header("Authorization") String authorization,
                                    @Field("encryptedLeadAppoinmentID") String leadAppoinmentID,
                                    @Field("state") String state);

    @GET("FollowUp/GetDueFollowUps")
    Call<GetFollowUpsModel> GetFollowUpsDue(@Header("Authorization") String authorization,
                                            @Query("PageNo") int page);

    @GET("FollowUp/GetOverDueFollowUps")
    Call<GetFollowUpsModel> GetFollowUpsOverDue(@Header("Authorization") String authorization,
                                                @Query("PageNo") int page);

    @GET("FollowUp/GetFollowUpDetails_New")
    Call<ViewFollowUpModel> GetFollowUpDetails(@Header("Authorization") String authorization,
                                               @Query("id") String dripDetailedId, @Query("reminderId") String reminderId);

    @FormUrlEncoded
    @POST("FollowUp/MarkComplete")
    Call<BaseResponse> MarkFollowUpComplete(@Header("Authorization") String authorization,
                                            @Field("encrypted_ReminderId") String reminderDI,
                                            @Field("state") String state);

    @GET("Tasks/GetDueTasksNew")
    Call<GetTasksModel> GetDueTasks(@Header("Authorization") String authorization,
                                    @Query("PageNo") int page);

    @GET("Tasks/GetOverDueTasksNew")
    Call<GetTasksModel> GetOverDueTasks(@Header("Authorization") String authorization,
                                        @Query("PageNo") int page);

    @GET("Tasks/GetFutureTasksNew")
    Call<GetTasksModel> GetFutureTasks(@Header("Authorization") String authorization,
                                       @Query("PageNo") int page);

    @FormUrlEncoded
    @POST("Tasks/MarkComplete")
    Call<BaseResponse> MarkTaskComplete(@Header("Authorization") String authorization,
                                        @Field("encrypted_TaskID") String reminderDI,
                                        @Field("state") String state);

    @GET("Dashboard/GetLeadTitles")
    Call<GetLeadTitlesModel> GetLeadTitlesModel(@Header("Authorization") String authorization,
                                                @Query("Name") String name);

    @GET("Dashboard/GetAgents")
    Call<GetAgentsModel> GetAgents(@Header("Authorization") String authorization);

    @GET("Dashboard/GetLeadTimeFrame")
    Call<GetLeadTimeFrame> GetLeadTimeFrame(@Header("Authorization") String authorization);

    @GET("Lead/GetSources")
    Call<GetLeadSource> GetLeadSource(@Header("Authorization") String authorization);

    @GET("Lead/GetLeadScore")
    Call<GetLeadScore> GetLeadScore(@Header("Authorization") String authorization);

    @GET("Lead/GetLastTouch")
    Call<GetLastTouch> GetLastTouch(@Header("Authorization") String authorization);

    @GET("Lead/GetLastLogin")
    Call<GetLastLogin> GetLastLogin(@Header("Authorization") String authorization);

    @GET("Lead/GetPipeline")
    Call<GetPipeline> GetPipeline(@Header("Authorization") String authorization);

    @GET("Lead/GetDueFollowUps")
    Call<GetFollowUpsModel> GetDueFollowUps(@Header("Authorization") String authorization,
                                            @Query("Encrypted_LeadID") String leadID,
                                            @Query("PageNo") int page);

    @GET("Lead/GetOverDueFollowUps")
    Call<GetFollowUpsModel> GetOverDueFollowUps(@Header("Authorization") String authorization,
                                                @Query("Encrypted_LeadID") String leadID,
                                                @Query("PageNo") int page);

    @GET("Lead/GetTodayAppointmentByLeadID")
    Call<GetAppointmentsModel> GetTodayAppointmentByLeadID(@Header("Authorization") String authorization,
                                                           @Query("Encrypted_LeadID") String leadID,
                                                           @Query("pageNumber") int page);

    @GET("Lead/GetUpcomingAppointmentByLeadID")
    Call<GetAppointmentsModel> GetUpcomingAppointmentByLeadID(@Header("Authorization") String authorization,
                                                              @Query("Encrypted_LeadID") String leadID,
                                                              @Query("pageNumber") int page);


    @GET("Lead/GetLeadDueTasksNew")
    Call<GetTasksModel> GetLeadDueTasksNew(@Header("Authorization") String authorization,
                                           @Query("LeadID") String leadID,
                                           @Query("PageNo") int page);

    @GET("Lead/GetLeadOverDueTasks")
    Call<GetTasksModel> GetLeadOverDueTasks(@Header("Authorization") String authorization,
                                            @Query("LeadID") String leadID,
                                            @Query("PageNo") int page);

    @GET("Lead/GetLeadFutureTasksNew")
    Call<GetTasksModel> GetLeadFutureTasksNew(@Header("Authorization") String authorization,
                                              @Query("LeadID") String leadID,
                                              @Query("PageNo") int page);

    @GET("Lead/GetPreviousAppointmentByLeadID")
    Call<GetAppointmentsModel> GetPreviousAppointmentByLeadID(@Header("Authorization") String authorization,
                                                              @Query("Encrypted_LeadID") String leadID,
                                                              @Query("pageNumber") int page);

    @GET("Lead/GetLead")
    Call<GetLead> GetLead(@Header("Authorization") String authorization,
                          @Query("LeadID") String leadID);

    @FormUrlEncoded
    @POST("Lead/AssignAgentToLead")
    Call<BaseResponse> AssignAgentToLead(@Header("Authorization") String authorization,
                                         @Field("agentIds") String agentIds,
                                         @Field("leadStringID") String leadStringID,
                                         @Field("typeIndex") boolean typeIndex);

    @POST("Lead/SetPrimaryAgent")
    Call<BaseResponse> setPrimaryAgent(@Header("Authorization") String header, @Query("LeadStringID") String encryptedLeadId, @Query("AgentStringID") String encryptedAgentId);

    @Headers("Content-Type: application/json")
    @POST("Lead/AddPipeLineMark")
    Call<BaseResponse> AddPipeLineMark(@Header("Authorization") String authorization,
                                       @Body String body);

    @POST("Lead/GetAgentCommissionByLeadDetail")
    Call<TransactionModel> getAgentCommissionViaLead(@Header("Authorization") String authorization,
                                                     @Query("Encrypted_LeadID") String encryptedLeadID,
                                                     @Query("EncryptedLeadDetailID") String encrypted_LeadDetailID);

    @Headers("Content-Type: application/json")
    @POST("Lead/AddCommissionTrasactionClose")
    Call<BaseResponse> addAgentCommissionViaLead(@Header("Authorization") String authorization,
                                                 @Body String body);


    @GET("Lead/GetLeadTransactionStage")
    Call<GetLeadTransactionStage> GetLeadTransactionStage(@Header("Authorization") String authorization,
                                                          @Query("LeadId") String leadID);


    @GET("Lead/GetSocialProfileDropdown")
    Call<GetSocialProfileDropdown> GetSocialProfileDropdown(@Header("Authorization") String authorization);

    @GET("Lead/GetLeadSocialProfile")
    Call<GetLeadSocialProfile> GetAllSocialProfiles(@Header("Authorization") String authorization,
                                                    @Query("LeadID") String leadID);

    @POST("Lead/UpgradeSocialProfile")
    Call<UpgradeSocialProfile> UpgradeSocialProfile(@Header("Authorization") String authorization,
                                                    @Query("LeadID") String leadID);

    @FormUrlEncoded
    @POST("Lead/AddSocialProfile")
    Call<BaseResponse> AddSocialProfile(@Header("Authorization") String authorization,
                                        @Field("leadID") String leadID,
                                        @Field("name") String name,
                                        @Field("siteName") String siteName,
                                        @Field("profilelink") String profilelink);

    @GET("Lead/GetNoteDropDown")
    Call<GetNoteDropDown> GetNoteDropDown(@Header("Authorization") String authorization);

    @GET("Lead/GetLeadNotes")
    Call<GetLeadNotes> GetLeadNotes(@Header("Authorization") String authorization,
                                    @Query("EncryptedLeadID") String leadID);

    @POST("Lead/EditNote")
    Call<BaseResponse> EditNote(@Header("Authorization") String authorization,
                                @Query("Encrypted_NoteID") String NoteID,
                                @Query("Encrypted_LeadStringID") String LeadID,
                                @Query("Description") String Description);

    @POST("Lead/Make_Lead_Note_Sticky")
    Call<BaseResponse> Make_Lead_Note_Sticky(@Header("Authorization") String authorization,
                                             @Query("Encrypted_NoteID") String NoteID,
                                             @Query("Encrypted_LeadStringID") String LeadID,
                                             @Query("IsSticky") Boolean isSticky);

    @FormUrlEncoded
    @POST("Lead/DeleteNote")
    Call<BaseResponse> DeleteNote(@Header("Authorization") String authorization,
                                  @Field("noteID") String leadAppoinmentID);

    @GET("Lead/GetTagListByLeadID")
    Call<GetTagListByLeadID> GetTagListByLeadID(@Header("Authorization") String authorization,
                                                @Query("Encrypted_LeadID") String leadID);

    @POST("Lead/AssignTagsToLeads")
    Call<BaseResponse> AssignTagsToLeads(@Header("Authorization") String authorization,
                                         @Query("Encrypted_LeadStringID") String leadID,
                                         @Query("TagIds") String tagsId);

    @POST("Lead/DeleteLeadTag")
    Call<BaseResponse> DeleteLeadTag(@Header("Authorization") String authorization,
                                     @Query("LeadId") String leadID,
                                     @Query("LabelId") String tagsId);

    @POST("Lead/UpdateUser_Password")
    Call<BaseResponse> UpdatePassword(@Header("Authorization") String authorization,
                                      @Query("userid") String userID,
                                      @Query("OldPassword") String oldPassword,
                                      @Query("Password") String password);

    @FormUrlEncoded
    @POST("Lead/AddNote")
    Call<BaseResponse> AddNote(@Header("Authorization") String authorization,
                               @Field("noteID") String noteID,
                               @Field("leadID") String leadID,
                               @Field("noteType") String noteType,
                               @Field("desc") String desc,
                               @Field("isSticky") String isSticky,
                               @Field("dated") String dated,
                               @Field("agentIDs") String agentIDs,
                               @Field("leadStringID") String leadStringID);


    @GET("Dashboard/GetLeadDripCampaignList")
    Call<GetLeadDripCampaignList> GetLeadDripCampaignList(@Header("Authorization") String authorization);

    @GET("Dashboard/GetLeadTagList")
    Call<GetLeadTagList> GetLeadTagList(@Header("Authorization") String authorization);

    @GET("Dashboard/GetLeadTypeList")
    Call<GetLeadTypeList> GetLeadTypeList(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("Appointment/AddNew")
    Call<BaseResponse> AddAppointment(@Header("Authorization") String authorization,
                                      @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Appointment/Update")
    Call<BaseResponse> UpdateAppointment(@Header("Authorization") String authorization,
                                         @Body String body);

    @GET("Tasks/GetTasks")
    Call<GetTaskDetail> getTaskDetail(@Header("Authorization") String authorization,
                                      @Query("TaskID") String taskId);

    @GET("Tasks/GetFutureTask")
    Call<GetTaskDetail> getFutureTaskDetail(@Header("Authorization") String authorization,
                                            @Query("ScheduleID") String taskId);

    @Headers("Content-Type: application/json")
    @POST("Tasks/AddTaskNew")
    Call<BaseResponse> AddTask(@Header("Authorization") String authorization,
                               @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Tasks/UpdateTaskNew")
    Call<BaseResponse> UpdateTask(@Header("Authorization") String authorization,
                                  @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Lead/AddNewLead")
    Call<BaseResponse> AddLead(@Header("Authorization") String authorization,
                               @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Lead/Update")
    Call<BaseResponse> UpdateLead(@Header("Authorization") String authorization,
                                  @Body String body);

    @FormUrlEncoded
    @POST("Lead/GetLeadCounts")
    Call<GetLeadCounts> GetLeadCounts(@Header("Authorization") String authorization,
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
                                      @Field("registeredDateAsc") boolean registeredDateAsc,
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
                                      @Field("ResultSetType") String resultSetType,
                                      @Field("pageNo") String pageNo,
                                      @Field("pageSize") String pageSize);

    @GET("Lead/SearchLead")
    Call<GetAllLeads> SearchLead(@Header("Authorization") String authorization,
                                 @Query("PageNo") int PageNo,
                                 @Query("Search") String Search);

    @Headers("Content-Type: application/json")
    @POST("Lead/GetAllLead")
    Call<GetAllLeads> GetAllLead(@Header("Authorization") String authorization,
                                 @Body String body);

    @GET("Lead/GetPropertyLeads")
    Call<GetPropertyLeads> GetPropertyLeads(@Header("Authorization") String authorization,
                                            @Query("PropertyID") String propertyId);

    @GET("Lead/GetAllOpenHouse")
    Call<GetAllOpenHousesModel> getAllOpenHouses(@Header("Authorization") String authorization,
                                                 @Query("type") String type);

    @GET("Lead/GetUserWebSites")
    Call<UserWebsites> getUserWebsites(@Header("Authorization") String header);

    @Multipart
    @POST("Lead/UploadImage")
    Call<Upload_ProfileImage> uploadFileToServer(@Header("Authorization") String authorization,
                                                 @Part MultipartBody.Part file,
                                                 @Query("image") String image);

    @Multipart
    @POST("Lead/Upload_ProfileImage")
    Call<Upload_ProfileImage> Upload_ProfileImage(@Header("Authorization") String authorization,
                                                  @Part MultipartBody.Part file,
                                                  @Query("image") String image);

    @Multipart
    @POST("Lead/UploadEmailDoc")
    Call<UploadImageModel> uploadEmailAttachedFile(@Header("Authorization") String authorization,
                                                   @Part MultipartBody.Part file,
                                                   @Query("image") String image,
                                                   @Query("EmailFrom") String emailFrom);

    @Headers("Content-Type: application/json")
    @POST("Lead/AddOpenHouse")
    Call<BaseResponse> addOpenHouse(@Header("Authorization") String authorization,
                                    @Body String body);

    @GET("Notification/GetNotificationCount")
    Call<UploadImageModel> getNotificationCount(@Header("Authorization") String authorization);

    @GET("Notification/GetAppointmentNotification")
    Call<AppointmentNotificationModel> getNotificationByAppointments(@Header("Authorization") String authorization, @Query("pageNumber") int page);

    @GET("Notification/GetTaskNotification")
    Call<TaskNotificationModel> getNotificationByTasks(@Header("Authorization") String authorization, @Query("Pageno") int page);

    @GET("Notification/Get_FollowUp_Notification")
    Call<FollowUpNotificationModel> getNotificationByFollowUps(@Header("Authorization") String authorization, @Query("Pageno") int page);

    @POST("Appointment/GetReminder")
    Call<AddAppointmentModel> getReminder(@Header("Authorization") String authorization);

    @POST("Appointment/GetSendVia")
    Call<AddAppointmentModel> getVia(@Header("Authorization") String authorization);

    @POST("Tasks/GetTypes")
    Call<AddAppointmentModel> getTypes(@Header("Authorization") String authorization);

    @POST("Tasks/GetRecur")
    Call<AddAppointmentModel> getRecur(@Header("Authorization") String authorization);

    @GET("Lead/GetLocationAutoCompleteDropdown")
    Call<GetLocationModel> getLocationByPrefix(@Header("Authorization") String authorization,
                                               @Query("Prefix") String prefix);

    @GET("Lead/GetOpenHouseSearchDropdown")
    Call<AddressDetailModel> getAddressDetailByPrefix(@Header("Authorization") String authorization,
                                                      @Query("Prefix") String prefix,
                                                      @Query("Type") String type);

    @GET("Lead/GetLeadListingInfo")
    Call<ListingInfoModel> getLeadListingInfo(@Header("Authorization") String authorization,
                                              @Query("LeadId") String leadId);

    @GET("Lead/GetLeadBuyingInfo")
    Call<ListingInfoModel> getLeadBuyingInfo(@Header("Authorization") String authorization,
                                             @Query("LeadId") String leadId);

    @GET("Lead/GetLeadEmailPopUpDetail")
    Call<LeadDetailModel> getLeadRecipient(@Header("Authorization") String authorization,
                                           @Query("LeadId") String leadId);

    @FormUrlEncoded
    @POST("Lead/SendLeadEmail")
    Call<BaseResponse> composeEmail(@Header("Authorization") String authorization,
                                    @Field("email_From") String from,
                                    @Field("emailTo") String[] to,
                                    @Field("email_Cc") String cc,
                                    @Field("email_Bcc") String bcc,
                                    @Field("subject") String subject,
                                    @Field("body") String body,
                                    @Field("lead_ID") String leadId,
                                    @Field("attachFileLink") String fileUrl,
                                    @Field("lead_EncryptedID") String lead_EncryptedID);

    @GET("Lead/GetLeadCallDetail")
    Call<GetCallerId> getCallerId(@Header("Authorization") String authorization,
                                  @Query("LeadID") String LeadID);

    @FormUrlEncoded
    @POST("Lead/SendLeadMessage")
    Call<BaseResponse> sendMessage(@Header("Authorization") String authorization,
                                   @Field("fromNumber") String fromNumber,
                                   @Field("message") String message,
                                   @Field("lead_EncryptedID") String leadId);

    @GET("Calender/GetCalendarData")
    Call<CalendarModel> getCalendarEvents(@Header("Authorization") String authorization,
                                          @Query("Encrypted_AgentIDs") String encryptedAgentId,
                                          @Query("CalendarId") String calendarId,
                                          @Query("month") String month,
                                          @Query("year") String year);

    @POST("Calender/GetUpdateFrom_GmailCalender")
    Call<GmailCalender> getGmailCalender(@Header("Authorization") String authorization);


    @GET("Calender/GetCalendartAppointmentPreview")
    Call<CalendarAppointmentDetailModel> getCalendarAppointmentDetail(@Header("Authorization") String authorization,
                                                                      @Query("CalendarId") String calendarId,
                                                                      @Query("dtStart") String dtStart);

    @GET("Calender/GetCalendarTaskPreview")
    Call<CalendarTaskDetailModel> getCalendarTaskDetail(@Header("Authorization") String authorization,
                                                        @Query("CalendarId") String calendarId,
                                                        @Query("dtStart") String dtStart);

    @POST("Calender/DeleteCalendarAppointment")
    Call<BaseResponse> deleteCalendarAppointment(@Header("Authorization") String authorization,
                                                 @Query("CalendarId") String calendarId);

    @POST("Calender/DeleteCalendarTask")
    Call<BaseResponse> deleteCalendarTask(@Header("Authorization") String authorization,
                                          @Query("CalendarId") String calendarId);


    @Headers("Content-Type: application/json")
    @POST("Calender/AddAppoinmentTaskCalender")
    Call<BaseResponse> addAppointmentByCalendar(@Header("Authorization") String authorization,
                                                @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Calender/EditAppoinmentTaskCalender")
    Call<BaseResponse> updateAppointmentTaskByCalendar(@Header("Authorization") String authorization,
                                                       @Body String body);


    @GET("Lead/GetLeadDocDetail")
    Call<DocumentModel> getDocumentByLeadId(@Header("Authorization") String authorization,
                                            @Query("LeadID") String leadId);

    @POST("Lead/DeleteLeadDoc")
    Call<BaseResponse> deleteDocumentByDocumentId(@Header("Authorization") String authorization,
                                                  @Query("DocID") String DocId);

    @Multipart
    @POST("Lead/AddLeadDoc")
    Call<BaseResponse> uploadDocumentByLeadId(@Header("Authorization") String authorization,
                                              @Part MultipartBody.Part file,
                                              @Query("LeadID") String leadId);

    @GET("Dashboard/GetLeadTimeFrame")
    Call<GetTimeFrameModel> getTimeFrame(@Header("Authorization") String authorization);

    @GET("Lead/GetPropertyAmountRangeDropDownList")
    Call<GetTimeFrameModel> getAmountRange(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("Lead/CreateOpenHouseLead")
    Call<BaseResponse> addLeadViaOpenHouse(@Header("Authorization") String authorization,
                                           @Field("propertyId") int propertyId,
                                           @Field("firstName") String firstName,
                                           @Field("lastName") String lastName,
                                           @Field("primaryEmail") String primaryEmail,
                                           @Field("primaryPhone") String primaryPhone,
                                           @Field("withAgent") boolean withAgent,
                                           @Field("timeFrame") String timeFrame,
                                           @Field("houseSell") boolean houseSell,
                                           @Field("preQual") boolean preQual,
                                           @Field("priceRange") String priceRange);

    @GET("Lead/Get_LeadSaveSearch")
    Call<ResponseBody> getLeadSearchFilters(@Header("Authorization") String hdr);


    @GET("Lead/GetLead_Communication")
    Call<CommunicationModel> getLeadCommunications(@Header("Authorization") String auth, @Query("Encrypted_LeadID") String encryptedLeadId);
}