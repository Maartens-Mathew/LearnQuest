package com.example.learnquest;

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;

public class Database {
    private Connection connection = null;
    private final String dTag = "Database";
    @Override
    protected void finalize() throws Throwable {
//        if (connection != null){
//            connection.close();
//        }
        super.finalize();
    }

    public Database(){
        new Thread(() -> {
            try{
                Log.i(dTag,Class.forName("com.mysql.jdbc.Driver").toString());
                connection = DriverManager.getConnection("jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222/defaultdb",
                        "avnadmin","AVNS_4DQayfBvqZRs5GSSp6m");
                Log.i(dTag,"connected");
            }
            catch (Exception e){
                for(StackTraceElement element : e.getStackTrace()){
                    Log.e(dTag,element.toString());
                }
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                do{
                    Driver d = drivers.nextElement();
                    Log.e(dTag,d.getClass().toString());
                }while (drivers.hasMoreElements());
            }
        }).start();
    }


}
