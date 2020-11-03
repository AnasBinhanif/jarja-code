package com.project.jarjamediaapp.Activities.search_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SearchResultAdapter;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivitySearchResultsBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends BaseActivity implements SearchResultsContract.View, View.OnClickListener {

    ActivitySearchResultsBinding bi;
    Context context = SearchResultsActivity.this;
    SearchResultsPresenter presenter;

    RecyclerAdapterUtil recyclerAdapterUtil;
    ArrayList<GetLeadTitlesModel.Data> nameList = new ArrayList<>();

    boolean fromAppoint;

    long delay = 600; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    Runnable input_finish_checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_search_results);
        presenter = new SearchResultsPresenter(this);
        presenter.initScreen();

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initViews() {

        fromAppoint = getIntent().getBooleanExtra("fromAppoint", true);

        if (fromAppoint) {
            bi.btnSave.setVisibility(View.GONE);
        } else {
            bi.btnSave.setVisibility(View.VISIBLE);
        }

        bi.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("idList", SearchResultAdapter.selectedIDs);
                intent.putStringArrayListExtra("nameList", SearchResultAdapter.selectedNames);
                intent.putStringArrayListExtra("encryptedList", SearchResultAdapter.selectedEncryted);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        bi.edtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {

               /* int length = bi.edtQuery.getText().length();
                try {
                    if (length > 0)
                        getLeadByText(bi.edtQuery.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                int length = bi.edtQuery.getText().length();
                try {
                    if (length > 0)
                        //    last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        input_finish_checker = () -> {
            getLeadByText(bi.edtQuery.getText().toString());

        };

    }

    private void getLeadByText(String query) {
        Log.d("onResponsequery", query);
        Call<GetLeadTitlesModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTitlesModel(GH.getInstance().getAuthorization(), query);
        _callToday.enqueue(new Callback<GetLeadTitlesModel>() {
            @Override
            public void onResponse(Call<GetLeadTitlesModel> call, Response<GetLeadTitlesModel> response) {

                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {
                    Log.d("onResponse", response.body().toString());
                    GetLeadTitlesModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        nameList = new ArrayList<>();
                        nameList.addAll(getAppointmentsModel.data);

                        Log.d("onResponseBody", response.body().data.size() + "");
                        Log.d("onResponseSize", getAppointmentsModel.data.size() + "");
                        if (nameList.size() != 0) {
                            setRecyclerSearch();
                        } else {
                            ToastUtils.showToast(context, "No Result Found");
                        }
                    } else {
                        ToastUtils.showToast(context, getAppointmentsModel.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLeadTitlesModel> call, Throwable t) {
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }


    private void setRecyclerSearch() {

        bi.recyclerSearch.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerSearch.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerSearch.addItemDecoration(new DividerItemDecoration(bi.recyclerSearch.getContext(), 1));
        bi.recyclerSearch.setAdapter(new SearchResultAdapter(SearchResultsActivity.this, nameList, fromAppoint));
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

        GH.getInstance().ShowProgressDialog(SearchResultsActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}