package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingUsersActivity extends AppCompatActivity {

    public static final String PENDING_USER_ACTIVITY = "PendingUserActivity";
    List<Pending> entries;
    private RecyclerView recyclerView;

    public void onPendingUserBtnBackPressed(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_users);
        recyclerView = findViewById(R.id.rwPendingUsers);
        new DatabaseThread().start();
    }

    public void setUpRecyclerView(List<Pending> users){
        //progressBar.setVisibility(View.GONE);
        PendingAdapter adapter = new PendingAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

//    public void getPendingUsers(){
//        Call<List<Pending>> pendingCall = App.api.getPendingUsers(App.group.getGroupID());
//        Response<List<Pending>> pendingResponse = null;
//
//        try{
//            pendingResponse = pendingCall.execute();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//
//        if (pendingResponse.isSuccessful())
//            entries = Collections.synchronizedList(pendingResponse.body());
//        else {
//            try {
//                Log.e("Custom", pendingResponse.errorBody().string());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    private class DatabaseThread extends Thread{

        @Override
        public void run() {
            super.run();
            //progressBar.setVisibility(View.VISIBLE);
            Call<List<Pending>> pendingCall = App.api.getPendingUsers(App.group.getGroupID());

            pendingCall.enqueue(new Callback<List<Pending>>() {
                @Override
                public void onResponse(Call<List<Pending>> call, Response<List<Pending>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // This runs on a background thread, so use runOnUiThread to update the UI
                        runOnUiThread(() -> setUpRecyclerView(response.body()));
                    } else {
                        // Handle error response
                        try {
                            Log.e(PENDING_USER_ACTIVITY, "Error: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(PENDING_USER_ACTIVITY, "Error parsing error body", e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Pending>> call, Throwable t) {
                    // Handle failure (e.g., network issues)
                    Log.e(PENDING_USER_ACTIVITY, "Request failed", t);
                }
            });
        }
    }
}