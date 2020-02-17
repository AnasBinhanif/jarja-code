package com.project.jarjamediaapp.Activities.notes;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetNoteDropDown;

public interface NotesContract {

    interface View extends BaseContract.View{

        void initViews();

        void updateUI(GetNoteDropDown response);

        void updateUI(GetLeadNotes response);

        void updateUI(GetAgentsModel response);

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

        void editNote(String leadID,String noteID,String Desc);

        void getNoteDropDown();

        void getAgentNames();

        void getLeadNotes(String encryptedLeadID);

        void addNote(String noteID,String leadID,String noteType,String desc,String isSticky,String dated,String agentIDs,String leadStringID);

        void initScreen();

    }

}
