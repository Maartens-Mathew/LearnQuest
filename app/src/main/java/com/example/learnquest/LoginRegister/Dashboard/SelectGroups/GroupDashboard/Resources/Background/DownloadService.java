package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.Background;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {
    private DownloadManager downloadManager;
    private long downloadId;

    @Override
    public IBinder onBind(Intent intent) {
        return null; // We are not binding this service
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileUrl = intent.getStringExtra("fileUrl");
        String fileName = intent.getStringExtra("fileName");
        startDownload(fileUrl, fileName);
        return START_NOT_STICKY; // Service will not restart if killed
    }

    private void startDownload(String fileUrl, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setTitle("Downloading " + fileName);
        request.setDescription("Please wait...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request); // Start the download
        Log.i("Download ID", downloadId + " ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cleanup if necessary
    }
}
