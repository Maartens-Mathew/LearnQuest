package com.example.learnquest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.R;
import com.example.learnquest.databinding.ActivityRegisterStudentBinding;
import com.example.learnquest.user.Student;

public class RegisterStudent extends AppCompatActivity {
    Student currentStudent;
    ActivityRegisterStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_student);
        currentStudent = new Student();
        binding.setStudent(currentStudent);



        //Must still check why this listener is displaying a warning.

    }
    public void ConfirmStudent(View view) {

        EditText rePassword = findViewById(R.id.txt_RePassword);

        if (rePassword.getText().toString() != currentStudent.getPassword())
            Toast.makeText(this,"Password is incorrect", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Welcome, " + currentStudent.getFirst_Name() + ".", Toast.LENGTH_SHORT).show();

    }

    public void ReturnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ResetFields(View view) {
        binding.setStudent(new Student());
        currentStudent = binding.getStudent();
        binding.txtFirstName.requestFocus();

    }
}