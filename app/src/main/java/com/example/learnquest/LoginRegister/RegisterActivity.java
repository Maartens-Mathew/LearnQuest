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
import com.example.learnquest.Utils.DataVal; // Import the DataVal class

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


        currentStudent = new User("Meghan", "Maartens", "0303025049000", "maartens.test@gmail.com", "Maartens.Meghan", "qwerty");
    }

    public void ConfirmStudent(View view) {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String nationalID = edtNationalID.getText().toString();
        String email = edtEmail.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        // Validate First Name
        if (!DataVal.isValidName(firstName)) {
            Toast.makeText(this, "Invalid first name. Please use letters only.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Last Name
        if (!DataVal.isValidName(lastName)) {
            Toast.makeText(this, "Invalid last name. Please use letters only.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate National ID
        if (!DataVal.isValidLuhn(nationalID)) {
            Toast.makeText(this, "Invalid National ID. It must be a valid 13-digit number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Email
        if (!DataVal.isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Password
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if Password and Re-Password match
        EditText rePassword = findViewById(R.id.txt_RePassword);
        if (!rePassword.getText().toString().equals(password)) {
            Toast.makeText(this, "Password does not match with Re-Password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // If all validations pass, set user details and send request
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setNationalID(nationalID);
        currentStudent.setEmail(email);
        currentStudent.setUsername(username);
        currentStudent.setPassword(password);

        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
        Call<User> call = api.addUser(currentStudent);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Person inserted successfully
                    System.out.println("Insert successful: " + response.body());
                    Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the error
                    System.out.println("Insert failed: " + response.errorBody());
                    Toast.makeText(RegisterActivity.this, "Error during registration. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
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
