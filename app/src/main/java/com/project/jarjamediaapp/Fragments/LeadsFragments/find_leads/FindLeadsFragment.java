package com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Activities.add_filters.AddFiltersActivity;
import com.project.jarjamediaapp.Activities.add_lead.AddLeadActivity;
import com.project.jarjamediaapp.Activities.all_leads.AllLeadsActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.Models.GetFindLeads;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.FragmentFindleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;


public class FindLeadsFragment extends BaseFragment implements FindLeadsContract.View, View.OnClickListener {

    FragmentFindleadsBinding bi;
    Context context;
    FindLeadsPresenter presenter;

    public FindLeadsFragment() {
        // Required empty public constructor
    }

    public static FindLeadsFragment newInstance(String fragment_title, int position) {
        FindLeadsFragment findLeadsFragment = new FindLeadsFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putInt("position", position);
        findLeadsFragment.setArguments(args);
        return findLeadsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_findleads, container, false);
        presenter = new FindLeadsPresenter(this);
        presenter.initScreen();

        return bi.getRoot();

    }

    @Override
    public void setupViews() {

        initViews();
        populateListData();

    }

    private void initViews() {

        bi.tvFilter.setOnClickListener(this);
    }

    private void populateListData() {


        List<GetFindLeads> leadsList = new ArrayList<>();

        leadsList.add(new GetFindLeads("All Leads", "482"));
        leadsList.add(new GetFindLeads("New Leads", "482"));
        leadsList.add(new GetFindLeads("Hot Leads", "48"));
        leadsList.add(new GetFindLeads("Active Clients", "4"));
        leadsList.add(new GetFindLeads("Under Contract", "4802"));
        leadsList.add(new GetFindLeads("Closed", "48"));

        bi.recyclerViewFindLeads.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewFindLeads.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewFindLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewFindLeads.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(getContext(), leadsList, R.layout.custom_find_leads_layout);
        recyclerAdapterUtil.addViewsList(/*R.id.txtTitle, R.id.txtDescription,*/ R.id.tvName, R.id.tvCount);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetFindLeads, Integer, Map<Integer, ? extends View>, Unit>) (view, findLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(findLeadsList.getName());

            TextView tvCount = (TextView) integerMap.get(R.id.tvCount);
            tvCount.setText(findLeadsList.getCount());

            return Unit.INSTANCE;
        });

        bi.recyclerViewFindLeads.setAdapter(recyclerAdapterUtil);


        recyclerAdapterUtil.addOnClickListener((Function2<GetFindLeads, Integer, Unit>) (viewComplainList, integer) -> {

            switchActivity(getActivity(), AllLeadsActivity.class);

            return Unit.INSTANCE;
        });


    }

    public void ReplaceFragment(Fragment fragment, String title, boolean shouldAnimate, boolean addToStack) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
        ft.replace(R.id.fragment_replacer, fragment, title);
        if (addToStack) {
            ft.addToBackStack(title);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        setHasOptionsMenu(true);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvFilter:

                switchActivity(getActivity(), AddFiltersActivity.class);

                break;

        }
    }
}
