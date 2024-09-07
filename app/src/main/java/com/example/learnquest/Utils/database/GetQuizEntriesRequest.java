package com.example.learnquest.Utils.database;

public class GetQuizEntriesRequest {
    private short groupid_input;


    public GetQuizEntriesRequest(short category_id){
        this.groupid_input = category_id;
    }

    public short getCategory_id() {
        return groupid_input;
    }

    public void setCategory_id(short category_id) {
        this.groupid_input = category_id;
    }
}
