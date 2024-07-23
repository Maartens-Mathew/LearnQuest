package com.example.learnquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.learnquest.User.User;
import com.example.learnquest.databinding.ActivityRegisterStudentBinding;

public class RegisterStudent extends AppCompatActivity {
    User currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        ActivityRegisterStudentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register_student);
        binding.setStudent(currentStudent);

    }

    public void ConfirmStudent(View view) {

        //Check if name is valid
    }

    public void ReturnToMain(View view) {
    }

    public void ResetFields(View view) {
    }
}