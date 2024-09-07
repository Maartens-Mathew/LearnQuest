package com.example.learnquest.QuizBank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnquest.R;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<QuizEntry> quizEntries;

    public QuizAdapter(List<QuizEntry> quizEntries) {
        this.quizEntries = quizEntries;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizEntry entry = quizEntries.get(position);
        holder.questionTextView.setText(entry.getQuestion());
        holder.answerTextView.setText(entry.getAnswer());
        holder.descriptionTextView.setText(entry.getDescription());

        // Display associated tags as a comma-separated string
        String tagsText = entry.getTags();
        holder.tagsTextView.setText(tagsText);
    }

    @Override
    public int getItemCount() {
        return quizEntries.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, answerTextView, descriptionTextView, tagsTextView;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.txtQuestion);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            tagsTextView = itemView.findViewById(R.id.tagsTextView);
        }
    }
}

