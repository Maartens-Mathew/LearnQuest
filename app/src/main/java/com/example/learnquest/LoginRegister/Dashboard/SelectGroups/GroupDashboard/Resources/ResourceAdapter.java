package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.studyResource.Temp.Tree;
import com.example.learnquest.model.studyResource.Temp.TreeNode;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceViewHolder> {

    private List<TreeNode> nodeList;
    private OnNodeClickListener listener;

    public ResourceAdapter(List<TreeNode> nodeList, OnNodeClickListener listener) {
        this.nodeList = nodeList;
        this.listener = listener;
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
        holder.bind(node, listener);
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
