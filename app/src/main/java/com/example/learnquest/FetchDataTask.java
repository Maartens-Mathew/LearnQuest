package com.example.learnquest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.learnquest.user.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FetchDataTask extends AsyncTask<Void,Void,List<Student>> {

    Connection connection;
    Context context;

    public FetchDataTask(Context context){
        this.context = context;
        try {
            connection = DatabaseHelper.getConnection(context);
        }catch(SQLException e){
            Log.e("Mathew error", "Could not get connection.");
        }
    }

    @Override
    protected void onPreExecute() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        super.onPreExecute();
    }

    @Override
    protected List<Student> doInBackground(Void... voids) {
        Log.i("The Asynx", "Started doInBackground");
        List<Student> users = new ArrayList<>();

        try {
            connection = DatabaseHelper.getConnection(context);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            while (resultSet.next()) {
                Student user = new Student();
                user.setFirst_Name(resultSet.getString("firstName"));
                user.setLast_Name(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }

            resultSet.close();
            statement.close();
           // connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.i("Task", "Finished tasks");
        return users;
    }

    @Override
    protected void onPostExecute(List<Student> users) {
        super.onPostExecute(users);
        // Handle the result (e.g., update UI)
        users.forEach( student -> Log.i("Database", student.getFirst_Name()));
    }
}
