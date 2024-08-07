package com.example.learnquest;

import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class SqlDatabase {
    private Connection connection = null;
    private final String dTag = "Database";
    @Override
    protected void finalize() throws Throwable {
        if (connection != null){
            connection.close();
        }
        super.finalize();
    }
    private static final String URL = "jdbc:mysql://project-sql-database-documentation4project.e.aivencloud.com:16222/defaultdb";
    private static final String USER ="avnadmin";
    private static final String PASS = "AVNS_4DQayfBvqZRs5GSSp6m";

    public SqlDatabase(){
        try{
            Log.i(dTag,Class.forName("com.mysql.jdbc.Driver").toString());
            connection = DriverManager.getConnection(URL,USER,PASS);
            Log.i(dTag,connection.toString());
            Log.i(dTag,"connected");
        }
        catch (Exception e){
            for(StackTraceElement element : e.getStackTrace()){
                Log.e(dTag,element.toString());
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            do{
                Driver d = drivers.nextElement();
                Log.i(dTag,d.getClass().toString());
            }while (drivers.hasMoreElements());
        }
    }


}
