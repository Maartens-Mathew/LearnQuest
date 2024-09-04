package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.R;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {
    private List<AssessmentData> assessments;
    private View.OnClickListener listener;
    private Context context;

    public void edit(int pos, AssessmentData data){
        AssessmentData oldData = assessments.get(pos);
        notifyItemChanged(assessments.size()-1);
    }


    public GoalAdapter(List<AssessmentData> assessments){
        this.assessments = assessments;
    }


    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.rw_goals_viewholder, parent, false);
        GoalViewHolder gvh = new GoalViewHolder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        AssessmentData data = assessments.get(position);
        holder.listener = listener;
        holder.setData(data, context.getApplicationContext());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder{

        public TextView lblAssessmentName, lblMarkDesired, lblMarkObtained, lblWeighting;
        public AssessmentData data;
        public View.OnClickListener listener;
        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            lblAssessmentName = itemView.findViewById(R.id.lblAssessmentName1);
            lblMarkDesired = itemView.findViewById(R.id.lblMarkDesired1);
            lblMarkObtained = itemView.findViewById(R.id.lblMarkObtained1);
            lblWeighting = itemView.findViewById(R.id.lblWeighting1);
        }
        public void setData(AssessmentData data, Context context){
            lblAssessmentName.setText(data.getAssessmentName());
            lblMarkDesired.setText(context.getResources().getString(R.string.adjust_goals_mark_desired, String.format("%.0f",data.getIdealMark())));
            lblMarkObtained.setText(context.getResources().getString(R.string.adjust_goals_mark_obtained, String.format("%.0f",data.getMarkObtained())));
            lblWeighting.setText(context.getResources().getString(R.string.adjust_goals_weighting, String.format("%.0f",data.getWeighting())));
            this.data = data;
        }
    }
}
