package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageAssessments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals.GoalsListActivity;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals.ViewProgressActivity;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings.ListViewActivity;
import com.example.learnquest.R;

public class AssessmentFragment extends Fragment {

    public AssessmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assessment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnViewProgress = view.findViewById(R.id.view_progress_button_assessment_fragment);
        btnViewProgress.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), ViewProgressActivity.class);
            startActivity(intent);
        });
        Button btnManageGoals = view.findViewById(R.id.btnManageGoals);
        btnManageGoals.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GoalsListActivity.class);
            intent.putExtra("stateType",0);
            startActivity(intent);
        });

        Button btnSetW = view.findViewById(R.id.btnSetW);
        if (!App.isModerator) {
            btnSetW.setOnClickListener(v -> {
                Toast.makeText(getContext(), "You do not have the sufficient permission for this", Toast.LENGTH_LONG).show();
            });
        } else {
            btnSetW.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ListViewActivity.class);
                startActivity(intent);
            });
        }


    }
}