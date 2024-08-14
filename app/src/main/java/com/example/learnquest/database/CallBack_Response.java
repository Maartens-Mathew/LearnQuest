package com.example.learnquest.database;

import android.widget.Toast;

import com.example.learnquest.Activities.RegisterStudent;
import com.example.learnquest.user.User;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallBack_Response<T> implements Callback<T> {


    Hashtable<DatabaseStates,Runnable> actions;

    public CallBack_Response(){
        actions = new Hashtable<>();
    }

    public void addState(DatabaseStates states, Runnable runnable){
        actions.put(states,runnable);

    }

    public Callback<T> getCallBack(){
        if (actions.keySet().size() < DatabaseStates.count())
         return null;
        else
            return this;
    }

    public void removeState(DatabaseStates state){
        actions.remove(state);
    }







    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            actions.get(DatabaseStates.ON_SUCCESSFUL).run();

        }
        else{
            //decide conditions
            actions.get(DatabaseStates.ON_FAILURE).run();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        actions.get(DatabaseStates.ON_NOT_CONNECT).run();
    }
}
