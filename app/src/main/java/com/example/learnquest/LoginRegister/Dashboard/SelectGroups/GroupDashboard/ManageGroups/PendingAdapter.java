package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import android.annotation.SuppressLint;
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
import com.example.learnquest.model.group.PendingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder>{
    public static final String PENDING_ADAPTER = "PendingAdapter";
    List<Pending> pending;

    public PendingAdapter(List<Pending> pending) {
        this.pending = pending;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rw_pending_viewholder,parent,false);
        return new PendingViewHolder(view);
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
        Pending user;
        Button btnAccept, btnReject;
        int pos;
        PendingAdapter adapter;
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            pendingName = itemView.findViewById(R.id.lblNamePending);//might not be the right R imported
            btnAccept = itemView.findViewById(R.id.btnAcceptPending);
            btnAccept.setOnClickListener(this::onAcceptClick);
            btnReject = itemView.findViewById(R.id.btnRejectPending);
            btnReject.setOnClickListener(this::onRejectClick);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Pending pendingUser, int pos, PendingAdapter adapter){
            pendingName.setText(pendingUser.getPendingUserFirstName() + " " + pendingUser.getPendingUserLastName());
            user = pendingUser;
            this.adapter = adapter;
            this.pos = pos;
        }



        public void onAcceptClick(View view){

                //TODO:remember to make this not hardcoded
                //TODO:PendingActivity Approve/Reject doesn't work




                Call<Void> updateCall = App.api.updateGroupMembership(PendingResponse.accept(user));
                updateCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Log.i(PENDING_ADAPTER,"Update Successful: "+ call.request().body().toString());
                            Log.i(PENDING_ADAPTER,"Response: "+ response.message().toString());
                        }
                        else{
                            Log.e(PENDING_ADAPTER,"Update failed: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.e(PENDING_ADAPTER,"Error: " + throwable.getStackTrace().toString());
                    }
                });
                adapter.remove(pos);

        }

        public void onRejectClick(View view){

                Call<Void> rejectionCall = App.api.removeUser(user.getUserID(), App.group.getGroupID());
                Log.i(PENDING_ADAPTER, "start of database call");
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

        }
    }
}
