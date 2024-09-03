package com.example.learnquest.QuizBank;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnquest.R;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    private final List<QuizEntryWithTags> quizEntries;

    public QuizAdapter(List<QuizEntryWithTags> quizEntries) {
        this.quizEntries = quizEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuizEntryWithTags entry = quizEntries.get(position);


        holder.questionTextView.setText(entry.getQuestion());
        holder.answerTextView.setText(entry.getAnswer());
        holder.descriptionTextView.setText(entry.getDescription());

        List<String> tags = entry.getTags();
        if (tags.isEmpty()) {
            holder.tagsTextView.setText("No tags");
        } else {

            holder.tagsTextView.setText(String.join(" |||||| ", tags));
        }
    }


    @Override
    public int getItemCount() {
        return quizEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;
        TextView descriptionTextView;
        TextView tagsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            tagsTextView = itemView.findViewById(R.id.tagsTextView);
        }
    }
}
