package com.example.learnquest.AppState;


import android.content.Context;
import android.util.Log;

import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.user.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/** <h1>App function</h1>
 * <p>Literally just a static class to make holding the groupID and userID easier. Not meant
 * to be instantiated.</p>
 */
public class App {

    public static Integer userID;
    public static Integer groupID;
    public static Context applicationContext;

    public static User user;
    public static Group group;

    public static void setApplicationContext(Context context){
        applicationContext = context;
        Group.loadImages();
    }

    public static SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);


    public static void updateUser(){

        Thread thread = new Thread( () -> {
            Call<List<User>> getCall = api.getUserWithID(App.user.getUserID());

            Response<List<User>> getResponse = null;

            try {
                getResponse = getCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (getResponse.isSuccessful() && getResponse.body() != null)
                user = getResponse.body().get(0);
            else{
                try {
                    Log.e("Custom", getResponse.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}
