package com.example.learnquest.GroupStuff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.learnquest.R;

public class ManageGroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_groups);
    }

    public void onBtnSearchClicked(View v){
        EditText edtSearchTerm = findViewById(R.id.edtSearchTerm);
        String searchStr = edtSearchTerm.getText().toString();

    }

    public void onAddGroupClicked(View v){

    }
}