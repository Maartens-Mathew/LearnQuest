package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.OnNodeClickListener;
import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.Temp.TreeNode;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceViewHolder> {

    private List<TreeNode> nodeList;
    private OnNodeClickListener listener;
    private OnNodeLongClickListener long_listener;

    public ResourceAdapter(List<TreeNode> nodeList, OnNodeClickListener listener, OnNodeLongClickListener long_listener) {
        this.nodeList = nodeList;
        this.listener = listener;
        this.long_listener = long_listener;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_studyresource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        TreeNode node = nodeList.get(position);
        holder.bind(node, listener, long_listener);
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    public void updateData(List<TreeNode> newNodes) {
        this.nodeList = newNodes;
        notifyDataSetChanged();
    }

}
