package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    Activity context;
    private List<GetLeadTitlesModel.Data> data;
    public static List<GetLeadTitlesModel.Data> selectedData = new ArrayList<>();
    public static ArrayList<Integer> selectedIDs = new ArrayList<>();
    public static ArrayList<String> selectedNames = new ArrayList<>();
    public static ArrayList<String> selectedEncryted = new ArrayList<>();
    int pos;
    boolean fromAppoint;
    private int selectedPos = RecyclerView.NO_POSITION;

    public SearchResultAdapter(Activity context, List<GetLeadTitlesModel.Data> data, boolean fromAppoint) {

        this.context = context;
        this.data = data;
        this.fromAppoint = fromAppoint;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.custom_search_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pos = position;

        GetLeadTitlesModel.Data model = data.get(position);
        holder.tvName.setText(model.name);
        if (selectedIDs.size() != 0) {

            if (selectedIDs.contains(model.decryptedLeadID)) {
                holder.itemView.setBackgroundColor(Color.LTGRAY);
            }else{
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rl;
        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            // UI Binding
            rl = itemView.findViewById(R.id.rlParent);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (fromAppoint) {
                Intent intent = new Intent();
                intent.putExtra("leadID", data.get(getAdapterPosition()).leadID);
                intent.putExtra("leadName", data.get(getAdapterPosition()).name);
                context.setResult(RESULT_OK, intent);
                context.finish();

            } else {
                if (selectedIDs.size() != 0) {

                    if (selectedIDs.contains(data.get(getAdapterPosition()).decryptedLeadID)) {
                        selectedIDs.remove(data.get(getAdapterPosition()).decryptedLeadID);
                        selectedNames.remove(data.get(getAdapterPosition()).name);
                        selectedEncryted.remove(data.get(getAdapterPosition()).leadID);
                        selectedData.remove(data.get(getAdapterPosition()));
                    } else {
                        selectedIDs.add(data.get(getAdapterPosition()).decryptedLeadID);
                        selectedNames.add(data.get(getAdapterPosition()).name);
                        selectedEncryted.add(data.get(getAdapterPosition()).leadID);
                        selectedData.add(data.get(getAdapterPosition()));
                    }
                } else {
                    selectedIDs.add(data.get(getAdapterPosition()).decryptedLeadID);
                    selectedNames.add(data.get(getAdapterPosition()).name);
                    selectedEncryted.add(data.get(getAdapterPosition()).leadID);
                    selectedData.add(data.get(getAdapterPosition()));
                }
                selectedPos = getLayoutPosition();
                notifyDataSetChanged();
            }
        }
    }


}

