package com.example.learnquest.LoginRegister.Dashboard.SelectGroups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteGroupActivity extends AppCompatActivity {

    public static final String DELETE_GROUP_ACTIVITY = "DeleteGroupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
    }


    public void btnConfirmClicked(View v){
        Call<Void> deleteGroupCall = App.api.deleteGroup("eq" + App.groupID);
        deleteGroupCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(DELETE_GROUP_ACTIVITY, "deleting group successful");
                }
                else{
                    Log.e(DELETE_GROUP_ACTIVITY,"something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(DELETE_GROUP_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void btnCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }
}