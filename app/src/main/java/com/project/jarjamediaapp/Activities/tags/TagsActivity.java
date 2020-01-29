package com.project.jarjamediaapp.Activities.tags;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeTagsRecyclerAdapter;
import com.project.jarjamediaapp.Models.GetTags;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTagsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TagsActivity extends BaseActivity implements TagsContract.View {

    ActivityTagsBinding bi;
    Context context = TagsActivity.this;
    TagsPresenter presenter;
    SwipeTagsRecyclerAdapter swipeTagsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_tags);
        presenter = new TagsPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.tags), true);
    }


    @Override
    public void onClick(View v) {


    }

    @Override
    public void setupUI(View view) {
        super.setupUI(view);
    }

    @Override
    public void initViews() {

        populateDataDue();

    }

    private void populateDataDue() {

        List<GetTags> tagsList = new ArrayList<>();

        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));
        tagsList.add(new GetTags("Gold Five"));

        swipeTagsRecyclerAdapter = new SwipeTagsRecyclerAdapter(context, tagsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewTags.getContext(), 1);
        bi.recyclerViewTags.setLayoutManager(mLayoutManager);
        bi.recyclerViewTags.setItemAnimator(new DefaultItemAnimator());
        bi.recyclerViewTags.addItemDecoration(dividerItemDecoration);
        bi.recyclerViewTags.setAdapter(swipeTagsRecyclerAdapter);
    }

    public void showCallDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_assignedto_dialog);

        TextView txtTitle = dialog.findViewById(R.id.tvAssignedTo);
        txtTitle.setText("Assign Tags");

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            showCallDialog(context);

            return true;
        }
        return super.onOptionsItemSelected(item);

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