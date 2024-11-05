package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ManageStudyResource extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private EditText editTextResourceName;
    private TextView textViewFileName;
    private Uri selectedFileUri;

    File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_study_resource);

        editTextResourceName = findViewById(R.id.editTextResourceName);
        textViewFileName = findViewById(R.id.textViewFileName);
        Button buttonSelectFile = findViewById(R.id.buttonSelectFile);

        // Check if editing an existing study resource
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("resourceName") && intent.hasExtra("resourceUri")) {
            String resourceName = intent.getStringExtra("resourceName");
            selectedFileUri = intent.getParcelableExtra("resourceUri");

            if (resourceName != null) {
                editTextResourceName.setText(resourceName);
            }
            if (selectedFileUri != null) {
                textViewFileName.setText(getFileName(selectedFileUri));
            }
        }

        // Set up button to select file from file explorer
        buttonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });
    }

    // Opens file picker
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*"); // Adjust MIME type as needed
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    // Handles result from file picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                try (InputStream inputStream = getContentResolver().openInputStream(selectedFileUri)) {
                    // Process the InputStream as needed
                    // For example, you can read the file content or upload it as a byte array
                    selectedFile = getFile(inputStream);
                    uploadEntry();

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception, e.g., display an error message
                }
            }
        }
    }

    public File getFile(InputStream inputStream){
        File file = null;
        try {
            file = File.createTempFile("Test","jpg", getCacheDir());

            FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return file;





        } catch (IOException e) {
            Log.e("Custom", "Error getting file.");
        }

        return null;
    }






    public void uploadEntry(){
        App.client = App.getInstance();
        App.client.uploadFile("StudyResources","Test.jpg",selectedFile);

    }

    // Retrieves file name from Uri
    private String getFileName(Uri uri) {
        String fileName = "Unknown file";
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex >= 0 && cursor.moveToFirst()) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
    }

    public void onBack2Click(View view) {
        Intent intent = new Intent(this, ManageResourcesActivity.class);
        startActivity(intent);
    }



    public void onUploadClick(View view) {
        String resourceName = editTextResourceName.getText().toString();

    }
}
