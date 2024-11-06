package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Home.GroupHomeFragment;
import com.example.learnquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.learnquest.model.group.Group;

public class DeleteGroupActivity extends AppCompatActivity {

    private Group group;


    public static final String DELETE_GROUP_ACTIVITY = "DeleteGroupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
        group = App.group;
        TextView groupTopic = findViewById(R.id.deleteGroupTopic);
        ImageView groupImage = findViewById(R.id.deleteGroupImage);
        CardView groupColor = findViewById(R.id.deleteGroupColor);
        groupTopic.setText(group.getTopic());
        groupColor.setCardBackgroundColor(Color.parseColor(group.getGroupColour()));
        groupImage.setImageBitmap(group.getImage());
    }


    public void btnDeleteGroupConfirmClicked(View v){
        Intent result = new Intent();
        result.putExtra("isDelete",true);
        setResult(GroupHomeFragment.RESULT_OK, result);
        finish();
    }

    public void btnDeleteGroupCancelClicked(View v){
        Intent result = new Intent();
        result.putExtra("isDelete",false);
        setResult(GroupHomeFragment.RESULT_OK, result);
        finish();
    }
}