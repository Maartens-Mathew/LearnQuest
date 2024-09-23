package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;

import android.app.Activity;
import android.content.Intent;
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
import com.example.learnquest.QuizBank.EditQuizActivity;
import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.R;

import java.io.IOException;
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

    @Override
    public int getItemCount() {
        return quizEntries.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        private static final int REQUEST_CODE_EDIT = 2;
        TextView txtQuestion, txtAnswer, txtTags;
        Button edtEditValid;
        Switch isValid;

        public QuizViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestionV);
            txtAnswer = itemView.findViewById(R.id.answerTextViewV);
            txtTags = itemView.findViewById(R.id.txtTags1V);
            isValid = itemView.findViewById(R.id.ValidSwitch);
            edtEditValid = itemView.findViewById(R.id.btnEditValid);
        }

        public void bind(QuizEntry quizEntry) {
            txtQuestion.setText(quizEntry.getQuestion());
            txtAnswer.setText(quizEntry.getAnswer());
            txtTags.setText("Tags: " + quizEntry.getTags());
            isValid.setChecked(quizEntry.getIsValid());

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
