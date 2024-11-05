package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateGroupActivity extends AppCompatActivity {

    public static final String CREATE_GROUP_ACTIVITY = "CreateGroupActivity";
    private String selectedColorHex = "#000000";
    private EditText description, topic;
    private Spinner groupType;
    private CardView groupColorCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Button btnPickColor = findViewById(R.id.btnPickColor);
        description = findViewById(R.id.edtCreateGroupDescription);
        topic = findViewById(R.id.edtCreateGroupTopic);
        groupType = findViewById(R.id.spinnerGroupType);
        groupColorCardView = findViewById(R.id.cardViewCreateGroupColor);
        List<String> groupTypes = new ArrayList<>();
        groupTypes.add("Social");
        groupTypes.add("Peer-to-Peer");
        groupTypes.add("Student-Teacher");
        groupTypes.add("Personal");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.grouptype_holder,groupTypes);
        btnPickColor.setOnClickListener(v -> {ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose Color")
                .initialColor(Color.parseColor(selectedColorHex)) // Starting color
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12) // Number of colors in the color picker
                .setOnColorSelectedListener(selectedColor ->
                        Toast.makeText(getApplicationContext(), "Selected color: " + Integer.toHexString(selectedColor).toUpperCase(), Toast.LENGTH_SHORT).show()
                )
                .setPositiveButton("OK", (dialog, selectedColor, allColors) -> {
                    if (selectedColor == Color.WHITE) {
                        Toast.makeText(getApplicationContext(), "White color is not allowed. Please choose another color.", Toast.LENGTH_SHORT).show();
                        // Optionally: Set button background to a default color (e.g., gray) to indicate invalid selection
                        groupColorCardView.setCardBackgroundColor(Color.parseColor(selectedColorHex));
                        //btnPickColor.setBackgroundColor(Color.GRAY);
                    } else {
                        // Convert the selected color to hex format
                        selectedColorHex = String.format("#%06X", (0xFFFFFF & selectedColor));
                        groupColorCardView.setCardBackgroundColor(Color.parseColor(selectedColorHex));
                        //btnPickColor.setBackgroundColor(selectedColor); // Update button color
                    }
                })
                //groupColorCardView.setCardBackgroundColor(Color.parseColor(selectedColorHex));
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .build()
                .show();
        });
    }

    public void onBtnCreateGroupCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    //TODO: Do error checking here
    public void onBtnCreateGroupClicked(View v){
        String description = this.description.getText().toString();
        String topic = this.topic.getText().toString();
        String color = selectedColorHex;
        int groupType = this.groupType.getSelectedItemPosition() + 1;
        Group group  = new Group(topic,description,groupType,color);
        Call<Void> createGroupCall = App.api.addGroup(group);
        createGroupCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(CREATE_GROUP_ACTIVITY,"creationg of group successful");
                }
                else{
                    Log.e(CREATE_GROUP_ACTIVITY,"something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(CREATE_GROUP_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }
}