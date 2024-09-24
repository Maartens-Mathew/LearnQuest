package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnquest.ExternalTools.ExpandableRecyclerView.viewholders.ChildViewHolder;
import com.example.learnquest.R;

public class QuizBody extends ChildViewHolder {
    EditText edtAnswer, edtDescription;
    TextView txtTags;
    Button btnEdit, btnDelete;

    public QuizBody(View itemView) {
        super(itemView);
        edtAnswer = itemView.findViewById(R.id.edtAnswer);
        edtDescription = itemView.findViewById(R.id.edtDescription);
        txtTags = itemView.findViewById(R.id.txtTags);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

    public void bind(QuizEntry quizEntry){
        edtAnswer.setText(quizEntry.getAnswer());
        edtDescription.setText(quizEntry.getDescription());
        txtTags.setText(quizEntry.getTags());
    }
}
