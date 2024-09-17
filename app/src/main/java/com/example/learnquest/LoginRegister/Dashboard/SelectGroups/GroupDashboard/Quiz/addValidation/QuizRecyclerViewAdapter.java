package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.R;

import java.util.List;


public class QuizRecyclerViewAdapter extends RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizViewHolder> {

    private List<QuizEntry> quizEntries;

    public QuizRecyclerViewAdapter(List<QuizEntry> quizEntries) {
        this.quizEntries = quizEntries;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
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

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswer, txtTags;

        public QuizViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtAnswer = itemView.findViewById(R.id.answerTextView);
            txtTags = itemView.findViewById(R.id.txtTags1);

        }

        public void bind(QuizEntry quizEntry) {
            txtQuestion.setText(quizEntry.getQuestion());
            txtAnswer.setText(quizEntry.getAnswer());
            txtTags.setText(quizEntry.getTags());

        }
    }
}
