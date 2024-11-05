package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.takingQuiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.QuizEntry;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.user.User;
import com.google.android.flexbox.FlexboxLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.widget.AdapterView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class takeQuizSplasha extends AppCompatActivity {

    private Spinner spinnerForTarget;
    private FlexboxLayout flexboxLayoutTT;
    private List<Tag> tagList = new ArrayList<>(); // Store all tags
    private Set<Tag> selectedTags = new HashSet<>(); // Store selected tag names to avoid duplicates
    private EditText edtNumQ;
    private TextView txtRecNum;
    public List<QuizEntry> entries ;
        public  Integer sizeOfFilteredSet;
    private List<QuizEntry> filteredEntries = new ArrayList<>();
    List<QuizEntry> reducedEntriesToIntentOver ;
    private  Button btnSetTagsforQuizz;




    private Integer GROUP_ID;
    private Integer USER_ID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_splash);
        sizeOfFilteredSet = 0;
        btnSetTagsforQuizz = findViewById(R.id.btnSetTagsforQuizz);

        entries = new ArrayList<>();
       // filteredEntries = new ArrayList<>();
        reducedEntriesToIntentOver = new ArrayList<>();
        if (App.group == null) {
            App.group = Group.demoGroup();
            App.user = User.demoUser();
        }

        GROUP_ID = App.group.getGroupID();
        USER_ID = App.user.getUserID();





        spinnerForTarget = findViewById(R.id.spinAddTagsToFIlter);
        flexboxLayoutTT = findViewById(R.id.flexboxForshowingTagsToFil);
        edtNumQ = findViewById(R.id.edtNumOfQuestions);

        // Clear the FlexboxLayout when the activity is created
        flexboxLayoutTT.removeAllViews();
        selectedTags.clear();

        // Fetch tags by group ID (for Spinner)
        fetchTagsByGroup(GROUP_ID);


        // Set listener to handle spinner selections
        spinnerForTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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




        Runnable runnable = () ->{

            fetchQuizQuestions();
        };

        Thread thread = new Thread(runnable);


        try{
            thread.start();
            thread.join();
        }catch(InterruptedException ignored){};

        if (entries.size() != 0 ) {
            Log.i("Custom", "SUCCESS???");
        } else {
            Log.e("Custom", "Entries list is null!");
        }

        btnSetTagsforQuizz.setOnClickListener(v->{
            txtRecNum = findViewById(R.id.txtRecSize);
            Integer seventyPOfSize = (int) (0.7*entries.size());
            txtRecNum.setText("Ideally a quiz of: " + seventyPOfSize +" as a rough estimate");

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
        spinnerForTarget.setAdapter(adapter);
    }

    private void fetchTagsByGroup(Integer groupID) {
        Call<List<Tag>> tagsCall = App.api.getTagsByGroup(groupID);

        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tagList = response.body();
                    populateSpinner(tagList);
                } else {
                    Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(takeQuizSplasha.this, "Failed to load tags", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
                Toast.makeText(takeQuizSplasha.this, "Error loading tags", Toast.LENGTH_SHORT).show();
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
        flexboxLayoutTT.addView(tagButton);

        // Optionally, you can add logic to remove the tag when the button is clicked
        tagButton.setOnClickListener(v -> {
            // Remove the button from the FlexboxLayout
            flexboxLayoutTT.removeView(tagButton);

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
    private void fetchQuizQuestions() {
        Call<List<QuizEntry>> call = App.api.getValidQuizEntries(GROUP_ID);
        Response<List<QuizEntry>> response = null;


        try {
            response = call.execute(); // Synchronous call
        } catch (IOException e) {
            Log.e("API Error", "Error: " + e.getMessage());
            Toast.makeText(takeQuizSplasha.this, "Error loading quiz entries", Toast.LENGTH_SHORT).show();
        }

// Process the response outside of the try-catch block
        if (response != null && response.isSuccessful() && response.body() != null) {
            entries.clear();
            entries.addAll(response.body());


        } else {
            if (response != null) {
                Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                Toast.makeText(takeQuizSplasha.this, "Failed to load quiz entries", Toast.LENGTH_SHORT).show();

                // Handle null body scenario
                if (response.body() == null) {
                    entries.clear(); // Optionally clear the list if needed
                } else {
                    entries.addAll(response.body()); // Only add if response.body() is not null
                }
            }
        }

    }









    private void filterQuizEntriesByTags() {

        for (QuizEntry entry : entries) {
            String quizTags = entry.getTags(); // Assuming getTags() returns a comma-separated string of tag names

            // Check if any tags in selectedTags match the quiz tags
            for (Tag selectedTag : selectedTags) {
                if (quizTags.contains(selectedTag.getTagName())) {
                    filteredEntries.add(entry); // Add matching entry to the filtered list
                    break; // Stop checking once a match is found
                }
            }
        }

        // Save filtered entries to reducedEntriesToIntentOver

        sizeOfFilteredSet = filteredEntries.size();

        // Notify user
        displayFilteredQuizEntries(filteredEntries);
    }


    private void displayFilteredQuizEntries(List<QuizEntry> filteredEntries) {
        // Update your UI here, for example, displaying the filtered quiz entries in a RecyclerView or FlexboxLayout
        Toast.makeText(takeQuizSplasha.this, "We have prepared a Quiz!", Toast.LENGTH_LONG).show();
    }

    public void getOnClick(View view) {
        String numOfQuestionsString = edtNumQ.getText().toString().trim();

        // Validate input is present and a valid number
        Integer numOfQuestionsFromUser;
        if (numOfQuestionsString.isEmpty()) {
            showToast("Please enter the number of questions.");
            return;
        }
        try {
            numOfQuestionsFromUser = Integer.parseInt(numOfQuestionsString);
        } catch (NumberFormatException e) {
            showToast("Please enter a valid number.");
            return;
        }

        // Validate input range
        int maxQuestions = (int) (entries.size() * 0.80);
        if (numOfQuestionsFromUser <= 0) {
            showToast("Please enter a positive number.");
            return;
        } else if (numOfQuestionsFromUser > maxQuestions) {
            showToast("Please select fewer questions (max: " + maxQuestions + ").");
            return;
        }

        // Filter quiz entries by selected tags
        filterQuizEntriesByTags();

        // Populate `reducedEntriesToIntentOver` based on the filtered entries and requested question count
        reducedEntriesToIntentOver.clear();
        for (int i = 0; i < numOfQuestionsFromUser && i < filteredEntries.size(); i++) {
            reducedEntriesToIntentOver.add(filteredEntries.get(i));
        }

        // Control visibility of Start Quiz button based on results
        Button btnStart = findViewById(R.id.btnStartQuiz);
        if (!reducedEntriesToIntentOver.isEmpty()) {
            btnStart.setVisibility(View.VISIBLE);
            edtNumQ.setEnabled(false); // Disable the EditText when "Start Quiz" button is visible
        } else {
            btnStart.setVisibility(View.GONE);
            edtNumQ.setEnabled(true); // Re-enable EditText if no quiz entries are available
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }












    public void goToQuizOnClick(View view) {

        Intent intent = new Intent(takeQuizSplasha.this, QuizNavigationActivity.class);
        Log.d("QuizEntriesDebug", "Entries count: " + reducedEntriesToIntentOver.size());
        intent.putExtra("quizEntries", (Serializable) reducedEntriesToIntentOver);
        startActivity(intent);
        resetValues();
    }

    private void resetValues() {
        // Clear selected tags and remove buttons from FlexboxLayout
        selectedTags.clear();
        flexboxLayoutTT.removeAllViews();

        // Reset EditText for the number of questions
        edtNumQ.setText("");

        // Reset the TextView recommendation
        txtRecNum.setText("");

        // Clear filtered and reduced entries
        filteredEntries.clear();
        reducedEntriesToIntentOver.clear();

        // Hide the Start Quiz button again
        findViewById(R.id.btnStartQuiz).setVisibility(View.GONE);

        // Re-populate spinner in case of any changes
        populateSpinner(tagList);
        edtNumQ.setEnabled(true);


        //Toast.makeText(this, "All fields reset!", Toast.LENGTH_SHORT).show();
    }


}