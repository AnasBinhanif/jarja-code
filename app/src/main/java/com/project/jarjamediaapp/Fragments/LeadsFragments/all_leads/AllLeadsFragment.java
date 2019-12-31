package com.project.jarjamediaapp.Fragments.LeadsFragments.all_leads;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailActivity;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.FragmentAllleadsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;


public class AllLeadsFragment extends BaseFragment implements AllLeadsContract.View, View.OnClickListener {

    FragmentAllleadsBinding bi;
    Context context;
    AllLeadsPresenter presenter;

    public AllLeadsFragment() {
        // Required empty public constructor
    }

    public static AllLeadsFragment newInstance(String fragment_title, int position) {
        AllLeadsFragment findLeadsFragment = new AllLeadsFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putInt("position", position);
        findLeadsFragment.setArguments(args);
        return findLeadsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter = new AllLeadsPresenter(this);
        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_allleads, container, false);
        presenter.initScreen();

        return bi.getRoot();

    }

    @Override
    public void setupViews() {

        populateListData();

    }

    private void populateListData() {


        List<GetAllLeads> leadsList = new ArrayList<>();

        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));
        leadsList.add(new GetAllLeads("Brendon", "(123) 456-1234", "Brendon@gmail.com"));

        bi.recyclerViewAllLeads.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewAllLeads.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewAllLeads.addItemDecoration(new DividerItemDecoration(bi.recyclerViewAllLeads.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(getContext(), leadsList, R.layout.custom_all_leads_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvPhone, R.id.tvEmail);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetAllLeads, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            TextView tvPhone = (TextView) integerMap.get(R.id.tvPhone);
            tvPhone.setText(allLeadsList.getPhone());

            TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
            tvEmail.setText(allLeadsList.getEmail());

            return Unit.INSTANCE;
        });

        bi.recyclerViewAllLeads.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetAllLeads, Integer, Unit>) (viewComplainList, integer) -> {

            switchActivity(context, LeadDetailActivity.class);

            return Unit.INSTANCE;
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }
}
