package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learnquest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsListFragment extends Fragment {

    private RecyclerView rwGoalsList;
    private GoalAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ASSESSMENT_DATA = "assessmentData";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<AssessmentData> asssessmentData = new ArrayList<>();
    private String mParam2;

    public GoalsListFragment() {
        // Required empty public constructor
    }

    public static GoalsListFragment newInstance(String param1, String param2) {
        GoalsListFragment fragment = new GoalsListFragment();
        Bundle args = new Bundle();
        args.putString(ASSESSMENT_DATA, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rwGoalsList = view.findViewById(R.id.rwGoalsList);
        adapter = new GoalAdapter(asssessmentData);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            asssessmentData = (List<AssessmentData>) getArguments().getSerializable(ASSESSMENT_DATA);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goals_list, container, false);
    }
}