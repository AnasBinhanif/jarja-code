package com.project.jarjamediaapp.Activities.add_lead;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTypeList;
import com.project.jarjamediaapp.Models.GetPropertyLeads;

import retrofit2.http.Field;

public interface AddLeadContract {

    interface View extends BaseContract.View{


        void initViews();

        void updateUI(GetAgentsModel response);

        void updateUI(GetLeadSource response);

        void updateUI(GetLeadTagList response);

        void updateUI(GetLeadTypeList response);

        void updateUI(GetLeadTimeFrame response);

        void updateUI(GetLeadDripCampaignList response);

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

        void initScreen();

        void getAgentNames();
        void GetLeadSource();
        void GetLeadTagList();
        void GetLeadTypeList();
        void GetLeadTimeFrame();
        void GetLeadDripCampaignList();
        void addLead(String body);

        void updateLead(String body);

    }

}
