package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private final List<Group> groups;
    private final onHolderClick<Group> onHolderClick;

    public GroupAdapter(List<Group> groups, onHolderClick<Group> onHolderClick){
        this.groups = groups;
        this.onHolderClick = onHolderClick;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_group,parent,false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.setGroup(group);

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView lblGroupTopic;
        ImageView imgGroupIcon;
        View background;
        public Group group;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            lblGroupTopic = itemView.findViewById(R.id.vh_txtGroupName);
            imgGroupIcon  = itemView.findViewById(R.id.vh_groupImage);
            background = itemView.findViewById(R.id.vh_background);

        }
        public void setGroup(Group group){
            this.group = group;
            lblGroupTopic.setText(group.getTopic());
            imgGroupIcon.setImageBitmap(group.getImage());
            background.setBackgroundColor(Color.parseColor(group.getGroupColour()));
            itemView.setOnClickListener(onHolderClick.onClick(group));
        }


    }
}
