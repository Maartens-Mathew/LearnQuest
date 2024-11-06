package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags.TagManageHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.quizBankHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation.quizValidHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.takingQuiz.takeQuizSplasha;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools.Pomodoro.pomoDoroMain;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.studyResource.StudyResource;

import java.util.List;

import retrofit2.Call;

public class StudyToolsFragment extends Fragment {

    public StudyToolsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_tools, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnPomodoro = view.findViewById(R.id.btnPomodoro);
        btnPomodoro.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), pomoDoroMain.class);
            startActivity(intent);
        });


    }




}