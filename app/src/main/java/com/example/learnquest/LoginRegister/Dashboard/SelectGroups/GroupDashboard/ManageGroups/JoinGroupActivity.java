package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//NB!!! Need to ensure the groupID and userID is correct in the App state when method starts.

public class JoinGroupActivity extends AppCompatActivity {

    public static final String JOIN_GROUP_ACTIVITY = "JoinGroupActivity";
    private GroupMembership newGroupMember;
    private Group group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        Intent intent = getIntent();
        if (intent != null){
            group = (Group) intent.getExtras().get("group");
            newGroupMember = new GroupMembership(App.user.getUserID(),group.getGroupID(), 4);
            CardView groupColor = findViewById(R.id.JoinGroupActivityGroupColor);
            TextView groupName = findViewById(R.id.JoinGroupActivitylabelGroupName);
            ImageView groupImage = findViewById(R.id.joinGroupImage);
            groupName.setText(group.getTopic());
            groupColor.setCardBackgroundColor(Color.parseColor(group.getGroupColour()));
            groupImage.setImageBitmap(group.getImage());
        }
    }

    public void btnJoinClicked(View v){
        Call<Void> joinMemberCall = App.api.addGroupMembership(newGroupMember);
        joinMemberCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(JOIN_GROUP_ACTIVITY,"Joining of group was successful: "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(JOIN_GROUP_ACTIVITY, throwable.getMessage());
                Log.e(JOIN_GROUP_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
    }

    public void btnCancelClicked(View v){
          getOnBackPressedDispatcher().onBackPressed();
    }

    public void getGroupDataFromDatabase(){
        Call<Group> groupCall = App.api.getGroup(App.group.getGroupID());
        groupCall.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()){
                    group = response.body();
                }
                else{
                    try {
                        Log.e(JOIN_GROUP_ACTIVITY,response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(JOIN_GROUP_ACTIVITY,e.getStackTrace().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable throwable) {
                Log.e(JOIN_GROUP_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
    }
}