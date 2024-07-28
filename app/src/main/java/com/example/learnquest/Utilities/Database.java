package com.example.learnquest.Utilities;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Database {
    private Connection connection = null;
    private final String dTag = "Database";
    @Override
    protected void finalize() throws Throwable {
        if (connection != null){
            connection.close();
        }
        super.finalize();
    }

    public Database(){
        new Thread(() -> {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222/defaultdb",
                        "avnadmin","AVNS_4DQayfBvqZRs5GSSp6m");
                Log.i(dTag,"connected");
            }
            catch (Exception e){
                for(StackTraceElement element : e.getStackTrace()){
                    Log.e(dTag,element.toString());
                }
            }
        }).start();
    }


}
