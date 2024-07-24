package com.example.learnquest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private ArrayList<Group> groups;
    private View.OnClickListener onClickListener;
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView lblGroupTopic = itemView.findViewById(R.id.lblGroupTopic);
            ImageView imgGroupIcon  = itemView.findViewById(R.id.imgGroupIcon);
        }
    }
}
