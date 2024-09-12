package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.R;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<Tag> tagList;
    private Context context;

    public TagAdapter(Context context, List<Tag> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tagName;
        public Button editButton;
        public Button deleteButton;

        public TagViewHolder(View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tagName);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag currentTag = tagList.get(position);
        holder.tagName.setText(currentTag.getTagName());

        // Set onClickListener for Edit and Delete buttons here
        holder.editButton.setOnClickListener(v -> {
            // Handle Edit action
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Handle Delete action
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }
}
