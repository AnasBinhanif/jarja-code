package com.project.jarjamediaapp.Activities.tags;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;

public interface TagsContract {

    interface View extends BaseContract.View{

        void initViews();

        void updateUI(GetTagListByLeadID response);

        void updateUIonFalse(String message);

        void updateUIonError(String error);

        void updateUIonFailure();

        void showProgressBar();

        void hideProgressBar();

        void updateUIOnTagDelete();
    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getTags(String encryptedLeadId);

        void assignTags(String encryptedLeadId,String tagsIds);

        void initScreen();

    }

}
