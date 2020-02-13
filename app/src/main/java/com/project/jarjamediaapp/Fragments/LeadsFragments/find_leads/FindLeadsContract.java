//Contract
package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;


import com.project.jarjamediaapp.Models.GetLeadCounts;

public interface FindLeadsContract {

    /*
    View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
    interface View {
        void setupViews();

        void updateUI(GetLeadCounts response);

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
    interface Actions {
        void initScreen();

        void getLeadCounts(String leadID, String spouseName, String email, String company, String phone, String address, String city, String state,
                           String county, String zip, String countryID, String propertyType, String timeFrameID, String preApproval, String houseToSell,
                           String agentID, String leadTypeID, String leadScoreMin, String leadScoreMax, String tagsID, String priceMin, String priceMax,
                           String notes, String dripCompaignID, String lastTouch, String lastLogin, String pipelineID, String sourceID, String fromDate,
                           String toDate, String searchBy, String firstNameAsc, String lastNameAsc, String emailAddressAsc, String registeredDateAsc,
                           String lastLoginedInAsc, String lastLoginedCountAsc, String lastTouchedInAsc, String conversationCellAsc, String conversationEmailAsc,
                           String conversationMsgAsc, String priceAsc, String cityAsc, String timeFrameAsc, String activitiesSavedSearchAsc,
                           String activitiesViewAsc, String activitiesFavoriteAsc, String isSaveSearch, String isFilterClear, String resultSetType,
                           String pageNo, String pageSize);
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
    */
    interface Repository {

    }

}
