package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupView;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.CreateGroupActivity;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.DeleteGroupActivity;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.JoinGroupActivity;
import com.example.learnquest.R;

import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.user.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageGroupsFragment extends Fragment {

    public static final String MANAGE_GROUPS_FRAGMENT = "ManageGroupsFragment";

    public ManageGroupsFragment() {
        // Required empty public constructor
    }

    Context context;
    RecyclerView groupView;
    Button btnSearch, btnCreate;
    EditText edtSearchTerm;

    List<Group> groups;

    ActivityResultLauncher confirmationLauncher;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_groups, container, false);

        groupView = view.findViewById(R.id.recyclerView_groups);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnCreate = view.findViewById(R.id.btnManageGroupCreateGroup);
        edtSearchTerm = view.findViewById(R.id.edtSearchTerm);
        App.setApplicationContext(getActivity());
        App.user = User.demoUser();

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
        btnCreate.setOnClickListener(this::onCreateGroupBtnCreateGroupClicked);
        btnSearch.setOnClickListener(this::selectGroup);

        // Inflate the layout for this fragment
        return view;

    }

    private void onCreateGroupBtnCreateGroupClicked(View v){
        Intent intent = new Intent(getContext(), CreateGroupActivity.class);
        startActivity(intent);
    }

    public View.OnClickListener onGroupClick(Group group){

        return (view) -> {
            Intent intent = new Intent(getActivity(), GroupView.class);
            App.group = group;
            startActivity(intent);
        };
    }

    public void selectGroup(View view) {
        String searchTerm = edtSearchTerm.getText().toString();
        if (searchTerm.length() == 0){
            Toast.makeText(context, "Enter an actual search term", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<List<Group>> getGroupsCall = App.api.getGroupByTopic("topic~~*"+searchTerm+"%");
        getGroupsCall.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()){
                    if (response.body() == null){
                        Toast.makeText(context, "could not find group", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        List<Group> groups = response.body();
                        Intent intent = new Intent(getActivity(), JoinGroupActivity.class);
                        intent.putExtra("groups", (Serializable) groups);
                        startActivity(intent);
                    }
                }
                else{
                    Log.e(MANAGE_GROUPS_FRAGMENT,"something went wrong");
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable throwable) {
                Log.e(MANAGE_GROUPS_FRAGMENT,throwable.getMessage());
                for (StackTraceElement e : throwable.getStackTrace()){
                    Log.e(MANAGE_GROUPS_FRAGMENT, e.toString());
                }
            }
        });
    }

    public void getGroups(){

        //TODO: Revert change here
        Call<List<Group>> groupCall = App.api.getUserGroups(App.user.getUserID());
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