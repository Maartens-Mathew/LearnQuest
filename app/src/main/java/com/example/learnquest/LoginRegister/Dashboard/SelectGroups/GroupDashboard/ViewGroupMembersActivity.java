package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null){
            isModerator = getIntent().getExtras().getBoolean("isModerator");
        }
        setContentView(R.layout.activity_view_group_members);
        RecyclerView recyclerView = findViewById(R.id.groupMembersRecyclerView);
        users = new ArrayList<>();
        databaseCall();
        GroupMemberAdapter adapter = new GroupMemberAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
    }

    public void databaseCall(){
        Call<List<GroupMemberUserName>> membersCall = App.api.getGroupMembers(App.groupID);
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