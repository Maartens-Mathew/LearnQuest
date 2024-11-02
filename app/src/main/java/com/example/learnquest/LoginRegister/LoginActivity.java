package com.example.learnquest.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.Dashboard;
import com.example.learnquest.R;
import com.example.learnquest.model.user.User;
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);


    }

    public void onLoginClick(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        Call<Integer> validCall = App.api.isUserValid(username, password);
        final Response<Integer>[] validResponse = new Response[]{null};

        Thread thread = new Thread( () -> {

            try {
                validResponse[0] = validCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (validResponse[0].isSuccessful() && validResponse[0].body() != null) {
              int valid = validResponse[0].body();
                processLogin(valid);
            } else {
                try {
                    Log.e("Custom", validResponse[0].errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    private void processLogin(int valid) {

        if (valid == 0)
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Username is not present.", Toast.LENGTH_SHORT).show());

        if (valid == 1)
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),"Password is incorrect.", Toast.LENGTH_SHORT).show());

        if (valid == 2) {
            setUser();
            runOnUiThread(() -> {

                        Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();

                        ProceedToDashboard();

                    }
            );
        }

    }

    private void ProceedToDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    private void setUser(){
        String username = edtUsername.getText().toString();


        Call<List<User>> getCall = App.api.getUser(username);

        Response<List<User>> getResponse = null;

        try{
            getResponse = getCall.execute();
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        if (getResponse.isSuccessful())
            App.user = getResponse.body().get(0);
        else {

            try {
                Log.e("Custom", getResponse.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}