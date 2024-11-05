package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.user.User;
import com.google.android.flexbox.FlexboxLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.lang.reflect.Field;

public class EditQuizActivity extends AppCompatActivity {

    private EditText edtQuestion, edtDescription, edtAnswer;
    private Button btnSave;
    private FlexboxLayout flexboxLayout;
    private Switch switchInContention;

    private Set<Tag> selectedTags = new HashSet<>();
    private List<Tag> tagList; // Replace with the actual source of your tags
    private List<Tag> foundTags; // New list for found tags

    private Spinner spinnerForAddNewTags ;
    private Integer GROUP_ID;
    private Integer USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_edit_insert);

        // Initialize views
        edtQuestion = findViewById(R.id.edtQuizQ);
        edtDescription = findViewById(R.id.edtQuizD);
        edtAnswer = findViewById(R.id.edtQuizA);
        btnSave = findViewById(R.id.btnAddEditedQuizEntry);
        flexboxLayout = findViewById(R.id.flexboxForshowingTagsToFil);
        switchInContention = findViewById(R.id.switchInContention);
        spinnerForAddNewTags = findViewById(R.id.spinAddTagsE);


        spinnerForAddNewTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        // Initialize foundTags list
        foundTags = new ArrayList<>();

        // Get the passed data
// Get the passed data safely with default values
        Intent intent = getIntent();
        int quizEntryID = intent.getIntExtra("quizEntryID", -1); // Check if this extra exists
        String question = intent.hasExtra("question") ? intent.getStringExtra("question") : "";
        String description = intent.hasExtra("description") ? intent.getStringExtra("description") : "";
        String answer = intent.hasExtra("answer") ? intent.getStringExtra("answer") : "";
        String tagsString = intent.hasExtra("tags") ? intent.getStringExtra("tags") : "";
        boolean inContention = intent.getBooleanExtra("inContention", false);



        edtQuestion.setText(question);
        edtAnswer.setText(answer);
        edtDescription.setText(description);
        switchInContention.setChecked(inContention);

        // Convert the comma-separated tags string to a List<String>
        List<String> tagsList = tagsString != null ? Arrays.asList(tagsString.split(",")) : new ArrayList<>();

        if (App.group == null) {
            App.group = Group.demoGroup();
            App.user = User.demoUser();
        }

        GROUP_ID = App.group.getGroupID();
        USER_ID = App.user.getUserID();
        fetchTagsByGroup(GROUP_ID, tagsList);
    }

    private void populateSpinner(List<Tag> tagList) {
        // Populate the spinner with tag names
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tagList) {
            tagNames.add(tag.getTagName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForAddNewTags.setAdapter(adapter);
    }



    private Tag getTagByName(String tagName) {
        for (Tag tag : tagList) {
            try {
                Field field = Tag.class.getDeclaredField("tagName");
                field.setAccessible(true); // Make the field accessible

                // Check if the tag name matches
                if (field.get(tag).equals(tagName.trim())) {
                    return tag;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private void initializeSelectedTags(List<String> tagsList) {
        for (String tagName : tagsList) {
            Tag tag = getTagByName(tagName); // Get tag object from the list
            if (tag != null) {
                foundTags.add(tag); // Add found tag to the list
                selectedTags.add(tag); // Also add found tag to selectedTags
                addTagButton(tag); // Add button for the found tag
            }
        }
    }

    private void addTagButton(Tag tag) {
        Button tagButton = new Button(this);
        tagButton.setText(tag.getTagName());
        //selectedTags.add(tag);
        try {
            tagButton.setBackground(getPillDrawableWithColor(tag.getTagColour()));
        } catch (IllegalArgumentException e) {
            Log.e("TagColorError", "Invalid color for tag: " + tag.getTagName());
            tagButton.setBackgroundColor(Color.BLACK); // Default color if parsing fails
        }

        tagButton.setPadding(20, 8, 20, 8);
        tagButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tagButton.setTextColor(Color.WHITE);
        tagButton.setAllCaps(false);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        tagButton.setLayoutParams(params);

        flexboxLayout.addView(tagButton);

        tagButton.setOnClickListener(v -> {
            flexboxLayout.removeView(tagButton);
            selectedTags.remove(tag);
        });
    }

    private Drawable getPillDrawableWithColor(String colorHex) {
        int color = Color.parseColor(colorHex);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(
                new float[]{50, 50, 50, 50, 50, 50, 50, 50},
                null,
                null
        ));
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        return drawable;
    }

    private void fetchTagsByGroup(Integer groupID, List<String> tagsList) {
        Call<List<Tag>> tagsCall = App.api.getTagsByGroup(groupID);

        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tagList = response.body();
                    initializeSelectedTags(tagsList); // Move tag initialization here
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

    public void ConfirmEdit(View view) {
        // Create and populate QuizEntry object
        QuizEntry entry = new QuizEntry();
        entry.setQuestion(edtQuestion.getText().toString());
        entry.setAnswer(edtAnswer.getText().toString());
        entry.setDescription(edtDescription.getText().toString());
        entry.setInContention(switchInContention.isChecked()); // Use isChecked() instead of isSelected()
        entry.setGroupID(GROUP_ID);

        // Add selected tags
        for (Tag tag : selectedTags) {
            entry.addTag(tag);
        }

        // You need to pass the actual QuizEntry ID if editing, not always set to -1
        entry.setQuizEntryID(getIntent().getIntExtra("quizEntryID", -1)); // Retrieve the quiz entry ID from the intent

        // If you're editing, this should reflect the actual state
        entry.setValidated(false);  // Assuming false for validation unless otherwise
        //entry.setInContention(switchInContention.isChecked()); // Again, use isChecked() for Switch

        // Run the network call in a background thread
        new Thread(() -> {
            Call<Void> quizCall = App.api.updateQuizEntry(entry);
            Response<Void> quizResponse;

            try {
                // Execute the API call
                quizResponse = quizCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Handle the response
            if (quizResponse.isSuccessful()) {
                runOnUiThread(() -> {
                    Toast.makeText(EditQuizActivity.this, "Update successful.", Toast.LENGTH_SHORT).show();

                    // When updating or finishing the edit
                    setResult(RESULT_OK);
                    finish();
                });
            } else {
                try {
                    Log.e("API Error", quizResponse.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }









}
