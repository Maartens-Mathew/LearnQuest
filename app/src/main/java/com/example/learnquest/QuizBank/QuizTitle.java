package com.example.learnquest.QuizBank;

import android.view.View;
import android.widget.TextView;

import com.example.learnquest.ExternalTools.ExpandableRecyclerView.viewholders.GroupViewHolder;
import com.example.learnquest.R;

public class QuizTitle extends GroupViewHolder {
    private final TextView txtQuestion;

    private TextView question;
    public QuizTitle(View itemView) {
        super(itemView);
        this.txtQuestion = itemView.findViewById(R.id.txtQuestion);
    }

    public void bind(String title){
        txtQuestion.setText(title);
    }
}
