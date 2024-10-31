package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveGroupMemberActivity extends AppCompatActivity {

    public static final String REMOVE_GROUP_MEMBER_ACTIVITY = "RemoveGroupMemberActivity";
    private GroupMemberUserName user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_group_member);
        if (getIntent() != null){
            user = (GroupMemberUserName) getIntent().getExtras().get("user");
            TextView userName = findViewById(R.id.removeUserUsername);
            userName.setText(user.getUsername());
        }
        else{
            getOnBackPressedDispatcher().onBackPressed();
        }
    }

    public void btnOnConfirmClicked(View v){
        Call<Void> deleteMemberCall = App.api.
                deleteGroupMembership("eq"+user.getUserid(),"eq"+App.groupID);
        deleteMemberCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(REMOVE_GROUP_MEMBER_ACTIVITY, "successfully removed groupMember");
                }
                else{
                    Log.e(REMOVE_GROUP_MEMBER_ACTIVITY,"something went wrong: "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(REMOVE_GROUP_MEMBER_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }


    public void btnOnCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }
}