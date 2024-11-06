package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.FileClient;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.studyResource.StudyResource;
import com.example.learnquest.model.user.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;

import kotlinx.datetime.LocalDate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStudyResource extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;


    private Uri selectedFileUri;

    FirebaseStorage storage;
    StorageReference reference;

    File selectedFile;

    StudyResource studyResource;
    TextView txtExtension;
    TextView txtFileName;
    private EditText edtResourceName;
    Button btnSelectFile;


    String filePath;

    Boolean isEdit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_study_resource);
        txtExtension = findViewById(R.id.txtExtension);
        txtFileName = findViewById(R.id.txtFileName);
        edtResourceName = findViewById(R.id.edtResourceName);
        btnSelectFile = findViewById(R.id.btnSelectFile);

        App.group = Group.demoGroup();
        App.user = User.demoUser();


        storage = FirebaseStorage.getInstance();

        // Check if editing an existing study resource
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("studyResource")) {
                studyResource = (StudyResource) intent.getSerializableExtra("studyResource");
                isEdit = true;
                setupUI();
            }else {
                filePath = intent.getStringExtra("filePath");
            }

        }

        txtExtension.setEnabled(false);
        txtFileName.setEnabled(false);

        btnSelectFile.setOnClickListener(view -> openFilePicker());
    }

        public void setupUI() {
            if (studyResource != null) {
                edtResourceName.setText(studyResource.getFileName());
                txtExtension.setText("File extension: ." + studyResource.getFileType());
                txtFileName.setText("File name: ." + studyResource.getFileName());


                btnSelectFile.setEnabled(false);

            }
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
                    selectedFile = new File(String.valueOf(selectedFileUri));
                    ContentResolver resolver = getContentResolver();
                    txtFileName.setText("File name: " + selectedFile.getName());
                    txtExtension.setText("File extension: ." + resolver.getType(selectedFileUri));


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


    // Retrieves file name from Uri
    private String getFileName(@NonNull Uri uri) {
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
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please select a file to upload.",Toast.LENGTH_SHORT).show();
            return;
        }

        String resourceName = edtResourceName.getText().toString();

        // Get a reference to Firebase Storage

        @NonNull Uri selectedFileUrl = selectedFileUri;
        // Use the file name with the proper extension (assuming selectedFileUri is a URI)
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(selectedFileUri));
        String fileName = selectedFile.getName() + (extension != null ? "." + extension : "");


        // Create a reference to the Firebase Storage path


        // Upload the file to Firebase Storage
        FileClient.uploadFile(selectedFileUri,resourceName, this::onSuccess, this::onFailure);
        build();

    }


    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
        getUrl();


    }

    public void onFailure(@NonNull Exception e){
        Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show();
    }

    public void addStudyEntry(){
        // Run the network call in a separate thread to avoid blocking the main thread
        new Thread(() -> {
            try {
                // Execute the call synchronously
                Response<Void> response = App.api.addStudyResource(studyResource).execute();

                // Check if the response was successful
                if (response.isSuccessful()) {
                    // Run UI updates on the main thread
                    runOnUiThread(() ->
                            Toast.makeText(ManageStudyResource.this.getApplicationContext(), "Study Resource added", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    // Log the error from the response
                    String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                    Log.e("Custom", errorMsg);
                }
            } catch (IOException e) {
                // Handle IOException that may occur during the network call
                Log.e("Custom", "Network call failed", e);
            }
        }).start();

    }

    public void build(){
        String fileName = edtResourceName.getText().toString();
        String fileType = txtFileName.getText().toString();
        String extension = txtExtension.getText().toString();
        Integer groupID = App.group.getGroupID();
        java.time.LocalDate currentDate = java.time.LocalDate.now();


        studyResource =  new StudyResource(currentDate.toString(),fileName,filePath,fileType,groupID,0);




    }

    public void getUrl(){
        FileClient.getUrl(studyResource.getFileName(), (uri ->
        {
            studyResource.setURL(uri.toString());
            addStudyEntry();
            Toast.makeText(ManageStudyResource.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ManageStudyResource.this, ManageResourcesActivity.class);
            startActivity(intent);
        }),(e) -> Toast.makeText(this, "Failed to get URL", Toast.LENGTH_SHORT).show());
    }
}
