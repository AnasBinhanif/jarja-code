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
import com.project.jarjamediaapp.Models.GetTags;
import com.project.jarjamediaapp.R;

import java.util.List;


public class SwipeNotesRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    int pos;
    String status = "";
    List<GetTags> mData;


    public SwipeNotesRecyclerAdapter(Context context) {

        this.context = context;
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

            GetTags modelData = mData.get(position);

            holder.tvName.setText(modelData.getName() + "");

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
        /*if (mData == null)
            return 0;*/
        return 5;
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
                    pos = getAdapterPosition();
                }
            });


        }
    }
}
