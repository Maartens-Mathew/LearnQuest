package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.AssessmentFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.GroupHomeFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.ResourcesFragment;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.StudyTools.StudyToolsFragment;
import com.example.learnquest.R;
import com.example.learnquest.databinding.ActivityGroupViewBinding;

public class GroupView extends AppCompatActivity {
    ActivityGroupViewBinding binding;

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




    }

    public boolean onItemSelectedListener(MenuItem item){
        Fragment fragment = null;


        if (item.getItemId() == R.id.mi_groupQuiz)
            fragment = new QuizFragment();

        if(item.getItemId() == R.id.mi_groupDashboard)
            fragment = new GroupHomeFragment();

        if(item.getItemId() == R.id.mi_groupAssessment)
            fragment = new AssessmentFragment();

        if (item.getItemId() == R.id.mi_GroupResources)
            fragment = new ResourcesFragment();

        if (item.getItemId() == R.id.mi_GroupTools)
            fragment = new StudyToolsFragment();


        getSupportFragmentManager().beginTransaction().replace(binding.fcvGroupView.getId(),fragment).commit();

        return true;




    }
}