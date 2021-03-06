package com.project.jarjamediaapp.Activities.tags;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.SwipeTagsRecyclerAdapter;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityTagsBinding;

import java.util.ArrayList;

import retrofit2.Response;

public class TagsActivity extends BaseActivity implements TagsContract.View {

    ActivityTagsBinding bi;
    Context context = TagsActivity.this;
    TagsPresenter presenter;
    SwipeTagsRecyclerAdapter swipeTagsRecyclerAdapter;
    ArrayList<GetTagListByLeadID.Data> tagsList;
    ArrayList<GetTagListByLeadID.Data> assignedTagsList;
    ArrayList<MultiSelectModel> getLeadTagModelList;
    ArrayList<MultiSelectModel> getLeadAssignedTagModelList;
    public static ArrayList<Integer> selectedTagIdsList = new ArrayList<>();
    String leadID = "", tagsIdsString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_tags);
        presenter = new TagsPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getTags(leadID);
    }

    @Override
    public void updateUI(GetTagListByLeadID response) {
        tagsList = new ArrayList<>();
        assignedTagsList = new ArrayList<>();
        getLeadTagModelList = new ArrayList<>();
        selectedTagIdsList = new ArrayList<>();
        tagsList = response.data;

        if (tagsList.size() != 0) {
            for (GetTagListByLeadID.Data model : tagsList) {
                //  Log.d( "updateUI: ",model.label + " id " + model.tagID);
                getLeadTagModelList.add(new MultiSelectModel(model.tagID, model.label, model.encryptedTagID));
                if (model.added != null) {
                    assignedTagsList.add(model);
                    selectedTagIdsList.add(model.tagID);
                }
            }
        }
//            populateData();
        //adding below block of code to replace above line of code -- akshay
        if (assignedTagsList.size() > 0) {
            bi.tvNoRecordFound.setVisibility(View.GONE);
            populateData();
        } else {
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        }


    }

    private void populateData() {

        if (swipeTagsRecyclerAdapter != null) {
            swipeTagsRecyclerAdapter = new SwipeTagsRecyclerAdapter(context, TagsActivity.this, assignedTagsList, leadID);
            bi.recyclerViewTags.setAdapter(swipeTagsRecyclerAdapter);
        } else {
            swipeTagsRecyclerAdapter = new SwipeTagsRecyclerAdapter(context, TagsActivity.this, assignedTagsList, leadID);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bi.recyclerViewTags.getContext(), 1);
            bi.recyclerViewTags.setLayoutManager(mLayoutManager);
            bi.recyclerViewTags.setItemAnimator(new DefaultItemAnimator());
            bi.recyclerViewTags.addItemDecoration(dividerItemDecoration);
            bi.recyclerViewTags.setAdapter(swipeTagsRecyclerAdapter);
        }
    }

    private void showTagsDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Tags") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedTagIdsList = new ArrayList<>();
                        selectedTagIdsList = selectedIds;
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {
                        tagsIdsString = "";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (tagsIdsString.equals("")) {
                                    tagsIdsString = i;
                                } else {
                                    tagsIdsString = tagsIdsString + "," + i;
                                }
                            }
                        } else {
                            ToastUtils.showToast(context, "No EncryptedID Found");
                        }

                        //  if (!tagsIdsString.equals("")) {
                        presenter.assignTags(leadID, tagsIdsString);
                        //   }
                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (selectedTagIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedTagIdsList);
            multiSelectDialog.multiSelectList(getLeadTagModelList);
        } else {
            multiSelectDialog.multiSelectList(getLeadTagModelList);
        }
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {
        if (response.body().getStatus().equals("Success")) {
            presenter.getTags(leadID);
        }
    }

    @Override
    public void updateUIonFalse(String message) {
        ToastUtils.showToastLong(context, message);
    }

    @Override
    public void updateUIonError(String error) {
       /* if (error.contains("Authorization has been denied for this request")) {
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_notify).setVisible(false);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            //showAddDialog(context);
            showTagsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(TagsActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void updateUIOnTagDelete() {
        if (selectedTagIdsList.size() > 0) {
            bi.tvNoRecordFound.setVisibility(View.GONE);
        } else {
            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        }

    }

}