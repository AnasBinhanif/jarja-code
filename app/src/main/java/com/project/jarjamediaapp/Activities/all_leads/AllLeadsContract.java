package com.project.jarjamediaapp.Activities.all_leads;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetPropertyLeads;

public interface AllLeadsContract {

    interface View extends BaseContract.View{

        void initViews();

        void updateUI(GetAllLeads response);

        void updateUI(GetPropertyLeads response);

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

        void GetPropertyLeads(String propertyID);
        void getAllLeads(String leadID, String spouseName, String email, String company, String phone, String address, String city, String state,
                           String county, String zip, String countryID, String propertyType, String timeFrameID, String preApproval, String houseToSell,
                           String agentID, String leadTypeID, String leadScoreMin, String leadScoreMax, String tagsID, String priceMin, String priceMax,
                           String notes, String dripCompaignID, String lastTouch, String lastLogin, String pipelineID, String sourceID, String fromDate,
                           String toDate, String searchBy, String firstNameAsc, String lastNameAsc, String emailAddressAsc, String registeredDateAsc,
                           String lastLoginedInAsc, String lastLoginedCountAsc, String lastTouchedInAsc, String conversationCellAsc, String conversationEmailAsc,
                           String conversationMsgAsc, String priceAsc, String cityAsc, String timeFrameAsc, String activitiesSavedSearchAsc,
                           String activitiesViewAsc, String activitiesFavoriteAsc, String isSaveSearch, String isFilterClear, String resultSetType,
                           String pageNo, String pageSize);

        void initScreen();

    }

}
