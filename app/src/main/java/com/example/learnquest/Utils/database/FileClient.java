package com.example.learnquest.Utils.database;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FileClient {

    static StorageReference bucket = FirebaseStorage.getInstance().getReference().child("LearnQuest");

    public static void uploadFile(File file, String fileName, OnSuccessListener<? super UploadTask.TaskSnapshot> successor, OnFailureListener failure){
        StorageReference child = bucket.child(fileName);
        child.putFile(Uri.fromFile(file)).addOnSuccessListener(successor).addOnFailureListener(failure);
    }

    public static void uploadFile(Uri uri, String fileName, OnSuccessListener<? super UploadTask.TaskSnapshot> successor, OnFailureListener failure){
        StorageReference child = bucket.child(fileName);
        child.putFile(uri).addOnSuccessListener(successor).addOnFailureListener(failure);
    }

    public static void downloadFile(String fileName, OnSuccessListener<? super Uri> successor, OnFailureListener failure){
        StorageReference child = bucket.child(fileName);
        child.getDownloadUrl().addOnSuccessListener(successor).addOnFailureListener(failure);
    }

    public static void deleteFile(String fileName){
        StorageReference child = bucket.child(fileName);
        child.delete();
    }

    public static void getUrl(String fileName, OnSuccessListener<? super Uri> successor, OnFailureListener failure){
        StorageReference child = bucket.child(fileName);
        Task<Uri> url = child.getDownloadUrl();
        url.addOnSuccessListener(successor).addOnFailureListener(failure);
    }


}
