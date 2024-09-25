package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnquest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdjustGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdjustGoalsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POS = "position";

    // TODO: Rename and change types of parameters
    private int pos;

    public AdjustGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdjustGoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdjustGoalsFragment newInstance(int dataPos) {
        AdjustGoalsFragment fragment = new AdjustGoalsFragment();
        Bundle args = new Bundle();
        args.putInt(POS, dataPos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(POS);
        }
    }

    private TextView lblAssessmentName, lblMarkDesired, lblWeight;
    private EditText edtMarkDesired;
    private AppCompatButton btnEnter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lblAssessmentName = view.findViewById(R.id.adjust_goals_fragment_assessmentname);
        lblMarkDesired = view.findViewById(R.id.adjust_goals_fragment_markdesired);
        lblWeight = view.findViewById(R.id.adjust_goals_fragment_weighting);
        edtMarkDesired = view.findViewById(R.id.edtMarkDesired);
        btnEnter = view.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this::btnEnterClicked);
    }
    public void btnEnterClicked(View v){
        double markDesired = Double.parseDouble(edtMarkDesired.getText().toString());
        TrackProgressAssessmentData data = GoalLogic.data.get(pos);
        data.setIdeal_mark(markDesired);
        GoalLogic.goalList.getAdapter().notifyItemChanged(pos);
        listener.onClick(v);
    }
    private View.OnClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof View.OnClickListener){
            listener = (View.OnClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adjust_goals, container, false);
    }
}