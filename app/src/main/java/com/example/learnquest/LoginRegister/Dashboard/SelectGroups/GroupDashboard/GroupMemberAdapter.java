package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.GroupMemberViewHolder> {
    @NonNull
    @Override
    public GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupmember_viewholder,parent,false);
        return new GroupMemberViewHolder(v);
    }

    private List<GroupMemberUserName> users;
    private boolean isMod;

    public GroupMemberAdapter(List<GroupMemberUserName> users, boolean isMod) {
        this.users = users;
        this.isMod = isMod;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMemberViewHolder holder, int position) {
        holder.setUser(users.get(position));
        holder.setModerator(isMod);
    }

    public GroupMemberAdapter(List<GroupMemberUserName> users) {
        this.users = users;
    }

    public void remove(int i){
        users.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class GroupMemberViewHolder extends RecyclerView.ViewHolder{
        private GroupMemberUserName user;
        private TextView username;

        public void setModerator(boolean moderator) {
            this.isModerator = moderator;
        }

        private boolean isModerator;
        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.groupMemberUsernameLabel);
            username.setOnClickListener(this::viewHolderListener);
        }

        public void viewHolderListener(View v){
            if (isModerator){
                Intent intent = new Intent(v.getContext(), RemoveGroupMemberActivity.class);
                intent.putExtra("user",user);
                v.getContext().startActivity(intent);
            }
        }

        public void setUser(GroupMemberUserName user){
            this.user = user;
            username.setText(user.getUsername());
        }
    }
}
