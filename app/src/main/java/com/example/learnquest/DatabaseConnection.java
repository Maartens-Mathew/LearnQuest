package com.example.learnquest;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connection;
    private static String URL = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
    private static String Username = "postgres.hddpqiabofrxxyptexff";
    private static String password = "the-Le@rn-Quest1";
    private static String CONNECTION_STRING = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.hddpqiabofrxxyptexff&password=the-Le@rn-Quest1";


    public DatabaseConnection(){


        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }




        try {
            connection = DriverManager.getConnection(URL, Username,password);
            Log.i("Connection successful", "The database connection was successful.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
