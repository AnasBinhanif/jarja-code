package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.project.jarjamediaapp.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends MaterialSpinnerAdapter {

    ArrayList<String> getLeadSources;
    int pos = -1;

    Context context;
    List items;

    public SpinnerAdapter(Context context, List items) {
        super(context, items);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     /*   if (convertView != null) {
            if (pos == position) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            }
        }
        return super.getView(position, convertView, parent);*/

        View itemView =  super.getView(position, convertView, parent);

        if (pos == position) {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        return itemView;


    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setselectedposition(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }

    public void setData(List items){
        this.items = items;
        notifyDataSetChanged();
    }
}
