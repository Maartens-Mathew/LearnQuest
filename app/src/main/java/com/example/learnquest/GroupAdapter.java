package com.example.learnquest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private final ArrayList<Group> groups;
    private View.OnClickListener onClickListener;

    public GroupAdapter(ArrayList<Group> groups){
        this.groups = groups;
    }

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
        TextView lblGroupTopic;
        ImageView imgGroupIcon;
        public Group group;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            lblGroupTopic = itemView.findViewById(R.id.lblGroupTopic);
            imgGroupIcon  = itemView.findViewById(R.id.imgGroupIcon);
        }
        public void setGroup(Group group){
            this.group = group;
            lblGroupTopic.setText(group.topic);
            imgGroupIcon.setImageResource(R.drawable.test_image);
        }
    }
}
