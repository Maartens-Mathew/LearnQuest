package com.example.learnquest.model.studyResource;

import android.util.Log;

import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Resources.AnalyzePDFActivity.OnReceivedListener;

import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatPDFClient {

    private final OkHttpClient httpClient;
    private final String apiKey;
    public String source_id;
    public String response;
    private OnReceivedListener listener;
    private Runnable waiting;
    private Runnable received;

    // Constructor to initialize the OkHttpClient and store the API key
    public ChatPDFClient(String apiKey, OnReceivedListener listener, Runnable waiting, Runnable received) {
        this.httpClient = new OkHttpClient();
        this.apiKey = apiKey;
        this.listener = listener;
        this.waiting = waiting;
        this.received = received;
    }

    // Method to upload a URL
    public void uploadUrl(String url) {
        waiting.run();
        String requestBody = String.format("{\"url\": \"%s\"}", url);

        Request request = new Request.Builder()
                .url("https://api.chatpdf.com/v1/sources/add-url")
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    saveSource(responseBody);
                } else {
                    System.err.println("Request failed: " + response);
                }

                received.run();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                received.run();
            }
        });
    }

    public void saveSource(String sourceID)  {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sourceID);
            this.source_id = jsonObject.getString("sourceId");
            listener.onReceived(
                    Message.fromAI("I have analyzed the PDF. What would you like to know?"));
        } catch (JSONException e) {
            Log.e("Custom","Could not parse input from JSON into string");
        }
    }

    // Method to send a message
    public void sendMessage(String messageContent) {
        waiting.run();
        String requestBody = String.format("{\"sourceId\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                this.source_id, messageContent);

        Request request = new Request.Builder()
                .url("https://api.chatpdf.com/v1/chats/message")
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response){
                if (response.isSuccessful() && response.body() != null) {
                    onReceiveMessage(response.body());
                } else {
                    System.err.println("Message request failed: " + response);
                }

                received.run();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onReceived(Message.fromAI("An error occurred while connecting to the database."));
                received.run();
            }
        });
    }


    public void onReceiveMessage(ResponseBody responseBody){
        if (responseBody == null){
            listener.onReceived(Message.fromAI("Error in processing message. Please try again. "));
            return;
        }

        JSONObject jsonObject = null;

        try {
            String json_response = responseBody.string();
            jsonObject = new JSONObject(json_response);
            String response = jsonObject.getString("content");
            Log.e("Custom", response);
            listener.onReceived(Message.fromAI(response));
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
