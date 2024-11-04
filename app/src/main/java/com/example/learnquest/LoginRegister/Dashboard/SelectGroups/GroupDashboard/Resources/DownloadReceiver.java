package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.learnquest.model.studyResource.StudyResource;

import java.io.File;
import java.util.Locale;

public class DownloadReceiver extends BroadcastReceiver {

    Runnable ui;
    StudyResource studyResource;
    public DownloadReceiver(Runnable runnable){
        ui = runnable;
    }


    public void setStudyResource(StudyResource studyResource){
        this.studyResource =studyResource;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "The download has been finished. ", Toast.LENGTH_SHORT).show();
        long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        // Check if the download ID matches the one you started
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // Download succeeded
                @SuppressLint("Range") String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                Uri fileUri = Uri.parse(uriString);

                // Now open the downloaded file
                openDownloadedFile(context, fileUri, studyResource.getFileType());
            } else {
                // Download failed
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
    }

    private void openDownloadedFile(Context context, Uri fileUri) {
        ui.run();
        // Use FileProvider to get a content URI
        Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(fileUri.getPath()));


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(contentUri, "image/*"); // Change MIME type as needed
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission to read the URI

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No application available to open this file", Toast.LENGTH_SHORT).show();
        }
    }



    void openDownloadedFile(Context context, Uri fileUri, String fileType) {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(fileUri.getPath()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        MimeTypeMap type = MimeTypeMap.getSingleton();
         //= type.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(fileUri.getPath()));
        String mimeType =  type.getMimeTypeFromExtension(fileType.toLowerCase());

        switch (fileType.toLowerCase()) {
            case "pdf":
            case "doc":
            case "docx":
            case "jpg":
            case "jpeg":
            case "png":
            case "zip":
            case "mp4":
            case "avi":
            case "mkv":
            case "xls":
            case "xlsx":
                intent.setDataAndType(contentUri.normalizeScheme(),mimeType);
                break;
            default:
                Toast.makeText(context, "Unsupported file type", Toast.LENGTH_SHORT).show();
                return; // Exit if unsupported
        }


             context.startActivity(intent);
             ui.run();



    }
    private void shareFileWithExcel(Context context, Uri fileUri, String mimeType) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Show a chooser to let the user pick an app, filtering by the mime type
        Intent chooser = Intent.createChooser(intent, "Open file with");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        } else {
            Toast.makeText(context, "No application found to open this file type", Toast.LENGTH_SHORT).show();
        }
    }





}
