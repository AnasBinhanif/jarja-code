package com.project.jarjamediaapp.Activities.lead_detail;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;

public interface LeadDetailContract {

    interface View extends BaseContract.View{
        void initViews();

        void updateUI(GetAgentsModel response);

        void updateUI(GetLead response);

        void updateUI(GetLeadTransactionStage response);

        void updateUIonFalse(String message);

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

        void assignAgents(String agentsIDs,String leadID,String typeIndex);

        void getAgentNames();

        void getLead(String leadID);

        void getTransaction(String leadID);

        void initScreen();

    }

}
