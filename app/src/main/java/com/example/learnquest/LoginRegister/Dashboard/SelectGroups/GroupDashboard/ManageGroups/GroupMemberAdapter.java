package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.List;

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

    public View.OnClickListener listener;

    @Override
    public void onBindViewHolder(@NonNull GroupMemberViewHolder holder, int position) {
        holder.listener = listener;
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
        public GroupMemberUserName user;
        private TextView username;
        public Button btnRemove;
        private CardView cardView;
        public View.OnClickListener listener;

        public void setModerator(boolean moderator) {
            this.isModerator = moderator;
        }

        private boolean isModerator;
        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.groupMemberUsernameLabel);
            username.setClickable(false);
            username.setFocusable(false);
            btnRemove = itemView.findViewById(R.id.btnGroupMemberRemove);
            btnRemove.setOnClickListener(this::viewHolderListener);
            cardView = itemView.findViewById(R.id.cardUserNameHolder);
        }

        public void viewHolderListener(View v){
            if (isModerator && !App.user.getUserID().equals(user.getUserID())){
                Intent intent = new Intent(v.getContext(), RemoveGroupMemberActivity.class);
                intent.putExtra("user",user);
                v.getContext().startActivity(intent);
            }
        }

        public void setUser(GroupMemberUserName user){
            this.user = user;
            username.setText(user.getUsername());
            cardView.setOnClickListener(listener);
        }
    }
}
