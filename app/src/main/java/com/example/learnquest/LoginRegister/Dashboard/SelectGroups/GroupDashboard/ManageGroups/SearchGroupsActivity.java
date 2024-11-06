package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupAdapter;
import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;

import java.util.List;

public class SearchGroupsActivity extends AppCompatActivity {

    private RecyclerView rwFoundGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_groups);
        rwFoundGroup = findViewById(R.id.rwFoundGroups);
        Intent intent = getIntent();
        if (intent != null){
            List<Group> foundgroups = (List<Group>) intent.getExtras().get("groups");
            rwFoundGroup.setAdapter(new GroupAdapter(foundgroups, g -> {
                return v -> {
                  Intent i = new Intent(this, JoinGroupActivity.class);
                  intent.putExtra("group",g);
                  startActivity(i);
                };
            }));
            GridLayoutManager manager = new GridLayoutManager(this,3);
            rwFoundGroup.setLayoutManager(manager);
            rwFoundGroup.addItemDecoration(new EqualSpacingItemDecoration(10));
        }
        else{
            getOnBackPressedDispatcher().onBackPressed();
        }
    }
}