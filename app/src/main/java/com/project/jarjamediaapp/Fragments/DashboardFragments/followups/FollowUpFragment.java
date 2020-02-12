package com.project.jarjamediaapp.Fragments.DashboardFragments.followups;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeFollowUpsDueRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentFollowupBinding;

import java.util.ArrayList;


public class FollowUpFragment extends BaseFragment implements FragmentLifeCycle, FollowUpContract.View, View.OnClickListener {

    FragmentFollowupBinding bi;
    Context context;
    FollowUpPresenter presenter;
    SwipeFollowUpsDueRecyclerAdapter swipeFollowUpsDueRecyclerAdapter;
    boolean isFromActivity;
    ArrayList<GetFollowUpsModel.Data> followUpsList = new ArrayList<>();

    public FollowUpFragment() {
        // Required empty public constructor
    }

    public static FollowUpFragment newInstance(String fragment_title, boolean fromActivity) {
        FollowUpFragment followUpFragment = new FollowUpFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putBoolean("isFromActivity", fromActivity);
        followUpFragment.setArguments(args);
        return followUpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_followup, container, false);
        presenter = new FollowUpPresenter(this);
        presenter.initScreen();


        return bi.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setupViews() {

        initViews();
        presenter.getDueFollowUps();
    }

    @Override
    public void updateUI(GetFollowUpsModel response, String whichFollowUp) {

        followUpsList = response.data;

        RecyclerView.LayoutManager mLayoutManager;

        switch (whichFollowUp) {

            case "due":

                if (followUpsList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerViewFollowDue.setVisibility(View.GONE);
                    bi.recyclerViewFollowOverDue.setVisibility(View.GONE);

                } else {

                    bi.tvNoRecordFound.setVisibility(View.GONE);
                    bi.recyclerViewFollowOverDue.setVisibility(View.GONE);
                    bi.recyclerViewFollowDue.setVisibility(View.VISIBLE);

                    swipeFollowUpsDueRecyclerAdapter = new SwipeFollowUpsDueRecyclerAdapter(context, followUpsList);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewFollowDue.setLayoutManager(mLayoutManager);
                    bi.recyclerViewFollowDue.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewFollowDue.setAdapter(swipeFollowUpsDueRecyclerAdapter);
                }
                break;
            case "overdue":
                if (followUpsList.size() == 0) {

                    bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                    bi.recyclerViewFollowDue.setVisibility(View.GONE);
                    bi.recyclerViewFollowOverDue.setVisibility(View.GONE);

                } else {

                    bi.tvNoRecordFound.setVisibility(View.GONE);
                    bi.recyclerViewFollowDue.setVisibility(View.GONE);
                    bi.recyclerViewFollowOverDue.setVisibility(View.VISIBLE);

                    swipeFollowUpsDueRecyclerAdapter = new SwipeFollowUpsDueRecyclerAdapter(context, followUpsList);
                    mLayoutManager = new LinearLayoutManager(getContext());
                    bi.recyclerViewFollowOverDue.setLayoutManager(mLayoutManager);
                    bi.recyclerViewFollowOverDue.setItemAnimator(new DefaultItemAnimator());
                    bi.recyclerViewFollowOverDue.setAdapter(swipeFollowUpsDueRecyclerAdapter);
                }
                break;
        }

    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {
        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            ToastUtils.showToastLong(context, error);
        }
    }

    @Override
    public void updateUIonFailure() {
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    private void initViews() {

        isFromActivity = this.getArguments().getBoolean("isFromActivity");
        if (isFromActivity) {
            bi.tvTitle.setVisibility(View.GONE);
        }


        bi.btnFollowDue.setOnClickListener(this);
        bi.btnFollowOverDue.setOnClickListener(this);

    }

    private void populateDataDue() {

       /* List<GetFollowUpsModel> appointmentList = new ArrayList<>();

        appointmentList.add(new GetFollowUpsModel("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetFollowUpsModel("NAME NAME ", "Address Address Address Address"));

        swipeFollowUpsDueRecyclerAdapter = new SwipeFollowUpsDueRecyclerAdapter(context, appointmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewPrevious.getContext(), 1);
        bi.recyclerViewFollowDue.setLayoutManager(mLayoutManager);
        bi.recyclerViewFollowDue.setItemAnimator(new DefaultItemAnimator());
        // bi.recyclerViewPrevious.addItemDecoration(dividerItemDecoration);
        bi.recyclerViewFollowDue.setAdapter(swipeFollowUpsDueRecyclerAdapter);*/
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnFollowDue:

                presenter.getDueFollowUps();

                Paris.style(bi.btnFollowDue).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnFollowOverDue).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnFollowOverDue:

                presenter.getOverDueFollowUps();

                Paris.style(bi.btnFollowOverDue).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnFollowDue).apply(R.style.TabButtonTranparentLeft);


                break;

        }
    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog(context);
    }

}
