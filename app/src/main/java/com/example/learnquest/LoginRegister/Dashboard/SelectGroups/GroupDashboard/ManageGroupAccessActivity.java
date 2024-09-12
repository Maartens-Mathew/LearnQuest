package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageGroupAccessActivity extends AppCompatActivity {

    private int userID = 0;
    private int groupID = 1;
    private int roleID = 2;
    private List<GroupMembership> pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group_access);

    }
}
//
//    public void aVoid(){
//
//    }

//    private void interactWithDatabase(){
////        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
////        Call<List<GroupMembership>> groupMembershipCall = api.getGroupMembership("1");
////        groupMembershipCall.enqueue(new Callback<List<GroupMembership>>() {
////            @Override
////            public void onResponse(Call<List<GroupMembership>> call, Response<List<GroupMembership>> response) {
////                if (response.isSuccessful() && response.body() != null){
////                    List<GroupMembership> groupMembershiplist = response.body();
////                    pending = new ArrayList<>();
////                    for (GroupMembership record : groupMembershiplist){
////                        if (record.getRoleID() == 4){
////                            pending.add(record);
////                        }
////                    }
////                }
////            }
//
////            @Override
////            public void onFailure(Call<List<GroupMembership>> call, Throwable throwable) {
////
////            }
////        });
////    }
////}