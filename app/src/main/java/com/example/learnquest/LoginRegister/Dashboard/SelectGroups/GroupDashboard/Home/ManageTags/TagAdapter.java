package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags;

import android.content.Context;
import android.util.Log; // Import for logging
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.R;
import com.example.learnquest.AppState.App;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<Tag> tagList;
    private Context context;
    private OnTagsChangedListener onTagsChangedListener; // Add this field

    public TagAdapter(Context context, List<Tag> tagList, OnTagsChangedListener listener) {
        this.context = context;
        this.tagList = tagList;
        this.onTagsChangedListener = listener;

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

        // Set onClickListener for Edit and Delete buttons
        holder.editButton.setOnClickListener(v -> {
            // Handle Edit action
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Confirmation dialog before deletion
            new AlertDialog.Builder(context)
                    .setTitle("Delete Tag")
                    .setMessage("Are you sure you want to delete this tag?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteTag(String.valueOf(currentTag.getTagID()), position))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    private void deleteTag(String tagID, int position) {
        Log.d("TagAdapter", "Attempting to delete tag with ID: " + tagID);

        // Use the correct format for the filter (e.g., "id=eq.<tagID>")
        String filter = "eq." + tagID;

        // API call to delete the tag using the correct filter format
        Call<Void> call = App.api.deleteTag(filter);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TagAdapter", "Tag deleted successfully: " + tagID);
                    Toast.makeText(context, "Tag deleted successfully", Toast.LENGTH_SHORT).show();

                    // Notify the listener to refresh the tag list
                    if (onTagsChangedListener != null) {
                        onTagsChangedListener.onTagsChanged();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();  // Convert error body to string
                        Log.e("TagAdapter", "Failed to delete tag: " + response.code() + ", message: " + response.message());
                        Log.e("TagAdapter", "Error body: " + errorBody);
                    } catch (Exception e) {
                        Log.e("TagAdapter", "Failed to parse error body", e);
                    }
                    Toast.makeText(context, "Failed to delete tag", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TagAdapter", "Error deleting tag: " + t.getMessage(), t);
                Toast.makeText(context, "Error deleting tag: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
