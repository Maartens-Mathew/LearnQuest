package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder>{
    public static final String PENDING_ADAPTER = "PendingAdapter";
    List<PendingUsersResult> pending;

    public PendingAdapter(List<PendingUsersResult> pending) {
        this.pending = pending;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rw_pending_viewholder,parent,false);
        PendingViewHolder pvh = new PendingViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingViewHolder holder, int position) {
        holder.setData(pending.get(position), position, this);
    }

    public void remove(int i){
        pending.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public int getItemCount() {
        return pending.size();
    }

    public static class PendingViewHolder extends RecyclerView.ViewHolder{
        TextView pendingName;
        PendingUsersResult user;
        Button btnAccept, btnReject;
        int pos;
        PendingAdapter adapter;
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            pendingName = itemView.findViewById(R.id.lblNamePending);//might not be the right R imported
            btnAccept = itemView.findViewById(R.id.btnAcceptPending);
            btnAccept.setOnClickListener(view -> {
                GroupMembership update = new GroupMembership(user.userID, App.groupID,1);
                Call<GroupMembership> call = App.api.setGroupMembership(update.getUserID(),update.getGroupID(),update);
                call.enqueue(new Callback<GroupMembership>() {
                    @Override
                    public void onResponse(Call<GroupMembership> call, Response<GroupMembership> response) {
                        if (response.isSuccessful()){
                            Log.i(PENDING_ADAPTER,"updated successfully");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupMembership> call, Throwable throwable) {
                        Log.e(PENDING_ADAPTER,"Did not update");
                        throwable.printStackTrace();
                    }
                });
                adapter.remove(pos);
            });
            btnReject = itemView.findViewById(R.id.btnRejectPending);
            btnReject.setOnClickListener(view ->{
                Call<Void> rejectionCall = App.api.deleteGroupMemberShip(user.userID, App.groupID);
                rejectionCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Log.i(PENDING_ADAPTER,"deleted successfully");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.e(PENDING_ADAPTER,"failed to delete");
                        throwable.printStackTrace();
                    }
                });
                adapter.remove(pos);
            });
        }

        public void setData(PendingUsersResult pendingUser, int pos, PendingAdapter adapter){
            pendingName.setText(pendingUser.getPendingUserFirstName() + " " + pendingUser.getPendingUserLastName());
            user = pendingUser;
            this.adapter = adapter;
            this.pos = pos;
        }
    }
}
