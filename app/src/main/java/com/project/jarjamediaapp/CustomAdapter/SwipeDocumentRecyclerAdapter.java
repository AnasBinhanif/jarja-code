package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.notes.DocumentModel;
import com.project.jarjamediaapp.Base.BaseResponse;
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


public class SwipeDocumentRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    ArrayList<DocumentModel.Data> mData;

    public SwipeDocumentRecyclerAdapter(Context context, Activity activity, ArrayList<DocumentModel.Data> getData) {

        this.context = context;
        this.activity = activity;
        mData = getData;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adp_view_files, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            DocumentModel.Data modelData = mData.get(position);
            String fileName = modelData.getAttachmentOrignalName();
            holder.txtFileName.setText(fileName);

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

        binderHelper.bind(holder.swipeLayout, String.valueOf(pos));
        holder.bind();
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        TextView txtFileName, txtRemove;
        LinearLayout lnEmail, lnDelete;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);

            lnEmail = itemView.findViewById(R.id.lnEmail);
            txtFileName = itemView.findViewById(R.id.txtFileName);
            lnDelete = itemView.findViewById(R.id.lnDelete);

        }

        public void bind() {

            lnDelete.setOnClickListener(v -> {
                pos = getAdapterPosition();
                DocumentModel.Data modelData = mData.get(pos);
                showDeleteDialog(modelData, swipeLayout);
            });

        }
    }

    public void addData(DocumentModel.Data modelData) {

        mData.add(modelData);
        notifyDataSetChanged();

    }

    private void showDeleteDialog(DocumentModel.Data data, SwipeRevealLayout swipeRevealLayout) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setIcon(R.drawable.logo_round);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.alert));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.delete_file));

        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            callDeleteDocument(String.valueOf(data.getLeadAttachmentID()), swipeRevealLayout);
        });
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void callDeleteDocument(String docId, SwipeRevealLayout swipeRevealLayout) {

        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).deleteDocumentByDocumentId(GH.getInstance().getAuthorization(),
                docId);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Successfully Done");
                        mData.remove(pos);
                        notifyDataSetChanged();
                        swipeRevealLayout.close(true);

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
