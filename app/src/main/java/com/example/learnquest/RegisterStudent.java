package com.example.learnquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learnquest.databinding.ActivityRegisterStudentBinding;

public class RegisterStudent extends AppCompatActivity {
    Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        currentStudent = new Student();

        ActivityRegisterStudentBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register_student);
        binding.setUser(currentStudent);
        binding.executePendingBindings();

    }

    public void ConfirmStudent(View view) {
        Toast.makeText(this,currentStudent.toString(), Toast.LENGTH_SHORT).show();
        //Check if name is valid
    }

    public void ReturnToMain(View view) {
    }

    public void ResetFields(View view) {
    }
}