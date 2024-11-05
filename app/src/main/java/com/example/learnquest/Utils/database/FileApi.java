package com.example.learnquest.Utils.database;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import retrofit2.http.Part;

public interface FileApi {

    @PUT
    Call<ResponseBody> uploadFile(
            @Url String url, // Dynamic URL for storage path
            @Header("Content-Type") String contentType, // File MIME type
            @Part MultipartBody.Part file // The file part to upload
    );
}