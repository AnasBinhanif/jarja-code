package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTasksModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeTasksDueRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    int pos;
    boolean isEditByLead;
    List<GetTasksModel.Data> mData;

    public SwipeTasksDueRecyclerAdapter(Context context, List<GetTasksModel.Data> data, boolean isEditByLead) {

        mData = data;
        this.context = context;
        this.isEditByLead = isEditByLead;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_tasks_due_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            GetTasksModel.Data modelData = mData.get(position);

            holder.tvName.setText(modelData.name + " for " + modelData.agentName);
            holder.tvAddress.setText(modelData.firstName + " " + modelData.lastName);

            holder.tvInitial.setText(modelData.name.substring(0, 1));

            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });


            holder.swipeLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout view) {
                    pos = position;
                    view.getParent().requestDisallowInterceptTouchEvent(false);

                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    pos = position;
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    pos = position;
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

    private void markAsRead(String taskID) {

        GH.getInstance().ShowProgressDialog(context);

        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).MarkTaskComplete(GH.getInstance().getAuthorization(),
                taskID + ",", "true");
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        binderHelper.closeLayout(String.valueOf(pos));
                        ToastUtils.showToast(context, "Successfully Done");
                        mData.remove(pos);
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
                GH.getInstance().HideProgressDialog(context);
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeRevealLayout swipeLayout;
        TextView tvName, tvAddress, tvEdit, tvDone, tvInitial;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDone = itemView.findViewById(R.id.tvDone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvInitial = itemView.findViewById(R.id.tvInitial);
        }

        public void bind() {

            tvDone.setOnClickListener(v -> {
                pos = getAdapterPosition();
                String taskID = mData.get(pos).taskID;
                markAsRead(taskID);
            });

            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    String leadID = mData.get(pos).leadID;
                    if (isEditByLead) {

                        swipeLayout.close(true);
                      /*  context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("leadID", leadID)
                                .putExtra("from", "2")
                                .putExtra("model", mData));*/
                    } else {

                        swipeLayout.close(true);
                       /* context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("leadID", leadID)
                                .putExtra("from", "3")
                                .putExtra("model", mData));*/
                    }
                }
            });

        }
    }

}
