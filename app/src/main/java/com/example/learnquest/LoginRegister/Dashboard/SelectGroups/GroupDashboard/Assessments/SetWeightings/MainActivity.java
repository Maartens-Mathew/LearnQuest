package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;
//import com.example.learnquest.Utils.database.SupabaseClient1;

//import io.github.jan.supabase.postgrest.Postgrest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // SupabaseClient1 client = new SupabaseClient1();

    }

    public void goToWeights(View view) {
        Intent showIntent = new Intent(this, ListViewActivity.class);
        startActivity(showIntent);
    }
}
