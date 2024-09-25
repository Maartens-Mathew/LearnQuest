package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class PendingUsersActivity extends AppCompatActivity {

    List<PendingUsersResult> entries;
    private RecyclerView recyclerView;
    private PendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_users);
        recyclerView = findViewById(R.id.rwPendingUsers);
        App.groupID = 1;
        Thread thread = new Thread(this::getPendingUsers);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        List<String> names = entries.stream()
                .map(pendingUsersResult ->  pendingUsersResult.getPendingUserFirstName() + " " +  pendingUsersResult.getPendingUserLastName())
                .collect(Collectors.toList());
        adapter = new PendingAdapter(entries);
        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getPendingUsers(){
        Call<List<PendingUsersResult>> pendingCall = App.api.getPendingUsers(1);
        Response<List<PendingUsersResult>> pendingResponse = null;

        try{
            pendingResponse = pendingCall.execute();
        }catch(IOException e){
            e.printStackTrace();
        }

        if (pendingResponse.isSuccessful())
            entries = Collections.synchronizedList(pendingResponse.body());
        else {
            try {
                Log.e("Custom", pendingResponse.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}