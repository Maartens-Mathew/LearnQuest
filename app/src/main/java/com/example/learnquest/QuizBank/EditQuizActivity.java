package com.example.learnquest.QuizBank;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.google.android.flexbox.FlexboxLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuizActivity extends AppCompatActivity {

    private EditText edtQuestion, edtDescription, edtAnswer;
    private Spinner spinAddTags;
    private Button btnSave;
    private FlexboxLayout flexboxLayout;
    private Switch switchInContention;

    private List<Tag> tagList = new ArrayList<>();
    private Set<Tag> selectedTags = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_edit_insert);

        // Initialize views
        edtQuestion = findViewById(R.id.edtQuizQ);
        edtDescription = findViewById(R.id.edtQuizD);
        edtAnswer = findViewById(R.id.edtQuizA);
        spinAddTags = findViewById(R.id.spinAddTags);
        btnSave = findViewById(R.id.btnAddQuizEntry);
        flexboxLayout = findViewById(R.id.flexboxForAddingQuizTags);
        switchInContention = findViewById(R.id.switchInContention);

        // Get the passed data
        Intent intent = getIntent();
        int quizEntryID = intent.getIntExtra("quizEntryID", -1);
        String question = intent.getStringExtra("question");
        String description = intent.getStringExtra("description");
        String answer = intent.getStringExtra("answer");
        List<String> tags = intent.getStringArrayListExtra("tags"); // Use getStringArrayListExtra for List<String>
        boolean inContention = intent.getBooleanExtra("inContention", false);

        // Set the data to the EditTexts
        edtQuestion.setText(question);
        edtDescription.setText(description);
        edtAnswer.setText(answer);
        switchInContention.setChecked(inContention);

        // Fetch tags by group ID to populate Spinner
        fetchTagsByGroup(App.groupID.toString());

        // Handle save button click
        btnSave.setOnClickListener(view -> {
            // Get the edited values
            String updatedQuestion = edtQuestion.getText().toString();
            String updatedDescription = edtDescription.getText().toString();
            String updatedAnswer = edtAnswer.getText().toString();
            boolean updatedInContention = switchInContention.isChecked();

            // Update the quiz entry
            updateQuizEntry(quizEntryID, updatedQuestion, updatedDescription, updatedAnswer, updatedInContention);
        });

        // Set listener to handle spinner selections
        spinAddTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected tag based on the position
                Tag selectedTag = tagList.get(position);

                // Only add the tag to the Flexbox if it hasn't been selected before
                if (!selectedTags.contains(selectedTag)) {
                    selectedTags.add(selectedTag); // Track the selected tag
                    addTagButton(selectedTag); // Add button for the selected tag
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private void initializeSelectedTags(List<String> tags) {
        // Ensure FlexboxLayout is cleared
        flexboxLayout.removeAllViews();

        for (String tagName : tags) {
            Tag tag = getTagByName(tagName);
            if (tag != null) {
                selectedTags.add(tag);
                addTagButton(tag); // Add button for the tag to the FlexboxLayout
            }
        }
    }





    private Tag getTagByName(String tagName) {
        // Implement this method to retrieve a Tag object by its name
        for (Tag tag : tagList) {
            if (tag.getTagName().equals(tagName)) {
                return tag;
            }
        }
        return null;
    }
    private void fetchTagsByGroup(String groupID) {
        Call<List<Tag>> tagsCall = App.api.getTagsByGroup("eq." + groupID);

        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tagList = response.body();
                    populateSpinner(tagList);
                } else {
                    Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(EditQuizActivity.this, "Failed to load tags", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
                Toast.makeText(EditQuizActivity.this, "Error loading tags", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void populateTags(List<String> tags) {
        flexboxLayout.removeAllViews(); // Clear existing views

        for (String tag : tags) {
            // Create a new TextView or Chip for each tag
            TextView tagView = new TextView(this);
            tagView.setText(tag);
            tagView.setPadding(16, 8, 16, 8);
            tagView.setBackgroundResource(R.drawable.pill_button); // Custom background drawable for tag
            tagView.setTextColor(Color.WHITE); // Set text color
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            // Add tag view to FlexboxLayout
            flexboxLayout.addView(tagView);
        }
    }

    private void addTagButton(Tag tag) {
        // Create a new Button for the selected tag
        Button tagButton = new Button(this);
        tagButton.setText(tag.getTagName());

        // Set the background color using the tag's color
        try {
            tagButton.setBackground(getPillDrawableWithColor(tag.getTagColour()));
        } catch (IllegalArgumentException e) {
            Log.e("TagColorError", "Invalid color for tag: " + tag.getTagName());
            tagButton.setBackgroundColor(Color.BLACK); // Default color if parsing fails
        }

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

        // Optionally, you can add logic to remove the tag when the button is clicked
        tagButton.setOnClickListener(v -> {
            // Remove the button from the FlexboxLayout
            flexboxLayout.removeView(tagButton);

            // Remove the actual tag object from the selectedTags set
            selectedTags.remove(tag); // This removes the actual Tag object, not just the tag name
        });
    }

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

    private void updateQuizEntry(int quizEntryID, String question, String description, String answer, boolean inContention) {
        QuizEntry entry = new QuizEntry();
        entry.setQuizEntryID(quizEntryID);
        entry.setQuestion(question);
        entry.setDescription(description);
        entry.setAnswer(answer);
        entry.setGroupID(App.groupID);
        entry.setInContention(inContention);

        for (Tag tag : selectedTags) {
            entry.addTag(tag);
        }

        Thread thread = new Thread(() -> {
            Call<Void> updateCall = App.api.updateQuizEntry(entry);
            Response<Void> response;

            try {
                response = updateCall.execute();
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(EditQuizActivity.this, "Entry updated!", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity and return to the previous one
                    });
                } else {
                    Log.e("EditQuizActivity", "Failed to update entry: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }


    private void populateSpinner(List<Tag> tagList) {
        // Populate the spinner with tag names
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tagList) {
            tagNames.add(tag.getTagName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAddTags.setAdapter(adapter);
    }

}
