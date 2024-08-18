package com.example.learnquest.LoginRegister.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learnquest.LoginRegister.Dashboard.Calendar.CalendarFragment;
import com.example.learnquest.LoginRegister.Dashboard.Profile.ProfileFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.ManageGroupsFragment;
import com.example.learnquest.R;
import com.example.learnquest.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;
    int currentItem = 0;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        manager = getSupportFragmentManager();

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //Navigation logic
        binding.navBarDash.setOnItemSelectedListener(view ->{
            Fragment fragment = null;


            FragmentTransaction transaction = manager.beginTransaction();

            if (view.getItemId() == R.id.selectGroups)
                fragment = new ManageGroupsFragment();

            if (view.getItemId() == R.id.calendar)
                fragment = new CalendarFragment();

            if (view.getItemId() == R.id.profile)
                fragment = new ProfileFragment();


            transaction.replace(R.id.fc_dashboard,fragment).commit();

            return true;
        });
    }
}