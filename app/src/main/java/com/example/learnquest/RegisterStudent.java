package com.example.learnquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.databinding.ActivityRegisterStudentBinding;
import com.example.learnquest.user.Student;

public class RegisterStudent extends AppCompatActivity {
    Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);


        ActivityRegisterStudentBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register_student);
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
    }

    public void ResetFields(View view) {
    }
}