package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private final List<Group> groups;
    private View.OnClickListener onClickListener;

    public GroupAdapter(List<Group> groups){
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
        TextView txtGroupTopic;
        View view;
        public Group group;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            txtGroupTopic = itemView.findViewById(R.id.txtGroupTopic);

        }
        public void setGroup(Group group){
            this.group = group;
            txtGroupTopic.setText(group.topic);
            view.setBackgroundColor(Color.parseColor(group.getGroupColour()));
        }
    }
}
