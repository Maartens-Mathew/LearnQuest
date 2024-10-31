package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learnquest.LoginRegister.Dashboard.Dashboard;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.AssessmentFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.GroupHomeFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.ManageTags.tagHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.quizBankHome;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.ResourcesFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools.StudyToolsFragment;
import com.example.learnquest.LoginRegister.LoginActivity;
import com.example.learnquest.R;
import com.example.learnquest.Utils.BottomSheet;
import com.example.learnquest.databinding.ActivityGroupViewBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.kennyc.bottomsheet.BottomSheetListener;

public class GroupView extends AppCompatActivity {
    ActivityGroupViewBinding binding;
    int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_view);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.groupBottomNavigation.setOnItemSelectedListener(this::onItemSelectedListener);
        binding.groupBottomNavigation.setOnItemReselectedListener(this::onLongClick);




    }

    public void onLongClick(MenuItem menuItem){


        FragmentManager manager = getSupportFragmentManager();
        new BottomSheet.Builder()
                .with(GroupView.this)
                .getItemTitles("Quiz Bank","Quiz Pool","Manage Tags","Take Quiz")
                .andClasses(quizBankHome.class, LoginActivity.class, tagHome.class, Dashboard.class)
                .build()
                .show(manager,"This is a tag");


    }



    public boolean onItemSelectedListener(MenuItem item){
        Fragment fragment = null;
        int newTab = 0;


        if (item.getItemId() == R.id.mi_groupQuiz) {
            fragment = new QuizFragment();
            newTab = 0;
        }

        if(item.getItemId() == R.id.mi_groupDashboard) {
            fragment = new GroupHomeFragment();
            newTab = 2;
        }

        if(item.getItemId() == R.id.mi_groupAssessment) {
            fragment = new AssessmentFragment();
            newTab = 1;
        }

        if (item.getItemId() == R.id.mi_GroupResources) {
            fragment = new ResourcesFragment();
            newTab = 4;
        }

        if (item.getItemId() == R.id.mi_GroupTools) {
            fragment = new StudyToolsFragment();
            newTab = 3;
        }


        replaceFragment(fragment,newTab);

        return true;




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
        transaction.replace(R.id.fcv_groupView, fragment)
                 // Add to back stack to manage navigation
                .commit();

        // Update current tab index
        currentTab = newTab;

    }
}