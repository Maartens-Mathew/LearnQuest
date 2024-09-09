package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.studyResource.StudyResource;

import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyToolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyToolsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudyToolsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyToolsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyToolsFragment newInstance(String param1, String param2) {
        StudyToolsFragment fragment = new StudyToolsFragment();
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
        Call<List<StudyResource>> studyCall = api.getStudyResources(1);
    }
}