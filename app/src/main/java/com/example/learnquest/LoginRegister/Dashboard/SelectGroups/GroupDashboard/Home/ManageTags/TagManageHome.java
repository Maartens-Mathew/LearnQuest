package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.example.learnquest.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.flexbox.FlexboxLayout;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagManageHome extends AppCompatActivity implements OnTagsChangedListener {

    private RecyclerView recyclerView;
    private TagAdapter adapter;
    private FlexboxLayout flexboxLayout;
    private String selectedColorHex = "#000000"; // Default white color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagrecycler);

        recyclerView = findViewById(R.id.tagRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize FlexboxLayout
        flexboxLayout = findViewById(R.id.flexboxForAddingQuizTags);

        // Assuming you set your group ID like this; adjust as needed
        App.groupID = 1;

        // Fetch tags by group ID
        fetchTagsByGroup(App.groupID.toString());
    }

    private void fetchTagsByGroup(String groupID) {
        // Direct API call to fetch tags
        Call<List<Tag>> tagsCall = App.api.getTagsByGroup("eq." + groupID);

        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Tag> tagList = response.body();
                    // Set up RecyclerView Adapter
                    adapter = new TagAdapter(TagManageHome.this, tagList,TagManageHome.this );
                    recyclerView.setAdapter(adapter);

                    // Display tags as pills
                    displayTagsAsPills(tagList);
                } else {
                    Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(TagManageHome.this, "Failed to load tags", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
                Toast.makeText(TagManageHome.this, "Error loading tags", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayTagsAsPills(List<Tag> tagList) {
        flexboxLayout.removeAllViews(); // Clear any existing views to avoid duplication
        for (Tag tag : tagList) {
            Button tagButton = new Button(this);


            // Add double-tap listener to the button
            tagButton.setOnClickListener(new DoubleTapListener(() -> {
                // Double tap detected, delete the tag
                deleteTag(tag);
            }));

            // Set the text of the button to the tag name
            tagButton.setText(tag.getTagName());

            // Set the background shape drawable with the tag color
            tagButton.setBackground(getPillDrawableWithColor(tag.getTagColour()));



            // Set padding and text size
            tagButton.setPadding(20, 8, 20, 8); // Adjust padding values if needed
            tagButton.setTextSize(12);

            tagButton.setTextColor(Color.WHITE);
            tagButton.setAllCaps(false); // Optional: to avoid all caps text

            // Set layout params for the button
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            tagButton.setLayoutParams(params);

            // Add the button to the FlexboxLayout
            flexboxLayout.addView(tagButton);
        }
    }

    // Helper function to create a drawable with the specified color
    private Drawable getPillDrawableWithColor(String colorHex) {
        int color = Color.parseColor(colorHex); // Convert hex string to color
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(
                new float[]{50, 50, 50, 50, 50, 50, 50, 50}, // corner radii
                null, // inner radius
                null  // border radius
        ));
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        return drawable;
    }

    public void addNew(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_tag_dialog, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.edtTagName);
        Button btnPickColor = dialogView.findViewById(R.id.btnPickColor);


        btnPickColor.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(TagManageHome.this)
                    .setTitle("Choose Color")
                    .initialColor(Color.parseColor(selectedColorHex)) // Starting color
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12) // Number of colors in the color picker
                    .setOnColorSelectedListener(selectedColor ->
                            Toast.makeText(getApplicationContext(), "Selected color: " + Integer.toHexString(selectedColor).toUpperCase(), Toast.LENGTH_SHORT).show()
                    )
                    .setPositiveButton("OK", (dialog, selectedColor, allColors) -> {
                        if (selectedColor == Color.WHITE) {
                            Toast.makeText(getApplicationContext(), "White color is not allowed. Please choose another color.", Toast.LENGTH_SHORT).show();
                            // Optionally: Set button background to a default color (e.g., gray) to indicate invalid selection
                            btnPickColor.setBackgroundColor(Color.GRAY);
                        } else {
                            // Convert the selected color to hex format
                            selectedColorHex = String.format("#%06X", (0xFFFFFF & selectedColor));
                            btnPickColor.setBackgroundColor(selectedColor); // Update button color
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .build()
                    .show();
        });


        builder.setPositiveButton("Add", (dialog, which) -> {
            String tagName = etName.getText().toString().trim();
            if (!tagName.isEmpty()) {
                addTag(tagName, selectedColorHex);
            } else {
                Toast.makeText(TagManageHome.this, "Tag name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addTag(String tagName, String colorHex) {
        Tag newTag = new Tag();
        newTag.setTagName(tagName);
        newTag.setTagColour(colorHex);
        newTag.setGroupID(App.groupID); // Assuming groupID is a String

        Log.d("AddTag", "Attempting to add tag with name: " + tagName + " and color: " + colorHex);

        Call<Void> addTagCall = App.api.addTag(newTag);
        addTagCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("AddTag", "Tag added successfully: " + response.body());
                        Toast.makeText(TagManageHome.this, "Tag added successfully", Toast.LENGTH_SHORT).show();
                        fetchTagsByGroup(App.groupID.toString()); // Refresh the tag list
                       // runOnUiThread(() -> TagManageHome.this.recyclerView.on);

                    } else {
                        Log.e("AddTag", "Response body is null");
                      //  Toast.makeText(TagManageHome.this, "Failed to add tag: No response body", Toast.LENGTH_SHORT).show();
                        fetchTagsByGroup(App.groupID.toString()); // Refresh the tag list

                    }
                } else {
                //    Log.e("AddTag", "Failed to add tag. Response Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(TagManageHome.this, "Failed to add tag", Toast.LENGTH_SHORT).show();
                    fetchTagsByGroup(App.groupID.toString()); // Refresh the tag list

                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddTag", "Error adding tag: " + t.getMessage(), t);
                Toast.makeText(TagManageHome.this, "Error adding tag", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper class to detect double-tap
    private class DoubleTapListener implements View.OnClickListener {
        private static final long DOUBLE_TAP_DELAY = 300; // Milliseconds between taps for a valid double-tap
        private long lastTapTime = 0;
        private Runnable doubleTapAction;

        public DoubleTapListener(Runnable doubleTapAction) {
            this.doubleTapAction = doubleTapAction;
        }

        @Override
        public void onClick(View v) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTapTime < DOUBLE_TAP_DELAY) {
                // Double tap detected
                doubleTapAction.run();
            }
            lastTapTime = currentTime;
        }
    }


    @Override
    public void onTagsChanged() {
        fetchTagsByGroup(App.groupID.toString());
    }


//    public void edit(View view) {
//        Tag tagToUpdate = new Tag();
//        tagToUpdate.setTagName("New Name");
//        tagToUpdate.setTagColour("#FF5733");
//
//        // Call the API
//        Call<Void> call = App.api.updateTagwID(tagToUpdate);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Log.d("EditTag", "Tag updated successfully");
//                    Toast.makeText(TagManageHome.this, "Tag updated successfully", Toast.LENGTH_SHORT).show();
//                    fetchTagsByGroup(App.groupID.toString()); // Refresh the tag list
//                } else {
//                    Log.e("EditTag", "Failed to update tag. Response Code: " + response.code() + ", Message: " + response.message());
//                    Toast.makeText(TagManageHome.this, "Failed to update tag", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("EditTag", "Error updating tag: " + t.getMessage(), t);
//                Toast.makeText(TagManageHome.this, "Error updating tag", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



    // Function to delete the tag and update the view
    private void deleteTag(Tag tag) {
        // Call the API to delete the tag

        String filter = "eq." + tag.getTagID();


        Call<Void> deleteTagCall = App.api.deleteTag(filter);
        deleteTagCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TagManageHome.this, "Tag deleted successfully", Toast.LENGTH_SHORT).show();
                    // Refresh tags after deletion
                    fetchTagsByGroup(App.groupID.toString());
                } else {
                    Toast.makeText(TagManageHome.this, "Failed to delete tag", Toast.LENGTH_SHORT).show();
                    Log.e("DeleteTag", "Response Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TagManageHome.this, "Error deleting tag as it is likely in use", Toast.LENGTH_SHORT).show();
                Log.e("DeleteTag", "Error: " + t.getMessage());
            }
        });
    }


//    private void deleteTag(String tagID, int position) {
//        Log.d("TagAdapter", "Attempting to delete tag with ID: " + tagID);
//
//        // Use the correct format for the filter (e.g., "id=eq.<tagID>")
//        String filter = "eq." + tagID;
//
//        // API call to delete the tag using the correct filter format
//        Call<Void> call = App.api.deleteTag(filter);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Log.d("TagAdapter", "Tag deleted successfully: " + tagID);
//                    Toast.makeText(context, "Tag deleted successfully", Toast.LENGTH_SHORT).show();
//
//                    // Notify the listener to refresh the tag list
//                    if (onTagsChangedListener != null) {
//                        onTagsChangedListener.onTagsChanged();
//                    }
//                } else {
//                    try {
//                        String errorBody = response.errorBody().string();  // Convert error body to string
//                        Log.e("TagAdapter", "Failed to delete tag: " + response.code() + ", message: " + response.message());
//                        Log.e("TagAdapter", "Error body: " + errorBody);
//                    } catch (Exception e) {
//                        Log.e("TagAdapter", "Failed to parse error body", e);
//                    }
//                    Toast.makeText(context, "Failed to delete tag", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("TagAdapter", "Error deleting tag: " + t.getMessage(), t);
//                Toast.makeText(context, "Error deleting tag: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//



}
