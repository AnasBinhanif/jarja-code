package com.project.jarjamediaapp.Activities.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.listing_info.ListingInfoContract;
import com.project.jarjamediaapp.Activities.listing_info.ListingInfoPresenter;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetNotifications;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotificationBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements ListingInfoContract.View {

    ActivityNotificationBinding bi;
    Context context = NotificationActivity.this;
    ListingInfoPresenter presenter;
    public static List<GetNotifications> leadsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        presenter = new ListingInfoPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, "Notifications", true);
    }

    private void populateListData() {

        List<GetNotifications> leadsList = new ArrayList<>();

        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));
        leadsList.add(new GetNotifications("Ariana Call today for ", "LeadName: Jared", "Contact : (123)456-1234", "Email : abc@xyz.com"));

        bi.recyclerTasks.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerTasks.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerTasks.addItemDecoration(new DividerItemDecoration(bi.recyclerTasks.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_notifications_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetNotifications, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            TextView tvDesc = (TextView) integerMap.get(R.id.tvLeadName);
            tvDesc.setText(allLeadsList.getLeadName());

            TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
            tvContact.setText(allLeadsList.getContact());

            TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
            tvEmail.setText(allLeadsList.getEmail());

            return Unit.INSTANCE;
        });

        bi.recyclerTasks.setAdapter(recyclerAdapterUtil);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnTasks:
                Paris.style(bi.btnTasks).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);


                break;

            case R.id.btnAppointments:

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);


                break;

            case R.id.btnFollowUps:

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonYellowRight);

                break;

        }

    }


    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        populateListData();
        bi.btnTasks.setOnClickListener(this);
        bi.btnAppointments.setOnClickListener(this);
        bi.btnFollowUps.setOnClickListener(this);
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {
        ToastUtils.showToastLong(context, error);
    }

    @Override
    public void updateUIonFailure() {
        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
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