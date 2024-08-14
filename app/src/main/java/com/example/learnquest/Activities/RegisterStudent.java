package com.example.learnquest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.R;
import com.example.learnquest.database.CallBack_Response;
import com.example.learnquest.database.DatabaseStates;
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


        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_student);
        setContentView(binding.getRoot());


        currentStudent = new User("Meghan","Maartens","0303025049000","maartens.test@gmail.com","Maartens.Meghan","qwerty");
        binding.setUser(currentStudent);







    }

    SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);


    public void ConfirmStudent(View view) {



        EditText rePassword = binding.txtRePassword;

        if (!rePassword.getText().toString().equals(currentStudent.getPassword()))
            Toast.makeText(this,"Password is incorrect", Toast.LENGTH_SHORT).show();
        else {

            if (!isIDUnique()){
                Call<User> id_check_call = api.getUser("* FROM Users WHERE nationalID = " + binding.txtID.getText().toString());
                List<User> existingUsersID = id_check_call.enqueue()

            }


            //Bind state conditions to how database reacts
            CallBack_Response<User> callBack = new CallBack_Response<>();
            callBack.addState(DatabaseStates.ON_SUCCESSFUL, this::onSuccessful);
            callBack.addState(DatabaseStates.ON_FAILURE, this::onFailure);
            callBack.addState(DatabaseStates.ON_NOT_CONNECT, this::onFailureConnect);

            //Get callback once valid states identified
            Callback<User> callback = callBack.getCallBack();

            //Add user to database
            Call<User> call = api.addUser(currentStudent);

            //Execute call (the 'request')
            call.enqueue(callback);
        }

    }

    public boolean isIDUnique(){

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

    public void onSuccessful(){
        Toast.makeText(this,"Student registration successful. ", Toast.LENGTH_SHORT).show();
    }

    public void onFailure(){
        //check if id is unique


        existingUsers = api.getUser(" * FROM Users WHERE email = " + binding.txtEmailAddress.getText().toString());
        if (existingUsers == null){
            Toast.makeText(this,getString(R.string.error_existing_id,"national ID", "national ID"), Toast.LENGTH_SHORT).show();
        }



        //check if email is unique
    }

    public void onFailureConnect(){
        Toast.makeText(this,"Could not connect to the database.",Toast.LENGTH_SHORT).show();
    }

    public
}