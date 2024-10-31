package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learnquest.R;

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
}