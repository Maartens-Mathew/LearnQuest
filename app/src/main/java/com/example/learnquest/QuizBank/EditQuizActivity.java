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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

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
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditQuizActivity extends AppCompatActivity {

    private EditText edtQuestion, edtDescription, edtAnswer;
    private Button btnSave;
    private FlexboxLayout flexboxLayout;
    private Set<Tag> selectedTags = new HashSet<>();
    private List<Tag> tagList; // Replace with the actual source of your tags
    private List<Tag> foundTags; // New list for found tags

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_edit_insert);

        // Initialize views
        edtQuestion = findViewById(R.id.edtQuizQ);
        edtDescription = findViewById(R.id.edtQuizD);
        edtAnswer = findViewById(R.id.edtQuizA);
        btnSave = findViewById(R.id.btnAddQuizEntry);
        flexboxLayout = findViewById(R.id.flexboxForEditingQuizTags);





        // Initialize foundTags list
        foundTags = new ArrayList<>();

        // Get the passed data
        Intent intent = getIntent();
        int quizEntryID = intent.getIntExtra("quizEntryID", -1);
        String question = intent.getStringExtra("question");
        String description = intent.getStringExtra("description");
        String answer = intent.getStringExtra("answer");
        String tagsString = intent.getStringExtra("tags");//main don
        boolean inContention = intent.getBooleanExtra("inContention", false);


        edtQuestion.setText(question);
        edtAnswer.setText(answer);
        edtDescription.setText(description);



        // Convert the comma-separated tags string to a List<String>
        List<String> tagsList = tagsString != null ? Arrays.asList(tagsString.split(",")) : new ArrayList<>();

        App.groupID = 1;
        fetchTagsByGroup(String.valueOf(App.groupID), tagsList);
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
            Tag tag = getTagByName(tagName);
            if (tag != null) {
                foundTags.add(tag); // Add found tag to the list
                addTagButton(tag); // Add button for the found tag
            }
        }
    }
    private void addTagButton(Tag tag) {
        Button tagButton = new Button(this);
        tagButton.setText(tag.getTagName());

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

    private void fetchTagsByGroup(String groupID, List<String> tagsList) {
        Call<List<Tag>> tagsCall = App.api.getTagsByGroup("eq." + groupID);

        tagsCall.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tagList = response.body();
                    initializeSelectedTags(tagsList); // Move tag initialization here
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
}
