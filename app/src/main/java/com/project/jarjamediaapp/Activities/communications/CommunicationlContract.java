package com.project.jarjamediaapp.Activities.communications;

import com.project.jarjamediaapp.Activities.lead_detail.GetCallerId;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailModel;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;

import okhttp3.MultipartBody;
import retrofit2.Response;

public interface CommunicationlContract {

    interface View extends BaseContract.View {

        void updateUI(CommunicationModel communicationModel);

        void initViews();

        void showProgressBar();

        void hideProgressBar();

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getCommunications(String encryptedID);


    }

}
