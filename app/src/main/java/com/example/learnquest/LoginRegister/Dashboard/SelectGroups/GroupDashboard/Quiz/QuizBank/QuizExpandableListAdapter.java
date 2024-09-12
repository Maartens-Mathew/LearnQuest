package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.viewholder_questiontitle, parent, false);
        }


        ImageView indicator = convertView.findViewById(R.id.imgIndicator);

            if (isExpanded) {
                indicator.setImageResource(R.drawable.baseline_arrow_drop_up_24);
            }else
                indicator.setImageResource(R.drawable.baseline_arrow_drop_down_24);


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

    public void bindChild(View convertView, QuizEntry quizEntry){
        EditText edtAnswer = convertView.findViewById(R.id.edtAnswer);
        EditText edtDescription = convertView.findViewById(R.id.edtDescription);
        TextView txtTags = convertView.findViewById(R.id.txtTags);
        Button btnDelete, btnEdit;

        edtAnswer.setText(quizEntry.getAnswer());
        edtDescription.setText(quizEntry.getDescription());
        txtTags.setText(quizEntry.getTags());

        btnDelete = convertView.findViewById(R.id.btnDelete); // Add a delete button
        btnDelete.setOnClickListener(deleteEntry(quizEntry));

        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(editEntry(convertView, quizEntry));

        edtAnswer.setEnabled(false);
        edtDescription.setEnabled(false);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public View.OnClickListener deleteEntry(QuizEntry quizEntry) {

        return (itemView) -> {

            Thread thread = new Thread( () -> {
                Call<Void> deleteCall = App.api.deleteQuizEntry("eq." + quizEntry.getQuizEntryID());
                Response<Void> deleteResponse = null;

                try {
                    deleteResponse = deleteCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (deleteResponse.isSuccessful())
                    Log.i("Custom", "Entry deleted successfully");
                else
                    Log.e("Custom", "Failed to delete entry: HTTP " + deleteResponse.code() + " " + deleteResponse.message());


            });

            thread.start();
            //Remove quiz Entry from list
            quizEntries.remove(quizEntry);

            //Notify adapter
            notifyDataSetChanged();
        };



    }


    //Serves as way to edit quiz entries on the fly (Narsi, will you use a dialogue popup instead?)
    public View.OnClickListener editEntry(View view, QuizEntry quizEntry){
        return (itemView) -> {
            Button btnEdit = (Button)itemView;
            EditText edtAnswer = view.findViewById(R.id.edtAnswer);
            EditText edtDescription = view.findViewById(R.id.edtDescription);

            String tag = (String)btnEdit.getTag();
            boolean editState = tag.equals("false");





            if (editState)
            {
                btnEdit.setText("Save");
                edtAnswer.setEnabled(true);
                edtDescription.setEnabled(true);
                btnEdit.setTag("true");
                //How change tags?
            }else {
                btnEdit.setText("Edit");
                edtAnswer.setEnabled(false);
                edtDescription.setEnabled(false);
                btnEdit.setTag("false");

                quizEntry.setAnswer(edtAnswer.getText().toString());
                quizEntry.setDescription(edtDescription.getText().toString());


                //Update database??

            }




        };




    }
}
