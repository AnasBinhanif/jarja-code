package com.project.jarjamediaapp.Activities.lead_detail;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.appointments.AppointmentActivity;
import com.project.jarjamediaapp.Activities.followups.FollowupsActivity;
import com.project.jarjamediaapp.Activities.listing_info.ListingInfoActivity;
import com.project.jarjamediaapp.Activities.social_profiles.Social_ProfilesActivity;
import com.project.jarjamediaapp.Activities.tasks.TasksActivity;
import com.project.jarjamediaapp.Activities.transactions.TransactionActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadDetails;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityLeadDetailBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class LeadDetailActivity extends BaseActivity implements LeadDetailContract.View {

    ActivityLeadDetailBinding bi;
    Context context = LeadDetailActivity.this;
    LeadDetailPresenter presenter;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getString(R.string.transaction1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_lead_detail);
        presenter = new LeadDetailPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.lead_details), true);
    }

    private void populateListData() {

        List<GetLeadDetails> leadsList = new ArrayList<>();

        leadsList.add(new GetLeadDetails("Followups"));
        leadsList.add(new GetLeadDetails("Social Profiles"));
        leadsList.add(new GetLeadDetails("Listing Info"));
        leadsList.add(new GetLeadDetails("Buying Info"));
        leadsList.add(new GetLeadDetails("Apply Tags"));
        leadsList.add(new GetLeadDetails("Appointments"));
        leadsList.add(new GetLeadDetails("Notes"));
        leadsList.add(new GetLeadDetails("Tasks"));

        bi.recyclerLeadDetails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerLeadDetails.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerLeadDetails.addItemDecoration(new DividerItemDecoration(bi.recyclerLeadDetails.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_lead_details);
        recyclerAdapterUtil.addViewsList(R.id.tvName);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadDetails, Integer, Map<Integer, ? extends View>, Unit>) (view, allLeadsList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allLeadsList.getName());

            return Unit.INSTANCE;
        });

        bi.recyclerLeadDetails.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadDetails, Integer, Unit>) (viewComplainList, position) -> {

            switch (position) {

                case 0:

                    switchActivity(FollowupsActivity.class);

                    break;

                case 1:

                    switchActivity(Social_ProfilesActivity.class);

                    break;

                case 2:

                    Map<String, String> map = new HashMap<>();
                    map.put("title", getString(R.string.listing_info));
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map);

                    break;

                case 3:
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("title", getString(R.string.buying_info));
                    switchActivityWithIntentString(ListingInfoActivity.class, (HashMap<String, String>) map1);

                    break;
                case 5:

                    switchActivity(AppointmentActivity.class);

                    break;
                case 7:

                    switchActivity(TasksActivity.class);

                    break;

            }

            return Unit.INSTANCE;
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fbAssignedTo:
                showCallDialog(context);
                break;

            case R.id.rlTransaction:

                Map<String, String> map = new HashMap<>();
                map.put("title", title);
                switchActivityWithIntentString(TransactionActivity.class, (HashMap<String, String>) map);

                break;

            case R.id.btnActions:

                bi.lnActions.setVisibility(View.VISIBLE);
                bi.recyclerLeadDetails.setVisibility(View.GONE);
                Paris.style(bi.btnActions).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnDetails:

                bi.lnActions.setVisibility(View.GONE);
                bi.recyclerLeadDetails.setVisibility(View.VISIBLE);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnActions).apply(R.style.TabButtonTranparentLeft);

                break;

            case R.id.btnTransaction1:

                title = getString(R.string.transaction1);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnTransaction2:

                title = getString(R.string.transaction2);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonTranparentLeft);

                break;
        }
    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        bi.btnActions.setOnClickListener(this);
        bi.btnDetails.setOnClickListener(this);
        bi.rlTransaction.setOnClickListener(this);
        bi.btnTransaction1.setOnClickListener(this);
        bi.btnTransaction2.setOnClickListener(this);
        bi.fbAssignedTo.setOnClickListener(this);

        populateListData();
    }

    public void showCallDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_assignedto_dialog);

        EditText txtCall1 = dialog.findViewById(R.id.edtAgent);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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