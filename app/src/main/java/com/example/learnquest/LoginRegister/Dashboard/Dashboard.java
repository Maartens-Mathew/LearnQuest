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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    int currentItem = 0;
    FragmentManager manager;
    int currentTab = 0;
    BottomNavigationView navBarDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        manager = getSupportFragmentManager();

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navBarDash = findViewById(R.id.navBarDash);



        //Navigation logic
        navBarDash.setOnItemSelectedListener(view ->{
            Fragment fragment = null;
            int newTab = 0;




            if (view.getItemId() == R.id.selectGroups) {
                fragment = new ManageGroupsFragment();
                newTab = 0;
            }

            if (view.getItemId() == R.id.calendar) {
                fragment = new CalendarFragment();
                newTab = 1;
            }

            if (view.getItemId() == R.id.profile) {
                fragment = new ProfileFragment();
                newTab = 2;
            }


            replaceFragment(fragment,newTab);

            return true;
        });
    }

    private void replaceFragment(Fragment fragment, int newTab) {



        if (currentTab == newTab)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentTab != -1) {


            // Set custom animations based on the direction of navigation
            if (newTab > currentTab) {
                // Navigate to the right
                transaction.setCustomAnimations(
                        R.anim.enter_from_right, // Enter animation
                        R.anim.exit_to_left,     // Exit animation
                        R.anim.enter_from_left,  // Pop enter animation (when fragment is restored)
                        R.anim.exit_to_right     // Pop exit animation
                );
            } else {
                // Navigate to the left
                transaction.setCustomAnimations(
                        R.anim.enter_from_left,  // Enter animation
                        R.anim.exit_to_right,    // Exit animation
                        R.anim.enter_from_right, // Pop enter animation (when fragment is restored)
                        R.anim.exit_to_left      // Pop exit animation
                );
            }
        }

        // Replace fragment and add to back stack
        transaction.replace(R.id.fcv_dashboard, fragment)
                 // Add to back stack to manage navigation
                .commit();

        // Update current tab index
        currentTab = newTab;

    }
}