package com.example.learnquest.QuizBank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.learnquest.R;

import java.util.HashMap;
import java.util.List;

public class QuizExpandableListAdapter extends BaseExpandableListAdapter {
    private final List<String> quizQuestions;
    private final HashMap<String, QuizEntryWithTags> quizData;

    public QuizExpandableListAdapter(List<String> quizQuestions, HashMap<String, QuizEntryWithTags> quizData) {
        this.quizQuestions = quizQuestions;
        this.quizData = quizData;
    }

    @Override
    public int getGroupCount() {
        return quizQuestions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // One child per group containing details and tags.
    }

    @Override
    public Object getGroup(int groupPosition) {
        return quizQuestions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return quizData.get(quizQuestions.get(groupPosition));
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
        String quizQuestion = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_group, parent, false);
        }
        TextView questionTextView = convertView.findViewById(R.id.questionTextView);
        questionTextView.setText(quizQuestion);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        QuizEntryWithTags quizEntry = (QuizEntryWithTags) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_child, parent, false);
        }
        TextView answerTextView = convertView.findViewById(R.id.answerTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView tagsTextView = convertView.findViewById(R.id.tagsTextView);

        answerTextView.setText(quizEntry.getAnswer());
        descriptionTextView.setText(quizEntry.getDescription());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
