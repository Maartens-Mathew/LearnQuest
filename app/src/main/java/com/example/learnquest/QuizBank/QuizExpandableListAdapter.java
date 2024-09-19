package com.example.learnquest.QuizBank;

import android.content.Intent;
import android.database.DataSetObservable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class QuizExpandableListAdapter extends BaseExpandableListAdapter {
    private List<QuizEntry> quizEntries;

    public QuizExpandableListAdapter(List<QuizEntry> quizEntries) {
        this.quizEntries = quizEntries;
    }

    // Add the updateData method to refresh the list
    public void updateData(List<QuizEntry> newQuizEntries) {
        this.quizEntries.clear(); // Clear the old data
        this.quizEntries.addAll(newQuizEntries); // Add the new data
        notifyDataSetChanged(); // Notify the adapter that the data has changed
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.viewholder_questiontitle, parent, false);
        }

        ImageView indicator = convertView.findViewById(R.id.imgIndicator);
        if (isExpanded) {
            indicator.setImageResource(R.drawable.baseline_arrow_drop_up_24);
        } else {
            indicator.setImageResource(R.drawable.baseline_arrow_drop_down_24);
        }

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

        bindChild(convertView, quizEntry);
        return convertView;
    }

    public void bindChild(View convertView, QuizEntry quizEntry) {
        TextView edtAnswer = convertView.findViewById(R.id.edtAnswer);
        TextView edtDescription = convertView.findViewById(R.id.edtDescription);
        TextView txtTags = convertView.findViewById(R.id.txtTags);
        Button btnDelete;
        Button btnEdit;



        edtAnswer.setText("Answer: " + quizEntry.getAnswer());
        edtDescription.setText("Desc: " + quizEntry.getDescription());
        txtTags.setText(quizEntry.getTags());

        btnDelete = convertView.findViewById(R.id.btnDelete); // Add a delete button
        btnEdit = convertView.findViewById(R.id.btnEdit); // Add a delete button

        btnDelete.setOnClickListener(deleteEntry(quizEntry));
        btnEdit.setOnClickListener(editEntry(quizEntry));
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public View.OnClickListener deleteEntry(QuizEntry quizEntry) {
        return (itemView) -> {
            Thread thread = new Thread(() -> {
                Call<Void> deleteCall = App.api.deleteQuizEntry("eq." + quizEntry.getQuizEntryID());
                Response<Void> deleteResponse = null;

                try {
                    deleteResponse = deleteCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (deleteResponse.isSuccessful()) {
                    Log.i("Custom", "Entry deleted successfully");
                } else {
                    Log.e("Custom", "Failed to delete entry: HTTP " + deleteResponse.code() + " " + deleteResponse.message());
                }
            });

            thread.start();
            // Remove quiz entry from list
            quizEntries.remove(quizEntry);

            // Notify adapter
            notifyDataSetChanged();
        };
    }
    public View.OnClickListener editEntry(QuizEntry quizEntry) {
        return (view) -> {
            // Create an intent to launch the EditQuizActivity
            Intent intent = new Intent(view.getContext(), EditQuizActivity.class);

            // Pass the quiz entry's data to the new activity
            intent.putExtra("quizEntryID", quizEntry.getQuizEntryID());
            intent.putExtra("question", quizEntry.getQuestion());
            intent.putExtra("description", quizEntry.getDescription());
            intent.putExtra("answer", quizEntry.getAnswer());
            intent.putExtra("tags", quizEntry.getTags()); // Pass tags if necessary
            intent.putExtra("inContention", quizEntry.getInContention());


            // Start the activity
            view.getContext().startActivity(intent);
        };
    }














}
