package com.example.learnquest.AppState;


import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

/** <h1>App function</h1>
 * <p>Literally just a static class to make holding the groupID and userID easier. Not meant
 * to be instantiated.</p>
 */
public class App {

    public static short userID;
    public static short groupID;


    public static SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
}
