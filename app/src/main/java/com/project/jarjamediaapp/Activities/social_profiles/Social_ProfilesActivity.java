package com.project.jarjamediaapp.Activities.social_profiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadSocialProfile;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;
import com.project.jarjamediaapp.Models.UpgradeSocialProfile;
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

    ArrayList<GetLeadSocialProfile.Data> getAllSocialProfiles;
    ArrayList<UpgradeSocialProfile.LeadSocialProfileList> getUpgradedSocialProfile;
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
        Log.d("LEAD ID ", leadID);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.social_profiles), true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAdd:
                showAddProfileDialog(context);
                break;
            case R.id.btnUpgrade:
                presenter.upgradeSocialProfile(leadID);
                break;
        }
    }

    @Override
    public void initViews() {

        presenter.getAllSocialProfiles(leadID);
        presenter.getSocialProfileDropdown();
        bi.btnAdd.setOnClickListener(this);
        bi.btnUpgrade.setOnClickListener(this);
    }


    @Override
    public void updateUI(GetLeadSocialProfile response) {

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

    @Override
    public void updateUI(UpgradeSocialProfile response) {

        getUpgradedSocialProfile = new ArrayList<>();

        if (response.data != null) {
            getUpgradedSocialProfile = response.data.leadSocialProfileList;

            if (getUpgradedSocialProfile.size() == 0) {
                bi.tvNoRecordFound.setVisibility(View.VISIBLE);
                bi.recyclerViewSocialProfiles.setVisibility(View.GONE);
            } else {
                bi.tvNoRecordFound.setVisibility(View.GONE);
                bi.recyclerViewSocialProfiles.setVisibility(View.VISIBLE);
                populateUpgradedListData();
            }
        }

    }

    private void populateListData() {

        bi.recyclerViewSocialProfiles.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewSocialProfiles.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewSocialProfiles.addItemDecoration(new DividerItemDecoration(bi.recyclerViewSocialProfiles.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, getAllSocialProfiles, R.layout.custom_social_profiles);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvViewProfile, R.id.imgSocial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadSocialProfile.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, allsocialprofiles, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allsocialprofiles.name);

            ImageView imgSocial = (ImageView) integerMap.get(R.id.imgSocial);
            Glide.with(context).load(allsocialprofiles.imagelink).into(imgSocial);

            return Unit.INSTANCE;
        });

        bi.recyclerViewSocialProfiles.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadSocialProfile.Data, Integer, Unit>) (allsocialprofiles, integer) -> {


            String url = allsocialprofiles.profilelink;
            String siteName = allsocialprofiles.siteName;
            if (url.startsWith("http") || url.startsWith("HTTP")) {
                openWebPage(url, siteName);
            } else if (url.startsWith("www") || url.startsWith("WWW")) {
                // url = "http://" + url;
                openWebPage(url, siteName);
            } else {
                ToastUtils.showToast(context, "No Profile Found / Incorrect Profile Url");
            }


            return Unit.INSTANCE;
        });

    }

    private void populateUpgradedListData() {

        bi.recyclerViewSocialProfiles.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewSocialProfiles.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewSocialProfiles.addItemDecoration(new DividerItemDecoration(bi.recyclerViewSocialProfiles.getContext(), 1));
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, getUpgradedSocialProfile, R.layout.custom_social_profiles);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvViewProfile, R.id.imgSocial);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, UpgradeSocialProfile.LeadSocialProfileList, Integer, Map<Integer, ? extends View>, Unit>) (view, allsocialprofiles, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(allsocialprofiles.name);

            ImageView imgSocial = (ImageView) integerMap.get(R.id.imgSocial);
            Glide.with(context).load(allsocialprofiles.imagelink).into(imgSocial);

            return Unit.INSTANCE;
        });

        bi.recyclerViewSocialProfiles.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<UpgradeSocialProfile.LeadSocialProfileList, Integer, Unit>) (allsocialprofiles, integer) -> {


            String url = allsocialprofiles.profilelink;
            String siteName = allsocialprofiles.siteName;
            if (url.startsWith("http") || url.startsWith("HTTP")) {
                openWebPage(url, siteName);
            } else if (url.startsWith("www") || url.startsWith("WWW")) {
                // url = "http://" + url;
                openWebPage(url, siteName);
            } else {
                ToastUtils.showToast(context, "No Profile Found / Incorrect Profile Url");
            }


            return Unit.INSTANCE;
        });

    }

    public void openWebPage(String url, String siteName) {
        Uri webpage = null;
        if (url.contains("/")) {
            String urlSplit[]  = url.split("/");
            String userName = urlSplit[urlSplit.length - 1];

            switch (siteName) {

                case "Facebook":
                    webpage = Uri.parse("fb://profile?app_scoped_user_id=" + userName);
                    break;
                case "Twitter":
                    webpage = Uri.parse("twitter://user?screen_name=" + userName);
                    break;
                case "LinkedIn":
                    webpage = Uri.parse("linkedin://profile?id=" + userName);
                    break;
                case "Gravatar":
                    webpage = Uri.parse(url);
                    break;
                case "GooglePlus":
                    webpage = Uri.parse("gplus://plus.google.com/" + userName);
                    break;
                case "Klout":
                    webpage = Uri.parse(url);
                    break;
                case "Vimeo":
                    webpage = Uri.parse(url);
                    break;
                case "Instagram":
                    webpage = Uri.parse("instagram://user?username=" + userName);
                    break;
                case "Snapchat":
                    webpage = Uri.parse("snapchat://add/" + userName);
                    break;
                case "Youtube":
                    webpage = Uri.parse("youtube://www.youtube.com/user/" + userName);
                    break;
                default:
                    webpage = Uri.parse(url);
                    break;
            }

        }else{
            webpage = Uri.parse(url);
        }

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

        spnSIte.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                edtName.clearFocus();
                edtProfileLink.clearFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText()) {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }

                return false;
            }
        });

        spnSIte.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    edtName.clearFocus();
                    edtName.setFocusable(false);
                    edtProfileLink.setFocusable(false);
                    edtProfileLink.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm.isAcceptingText()) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }

                } else {
                    edtName.clearFocus();
                    edtName.setFocusable(true);
                    edtProfileLink.setFocusable(true);
                    edtProfileLink.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm.isAcceptingText()) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                }
            }
        });

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {

            String name = edtName.getText().toString() + "";
            String siteName = getSocialProfileDropdown.get(spnSIte.getSelectedIndex()).value + "";
            String profilelink = edtProfileLink.getText().toString() + "";
            if (name.equals("") || profilelink.equals("")) {
                ToastUtils.showToast(context, "Please Fill all the fields");
            } else if (!android.util.Patterns.WEB_URL.matcher(profilelink).matches()) {
                ToastUtils.showToast(context, "Invalid Profile Link");
            } else {
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
            ToastUtils.showToast(context, response.body().message);
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

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);
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
