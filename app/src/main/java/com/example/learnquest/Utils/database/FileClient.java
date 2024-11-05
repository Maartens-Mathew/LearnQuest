package com.example.learnquest.Utils.database;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.net.URI;

public class FileClient {

    private static final String SUPABASE_ENDPOINT = "https://hddpqiabofrxxyptexff.supabase.co/storage/v1/s3";
    private static final String ACCESS_KEY = "a5bf58719c484964a3278c5516dc878b";
    private static final String SECRET_KEY = "5ca553e347d9617ceaeb543d757861a89fe739c795aa9aa8e6999e26e6e9c243";
    private static final Region REGION = Region.EU_CENTRAL_1; // Use a region that S3 client accepts, does not matter for Supabase

    private final S3Client s3Client;

    public FileClient() {
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(SUPABASE_ENDPOINT))
                .region(REGION) // Supabase does not enforce region, so this is a formality
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .build();

    }

    public void uploadFile(String bucketName, String objectKey, File file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, file.toPath());
            System.out.println("File uploaded successfully. ETag: " + response.eTag());

        } catch (S3Exception e) {
            System.err.println("Upload failed: " + e.awsErrorDetails().errorMessage());
        }
    }


}
