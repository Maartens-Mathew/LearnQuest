package com.example.learnquest.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resources_constraint), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progress_login);
        progressBar.setVisibility(View.GONE);



        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);


    }

    public void onLoginClick(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();


        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter both username and password.", Toast.LENGTH_SHORT).show();
            return;
        }


        Call<Integer> validCall = App.api.isUserValid(username, password);
        progressBar.setVisibility(View.VISIBLE);
        validCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int valid = response.body();
                    //progressBar.setVisibility(View.GONE);
                    processLogin(valid); // Handle successful login

                } else {
                    try {
                        Log.e("Custom", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void processLogin(int valid) {

        if (valid == 0)
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Username is not present.", Toast.LENGTH_SHORT).show());

        if (valid == 1)
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),"Password is incorrect.", Toast.LENGTH_SHORT).show());

        if (valid == 2) {
            setUser();

        }

    }

    private void ProceedToDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    private void setUser() {
        String username = edtUsername.getText().toString();

        Call<List<User>> getCall = App.api.getUser(username);

        //progressBar.setVisibility(View.VISIBLE);

        getCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    App.user = response.body().get(0);
                    runOnUiThread(() -> {

                                Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                                ProceedToDashboard();

                            }
                    );
                } else {
                    try {
                        Log.e("Custom", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}