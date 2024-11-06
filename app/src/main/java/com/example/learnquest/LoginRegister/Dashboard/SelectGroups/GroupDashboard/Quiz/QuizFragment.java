package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz;

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
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags.TagManageHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.quizBankHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation.quizValidHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.takingQuiz.takeQuizSplasha;
import com.example.learnquest.R;

public class QuizFragment extends Fragment {


    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnQuizBankFromFrag = view.findViewById(R.id.btnQuizBankFromFrag);
        btnQuizBankFromFrag.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), quizBankHome.class);
            startActivity(intent);
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        Button btnTakeQuizFromFrag = view.findViewById(R.id.btnTakeQuizFromFrag);
        btnTakeQuizFromFrag.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), takeQuizSplasha.class);
            startActivity(intent);
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        Button btnQuizPoolFromFrag = view.findViewById(R.id.btnQuizPoolFromFrag);
        if (!App.isModerator) {
            btnQuizPoolFromFrag.setOnClickListener(v -> {
                Toast.makeText(getContext(), "You do not have the sufficient permission for this", Toast.LENGTH_LONG).show();
            });
        } else {
            btnQuizPoolFromFrag.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ListViewActivity.class);
                startActivity(intent);
            });
        }




        //////////////////////////////////////////////////////////////////////////////////////////////////////
        Button btnManageTags = view.findViewById(R.id.btnManageTags);
        if (!App.isModerator) {
            btnManageTags.setOnClickListener(v -> {
                Toast.makeText(getContext(), "You do not have the sufficient permission for this", Toast.LENGTH_LONG).show();
            });
        } else {
            btnManageTags.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ListViewActivity.class);
                startActivity(intent);
            });
        }

    }
}