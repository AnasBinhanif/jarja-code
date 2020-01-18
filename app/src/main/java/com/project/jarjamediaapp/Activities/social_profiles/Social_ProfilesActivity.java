package com.project.jarjamediaapp.Activities.social_profiles;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetSocialProfiles;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivitySocialProfilesBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class Social_ProfilesActivity extends BaseActivity implements View.OnClickListener, Social_ProfilesContract.View {

    ActivitySocialProfilesBinding bi;
    Context context = Social_ProfilesActivity.this;
    Social_ProfilesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_social_profiles);
        presenter = new Social_ProfilesPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.social_profiles), true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnAdd:

                showAddProfileDialog(context);

                break;

        }

    }

    @Override
    public void initViews() {

        populateListData();
        bi.btnAdd.setOnClickListener(this);

    }


    private void populateListData() {


        List<GetSocialProfiles> leadsList = new ArrayList<>();

        leadsList.add(new GetSocialProfiles("Facebook", getDrawable(R.drawable.ic_facebook)));
        leadsList.add(new GetSocialProfiles("Youtube", getDrawable(R.drawable.ic_youtube)));
        leadsList.add(new GetSocialProfiles("Twitter", getDrawable(R.drawable.ic_twitter)));
        leadsList.add(new GetSocialProfiles("Linkedin", getDrawable(R.drawable.ic_linkedin)));
        leadsList.add(new GetSocialProfiles("Instagram", getDrawable(R.drawable.ic_instagram)));

        bi.recyclerViewSocialProfiles.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewSocialProfiles.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewSocialProfiles.addItemDecoration(new DividerItemDecoration(bi.recyclerViewSocialProfiles.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_social_profiles);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvViewProfile, R.id.imgSocial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetSocialProfiles, Integer, Map<Integer, ? extends View>, Unit>) (view, allsocialprofiles, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allsocialprofiles.getName());

            ImageView imgSocial = (ImageView) integerMap.get(R.id.imgSocial);
            imgSocial.setImageDrawable(allsocialprofiles.getImage());

            return Unit.INSTANCE;
        });

        bi.recyclerViewSocialProfiles.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetSocialProfiles, Integer, Unit>) (allsocialprofiles, integer) -> {


            return Unit.INSTANCE;
        });


    }

    public void showAddProfileDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_social_dialog);

        EditText edtName = dialog.findViewById(R.id.edtName);

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
