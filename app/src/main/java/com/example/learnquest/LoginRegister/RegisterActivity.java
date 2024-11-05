package com.example.learnquest.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    User currentStudent;
    EditText edtFirstName, edtLastName, edtNationalID, edtEmail, edtUsername, edtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        edtFirstName = findViewById(R.id.txt_FirstName);
        edtLastName = findViewById(R.id.txt_Surname);
        edtNationalID = findViewById(R.id.txt_ID);
        edtEmail = findViewById(R.id.txt_EmailAddress);
        edtUsername = findViewById(R.id.txt_Username);
        edtPassword = findViewById(R.id.txt_Password);



        currentStudent = new User("Meghan","Maartens","0303025049000","maartens.test@gmail.com","Maartens.Meghan","qwerty");








        //Must still check why this listener is displaying a warning.

    }
    public void ConfirmStudent(View view) {


        currentStudent.setFirstName(edtFirstName.getText().toString());
        currentStudent.setLastName(edtLastName.getText().toString());
        currentStudent.setNationalID(edtNationalID.getText().toString());
        currentStudent.setEmail(edtEmail.getText().toString());
        currentStudent.setUsername(edtUsername.getText().toString());
        currentStudent.setPassword(edtPassword.getText().toString());

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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void ResetFields(View view) {
        currentStudent.clear();
        edtFirstName.setText("");
        edtLastName.setText("");
        edtNationalID.setText("");
        edtEmail.setText("");
        edtUsername.setText("");
        edtPassword.setText("");

    }
}