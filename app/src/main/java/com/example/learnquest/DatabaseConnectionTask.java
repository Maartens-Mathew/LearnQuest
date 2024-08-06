package com.example.learnquest;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTask extends AsyncTask<Void,Void, Connection> {
    @Override
    protected Connection doInBackground(Void... voids) {
        Connection connection = null;
        try{
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    @Override
    protected void onPostExecute(Connection connection) {
        super.onPostExecute(connection);
        if (connection != null){
            try {
                Log.i("Database",Boolean.toString(connection.isValid(60)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            Log.e("Database","connection is null");
        }
        Log.i("Database","connected");
    }
}
