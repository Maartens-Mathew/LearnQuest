package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder>{
    List<String> pending;

    public PendingAdapter(List<String> pending) {
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
        Button btnAccept, btnReject;
        int pos;
        PendingAdapter adapter;
        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            pendingName = itemView.findViewById(R.id.lblNamePending);//might not be the right R imported
            btnAccept = itemView.findViewById(R.id.btnAcceptPending);
            btnAccept.setOnClickListener(view -> {
                adapter.remove(pos);
            });
            btnReject = itemView.findViewById(R.id.btnRejectPending);
            btnReject.setOnClickListener(view ->{
                adapter.remove(pos);
            });
        }

        public void setData(String name, int pos, PendingAdapter adapter){
            pendingName.setText(name);
            this.pos = pos;
        }
    }
}
