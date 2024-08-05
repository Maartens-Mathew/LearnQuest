package com.example.learnquest.SetWeightings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToWeights(View view) {
        Intent showIntent = new Intent(this, ListViewActivity.class);
        startActivity(showIntent);
    }
}
