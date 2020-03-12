package com.project.jarjamediaapp.Activities.social_profiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAllSocialProfiles;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivitySocialProfilesBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class Social_ProfilesActivity extends BaseActivity implements View.OnClickListener, Social_ProfilesContract.View {

    ActivitySocialProfilesBinding bi;
    Context context = Social_ProfilesActivity.this;
    Social_ProfilesPresenter presenter;

    ArrayList<GetAllSocialProfiles.Data> getAllSocialProfiles;
    ArrayList<GetSocialProfileDropdown.Data> getSocialProfileDropdown;
    ArrayList<String> getSocialProfileDropdownNames;
    String leadID = "";

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_social_profiles);
        presenter = new Social_ProfilesPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
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

        presenter.getAllSocialProfiles(leadID);
        presenter.getSocialProfileDropdown();
        bi.btnAdd.setOnClickListener(this);
    }


    @Override
    public void updateUI(GetAllSocialProfiles response) {

        getAllSocialProfiles = new ArrayList<>();
        getAllSocialProfiles = response.data;

        if (getAllSocialProfiles.size() == 0) {
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
            bi.recyclerViewSocialProfiles.setVisibility(View.GONE);
        } else {
            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.recyclerViewSocialProfiles.setVisibility(View.VISIBLE);
            populateListData();
        }

    }

    @Override
    public void updateUI(GetSocialProfileDropdown response) {
        getSocialProfileDropdown = new ArrayList<>();
        getSocialProfileDropdownNames = new ArrayList<>();

        getSocialProfileDropdown = response.data;
        for (GetSocialProfileDropdown.Data model : getSocialProfileDropdown) {
            getSocialProfileDropdownNames.add(model.text);
        }

    }

    private void populateListData() {

        bi.recyclerViewSocialProfiles.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewSocialProfiles.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewSocialProfiles.addItemDecoration(new DividerItemDecoration(bi.recyclerViewSocialProfiles.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, getAllSocialProfiles, R.layout.custom_social_profiles);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvViewProfile, R.id.imgSocial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetAllSocialProfiles.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, allsocialprofiles, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allsocialprofiles.name);

            ImageView imgSocial = (ImageView) integerMap.get(R.id.imgSocial);
            imgSocial.setImageDrawable(getDrawable(R.drawable.ic_facebook));

            return Unit.INSTANCE;
        });

        bi.recyclerViewSocialProfiles.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetAllSocialProfiles.Data, Integer, Unit>) (allsocialprofiles, integer) -> {


            String url = allsocialprofiles.profilelink;
            if (url.startsWith("http")) {
                openWebPage(url);
            } else if (url.startsWith("www")) {
                url = "http://" + url;
                openWebPage(url);
            } else {
                ToastUtils.showToast(context, "No Profile Found / Incorrect Profile Url");
            }



            return Unit.INSTANCE;
        });

    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showAddProfileDialog(Context context) {

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_social_dialog);

        EditText edtName = dialog.findViewById(R.id.edtName);
        EditText edtProfileLink = dialog.findViewById(R.id.edtProfileLink);
        MaterialSpinner spnSIte = dialog.findViewById(R.id.spnSite);
        spnSIte.setBackground(getDrawable(R.drawable.bg_search));
        spnSIte.setItems(getSocialProfileDropdownNames);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {

            String name = edtName.getText().toString() + "";
            String siteName = getSocialProfileDropdown.get(spnSIte.getSelectedIndex()).value + "";
            String profilelink = edtProfileLink.getText().toString() + "";
            if (name.equals("") || profilelink.equals("")) {
                ToastUtils.showToast(context, "Please Fill all the fields");
            } else if (!android.util.Patterns.WEB_URL.matcher(profilelink).matches()){
                ToastUtils.showToast(context, "Invalid Profile Link");
            }else
            {
                presenter.addSocialProfile(leadID, name, siteName, profilelink);
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

        if (response.body().getStatus().equalsIgnoreCase("Success")) {
            ToastUtils.showToast(context, "Added Successfully");
            if (dialog != null) {
                dialog.dismiss();
                presenter.getAllSocialProfiles(leadID);
            }
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

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(Social_ProfilesActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}
