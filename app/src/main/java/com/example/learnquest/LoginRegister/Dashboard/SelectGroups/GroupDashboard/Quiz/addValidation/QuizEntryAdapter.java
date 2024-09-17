package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.R;

import java.util.List;

public class QuizEntryAdapter extends RecyclerView.Adapter<QuizEntryAdapter.QuizEntryViewHolder> {

    private List<QuizEntry> quizEntryList;
    private Context context;

    public QuizEntryAdapter(Context context, List<QuizEntry> quizEntryList) {
        this.context = context;
        this.quizEntryList = quizEntryList;
    }

    @NonNull
    @Override
    public QuizEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizEntryViewHolder holder, int position) {
        QuizEntry quizEntry = quizEntryList.get(position);
        holder.questionTextView.setText(quizEntry.getQuestion());
        holder.answerTextView.setText(quizEntry.getAnswer());
        holder.descriptionTextView.setText(quizEntry.getDescription());
        holder.tagsTextView.setText(quizEntry.getTags());  // Display the tags as a string
    }

    @Override
    public int getItemCount() {
        return quizEntryList.size();
    }

    public static class QuizEntryViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;
        TextView descriptionTextView;
        TextView tagsTextView;

        public QuizEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.txtQuestion);
            answerTextView = itemView.findViewById(R.id.answerTextView);
            descriptionTextView = itemView.findViewById(R.id.answerTextView);
            tagsTextView = itemView.findViewById(R.id.txtTags1);
        }
    }
}
