package com.project.jarjamediaapp.Activities.lead_detail;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityLeadDetailBinding;

import retrofit2.Response;

public class LeadDetailActivity extends BaseActivity implements LeadDetailContract.View {

    ActivityLeadDetailBinding bi;
    Context context = LeadDetailActivity.this;
    LeadDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_lead_detail);
        presenter = new LeadDetailPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.lead_details), true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fbAssignedTo:
                showCallDialog(context);
                break;

            case R.id.btnActions:

                Paris.style(bi.btnActions).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnDetails).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnDetails:

                Paris.style(bi.btnDetails).apply(R.style.TabButtonYellowRight);
                Paris.style(bi.btnActions).apply(R.style.TabButtonTranparentLeft);

                break;

            case R.id.btnTransaction1:

                Paris.style(bi.btnTransaction1).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnTransaction2).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnTransaction2:

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
        bi.btnTransaction1.setOnClickListener(this);
        bi.btnTransaction2.setOnClickListener(this);
        bi.fbAssignedTo.setOnClickListener(this);
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