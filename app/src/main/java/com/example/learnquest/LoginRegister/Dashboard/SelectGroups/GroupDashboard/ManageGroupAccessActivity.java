package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageGroupAccessActivity extends AppCompatActivity {

    private int userID = 0;
    private int groupID = 1;
    private int roleID = 2;
    private List<PendingUsersResult> pending;
    private List<String> pendingNames = new ArrayList<>();

    private PendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group_access);
        interactWithDatabase();
    }

    public void setUpRecyclerView(){

    }

    public void btnAcceptClicked(View view){

    }

    public void createNamesList(){
        for (PendingUsersResult p : pending){
            pendingNames.add(p.getPendingUserFirstName() + " " + p.getPendingUserLastName());
        }
    }


    private void interactWithDatabase(){
        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
        Call<List<PendingUsersResult>> groupMembershipCall = api.getPendingUsers(groupID);
        Response<List<PendingUsersResult>> response = null;
        try{
            response = groupMembershipCall.execute();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        if (response.isSuccessful()){
            response.body().toString();
        }
    }
}