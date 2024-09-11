package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_users);

        App.groupID = 1;
        Thread thread = new Thread(this::getPendingUsers);

        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        List<String> names = entries.stream()
                .map(pendingUsersResult ->  pendingUsersResult.getPendingUserLastName() + " " +  pendingUsersResult.getPendingUserLastName())
                .collect(Collectors.toList());






        PendingAdapter adapter = new PendingAdapter(names);
    }

    public void getPendingUsers(){
        Call<List<Object>> pendingCall = App.api.getPendingUsers(App.groupID);
        Response<List<Object>> pendingResponse = null;

        try{
            pendingResponse = pendingCall.execute();
        }catch(IOException e){
            e.printStackTrace();
        }

        if (pendingResponse.isSuccessful())
            pendingResponse.body().toString();
        else {
            try {
                Log.e("Custom", pendingResponse.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}