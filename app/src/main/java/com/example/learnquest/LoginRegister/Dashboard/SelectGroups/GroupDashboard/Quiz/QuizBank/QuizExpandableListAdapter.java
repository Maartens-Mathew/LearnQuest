package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags.TagManageHome;
import com.example.learnquest.R;
import com.google.android.flexbox.FlexboxLayout;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class QuizExpandableListAdapter extends BaseExpandableListAdapter {
    private List<QuizEntry> quizEntries;
    private static final int REQUEST_CODE_EDIT = 2;

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

        TextView questionTextView = convertView.findViewById(R.id.txtQuestionV);
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

        TextView txtInCon = convertView.findViewById(R.id.txtinContention);
        FlexboxLayout fbl = convertView.findViewById(R.id.flexboxForViewingQuizs);
        Button btnDelete;
        Button btnEdit;
        Boolean inContention = quizEntry.getInContention();
String status ="" ;
        if (inContention.toString() == "false") {
            status = "no";
        }
        else status = "yes";

        txtInCon.setText("In pool queue? " + status);

        edtAnswer.setText("Answer: " + quizEntry.getAnswer());
        edtDescription.setText("Desc: " + quizEntry.getDescription());


        btnDelete = convertView.findViewById(R.id.btnDelete); // Add a delete button
        btnEdit = convertView.findViewById(R.id.btnEdit); // Add a delete button

        btnDelete.setOnClickListener(deleteEntry(quizEntry));
        btnEdit.setOnClickListener(editEntry(quizEntry));


        displayTagsAsPills(quizEntry.getTagsAsObjectList(), fbl,convertView.getContext() );







    }

    private void displayTagsAsPills(List<Tag> tagList, FlexboxLayout flb, Context context) {
        FlexboxLayout fbl = flb;
        fbl.removeAllViews(); // Clear any existing views to avoid duplication
        for (Tag tag : tagList) {
            Button tagButton = new Button(context);


            // Set the text of the button to the tag name
            tagButton.setText(tag.getTagName());

            // Set the background shape drawable with the tag color
            tagButton.setBackground(getPillDrawableWithColor(tag.getTagColour()));

            // Set padding and text size
            tagButton.setPadding(20, 8, 20, 8); // Adjust padding values if needed
            tagButton.setTextSize(12);
            tagButton.setWidth(20);  // Width in pixels, adjust as needed
            tagButton.setHeight(20); // Height in pixels, adjust as needed

            tagButton.setTextColor(Color.WHITE);
            tagButton.setAllCaps(false); // Optional: to avoid all caps text

            // Set layout params for the button
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            tagButton.setLayoutParams(params);

            // Add the button to the FlexboxLayout
            fbl.addView(tagButton);
        }
    }


    private Drawable getPillDrawableWithColor(String colorHex) {
        int color = Color.parseColor(colorHex); // Convert hex string to color
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(
                new float[]{50, 50, 50, 50, 50, 50, 50, 50}, // corner radii
                null, // inner radius
                null  // border radius
        ));
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        return drawable;
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public View.OnClickListener editEntry(QuizEntry quizEntry) {
        return (view) -> {
            // Create an intent to launch the EditQuizActivity

            // Start the activity

            Intent intent2 = new Intent(view.getContext(), EditQuizActivity.class);
            intent2.putExtra("quizEntryID", quizEntry.getQuizEntryID());
            intent2.putExtra("question", quizEntry.getQuestion());
            intent2.putExtra("description", quizEntry.getDescription());
            intent2.putExtra("answer", quizEntry.getAnswer());
            intent2.putExtra("tags", quizEntry.getTags()); // Method to convert tags to a string
            intent2.putExtra("inContention", quizEntry.getInContention());
            ((Activity) view.getContext()).startActivityForResult(intent2, REQUEST_CODE_EDIT);











        };
    }



}
