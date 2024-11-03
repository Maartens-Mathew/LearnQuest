package com.example.learnquest.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.core.content.FileProvider;

import com.example.learnquest.model.studyResource.StudyResource;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileDownloaderTask extends AsyncTask<String, Void, File> {

    Context context;
    StudyResource studyResource;
    public FileDownloaderTask(Context context, StudyResource studyResource){
        this.context = context.getApplicationContext();
        this.studyResource = studyResource;
    }
    @Override
    protected File doInBackground(String... strings) {
        String fileUrl = strings[0];
        String fileName = strings[1];
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fileUrl).build();

        try (Response response = client.newCall(request).execute();
             InputStream inputStream = response.body().byteStream();
             OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(File file) {
        if (file != null) {

            Uri fileUri = FileProvider.getUriForFile(context, "com.example.learnquest.fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/" + studyResource.getFileType());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }
}
