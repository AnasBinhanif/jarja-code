package com.project.jarjamediaapp.Activities.add_lead;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadDripCampaignList;
import com.project.jarjamediaapp.Models.GetLeadSource;
import com.project.jarjamediaapp.Models.GetLeadTagList;
import com.project.jarjamediaapp.Models.GetLeadTimeFrame;
import com.project.jarjamediaapp.Models.GetLeadTypeList;

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
        void addLead(String firstName, String lastName, String spousname, String company, String cellPhone, String primaryPhone,String primaryEmail,
                     String dateOfBirth, boolean isBirthDayNotify, String dateOfMarriage, boolean isAnniversaryNotify, String leadAgentIDs, String allAgentIds, String alldripcampaignids,
                     String notes, String b_PreQual, String address, String street, String zipcode, String city, String state, String description, String source,
                     String county, String timeFrameId, String state2, String city2, String zipcode2, int leadTypeID,String emailList, String phoneList, String labelsID,
                     String leadStringID, String countryid);

        void updateLead(String firstName, String lastName, String spousname, String company, String cellPhone, String primaryPhone,String primaryEmail,
                     String dateOfBirth, boolean isBirthDayNotify, String dateOfMarriage, boolean isAnniversaryNotify, String leadAgentIDs, String allAgentIds, String alldripcampaignids,
                     String notes, String b_PreQual, String address, String street, String zipcode, String city, String state, String description, String source,
                     String county, String timeFrameId, String state2, String city2, String zipcode2, int leadTypeID,String emailList, String phoneList, String labelsID,
                     String leadStringID, String countryid);

    }

}
