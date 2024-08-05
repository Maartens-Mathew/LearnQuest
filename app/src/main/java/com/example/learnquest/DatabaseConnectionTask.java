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
        Log.i("Database","connected");
    }
}
