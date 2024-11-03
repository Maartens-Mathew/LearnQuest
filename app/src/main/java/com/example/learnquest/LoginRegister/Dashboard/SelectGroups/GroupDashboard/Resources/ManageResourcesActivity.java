package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.GroupView;
import com.example.learnquest.R;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.studyResource.StudyResource;
import com.example.learnquest.model.studyResource.Temp.LeafNode;
import com.example.learnquest.model.studyResource.Temp.Tree;
import com.example.learnquest.model.studyResource.Temp.TreeNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageResourcesActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 500;
    private ProgressBar progressBar;
    private Tree tree;
    private ResourceAdapter adapter;
    private List<StudyResource> studyResources;
    private RecyclerView filesView;
    private TextView txtFolder;


    private DownloadReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_resources);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resources_constraint), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        App.group = Group.demoGroup();
        tree = new Tree();
        downloadReceiver = new DownloadReceiver(() -> progressBar.setVisibility(View.GONE));
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter, Context.RECEIVER_EXPORTED);

        txtFolder = findViewById(R.id.txtFolder);
        filesView = findViewById(R.id.filesView);
        txtFolder.setText(tree.getCurrentName());

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        studyResources = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);


        getStudyResources();



        filesView.setLayoutManager(manager);

        filesView.addItemDecoration(new EqualSpacingItemDecoration(3));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadReceiver);
    }

    public void setAdapter(List<TreeNode> studyResources){
        if (adapter == null) {
            adapter = new ResourceAdapter(studyResources, this::onResourceClick);
            filesView.setAdapter(adapter);
        }
        else{
            adapter.updateData(studyResources);
        }
    }

    public void traverseUp(){
        tree.setParentToCurrent();
        setAdapter(tree.getCurrentChildren());
        changeFolderName();

    }



    public void changeFolderName(){
        txtFolder.setText(tree.getCurrentName());
    }

    public View.OnClickListener onResourceClick(TreeNode node) {
        return (itemView) -> {
            if (node.isInner()) {
                String result = tree.setCurrentChild(node.getName());
                if (!"Child node not found!".equals(result)) {
                    adapter.updateData(tree.getCurrentChildren());
                    txtFolder.setText(tree.getCurrentName());
                }
            } else if (node.isLeaf()) {
                LeafNode leafNode = (LeafNode) node;
                StudyResource resource = leafNode.getCargo();
                processResource(resource);
            }
        };
    }

    public void processResource(StudyResource studyResource) {
        String fileType = studyResource.getFileType();
        Uri fileUri = Uri.parse(studyResource.getURL());
        downloadReceiver.setStudyResource(studyResource);

        // Show the ProgressBar
        progressBar.setVisibility(View.VISIBLE);
        filesView.setEnabled(false);

        File file = getFileFromDownloads(studyResource.getFileName() + "." + studyResource.getFileType());
        if (file != null) {
            downloadReceiver.openDownloadedFile(this, Uri.fromFile(file), studyResource.getFileType());
            return;
        }

        // Directly open PDFs and URLs in a browser
        if (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("html")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            startActivity(intent);
            progressBar.setVisibility(View.GONE); // Hide immediately since it's opened directly
            return;
        }

        // Start the Download Service
        Intent downloadIntent = new Intent(this, DownloadService.class);
        downloadIntent.putExtra("fileUrl", studyResource.getURL());
        downloadIntent.putExtra("fileName", studyResource.getFileName());
        startService(downloadIntent);
    }

    @Override
    public void onBackPressed() {
        // Check if you are at the root of your filing system (e.g., main directory)
        if (tree.isRoot()) {
            // Perform default back action (exits the activity)
            super.onBackPressed();
        } else {
            // Go back to the previous directory or state
            traverseUp();
        }
    }

    public File getFileFromDownloads(String fileName) {
        File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsFolder != null && downloadsFolder.isDirectory()) {
            File targetFile = new File(downloadsFolder, fileName);
            if (targetFile.exists() && targetFile.isFile()) {
                return targetFile; // Return the file if found
            }
        }
        return null; // Return null if the file doesn't exist
    }

    public void getStudyResources() {
        Call<List<StudyResource>> studyCall = App.api.getStudyResources(App.group.getGroupID());

        studyCall.enqueue(new Callback<List<StudyResource>>() {
            @Override
            public void onResponse(@NonNull Call<List<StudyResource>> call, @NonNull Response<List<StudyResource>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studyResources.addAll(response.body());
                    studyResources.forEach(tree::addResource);

                    // Update the UI or notify the adapter if needed
                    setAdapter(tree.getCurrentChildren());
                    progressBar.setVisibility(View.GONE);


                } else {
                    try {
                        Log.e("Database error: ", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StudyResource>> call, Throwable t) {
                Log.e("Network error: ", "Failed to retrieve study resources", t);
                Toast.makeText(ManageResourcesActivity.this, "Failed to load resources. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onBackClick(View view) {
        if (tree.isRoot()) {
            Intent intent = new Intent(this, GroupView.class);
            startActivity(intent);
        }
        tree.setParentToCurrent();
        adapter.updateData(tree.getCurrentChildren());
        txtFolder.setText(tree.getCurrentName());
    }

    public void onDashboardClick(View view) {
        Intent intent = new Intent(this, GroupView.class);
        startActivity(intent);
    }
}
