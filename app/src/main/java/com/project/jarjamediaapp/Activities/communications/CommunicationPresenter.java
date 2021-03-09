package com.project.jarjamediaapp.Activities.communications;

import android.net.NetworkRequest;
import android.util.Log;

import com.google.gson.Gson;
import com.project.jarjamediaapp.Activities.lead_detail.GetCallerId;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailModel;
import com.project.jarjamediaapp.Activities.notes.NotesContract;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLead;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunicationPresenter extends BasePresenter<CommunicationlContract.View> implements CommunicationlContract.Actions {


    Call<CommunicationModel> call;

    public CommunicationPresenter(CommunicationlContract.View view) {
        super(view);
    }

    @Override
    public void getCommunications(String encryptedID) {

        call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class)
                .getLeadCommunications(GH.getInstance().getAuthorization(),
                        encryptedID);
       call.enqueue(new Callback<CommunicationModel>() {
           @Override
           public void onResponse(Call<CommunicationModel> call, Response<CommunicationModel> response) {

               if (response.isSuccessful()){

                   CommunicationModel communicationModel = response.body();
                   Log.d("onResponse: ",communicationModel.toString());
                   _view.updateUI(communicationModel);
               }


           }

           @Override
           public void onFailure(Call<CommunicationModel> call, Throwable t) {

           }
       });


    }

    @Override
    public void initScreen() {
        _view.initViews();

    }
}
