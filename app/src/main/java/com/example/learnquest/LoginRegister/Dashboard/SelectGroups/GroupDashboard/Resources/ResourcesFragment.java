package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnquest.R;

public class ResourcesFragment extends Fragment {


    public ResourcesFragment() {
        // Required empty public constructor
    }

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        button = view.findViewById(R.id.button14);
        button.setOnClickListener(this::onFileClick);
        // Inflate the layout for this fragment
        return view;
    }


    public void onFileClick(View view) {
        Intent intent = new Intent(getActivity(), ManageResourcesActivity.class);
        startActivity(intent);
    }
}