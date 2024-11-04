package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class quizNewInsertAct extends AppCompatActivity {
    private Spinner spinnerTags;
    private FlexboxLayout flexboxLayout;
    private List<Tag> tagList = new ArrayList<>(); // Store all tags
    private Set<Tag> selectedTags = new HashSet<>(); // Store selected tag names to avoid duplicates
    private EditText edtQuizQ;
    private EditText edtQuizD;
    private EditText edtQuizA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_new_insert);

        App.group.setGroupID(1);

        spinnerTags = findViewById(R.id.spinAddTagsE);
        flexboxLayout = findViewById(R.id.flexboxForValidatingQuizs);
        edtQuizQ = findViewById(R.id.edtQuizQ);
        edtQuizD = findViewById(R.id.edtQuizD);
        edtQuizA = findViewById(R.id.edtQuizA);

        // Clear the FlexboxLayout when the activity is created
        flexboxLayout.removeAllViews();
        selectedTags.clear();

        // Fetch tags by group ID (for Spinner)
        fetchTagsByGroup(App.group.getGroupID().toString());

        // Set listener to handle spinner selections
        spinnerTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void populateSpinner(List<Tag> tagList) {
        // Populate the spinner with tag names
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tagList) {
            tagNames.add(tag.getTagName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTags.setAdapter(adapter);
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
                    Toast.makeText(quizNewInsertAct.this, "Failed to load tags", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
                Toast.makeText(quizNewInsertAct.this, "Error loading tags", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void addNewQE(View view) {
        // Get the text from the EditText fields
        String question = edtQuizQ.getText().toString().trim();
        String answer = edtQuizA.getText().toString().trim();
        String description = edtQuizD.getText().toString().trim();

        // Check if all fields are blank
        if (question.isEmpty() || answer.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return; // Exit the method without proceeding
        }

        // Proceed with creating the QuizEntry
        QuizEntry entry = new QuizEntry();
        entry.setQuestion(question);
        entry.setAnswer(answer);
        entry.setDescription(description);
        entry.setGroupID(App.group.getGroupID());

        // Add selected tags to the entry
        for (Tag tag : selectedTags) {
            entry.addTag(tag);
        }

        if (selectedTags.isEmpty()) {
            Toast.makeText(this, "Ensure 1 tag is selected.", Toast.LENGTH_SHORT).show();
            return; // Exit the method without proceeding
        }

        entry.setQuizEntryID(-1);
        entry.setValidated(false);
        entry.setInContention(false);

        Thread thread = new Thread(() -> {
            Call<Void> quizCall = App.api.addQuizEntry(entry);
            Response<Void> quizResponse;

            try {
                quizResponse = quizCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (quizResponse.isSuccessful()) {
                runOnUiThread(() -> {
                    Toast.makeText(quizNewInsertAct.this, "Insertion successful.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });
            } else {
                try {
                    Log.e("Custom", quizResponse.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


}
