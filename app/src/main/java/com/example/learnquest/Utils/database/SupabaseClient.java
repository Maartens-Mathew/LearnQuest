package com.example.learnquest.Utils.database;

import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.Utils.database.Serializers.QuizEntryDeserializer;
import com.example.learnquest.Utils.database.Serializers.Tag_JSONConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * SupabaseClient class to create a Retrofit client
 * Essentially, this class is a singleton class that creates a Retrofit client
 * If one is already created, it will return the existing one
 */
public class SupabaseClient {
    private static Retrofit retrofit = null;
    private static String baseUrl = "https://hddpqiabofrxxyptexff.supabase.co"; //add your url
    private static String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhkZHBxaWFib2ZyeHh5cHRleGZmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjMwNTQwMDEsImV4cCI6MjAzODYzMDAwMX0.RRmQ6stCuLek1yzwLMB1GRnH9gPN9_FNkGwJCYTUcHU";
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("apikey", apiKey)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .build();
                return chain.proceed(request);
            }).build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(QuizEntry.class, new QuizEntryDeserializer())
                    .registerTypeAdapter(Tag.class, new Tag_JSONConverter())
                    .create();



            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl + "/rest/v1/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

        
    }
}
