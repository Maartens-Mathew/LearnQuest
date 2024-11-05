package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;
import com.example.learnquest.model.assessment.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_viewholder,parent,false);
        return new AssessmentViewHolder(v);
    }
    List<Assessment> assessments;
    View.OnClickListener listener;

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public AssessmentAdapter(List<Assessment> assessments, View.OnClickListener listener) {
        this.assessments = assessments;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        holder.setData(assessments.get(position));
        holder.setListener(listener);
    }

    public AssessmentAdapter(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public void remove(int p){
        assessments.remove(p);
        notifyItemRemoved(p);
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        public Assessment data;
        private TextView lblAssessmentName, lblWeighting, lblDueDate;
        private CardView cardView;
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            lblAssessmentName = itemView.findViewById(R.id.lblAssessmentName_AddActivity);
            lblWeighting = itemView.findViewById(R.id.lblWeighting_AddActivity);
            lblDueDate = itemView.findViewById(R.id.lblDueDate_AddActivity);
            cardView = itemView.findViewById(R.id.cdvAssessment);
        }

        public void setData(Assessment data){
            this.data = data;
            lblAssessmentName.setText(itemView.getContext().getResources().getString(R.string.assessment_name,
                    this.data.getName()));
            lblWeighting.setText(itemView.getContext().getResources().getString(R.string.adjust_goals_weighting,
                    String.format("%.0f",this.data.getWeighting())));
            lblDueDate.setText(itemView.getContext().getResources().getString(R.string.due_date,
                    this.data.getDueDate()));
        };

        public void setListener(View.OnClickListener listener) {
            cardView.setOnClickListener(listener);
        }
    }

}
