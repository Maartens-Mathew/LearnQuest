package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnquest.LoginRegister.Dashboard.Dashboard;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupView;
import com.example.learnquest.R;
import com.example.learnquest.databinding.FragmentManageGroupsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageGroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageGroupsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageGroupsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageGroupsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageGroupsFragment newInstance(String param1, String param2) {
        ManageGroupsFragment fragment = new ManageGroupsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Context context;
    FragmentManageGroupsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_manage_groups, container, false);
        View view = binding.getRoot();

        binding.btnSearch.setOnClickListener(this::selectGroup);

        // Inflate the layout for this fragment
        return view;

    }

    public void selectGroup(View view) {

        Intent intent = new Intent(getActivity(), GroupView.class);
        startActivity(intent);

    }
}