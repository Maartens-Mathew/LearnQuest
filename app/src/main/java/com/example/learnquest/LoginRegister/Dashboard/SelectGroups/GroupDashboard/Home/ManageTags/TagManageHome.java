package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.flexbox.FlexboxLayout;


import java.util.List;
import java.util.Random;
import java.util.UUID;

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
        flexboxLayout = findViewById(R.id.flexboxLayoutTags);

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
                        // Convert the selected color to hex format
                        selectedColorHex = String.format("#%06X", (0xFFFFFF & selectedColor));
                        btnPickColor.setBackgroundColor(selectedColor); // Update button color as feedback
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
                        Toast.makeText(TagManageHome.this, "Failed to add tag: No response body", Toast.LENGTH_SHORT).show();
                        fetchTagsByGroup(App.groupID.toString()); // Refresh the tag list

                    }
                } else {
                    Log.e("AddTag", "Failed to add tag. Response Code: " + response.code() + ", Message: " + response.message());
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

    @Override
    public void onTagsChanged() {
        fetchTagsByGroup(App.groupID.toString());
    }





}
