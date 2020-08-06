package com.project.jarjamediaapp.Activities.user_profile;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetCountries;
import com.project.jarjamediaapp.Models.GetTimeZoneList;
import com.project.jarjamediaapp.Models.GetTwilioNumber;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;

import okhttp3.MultipartBody;
import retrofit2.Response;

public interface UserProfileContract {

    interface View extends BaseContract.View {

        void updateUIonFalse(String message);

        void updateUI(GetUserProfile getUserProfile);

        void updateUI(GetTwilioNumber getUserProfile);

        void updateUI(BaseResponse getUserProfile);

        void updateUI(GetTimeZoneList getUserProfile);

        void updateUI(GetCountries getUserProfile);

        void updateUI(Upload_ProfileImage getUserProfile);

        void updateUIonError(String error);

        void updateUIonFailure();

        void showProgressBar();

        void hideProgressBar();

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void initScreen();
        void getUserProfile();
        void getTimeZoneList();
        void getCountries();
        void getTwilioNumber();
        void uploadImage(MultipartBody.Part file);
        void updateUserProfile(String firstName, String state, String licenseNo, String picName, String companyAddress, String agentType,
                               String zipCode, String streetAddress, String title, int countryId, String forwardedNumber,
                               boolean leadDistributionMessageEnabled, String emailAddress, String company, String lastName, String tmzone,
                               String picGuid, String phone, String city,String virtualNumber);

    }

}
