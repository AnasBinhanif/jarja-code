package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.notes.AddNotesActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadNotes;
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


public class SwipeNotesRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    int pos;
    String status = "";
    ArrayList<GetLeadNotes.NotesList> mData;

    public static boolean isEditable=false;


    public SwipeNotesRecyclerAdapter(Context context, ArrayList<GetLeadNotes.NotesList> getLeadNotes) {

        this.context = context;
        mData = getLeadNotes;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_notes_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            GetLeadNotes.NotesList modelData = mData.get(position);

            holder.tvNoteType.setText(modelData.noteType + "");
            holder.tvDesc.setText(modelData.desc);

            if (!modelData.createdDate.equals("") || !modelData.createdDate.equals("null")) {
                String dateTime[] = modelData.createdDate.split("T");
                String createdDate = dateTime[0] + " " + dateTime[1].substring(0, 5);
                String createdTime = dateTime[1].substring(0, 5);

                holder.tvDateTime.setText(createdDate + "");
                holder.tvTime.setText(createdTime + "");
            } else {
                holder.tvDateTime.setText("");
                holder.tvTime.setText("");
            }

            if (modelData.isSticky) {
                holder.cbNoteSticky.setChecked(true);
            } else {
                holder.cbNoteSticky.setChecked(false);
            }

            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
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
        TextView tvDateTime, tvTime, tvNoteType, tvDesc, tvAgentNames, tvEdit, tvDelete;
        CheckBox cbNoteSticky;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);

            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAgentNames = itemView.findViewById(R.id.tvAgentNames);
            tvNoteType = itemView.findViewById(R.id.tvNoteType);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            cbNoteSticky = itemView.findViewById(R.id.cbNoteSticky);
        }

        public void bind() {

            tvDelete.setOnClickListener(v -> {
                pos = getAdapterPosition();
                String noteID = mData.get(pos).encryptedNoteID;
                callDeleteNote(noteID);
            });

            tvEdit.setOnClickListener(v -> {

                isEditable=true;
                pos = getAdapterPosition();
                Intent intent = new Intent(context, AddNotesActivity.class);
                intent.putExtra("Note", mData.get(pos));
                context.startActivity(intent);

            });

        }
    }

    private void callDeleteNote(String encryptedNoteID) {
        GH.getInstance().ShowProgressDialog(context);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).DeleteNote(GH.getInstance().getAuthorization(),
                encryptedNoteID);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

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
}
