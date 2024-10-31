package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags;

import android.content.Context;
import android.graphics.Color;
import android.util.Log; // Import for logging
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.example.learnquest.R;
import com.example.learnquest.AppState.App;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

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

        // Set onClickListener for Edit
        holder.editButton.setOnClickListener(v -> {
            // Show a dialog to edit the tag
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.add_tag_dialog, null); // Reuse the dialog layout

            EditText etName = dialogView.findViewById(R.id.edtTagName);
            Button btnPickColor = dialogView.findViewById(R.id.btnPickColor);

            // Set current tag values
            etName.setText(currentTag.getTagName());
            btnPickColor.setBackgroundColor(Color.parseColor(currentTag.getTagColour()));

            // Color picker dialog
            btnPickColor.setOnClickListener(v2 -> {
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose Color")
                        .initialColor(Color.parseColor(currentTag.getTagColour()))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setPositiveButton("OK", (dialog, selectedColor, allColors) -> {
                            // Convert the selected color to hex format
                            String selectedColorHex = String.format("#%06X", (0xFFFFFF & selectedColor));
                            btnPickColor.setBackgroundColor(selectedColor); // Update button color
                            currentTag.setTagColour(selectedColorHex); // Update tag color
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .build()
                        .show();
            });

            builder.setView(dialogView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        String updatedName = etName.getText().toString().trim();
                        if (!updatedName.isEmpty()) {
                            currentTag.setTagName(updatedName);
                            updateTag(currentTag, position);
                        } else {
                            Toast.makeText(context, "Tag name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
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

    private void updateTag(Tag tag, int Pos) {
        // Construct the filter with the eq operator for the primary key
        String filter = "eq." + tag.getTagID(); // Example: "eq.22" to filter by tagID 22

        // Make the API call
        Call<Void> call = App.api.updateTagwID(filter, tag);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TagAdapter", "Tag updated successfully: " + tag.getTagID());
                    Toast.makeText(context, "Tag updated successfully", Toast.LENGTH_SHORT).show();

                    // Update the tag in the data source
                    if (onTagsChangedListener != null) {
                        onTagsChangedListener.onTagsChanged();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("TagAdapter", "Failed to update tag: " + response.code() + ", message: " + response.message());
                        Log.e("TagAdapter", "Error body: " + errorBody);
                    } catch (Exception e) {
                        Log.e("TagAdapter", "Failed to parse error body", e);
                    }
                    Toast.makeText(context, "Failed to update tag", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TagAdapter", "Error updating tag: " + t.getMessage(), t);
                Toast.makeText(context, "Error updating tag: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
