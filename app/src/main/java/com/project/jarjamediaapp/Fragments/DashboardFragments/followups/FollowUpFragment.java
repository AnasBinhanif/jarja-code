package com.project.jarjamediaapp.Fragments.DashboardFragments.followups;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeFollowUpsRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentFollowupBinding;

import java.util.ArrayList;
import java.util.List;


public class FollowUpFragment extends BaseFragment implements FragmentLifeCycle, FollowUpContract.View, View.OnClickListener {

    FragmentFollowupBinding bi;
    Context context;
    FollowUpPresenter presenter;
    SwipeFollowUpsRecyclerAdapter swipeFollowUpsRecyclerAdapter;
    boolean isFromActivity;
    List<GetFollowUpsModel.Data.FollowUpsList> followUpsList = new ArrayList<>();
    String leadID = "";
    int page = 1;
    int totalPages;
    String taskType = "due";

    public FollowUpFragment() {
        // Required empty public constructor
    }

    public static FollowUpFragment newInstance(String fragment_title, String leadID, boolean fromActivity) {
        FollowUpFragment followUpFragment = new FollowUpFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putString("leadID", leadID);
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

    }

    @Override
    public void updateUI(GetFollowUpsModel response) {

        totalPages = response.getData().getFollowCount();
        followUpsList = response.getData().getFollowUpsList();
        if (followUpsList.size() == 0) {

            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
            bi.rvFollowUp.setVisibility(View.GONE);
        } else {

            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.rvFollowUp.setVisibility(View.VISIBLE);
            swipeFollowUpsRecyclerAdapter.notifyDataSetChanged();

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

    private void hitApi() {
        if (isFromActivity) {
            presenter.getLeadFollowupsDue(leadID, page);
        } else {
            presenter.getDueFollowUps(page);
        }
    }

    private void initViews() {

        isFromActivity = this.getArguments().getBoolean("isFromActivity");
        if (isFromActivity) {
            leadID = this.getArguments().getString("leadID");
            bi.tvTitle.setVisibility(View.GONE);
        }
        bi.btnFollowDue.setOnClickListener(this);
        bi.btnFollowOverDue.setOnClickListener(this);

        hitApi();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        swipeFollowUpsRecyclerAdapter = new SwipeFollowUpsRecyclerAdapter(context, getActivity(), followUpsList);
        bi.rvFollowUp.setAdapter(swipeFollowUpsRecyclerAdapter);
        bi.rvFollowUp.setLayoutManager(layoutManager);
        bi.rvFollowUp.setItemAnimator(new DefaultItemAnimator());

        bi.rvFollowUp.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    Log.d("scroll", "scroll down");
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");
                        if (totalPages > followUpsList.size()) {
                            page++;
                            try {
                                hitApi();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }
                    }
                }
            }
        });

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

                followUpsList.clear();
                if (isFromActivity) {
                    presenter.getLeadFollowupsDue(leadID, page);
                } else {
                    presenter.getDueFollowUps(page);
                }

                Paris.style(bi.btnFollowDue).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnFollowOverDue).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnFollowOverDue:

                followUpsList.clear();
                if (isFromActivity) {
                    presenter.getLeadFollowupsOverDue(leadID, page);
                } else {
                    presenter.getOverDueFollowUps(page);
                }

                Paris.style(bi.btnFollowOverDue).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnFollowDue).apply(R.style.TabButtonTranparentLeft);


                break;

        }
    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(getActivity());
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

}
