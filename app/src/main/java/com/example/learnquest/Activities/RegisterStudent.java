package com.example.learnquest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.R;
import com.example.learnquest.database.SupabaseApi;
import com.example.learnquest.database.SupabaseClient;
import com.example.learnquest.databinding.ActivityRegisterStudentBinding;
import com.example.learnquest.user.Student;
import com.example.learnquest.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterStudent extends AppCompatActivity {
    User currentStudent;
    ActivityRegisterStudentBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);






        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_student);
        currentStudent = new User("Meghan","Maartens","0303025049000","maartens.test@gmail.com","Maartens.Meghan","qwerty");
        binding.setUser(currentStudent);





        //Must still check why this listener is displaying a warning.

    }
    public void ConfirmStudent(View view) {

        EditText rePassword = findViewById(R.id.txt_RePassword);

        if (!rePassword.getText().toString().equals(currentStudent.getPassword()))
            Toast.makeText(this,"Password is incorrect", Toast.LENGTH_SHORT).show();
        else {
            SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);

            Call<User> call = api.addUser(currentStudent);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        // Person inserted successfully
                        System.out.println("Insert successful: " + response.body());
                    } else {
                        // Handle the error
                        System.out.println("Insert failed: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle failure
                    t.printStackTrace();
                }
            });

        }



          //  Toast.makeText(this, "User registered.", Toast.LENGTH_SHORT).show();


    }

    public void ReturnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ResetFields(View view) {
        binding.setUser(new User());
        currentStudent = binding.getUser();
        binding.txtID.setText("");
        binding.txtRePassword.setText("");
        binding.txtFirstName.requestFocus();

    }
}