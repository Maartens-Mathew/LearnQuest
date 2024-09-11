package com.example.learnquest.QuizBank;

public class GetQuizEntriesRequest {
    private short groupid_input;

    //java short = supabase int2

    //btw, notice how the default value of a 'null' short is 0
    //but the default value of a 'null' Short(class), is null.

    //The nulls are ignored when the object is sent through to the database.

    //For example, if you are intending on inserting, but the userID (for example) was an unassigned int,
    //it will overwrite an existing userID = 0. Is this making any sense?yep i fet it



    // Constructor
    public GetQuizEntriesRequest(short groupid_input) {
        this.groupid_input = groupid_input;
    }

    // Getter and setter
    public int getGroupid_input() {
        return groupid_input;
    }

    public void setGroupid_input(short groupid_input) {
        this.groupid_input = groupid_input;
    }
}
