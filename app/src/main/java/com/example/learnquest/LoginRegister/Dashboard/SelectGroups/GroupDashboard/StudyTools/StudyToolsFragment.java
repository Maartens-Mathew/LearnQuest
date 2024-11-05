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
    public void getStudyResources(){
        /* Granville you NEED to regularly merge your branches into the main branch.
            App is a class with static fields, like groupID and userID to track
             the current user and the current group. If you don't have that class, then
             a lot of functionality won't work. So it's important that you branch asap so
             that you get the latest changes.

         */


        //Short groupID = App.groupID;


        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
        //Call<List<StudyResource>> studyCall = api.getStudyResources(1);
    }
}