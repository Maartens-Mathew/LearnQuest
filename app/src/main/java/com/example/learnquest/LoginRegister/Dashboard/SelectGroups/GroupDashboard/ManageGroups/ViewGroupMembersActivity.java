package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewGroupMembersActivity extends AppCompatActivity {

    public static final String VIEW_GROUP_MEMBERS_ACTIVITY = "ViewGroupMembersActivity";
    private List<GroupMemberUserName> users;
    private boolean isModerator;
    private RecyclerView recyclerView;
    private GroupMemberAdapter.GroupMemberViewHolder selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null){
            isModerator = getIntent().getExtras().getBoolean("isModerator");
        }
        setContentView(R.layout.activity_view_group_members);
        recyclerView = findViewById(R.id.groupMembersRecyclerView);
        //users = new ArrayList<>();
        doCode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doCode();
    }

    //TODO: Need a way to determine whether a user is a moderator of a group when the user selects the group and store that in the App class;

    public void doCode(){
        databaseCall();
        GroupMemberAdapter adapter = new GroupMemberAdapter(users, isModerator);
        if (isModerator){
            adapter.listener = v ->{
                if (selected != null){
                    selected.btnRemove.setVisibility(View.GONE);
                }
                selected = (GroupMemberAdapter.GroupMemberViewHolder) recyclerView.findContainingViewHolder(v);
                if (!selected.user.getUserID().equals(App.user.getUserID()))
                    selected.btnRemove.setVisibility(View.VISIBLE);
            };
        }
        else{
            adapter.listener = null;
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doCode();
    }

    public void onViewGroupMembersBtnBackButtonPressed(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    private void databaseCall(){
        Call<List<GroupMemberUserName>> membersCall = App.api.getGroupMembers(App.group.getGroupID());
        membersCall.enqueue(new Callback<List<GroupMemberUserName>>() {
            @Override
            public void onResponse(Call<List<GroupMemberUserName>> call, Response<List<GroupMemberUserName>> response) {
                if (response.isSuccessful()){
                    users = response.body();
                }
                else{
                    try {
                        Log.e(VIEW_GROUP_MEMBERS_ACTIVITY,response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(VIEW_GROUP_MEMBERS_ACTIVITY, e.getStackTrace().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GroupMemberUserName>> call, Throwable throwable) {
                Log.e(VIEW_GROUP_MEMBERS_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
    }
}