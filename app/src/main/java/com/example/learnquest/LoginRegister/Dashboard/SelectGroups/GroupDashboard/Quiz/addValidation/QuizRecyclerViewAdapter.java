package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.QuizEntry;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.Tag;
import com.example.learnquest.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizViewHolder> {

    private List<QuizEntry> quizEntries;

    public QuizRecyclerViewAdapter(List<QuizEntry> quizEntries) {
        this.quizEntries = quizEntries;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_for_valid, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        QuizEntry quizEntry = quizEntries.get(position);
        holder.bind(quizEntry);
    }


    private void displayTagsAsPills(List<Tag> tagList, FlexboxLayout flb, Context context) {
        FlexboxLayout fbl = flb;
        fbl.removeAllViews(); // Clear any existing views to avoid duplication

        for (Tag tag : tagList) {
            Button tagButton = new Button(context);

            // Set the text of the button to the tag name
            tagButton.setText(tag.getTagName());

            // Set the background shape drawable with the tag color
            tagButton.setBackground(getPillDrawableWithColor(tag.getTagColour()));

            // Set padding and text size for smaller appearance
            tagButton.setPadding(12, 4, 12, 4); // Reduced padding
            tagButton.setTextSize(10); // Smaller text size

            tagButton.setTextColor(Color.WHITE);
            tagButton.setAllCaps(false); // Optional: to avoid all caps text

            // Set layout params for the button
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 4, 4, 4); // Reduced margin for more compact layout
            tagButton.setLayoutParams(params);

            // Add the button to the FlexboxLayout
            fbl.addView(tagButton);
        }
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



    @Override
    public int getItemCount() {
        return quizEntries.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        private static final int REQUEST_CODE_EDIT = 2;
        TextView txtQuestion, txtAnswer, txtDesc;
        Button edtEditValid;
        Switch isValid;
        FlexboxLayout fbforValid;
        public QuizViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestionV);
            txtAnswer = itemView.findViewById(R.id.answerTextViewV);
            isValid = itemView.findViewById(R.id.ValidSwitch);
            edtEditValid = itemView.findViewById(R.id.btnEditValid);
            fbforValid = itemView.findViewById(R.id.flexboxForValidatingQuizs);
            txtDesc = itemView.findViewById(R.id.descriptionTextViewV);
        }

        public void bind(QuizEntry quizEntry) {
            txtQuestion.setText(quizEntry.getQuestion());
            txtAnswer.setText(quizEntry.getAnswer());
            isValid.setChecked(quizEntry.getIsValid());
            txtDesc.setText(quizEntry.getDescription());


            displayTagsAsPills(quizEntry.getTagsAsObjectList(), fbforValid,itemView.getContext() );


            // Set listener for the Switch to handle validation logic
            isValid.setOnCheckedChangeListener((buttonView, isChecked) -> {
                quizEntry.setValidated(isChecked);
                updateQuizEntry(quizEntry);
            });

            // Edit button listener
            edtEditValid.setOnClickListener(v -> {
                Intent editIntent = new Intent(itemView.getContext(), editQuizFromValidAct.class);
                editIntent.putExtra("quizEntryID", quizEntry.getQuizEntryID());
                editIntent.putExtra("question", quizEntry.getQuestion());
                editIntent.putExtra("answer", quizEntry.getAnswer());
                editIntent.putExtra("description", quizEntry.getDescription());
                editIntent.putExtra("tags", quizEntry.getTags());
                editIntent.putExtra("inContention", quizEntry.getInContention());
            ((Activity) itemView.getContext()).startActivityForResult(editIntent, REQUEST_CODE_EDIT);
            });
        }

        private void updateQuizEntry(QuizEntry quizEntry) {
            Call<Void> quizCall = App.api.updateQuizEntry(quizEntry);
            quizCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(itemView.getContext(), "Update successful.", Toast.LENGTH_SHORT).show();
                        ///does the refresh code go here?
                    } else {
                        Toast.makeText(itemView.getContext(), "Update failed. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("QuizRecyclerViewAdapter", "API error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("QuizRecyclerViewAdapter", "API request failed", t);
                    Toast.makeText(itemView.getContext(), "Update failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
