package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.notes.AddNotesActivity;
import com.project.jarjamediaapp.Activities.notes.NotesActivity;
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
    Activity activity;
    int pos;
    String status = "";
    ArrayList<GetLeadNotes.NotesList> mData;

    public static boolean isEditable = false;
    String agentString = "";


    public SwipeNotesRecyclerAdapter(Context context, Activity activity, ArrayList<GetLeadNotes.NotesList> getLeadNotes) {

        this.context = context;
        this.activity = activity;
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

                holder.tvDateTime.setText(GH.getInstance().formatter(createdDate, "dd-MM-yyyy", "yyyy-MM-dd HH:mm") + "");
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
            agentString = "";
            for (GetLeadNotes.AgentList agents : modelData.agentList) {

                if (agentString.equals("")) {
                    agentString = agents.agentName;
                } else {
                    agentString = agentString + "," + agents.agentName;
                }
            }

            holder.tvAgentNames.setText("Note For : " + agentString);

            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            holder.cbNoteSticky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Confirmation");
                    alertDialog.setMessage("Are you sure you want to edit this Note ?");
                    alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        if (holder.cbNoteSticky.isChecked()) {
                            callStickyNote(modelData.encryptedNoteID, modelData.encrypted_LeadID, holder.cbNoteSticky.isChecked());
                        } else {
                            callStickyNote(modelData.encryptedNoteID, modelData.encrypted_LeadID, holder.cbNoteSticky.isChecked());
                        }
                        dialog.dismiss();

                    });
                    alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (holder.cbNoteSticky.isChecked()) {
                                holder.cbNoteSticky.setChecked(false);
                            } else {
                                holder.cbNoteSticky.setChecked(true);
                            }
                        }
                    });

                    alertDialog.create().show();

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
        TextView tvDateTime, tvTime, tvNoteType, tvDesc, tvAgentNames;
        LinearLayout lnEdit, lnDelete;
        CheckBox cbNoteSticky;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);

            lnEdit = itemView.findViewById(R.id.lnEdit);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAgentNames = itemView.findViewById(R.id.tvAgentNames);
            tvNoteType = itemView.findViewById(R.id.tvNoteType);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            lnDelete = itemView.findViewById(R.id.lnDelete);
            cbNoteSticky = itemView.findViewById(R.id.cbNoteSticky);
        }

        public void bind() {


            lnEdit.setOnClickListener(v -> {

                isEditable = true;
                pos = getAdapterPosition();
                Intent intent = new Intent(context, AddNotesActivity.class);
                intent.putExtra("Note", mData.get(pos));
                context.startActivity(intent);

            });

            lnDelete.setOnClickListener(v -> {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to delete this Note ?");
                alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    pos = getAdapterPosition();
                    String noteID = mData.get(pos).encryptedNoteID;
                    callDeleteNote(noteID, swipeLayout);
                    dialog.dismiss();

                });
                alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            });


        }
    }

    private void callDeleteNote(String encryptedNoteID, SwipeRevealLayout swipeLayout) {

        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).DeleteNote(GH.getInstance().getAuthorization(),
                encryptedNoteID);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Successfully Deleted");
                        mData.remove(pos);
                        swipeLayout.close(true);
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

    private void callStickyNote(String encryptedNoteID, String encryptedLeadid, Boolean isSticky) {
        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Make_Lead_Note_Sticky(GH.getInstance().getAuthorization(),
                encryptedNoteID, encryptedLeadid, isSticky);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Successfully Done");
                        ((NotesActivity) context).refreshData();

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
