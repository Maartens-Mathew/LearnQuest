package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupView;
import com.example.learnquest.R;

import com.example.learnquest.model.group.Group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ManageGroupsFragment extends Fragment {

    public ManageGroupsFragment() {
        // Required empty public constructor
    }

    Context context;
    RecyclerView groupView;
    Button btnSearch;

    List<Group> groups;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_groups, container, false);

        groupView = view.findViewById(R.id.recyclerView_groups);
        btnSearch = view.findViewById(R.id.btnSearch);
        App.setApplicationContext(getActivity());

        GridLayoutManager manager = new GridLayoutManager(getActivity(),3);
        groupView.setLayoutManager(manager);
        groupView.addItemDecoration(new EqualSpacingItemDecoration(10));

        Thread thread = new Thread(this::getGroups);

        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        GroupAdapter adapter = new GroupAdapter(groups, this::onGroupClick);
        groupView.setAdapter(adapter);

        btnSearch.setOnClickListener(this::selectGroup);

        // Inflate the layout for this fragment
        return view;

    }

    public View.OnClickListener onGroupClick(Group group){

        return (view) -> {
            Intent intent = new Intent(getActivity(), GroupView.class);
            App.group = group;
            startActivity(intent);
        };
    }

    public void selectGroup(View view) {


    }

    public void getGroups(){

        //TODO: Revert change here
        //Call<List<Group>> groupCall = App.api.getUserGroups(App.user.getUserID());
        Call<List<Group>> groupCall = App.api.getUserGroups(0);
        Response<List<Group>> groupResponse = null;

        try{
            groupResponse = groupCall.execute();
        }catch(IOException e){
            e.printStackTrace();
            groups = new ArrayList<>();
        }

        if (groupResponse.isSuccessful())
        {
            groups= Collections.synchronizedList(groupResponse.body());
            return;
        }else
        {

            try {
                Log.e("Custom",groupResponse.errorBody().string());
            } catch (IOException e) {
                Log.e("Custom","Something wrong happened.");
            }
        }

        groups = new ArrayList<>();

    }

}