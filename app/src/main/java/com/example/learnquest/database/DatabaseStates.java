package com.example.learnquest.database;

public enum DatabaseStates {
    ON_SUCCESSFUL,
    ON_FAILURE,
    ON_NOT_CONNECT;

    public static int count(){
        return 3;
    }
}
