package com.example.learnquest.GroupStuff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    public void filterList(List<Group> filteredGroup){
        groups = filteredGroup;
        notifyDataSetChanged();
    }

    private List<Group> groups;
    private View.OnClickListener onClickListener;

    public GroupAdapter(List<Group> groups){
        this.groups = groups;
    }

    public void add(Group group){
        groups.add(group);
        notifyItemChanged(groups.size()-1);
        ArrayList<Group> g = new ArrayList<>();
    }

    public void remove(int i){
        groups.remove(i);
        notifyItemRemoved(i);
    }

    public Group get(int i){
        return groups.get(i);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rw_groups_viewholder, parent, false);
        GroupViewHolder gvh = new GroupViewHolder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.setGroup(group);
        holder.onClickListener = this.onClickListener;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView lblGroupTopic;
        ImageView imgGroupIcon;
        public View.OnClickListener onClickListener;
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
