package com.project.jarjamediaapp.Activities.communications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.CommunicationAdapter;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.databinding.ActivityCommunicationsBinding;

import retrofit2.Response;

public class CommunicationsActivity extends BaseActivity implements CommunicationlContract.View {

    ActivityCommunicationsBinding activityCommunicationsBinding;
    LinearLayoutManager linearLayoutManager;
    CommunicationAdapter communicationAdapter;
    CommunicationPresenter communicationPresenter;
    String leadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_communications);

        activityCommunicationsBinding = DataBindingUtil.setContentView(this, R.layout.activity_communications);
        communicationPresenter = new CommunicationPresenter(this);
        setToolBarTitle(activityCommunicationsBinding.epToolbar.toolbar, getString(R.string.communication), true);
        communicationAdapter = new CommunicationAdapter(this,null);
        leadId = getIntent().getStringExtra("leadID");
        communicationPresenter.initScreen();


        setRecyclerView();


    }

    private void setRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityCommunicationsBinding.rvCommunication.setLayoutManager(linearLayoutManager);
        activityCommunicationsBinding.rvCommunication.setAdapter(communicationAdapter);


    }

    @Override
    public void initViews() {

        communicationPresenter.getCommunications(leadId);


    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

    }

    @Override
    public void updateUI(CommunicationModel communicationModel) {

        communicationAdapter.setData(communicationModel.data);


    }


    @Override
    public void updateUIonFalse(String message) {

    }

    @Override
    public void updateUIonError(String error) {

    }

    @Override
    public void updateUIonFailure() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}