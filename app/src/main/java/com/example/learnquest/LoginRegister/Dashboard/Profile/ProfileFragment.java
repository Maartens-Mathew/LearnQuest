package com.example.learnquest.LoginRegister.Dashboard.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.model.user.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ProfileFragment extends Fragment {


    EditText edtFirstName, edtLastName, edtNationalID, edtEmail, edtUsername, edtPassword;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edtFirstName = view.findViewById(R.id.edt_profile_firstName);
        edtLastName = view.findViewById(R.id.edt_profile_lastName);
        edtNationalID = view.findViewById(R.id.edt_profile_nationalID);
        edtEmail = view.findViewById(R.id.edt_profile_email);
        edtUsername = view.findViewById(R.id.edt_profile_username);
        edtPassword = view.findViewById(R.id.edt_profile_password);

        Button button = view.findViewById(R.id.btn_profile_update);
        button.setOnClickListener(this::onUpdateClick);

        edtFirstName.setText(App.user.getFirstName());
        edtLastName.setText(App.user.getLastName());
        edtNationalID.setText(App.user.getNationalID());
        edtEmail.setText(App.user.getEmail());
        edtUsername.setText(App.user.getUsername());
        edtPassword.setText(App.user.getPassword());

        return view;




    }

    public void onUpdateClick(View view) {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String nationalID = edtNationalID.getText().toString();
        String email = edtEmail.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();


        //Check entries

        User user = new User(firstName, lastName, nationalID, email, username, password);
        user.setUserID(App.user.getUserID());

        Thread thread = new Thread( () -> {


            Call<Void> updateCall = App.api.updateUser("eq." + App.user.getUserID(), user);
            Response<Void> updateResponse = null;

            try {
                updateResponse = updateCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


            if (updateResponse.isSuccessful()) {
                getActivity().runOnUiThread(() -> Toast.makeText(getContext().getApplicationContext(), "Profile has been updated.", Toast.LENGTH_SHORT).show());
                App.updateUser();
            } else {
                try {
                    Log.e("Custom", updateResponse.errorBody().string());
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

}