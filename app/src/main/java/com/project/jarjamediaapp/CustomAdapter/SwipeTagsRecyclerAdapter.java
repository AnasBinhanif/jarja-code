package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Activities.tags.TagsActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTagListByLeadID;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SwipeTagsRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    String leadID = "";
    ArrayList<GetTagListByLeadID.Data> mData;

    public SwipeTagsRecyclerAdapter(Context context, Activity activity, ArrayList<GetTagListByLeadID.Data> data, String leadID) {

        mData = data;
        this.leadID = leadID;
        this.context = context;
        this.activity = activity;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_tags_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            GetTagListByLeadID.Data modelData = mData.get(position);

            if (modelData.added != null) {
                holder.tvName.setText(modelData.label + "");
            }

            holder.frameLayout.setOnClickListener(v -> {
            });

            holder.swipeLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout view) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
            });

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data

            binderHelper.bind(holder.swipeLayout, String.valueOf(pos));
            // Bind your data here
            holder.bind();
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        TextView tvName, tvDelete;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }

        public void bind() {

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTag();
                }
            });
        }

        public void deleteTag() {

            AlertDialog alertDialog1;
            alertDialog1 = new AlertDialog.Builder(
                    context).create();

            // Setting Dialog Title
            alertDialog1.setTitle("Alert");

            // Setting Dialog Message
            alertDialog1.setMessage("\nAre You Sure! You Want To Delete This Tag ?");

            // Setting Icon to Dialog
            // alertDialog1.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog1.setButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog
                    // closed
                    pos = getAdapterPosition();
                    String noteID = mData.get(pos).encryptedTagID;
                    callDeleteTag(noteID, leadID, swipeLayout);
                }
            });

            alertDialog1.setButton2("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog
                    // closed
                    alertDialog1.dismiss();
                }
            });

            // Showing Alert Message
            alertDialog1.show();
        }
    }



    private void callDeleteTag(String encryptedNoteID, String leadID, SwipeRevealLayout swipeRevealLayout) {
        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).DeleteLeadTag(GH.getInstance().getAuthorization(),
                leadID, encryptedNoteID);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Removed Successfully");
                        TagsActivity.selectedTagIdsList.remove(mData.get(pos).tagID);
                        mData.remove(pos);
                        swipeRevealLayout.close(true);
                        notifyDataSetChanged();

                    } else {

                        ToastUtils.showToast(context, getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }
}
