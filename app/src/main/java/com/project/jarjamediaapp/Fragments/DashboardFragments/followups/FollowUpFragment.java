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

import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeFollowUpsDueRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetDueFollowUps;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.FragmentFollowupBinding;

import java.util.ArrayList;
import java.util.List;


public class FollowUpFragment extends BaseFragment implements FragmentLifeCycle, FollowUpContract.View {

    FragmentFollowupBinding bi;
    Context context;
    FollowUpPresenter presenter;
    SwipeFollowUpsDueRecyclerAdapter swipeFollowUpsDueRecyclerAdapter;

    public FollowUpFragment() {
        // Required empty public constructor
    }

    public static FollowUpFragment newInstance(String fragment_title) {
        FollowUpFragment followUpFragment = new FollowUpFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
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
        populateDataDue();
    }

    private void populateDataDue() {

        List<GetDueFollowUps> appointmentList = new ArrayList<>();

        appointmentList.add(new GetDueFollowUps("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME NAME ", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME", "Address Address Address Address"));
        appointmentList.add(new GetDueFollowUps("NAME NAME ", "Address Address Address Address"));

        swipeFollowUpsDueRecyclerAdapter = new SwipeFollowUpsDueRecyclerAdapter(context, appointmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewPrevious.getContext(), 1);
        bi.recyclerViewFollowDue.setLayoutManager(mLayoutManager);
        bi.recyclerViewFollowDue.setItemAnimator(new DefaultItemAnimator());
        // bi.recyclerViewPrevious.addItemDecoration(dividerItemDecoration);
        bi.recyclerViewFollowDue.setAdapter(swipeFollowUpsDueRecyclerAdapter);


    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }


}
