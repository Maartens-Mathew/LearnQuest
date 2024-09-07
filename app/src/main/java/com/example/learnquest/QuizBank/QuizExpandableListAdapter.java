package com.example.learnquest.QuizBank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnquest.R;

import java.util.List;

public class QuizExpandableListAdapter extends BaseExpandableListAdapter {
    private List<QuizEntry> quizEntries;

    public QuizExpandableListAdapter(List<QuizEntry> quizEntries) {
        this.quizEntries = quizEntries;
    }

    @Override
    public int getGroupCount() {
        return quizEntries.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // One child per group containing details and tags.
    }

    @Override
    public Object getGroup(int groupPosition) {
        return quizEntries.get(groupPosition);
    }

    @Override

    public Object getChild(int groupPosition, int childPosition) {
        return quizEntries.get(groupPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        //Get layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.viewholder_questiontitle, parent, false);
        }

        //Get textView and assign
        TextView questionTextView = convertView.findViewById(R.id.txtQuestion);
        questionTextView.setText(quizEntries.get(groupPosition).getQuestion());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        QuizEntry quizEntry = (QuizEntry) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.viewholder_questionbody, parent, false);
        }
        EditText edtAnswer = convertView.findViewById(R.id.edtAnswer);
        EditText edtDescription = convertView.findViewById(R.id.edtDescription);
        TextView txtTags = convertView.findViewById(R.id.txtTags);

        edtAnswer.setText(quizEntry.getAnswer());
        edtDescription.setText(quizEntry.getDescription());
        txtTags.setText(quizEntry.getTags());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
